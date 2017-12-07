package com.example.Demo.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hz on 12/7/17.
 */
@RestController
@RequestMapping("/test")
public class ExcelController {

    // 允许上传的格式
    private static final String[] IMAGE_TYPE = new String[]{".xlsx", ".xls"};

    @RequestMapping(method = RequestMethod.POST)
    public String imports(@RequestParam("uploadFiles") MultipartFile uploadFiles) {
        String origName = uploadFiles.getOriginalFilename();
        // 校验格式
        boolean isLegal = false;
        for (String type : IMAGE_TYPE) {
            if (StringUtils.endsWithIgnoreCase(origName, type)) {
                isLegal = true;
                break;
            }
        }
        if (!isLegal) {
            return "文件格式不正确";
        }
        InputStream inputStream = null;
        try {
            inputStream = uploadFiles.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        String keyName = "name,phoneNumber,address,goodsName,num";
        String keyType = "string,string,string,string,string";
        String nullable = "false,false,false,false,false";
        List<Map<String, Object>> list = Util.importEx(keyName, keyType, nullable, (FileInputStream) inputStream, origName);
        List<Map<String, Object>> ReList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (ValueUtil.notEmpity(list.get(i).get("erro"))) {
                Map<String, Object> map = new HashMap<>();
                map = list.get(i);
                ReList.add(map);
            }
        }
        if (ReList.size() != 0) {
            return ValueUtil.toJson(500, ReList);
        }
        if (ValueUtil.isEmpity(list)) {
            return "导入失败";
        }
        for (Map<String, Object> map : list) {
            Map<String, String> param = new HashMap<>();
            String name = map.get("name").toString();
            String phoneNumber = map.get("phoneNumber").toString();
            String address = map.get("address").toString();
            String goodsName = map.get("goodsName").toString();
            String num = map.get("num").toString();
            param.put("name", name);
            param.put("phoneNumber", phoneNumber);
            param.put("address", address);
            param.put("goodsName", goodsName);
            param.put("num", num);

        }
        return ValueUtil.toJson(200,list);
    }

    @RequestMapping(value = "/tttt",method = RequestMethod.POST)
    public String getJson(){
        JSONArray jsonArray=new JSONArray();
        for(int i=0;i<2;i++){
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("name",111);
            jsonObject.put("goodsName",111);
            jsonObject.put("num",111);
            jsonArray.add(jsonObject);
        }
        return ValueUtil.toJson(200,jsonArray);

    }


}
