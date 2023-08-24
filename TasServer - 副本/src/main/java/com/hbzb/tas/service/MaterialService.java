package com.hbzb.tas.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * common material service
 * created by dusizhong at 2020.02.20
 */
@Service
public class MaterialService {

//    private Logger logger = LoggerFactory.getLogger(this.getClass());

//    @Value("${res.url}")
//    String RES_URL;
//    @Value("${res.path}")
//    String RES_PATH;
//    @Autowired
//    UaaService uaaService;
//    @Autowired
////    MaterialRepo materialRepo;
//
//    public String uploadMaterial(String type, String typeUid, String name, MultipartFile file) {
//
//        String result = "success";
//
////        // check params
////        if(StringUtils.isEmpty(type)) result = "附件类别不能为空";
////        if(StringUtils.isEmpty(typeUid)) result = "所属项uid不能为空";
////        if(StringUtils.isEmpty(name)) result = "电子件名称不能为空";
////        if(file.isEmpty()) result = "文件不能为空";
////        if(file.getSize() > 51200000) result = "文件不能超过50M";
////        // request user
////        JSONObject jsonUser = uaaService.requestUserDetail();
////        if(jsonUser == null) result = "UAA: 获取用户信息失败";
////        if(!"200".equals(jsonUser.get("code"))) result = "UAA: " + jsonUser.getString("message");
////        JSONObject user = (JSONObject) JSONObject.toJSON(jsonUser.get("data"));
////        String creatorUid = user.getString("uid");
////        String creatorName = user.getString("username");
////        if(StringUtils.isEmpty(creatorUid)) result = "UAA: 获取用户uid失败";
////        if(StringUtils.isEmpty(creatorName)) result = "UAA: 获取用户名失败";
////        // check and create file name
////        String fileName = UUID.randomUUID().toString().replace("-", "");
////        if(file.getOriginalFilename().toLowerCase().endsWith(".jpg")) fileName = fileName + ".jpg";
////        else if(file.getOriginalFilename().toLowerCase().endsWith(".png")) fileName = fileName + ".png";
////        else if(file.getOriginalFilename().toLowerCase().endsWith(".pdf")) fileName = fileName + ".pdf";
////        else result = "上传文件仅支持jpg、png、pdf格式";
////        // check and create folder
////        String folder = RES_PATH + "/project/" + typeUid + "/";
////        File targetFile = new File(folder);
////        if (!targetFile.exists()) targetFile.mkdirs();
////        //upload new file
////        FileOutputStream out;
////        try {
////            out = new FileOutputStream(folder + fileName);
////            out.write(file.getBytes());
////            out.flush();
////            out.close();
////        } catch (FileNotFoundException e) {
////            e.printStackTrace();
////            result = "上传时获取文件失败";
////        } catch (IOException e) {
////            result = "上传失败";
////        }
////        // create material
////        Material material = new Material();
////        material.setItem(type);
////        material.setItemUid(typeUid);
////        material.setName(name.trim());
////        material.setSize(file.getSize()/1000 + "");
////        material.setUrl(fileName);
////        material.setCreatorUid(creatorUid);
////        material.setCreatorName(creatorName);
////        material.setUpdateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
////        material.setCreateTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
//////        materialRepo.save(material);
////        //return new Resp("200", "上传成功", RES_URL + "/material/" + typeUid + "/" + fileName);
//
//        return result;
//    }
}
