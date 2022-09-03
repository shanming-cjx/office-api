package com.chenjx.office.api.common.util;

import org.apache.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;


public class Resp extends HashMap<String, Object> {

    public Resp() {
        put("code", HttpStatus.SC_OK);
        put("msg", "success");
    }

    public Resp put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static Resp ok() {
        return new Resp();
    }

    public static Resp ok(String msg) {
        Resp resp = new Resp();
        resp.put("msg", msg);
        return resp;
    }

    public static Resp ok(Map<String, Object> map) {
        Resp resp = new Resp();
        resp.putAll(map);
        return resp;
    }

    public static Resp error(int code, String msg) {
        Resp resp = new Resp();
        resp.remove("msg");
        resp.put("code", code);
        resp.put("error", msg);
        return resp;
    }

    public static Resp error(String msg) {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, msg);
    }

    public static Resp error() {
        return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "出现未知异常，请联系管理员");
    }

}