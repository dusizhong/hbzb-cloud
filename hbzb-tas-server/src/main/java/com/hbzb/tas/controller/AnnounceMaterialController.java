package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.AnnounceMaterialRepo;
import com.hbzb.tas.dao.AnnounceRepo;
import com.hbzb.tas.entity.Announce;
import com.hbzb.tas.entity.AnnounceMaterial;
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
 * announce material controller
 * created by dusizhong at 2020.02.05
 */
//@Api(tags = "7.公告材料接口", description = "公告附件材料接口服务。")
@RestController
public class AnnounceMaterialController {

    @Value("${uaa.server}")
    String UAA_SERVER;
    @Value("${res.url}")
    String RES_URL;
    @Value("${res.path}")
    String RES_PATH;
    @Autowired
    RestService restService;
    @Autowired
    AnnounceRepo announceRepo;
    @Autowired
    AnnounceMaterialRepo announceMaterialRepo;

    /**
     * 1.上传公告材料
     * @param announceUid
     * @param name
     * @param file
     * @return
     */
    @ApiOperation(value = "上传公告材料", notes="上传公告相关材料。",
            httpMethod = "POST", response = AnnounceMaterial.class, position = 1)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceUid", value = "所属公告uid", required = true),
            @ApiImplicitParam(name = "name", value = "材料名称", required = true),
            @ApiImplicitParam(name = "file", value = "电子件", allowMultiple = true, required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "上传成功"),
            @ApiResponse(code=400, message = "参数不能为空|参数格式无效|上传文件仅支持jpg、png、pdf格式|无此公告|状态不符|上传失败")})
    @PostMapping(value = "/announce/material/upload")
    public Resp uploadAnnounceMaterial(@RequestParam(defaultValue = "") String announceUid,
                                       @RequestParam("file") MultipartFile file) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(announceUid)) return new Resp("400", "公告uid不能为空");
        if(file.isEmpty()) return new Resp("400", "文件不能为空");
        if(file.getSize() > 5120000) return new Resp("400", "文件不能超过50M");
        // get user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp("400", "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        // check announce
        Announce announce = announceRepo.findByUid(announceUid);
        if(announce == null) return new Resp("400", "无此公告");
        if(!"EDIT".equals(announce.getStatus())) return new Resp("400", "公告状态不符");
        if(!announce.getCreatorUid().equals(user.get("uid"))) return new Resp("400", "无权修改他人公告");
        // check and create file name
        String fileName = UUID.randomUUID().toString().replace("-", "");
        if(file.getOriginalFilename().toLowerCase().endsWith(".jpg")) fileName = fileName + ".jpg";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".png")) fileName = fileName + ".png";
        else if(file.getOriginalFilename().toLowerCase().endsWith(".pdf")) fileName = fileName + ".pdf";
        else return new Resp("400", "上传文件仅支持jpg、png、pdf格式");
        // check and create folder
        String folder = RES_PATH + "/announce/" + announce.getUid() + "/";
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
            return new Resp("400", "上传时获取文件失败");
        } catch (IOException e) {
            return new Resp("400", "上传失败");
        }
        // create announce material
        AnnounceMaterial announceMaterial = new AnnounceMaterial();
        announceMaterial.setAnnounceUid(announceUid);
        announceMaterial.setName(file.getOriginalFilename());
        announceMaterial.setUrl(fileName);
        announceMaterial.setCreatorUid(user.getString("uid"));
        announceMaterial.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        announceMaterial.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        announceMaterialRepo.save(announceMaterial);
        return new Resp("200", "上传成功", RES_URL + "/announce/" + announce.getUid() + "/" + fileName);
    }

    /**
     * 2.获取公告材料列表
     * @param announceUid
     * @return
     */
    @ApiOperation(value = "获取公告材料列表", notes="获取公告材料列表。",
            httpMethod = "GET", response = Resp.class, position = 2)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceUid", value = "公告uid", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "获取公告材料列表"),
            @ApiResponse(code=400, message = "角色不符|无此公告")})
    @RequestMapping(value = "/announce/material/list")
    public Resp getAnnounceMaterialList(@RequestParam(defaultValue = "") String announceUid) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // check params
        if(StringUtils.isEmpty(announceUid)) return new Resp("400", "公告uid不能为空");
        // check announce
        Announce announce = announceRepo.findByUid(announceUid);
        if(announce == null) return new Resp("400", "无此公告");
        // get material list
        List<AnnounceMaterial> materials = announceMaterialRepo.findByAnnounceUid(announceUid);
        materials.forEach(material -> material.setUrl(RES_URL + "/announce/" + announce.getUid() + "/" + material.getUrl()));
        return new Resp("200", "获取成功", materials);
    }

    /**
     * 3.删除材料
     * @param announceMaterialId
     * @return
     */
    @ApiOperation(value = "删除材料", notes="用户删除已上传的材料文件。",
            httpMethod = "POST", response = Resp.class, position = 3)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "announceMaterialId", value = "公告材料id", required = true)
    })
    @ApiResponses({
            @ApiResponse(code=200, message = "删除成功"),
            @ApiResponse(code=400, message = "参数不能为空|参数格式无效|材料不存在|无此公告|公告状态不符")})
    @RequestMapping(value = "/announce/material/del")
    public Resp delAnnounceMaterial(@RequestParam(defaultValue = "") String announceMaterialId) {

        // check authorities
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");

        // check params
        if(StringUtils.isEmpty(announceMaterialId)) return new Resp("400", "材料id不能为空");
        if(!StringUtils.isInt(announceMaterialId)) return new Resp("400", "材料id无效");
        // check material
        AnnounceMaterial announceMaterial = announceMaterialRepo.findOne(Integer.valueOf(announceMaterialId));
        if(announceMaterial == null) return new Resp("400", "无此材料");
        // check announce
        Announce announce = announceRepo.findByUid(announceMaterial.getAnnounceUid());
        if(announce == null) return new Resp("400", "材料所属公告不存在");
        if(!announce.getStatus().equals("EDIT")) return new Resp("400", "公告状态不符");
        // check user
        JSONObject jsonUser = restService.get(UAA_SERVER + "/user/detail");
        if(jsonUser == null) return new Resp("400", "获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        if(!announceMaterial.getCreatorUid().equals(user.get("uid"))) return new Resp("400", "无权删除他人材料");
        // del file
        String folder = RES_PATH + "/announce/" + announce.getUid() + "/";
        if(!StringUtils.isEmpty(announceMaterial.getUrl())) {
            File oldFile = new File(folder + announceMaterial.getUrl());
            if (oldFile.exists() && oldFile.isFile()) oldFile.delete();
        }
        announceMaterialRepo.delete(Integer.valueOf(announceMaterialId));
        return new Resp("200", "删除成功");
    }
}
