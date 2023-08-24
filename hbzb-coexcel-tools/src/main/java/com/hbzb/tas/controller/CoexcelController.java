package com.hbzb.tas.controller;

import com.hbzb.tas.model.Resp;
import com.hbzb.tas.util.ExcelUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

/**
 * Coexcel
 * @author dusizhong
 * @since 2020-08-28
 */
@RestController
public class CoexcelController {

    @RequestMapping("/excel")
    public Resp upload(@RequestParam(defaultValue = "") String uid) {

        // check params
//        if(StringUtils.isEmpty(uid)) return new Resp("400", "uid不能为空");
//        if(file.isEmpty()) return new Resp("400", "文件不能为空");
//        if(file.getSize() > 5120000) return new Resp("400", "文件不能超过50M");

        return new Resp("200", "上传成功", ExcelUtils.readTargetFile("d:\\电气1.xlsx"));
    }
}
