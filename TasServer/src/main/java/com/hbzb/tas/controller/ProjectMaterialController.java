package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.ProjectMaterialRepo;
import com.hbzb.tas.dao.ProjectRepo;
import com.hbzb.tas.entity.Project;
import com.hbzb.tas.entity.ProjectMaterial;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.RestService;
import com.hbzb.tas.util.StringUtils;
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
//@Api(tags = "1.2 项目材料接口", description = "上传、删除项目相关材料")
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
        if (!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if (StringUtils.isEmpty(projectUid)) return new Resp("400", "项目uid不能为空");
        if (StringUtils.isEmpty(name)) return new Resp("400", "材料名称不能为空");
        if (file.isEmpty()) return new Resp("400", "文件不能为空");
        if(file.getSize() > 1024*1024*5) return new Resp("400", "文件不能超过5M");
        //create material name
        String fileName = UUID.randomUUID().toString();
        if(file.getOriginalFilename().toLowerCase().endsWith(".jpg")) fileName = fileName + ".jpg";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".jpeg")) fileName = fileName + ".jpeg";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".png")) fileName = fileName + ".png";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".pdf")) fileName = fileName + ".pdf";
        else return new Resp("400", "上传文件仅支持jpg、png、pdf格式");
        // check project
        Project project = projectRepo.findByUid(projectUid.trim());
        if (project == null) return new Resp("400", "无此项目");
        if (!"EDIT".equals(project.getStatus())) return new Resp("400", "项目状态不在编辑中，不能操作");
        // request user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if (jsonUser == null) return new Resp("400", "获取用户信息失败");
        if (!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        if (!project.getCreatorUid().equals(user.get("uid"))) return new Resp("400", "此项目非你创建，无权操作");
        // check and create folder
        String folder = RES_PATH + "/project/" + project.getUid() + "/";
        File targetFile = new File(folder);
        if (!targetFile.exists()) targetFile.mkdirs();
        //upload file
        FileOutputStream out;
        try {
            out = new FileOutputStream(folder + fileName);
            out.write(file.getBytes());
            out.flush();
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new Resp("400", "上传时获取文件失败");
        } catch (IOException e) {
            return new Resp("400", "上传失败");
        }
        // create or update material
        ProjectMaterial projectMaterial = projectMaterialRepo.findByProjectUidAndName(projectUid.trim(), name.trim());
        if(projectMaterial != null) {
            if(!StringUtils.isEmpty(projectMaterial.getUrl())) {
                File oldFile = new File(folder + projectMaterial.getUrl());
                if (oldFile.exists() && oldFile.isFile()) oldFile.delete();
            }
            projectMaterial.setUrl(fileName);
            projectMaterial.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            projectMaterial = new ProjectMaterial();
            projectMaterial.setProjectUid(projectUid.trim());
            projectMaterial.setName(name.trim());
            projectMaterial.setUrl(fileName);
            projectMaterial.setCreatorUid(user.getString("uid"));
            projectMaterial.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
            projectMaterial.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        projectMaterialRepo.save(projectMaterial);
        projectMaterial.setUrl(RES_URL + "/project/" + project.getUid() + "/" + fileName);
        return new Resp("200", "上传成功", projectMaterial);
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
            @ApiResponse(code=400, message = "角色不符|无此项目")})
    @RequestMapping(value = "/project/material/list")
    public Resp getProjectMaterialList(@RequestParam(defaultValue = "") String projectUid) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if (!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if (StringUtils.isEmpty(projectUid)) return new Resp("400", "项目uid不能为空");
        // check project
        Project project = projectRepo.findByUid(projectUid.trim());
        if (project == null) return new Resp("400", "无此项目");
        // get material list
        List<ProjectMaterial> materials = projectMaterialRepo.findByProjectUid(projectUid.trim());
        materials.forEach(material -> material.setUrl(RES_URL + "/project/" + project.getUid() + "/" + material.getUrl()));
        return new Resp("200", "获取成功", materials);
    }

    /**
     * 3.删除材料
     * @param projectUid
     * @param name
     * @return
     */
    @ApiOperation(value = "删除材料", notes="删除已上传的材料。",
            httpMethod = "POST", response = Resp.class, position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "projectUid", value = "项目uid", required = true),
            @ApiImplicitParam(name = "name", value = "材料名称", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "删除成功"),
            @ApiResponse(code=400, message = "参数不能为空|参数格式无效|材料不存在|无此项目|项目状态不符")})
    @RequestMapping(value = "/project/material/del")
    public Resp delProjectMaterial(@RequestParam(defaultValue = "") String projectUid,
                                   @RequestParam(defaultValue = "") String name) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if (!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if (StringUtils.isEmpty(projectUid)) return new Resp("400", "项目uid不能为空");
        if (StringUtils.isEmpty(name)) return new Resp("400", "材料名称不能为空");
        // check material
        ProjectMaterial projectMaterial = projectMaterialRepo.findByProjectUidAndName(projectUid.trim(), name.trim());
        if (projectMaterial == null) return new Resp("400", "材料不存在");
        // check project
        Project project = projectRepo.findByUid(projectUid.trim());
        if (project == null) return new Resp("400", "项目不存在");
        if (!"EDIT".equals(project.getStatus())) return new Resp("400", "项目状态不在编辑中，不能删除");
        // request user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if (jsonUser == null) return new Resp("400", "获取用户信息失败");
        if (!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        if (!projectMaterial.getCreatorUid().equals(user.get("uid"))) return new Resp("400", "此项目非你创建，无权删除");
        // del file
        String folder = RES_PATH + "/project/" + project.getUid() + "/";
        if (!StringUtils.isEmpty(projectMaterial.getUrl())) {
            File oldFile = new File(folder + projectMaterial.getUrl());
            if (oldFile.exists() && oldFile.isFile()) oldFile.delete();
        }
        projectMaterialRepo.delete(projectMaterial);
        return new Resp("200", "删除成功");
    }
}
