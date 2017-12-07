package com.example.Demo.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by hz on 12/7/17.
 */
public class ValueUtil {

    public static Gson gson = new GsonBuilder().serializeNulls().create();

    public static boolean notEmpity(Object obj) {
        if (null == obj) {
            return false;
        } else if (obj.toString().equals("")) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean isEmpity(Object obj) {
        return !notEmpity(obj);
    }


    public static String toJson(Integer code, Object obj) {
        RestJson restJson = new RestJson();
        restJson.setCode(String.valueOf(code));
        restJson.setMsg("success");
        restJson.setData(coalesce(obj, ""));
        return gson.toJson(restJson);
    }

    public static <T> T coalesce(T... args) {
        for (int i = 0; i < args.length; i++) {
            if (notEmpity(args[i])) {
                return args[i];
            }
        }
        return (T) "";
    }
    public static String toJson(Object obj) {
        try {
            RestJson restJson = new RestJson();
            restJson.setCode(String.valueOf("200"));
            restJson.setMsg("success");
            restJson.setData(coalesce(obj, ""));
//            JSONValue jsonValue = JSONMapper.toJSON(restJson);
//            String jsonStr = jsonValue.render(false);
//            return jsonStr;
            return gson.toJson(restJson);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
