package com.hbzb.tas.controller;


import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.MaterialRepo;
import com.hbzb.tas.entity.Material;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.UaaService;
import com.hbzb.tas.util.StringUtils;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * 电子件接口
 */
@Api(tags = "电子件接口", description = "电子件公用接口。")
@RestController
public class MaterialController {

    @Value("${res.url}")
    String RES_URL;
    @Value("${res.path}")
    String RES_PATH;
    @Autowired
    UaaService uaaService;
    @Autowired
    MaterialRepo materialRepo;



    @ApiOperation(value = "上传材料", notes="上传材料",
            httpMethod = "POST", response = Material.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "belong", value = "所属项", required = true),
            @ApiImplicitParam(name = "belongUid", value = "所属项uid", required = true),
            @ApiImplicitParam(name = "name", value = "材料名称", required = true),
            @ApiImplicitParam(name = "file", value = "文件", allowMultiple = true, required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "上传成功"),
            @ApiResponse(code=400, message = "参数不能为空|上传失败")})
    @PostMapping(value = "/material/upload")
    public Resp uploadMaterial(@RequestParam(defaultValue = "") String belong,
                               @RequestParam(defaultValue = "") String belongUid,
                               @RequestParam(defaultValue = "") String name,
                               @RequestParam("file") MultipartFile file) {

        // 检测权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if (!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检测参数
        if (StringUtils.isEmpty(belong)) return new Resp("400", "所属项不能为空");
        if (StringUtils.isEmpty(belongUid)) return new Resp("400", "所属项uid不能为空");
        if (StringUtils.isEmpty(name)) return new Resp("400", "材料名称不能为空");
        if (file.isEmpty()) return new Resp("400", "文件不能为空");
        if (file.getSize() > 1024*1024*50) return new Resp("400", "文件不能超过50M");
        // 检测文件
        String originName = file.getOriginalFilename();
        String fileName = UUID.randomUUID().toString();
        if (file.getOriginalFilename().toLowerCase().endsWith(".jpg")) fileName = fileName + ".jpg";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".jpeg")) fileName = fileName + ".jpeg";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".png")) fileName = fileName + ".png";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".pdf")) fileName = fileName + ".pdf";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".doc")) fileName = fileName + ".doc";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".docx")) fileName = fileName + ".docx";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".xls")) fileName = fileName + ".xls";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".xlsx")) fileName = fileName + ".xlsx";
        else return new Resp("400", "上传文件仅支持word、excel、jpg、png、pdf格式");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        if(StringUtils.isEmpty(creatorName)) return new Resp("400", "UAA: 获取用户姓名失败");
        // 检测文件目录
        String folder = RES_PATH + "/project/" + 123 + "/";
        File targetFile = new File(folder);
        if (!targetFile.exists()) targetFile.mkdirs();
        // 上传文件
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
        // 保存记录
        Material material = new Material();
        material.setBelong(belong.trim());
        material.setBelongUid(belongUid.trim());
        material.setName(name.trim());
        material.setOriginName(originName);
        material.setFileName(fileName);
        material.setCreatorUid(creatorUid);
        material.setCreatorName(creatorName);
        material.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        material.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        materialRepo.save(material);
        material.setFileName(RES_URL + "/project/" + 123 + "/" + fileName);
        return new Resp("200", "上传成功", material);
    }


//    @ApiOperation(value = "获取材料列表", notes="获取材料列表。",
//            httpMethod = "GET", response = Resp.class, position = 2)
//    @ApiImplicitParams({
//            @ApiImplicitParam(name = "belong", value = "所属项", required = true),
//            @ApiImplicitParam(name = "belongUid", value = "所属项uid", required = true)
//    })
//    @ApiResponses({
//            @ApiResponse(code=200, message = "获取材料列表"),
//            @ApiResponse(code=400, message = "角色不符")})
//    @RequestMapping(value = "/material/list")
//    public Resp getMaterialList(@RequestParam(defaultValue = "") String belong,
//                                @RequestParam(defaultValue = "") String belongUid) {
//        // 检查权限
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String authorities = auth.getAuthorities().toString();
//        // 检查参数
//        if (!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
//        if (StringUtils.isEmpty(belong)) return new Resp("400", "所属项不能为空");
//        if (StringUtils.isEmpty(belongUid)) return new Resp("400", "所属项uid不能为空");
//        // 获取代理信息
//        JSONObject jsonCorp = uaaService.requestCorpDetail();
//        if(jsonCorp == null) return new Resp("400", "UAA: 获取招标代理信息失败");
//        if(!"200".equals(jsonCorp.get("code"))) return new Resp("400", "UAA: " + jsonCorp.getString("message"));
//        JSONObject agency = (JSONObject) JSONObject.toJSON(jsonCorp.get("data"));
//        String agencyUid = agency.getString("uid");
//        if(StringUtils.isEmpty(agencyUid)) return new Resp("400", "UAA: 获取招标代理uid失败");
//        String agencyName = agency.getString("name");
//        // 检查所属项
//        String projectUid = "";
//        if("PROJECT".equals(belong.trim())) {
//            Project project = projectRepo.findByUid(belongUid.trim());
//            if(project == null) return new Resp("400", "所属项Uid无效");
//            if(!"EDIT".equals(project)) return new Resp("400", "所属项状态不在编辑中，不能操作");
//            if(!creatorUid.equals(project.getCreatorUid())) return new Resp("400", "无权操作他人信息");
//            projectUid = project.getUid();
//        } else if("ANNOUNCE".equals(belong.trim())) {
//            //projectUid = announce.getProjectUid();
//        } else return new Resp("400", "所属项无效");
//        List<ProjectMaterial> materials = materialRepo.findByBelongUid(projectUid.trim());
//        materials.forEach(material -> material.setUrl(RES_URL + "/project/" + project.getUid() + "/" + material.getUrl()));
//        return new Resp("200", "获取成功", materials);
//    }

}
