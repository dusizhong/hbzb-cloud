package com.hbzb.cloud.tds.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.cloud.tds.dao.ProjectMaterialRepo;
import com.hbzb.cloud.tds.dao.ProjectRepo;
import com.hbzb.cloud.tds.entity.Project;
import com.hbzb.cloud.tds.entity.ProjectMaterial;
import com.hbzb.cloud.tds.model.Resp;
import com.hbzb.cloud.tds.service.RestService;
import com.hbzb.cloud.tds.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 项目材料接口
 * created by dusizhong at 2020.01.21
 */
@Api(tags = "2.项目材料接口", description = "上传、删除项目相关材料")
@RestController
public class ProjectMaterialController {

    @Value("${uaa.server}")
    String UAA_SERVER;
    @Value("${res.url}")
    String RES_URL;
    @Value("${res.path}")
    String RES_PATH;
    @Autowired
    RestService restService;
    @Autowired
    ProjectRepo projectRepo;
    @Autowired
    ProjectMaterialRepo projectMaterialRepo;

    /**
     * 1.上传项目材料
     * @param projectUid
     * @param name
     * @param file
     * @return
     */
    @ApiOperation(value = "上传项目材料", notes="上传项目相关材料。",
            httpMethod = "POST", response = ProjectMaterial.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectUid", value = "项目uid", required = true),
            @ApiImplicitParam(name = "name", value = "材料名称", required = true),
            @ApiImplicitParam(name = "file", value = "电子件", allowMultiple = true, required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "上传成功"),
            @ApiResponse(code=400, message = "参数不能为空|参数格式无效|上传文件仅支持jpg、png、pdf格式|无此项目|项目状态不符|上传失败")})
    @PostMapping(value = "/project/material/upload")
    public Resp uploadProjectMaterial(@RequestParam(defaultValue = "") String projectUid,
                                      @RequestParam(defaultValue = "") String name,
                                      @RequestParam("file") MultipartFile file) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check params
        if(StringUtils.isEmpty(projectUid)) return new Resp(400, "项目uid不能为空");
        if(StringUtils.isEmpty(name)) return new Resp(400, "材料名称不能为空");
        if(file.isEmpty()) return new Resp(400, "文件不能为空");
        if(file.getSize() > 512000) return new Resp(400, "文件不能超过5M");
        // get user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        // check project
        Project project = projectRepo.findByUid(projectUid);
        if(project == null) return new Resp(400, "无此项目");
        if(!project.getStatus().equals("EDIT")) return new Resp(400, "项目状态不符");
        if(!project.getCreatorUid().equals(user.get("uid"))) return new Resp(400, "无权修改他人项目");
        // check and create file name
        String fileName = UUID.randomUUID().toString().replace("-", "");
        if(file.getOriginalFilename().toLowerCase().endsWith(".jpg")) fileName = fileName + ".jpg";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".png")) fileName = fileName + ".png";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".pdf")) fileName = fileName + ".pdf";
        else return new Resp(400, "上传文件仅支持jpg、png、pdf格式");
        // check and create folder
        String folder = RES_PATH + "/project/" + project.getUid() + "/";
        File targetFile = new File(folder);
        if (!targetFile.exists()) targetFile.mkdirs();
        //upload new file
        FileOutputStream out;
        try {
            out = new FileOutputStream(folder + fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Resp(400, "上传时获取文件失败");
        } catch (IOException e) {
            return new Resp(400, "上传失败");
        }
        // create project material
        ProjectMaterial projectMaterial = new ProjectMaterial();
        projectMaterial.setProjectUid(projectUid);
        projectMaterial.setName(name.trim());
        projectMaterial.setUrl(fileName);
        projectMaterial.setCreatorUid(user.getString("uid"));
        projectMaterial.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        projectMaterial.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        projectMaterialRepo.save(projectMaterial);
        return new Resp(200, "上传成功", RES_URL + "/project/" + project.getUid() + "/" + fileName);
    }

    /**
     * 2.获取项目材料列表
     * @param projectUid
     * @return
     */
    @ApiOperation(value = "获取项目材料列表", notes="获取项目材料列表。",
            httpMethod = "GET", response = Resp.class, position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectUid", value = "项目uid", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "获取项目材料列表"),
            @ApiResponse(code=400, message = "角色不符|无此项目|项目状态不符")})
    @RequestMapping(value = "/project/material/list")
    public Resp getProjectMaterialList(@RequestParam(defaultValue = "") String projectUid) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");
        // check params
        if(StringUtils.isEmpty(projectUid)) return new Resp(400, "项目uid不能为空");
        // check project
        Project project = projectRepo.findByUid(projectUid);
        if(project == null) return new Resp(400, "无此项目");
        // get material list
        List<ProjectMaterial> materials = projectMaterialRepo.findByProjectUid(projectUid);
        materials.forEach(material -> material.setUrl(RES_URL + "/project/" + project.getUid() + "/" + material.getUrl()));
        return new Resp(200, "获取成功", materials);
    }

    /**
     * 3.删除材料
     * @param projectMaterialId
     * @return
     */
    @ApiOperation(value = "删除材料", notes="用户删除已上传的材料文件。",
            httpMethod = "POST", response = Resp.class, position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectMaterialId", value = "项目材料id", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "删除成功"),
            @ApiResponse(code=400, message = "参数不能为空|参数格式无效|材料不存在|无此项目|项目状态不符")})
    @RequestMapping(value = "/project/material/del")
    public Resp delProjectMaterial(@RequestParam(defaultValue = "") String projectMaterialId) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp(400, "角色不符");

        // check params
        if(StringUtils.isEmpty(projectMaterialId)) return new Resp(400, "材料id不能为空");
        if(!StringUtils.isInt(projectMaterialId)) return new Resp(400, "材料id无效");
        // check material
        ProjectMaterial projectMaterial = projectMaterialRepo.findOne(Integer.valueOf(projectMaterialId));
        if(projectMaterial == null) return new Resp(400, "无此材料");
        // check project
        Project project = projectRepo.findByUid(projectMaterial.getProjectUid());
        if(project == null) return new Resp(400, "材料所属项目不存在");
        if(!project.getStatus().equals("EDIT")) return new Resp(400, "项目状态不符");
        // check user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp(400, "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp(400, "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        if(!projectMaterial.getCreatorUid().equals(user.get("uid"))) return new Resp(400, "无权删除他人项目材料");
        // del file
        String folder = RES_PATH + "/project/" + project.getUid() + "/";
        if(!StringUtils.isEmpty(projectMaterial.getUrl())) {
            File oldFile = new File(folder + projectMaterial.getUrl());
            if (oldFile.exists() && oldFile.isFile()) oldFile.delete();
        }
        projectMaterialRepo.delete(Integer.valueOf(projectMaterialId));
        return new Resp(200, "删除成功");
    }
}
