package com.hbzb.tas.controller;

import com.alibaba.fastjson.JSONObject;
import com.hbzb.tas.dao.SectionRepo;
import com.hbzb.tas.dao.TenderFileRepo;
import com.hbzb.tas.dao.TenderSectionRepo;
import com.hbzb.tas.entity.*;
import com.hbzb.tas.model.Resp;
import com.hbzb.tas.service.UaaService;
import com.hbzb.tas.util.StringUtils;
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
import java.util.UUID;

/**
 * 招标文件接口
 * created by dusizhong at 2020.05.07
 */
@RestController
public class TenderFileController {

    @Value("${res.url}")
    String RES_URL;
    @Value("${res.path}")
    String RES_PATH;
    @Autowired
    UaaService uaaService;
    @Autowired
    SectionRepo sectionRepo;
    @Autowired
    TenderFileRepo tenderFileRepo;

    @RequestMapping("/tender/file/create")
    public Resp createTenderFile(@RequestParam(defaultValue = "") String sectionUid,
                                 @RequestParam(defaultValue = "") String price,
                                 @RequestParam(defaultValue = "") String startSellTime,
                                 @RequestParam(defaultValue = "") String endSellTime,
                                 @RequestParam(defaultValue = "") String obtainMethod) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        //if(!authorities.contains("ROLE_AGENCY_ADMIN")) return new Resp("400", "权限不足");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if(StringUtils.isEmpty(price)) return new Resp("400", "文件售价不能为空");
        if(!StringUtils.isAmount(price)) return new Resp("400", "文件售价格式无效");
        if(StringUtils.isEmpty(startSellTime)) return new Resp("400", "发售时间不能为空");
        if(StringUtils.isEmpty(endSellTime)) return new Resp("400", "发售截止时间不能为空");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if(jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if(!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        String creatorName = user.getString("username");
        if(StringUtils.isEmpty(creatorUid)) return new Resp("400", "UAA: 获取用户uid失败");
        // 检查标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if(section == null) return new Resp("400", "无此标段");
        if(!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中，不能操作");
        if(!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        // 保存记录
        TenderFile tenderFile = tenderFileRepo.findBySectionUid(sectionUid.trim());
        if(tenderFile == null) {
            tenderFile = new TenderFile();
            tenderFile.setSectionUid(sectionUid);
            tenderFile.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        }
        tenderFile.setPrice(price.trim());
        tenderFile.setStartSellTime(startSellTime.trim());
        tenderFile.setEndSellTime(endSellTime.trim());
        tenderFile.setObtainMethod(obtainMethod.trim());
        tenderFile.setCreatorUid(creatorUid);
        tenderFile.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tenderFile = tenderFileRepo.saveAndFlush(tenderFile);
        return new Resp("200", "操作成功", tenderFile);
    }


    @PostMapping(value = "/tender/file/upload")
    public Resp uploadTenderFile(@RequestParam(defaultValue = "") String sectionUid,
                                 @RequestParam("file") MultipartFile file) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if (!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if (StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        if (file.isEmpty()) return new Resp("400", "文件不能为空");
        if (file.getSize() > 1024*1024*50) return new Resp("400", "文件不能超过50M");
        // 准备文件名
        String originName = file.getOriginalFilename();
        System.out.println(originName);
        String fileName = UUID.randomUUID().toString();
        if (file.getOriginalFilename().toLowerCase().endsWith(".pdf")) fileName = fileName + ".pdf";
        else return new Resp("400", "上传文件仅支持pdf格式");
        // 获取用户
        JSONObject jsonUser = uaaService.requestUserDetail();
        if (jsonUser == null) return new Resp("400", "UAA: 获取用户信息失败");
        if (!"200".equals(jsonUser.get("code"))) return new Resp("400", "UAA: " + jsonUser.getString("message"));
        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
        String creatorUid = user.getString("uid");
        if (StringUtils.isEmpty(creatorUid)) return new Resp("400", "获取用户uid失败");
        // 检查标段
        Section section = sectionRepo.findByUid(sectionUid.trim());
        if (section == null) return new Resp("400", "无此标段");
        if (!"EDIT".equals(section.getStatus())) return new Resp("400", "标段状态不在编辑中，不能操作");
        if (!creatorUid.equals(section.getCreatorUid())) return new Resp("400", "此标段非你创建，无权操作");
        // 检查目录
        String folder = RES_PATH + "/project/" + section.getProjectUid() + "/";
        File targetFile = new File(folder);
        if (!targetFile.exists()) targetFile.mkdirs();
        // 检查记录
        TenderFile tenderFile = tenderFileRepo.findBySectionUid(sectionUid.trim());
        if(tenderFile == null) {
            tenderFile = new TenderFile();
            tenderFile.setSectionUid(sectionUid);
            tenderFile.setCreatorUid(creatorUid);
            tenderFile.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        } else {
            // 删除旧文件
            if (!StringUtils.isEmpty(tenderFile.getOriginFile())) {
                File oldFile = new File(folder + tenderFile.getOriginFile());
                if (oldFile.exists() && oldFile.isFile()) oldFile.delete();
            }
            if (!StringUtils.isEmpty(tenderFile.getEncryptFile())) {
                File oldFile = new File(folder + tenderFile.getEncryptFile());
                if (oldFile.exists() && oldFile.isFile()) oldFile.delete();
            }
            tenderFile.setEncryptFile("");
        }
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
        tenderFile.setName(originName);
        tenderFile.setOriginFile(fileName);
        tenderFile.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        tenderFile = tenderFileRepo.save(tenderFile);
        return new Resp("200", "上传成功", tenderFile);
    }


    @RequestMapping("/tender/file/detail")
    public Resp getTenderFileDetail(@RequestParam(defaultValue = "") String sectionUid) {

        // 检查权限
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String authorities = auth.getAuthorities().toString();
        if(!authorities.contains("ROLE_AGENCY")) return new Resp("400", "角色不符");
        // 检查参数
        if(StringUtils.isEmpty(sectionUid)) return new Resp("400", "标段uid不能为空");
        // 获取招标文件信息
        TenderFile tenderFile = tenderFileRepo.findBySectionUid(sectionUid.trim());
        if(tenderFile == null) return new Resp("400", "招标文件信息不存在");
        return new Resp("200", "获取成功", tenderFile);
    }
}
