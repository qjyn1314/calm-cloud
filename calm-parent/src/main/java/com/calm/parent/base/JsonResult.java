package com.calm.parent.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 通用返回结果
 *
 * @author wangjunming
 * @since 2020/10/12 13:24
 */
@Data
@ToString
@AllArgsConstructor
@ApiModel(description = "通用返回值对象")
public class JsonResult<R> implements Serializable {

    @ApiModelProperty(value = "是否成功的编码")
    private int code = 0;
    @ApiModelProperty(value = "返回的错误信息")
    private String msg;
    @ApiModelProperty(value = "返回的数据")
    private R data;

    public JsonResult() {
    }

    private static <R> JsonResult<R> defaultSuccess() {
        return new JsonResult<R>().setCode(0);
    }

    public static JsonResult<Object> success() {
        return defaultSuccess().setMsg("success");
    }

    public static <R> JsonResult<Object> success(R data) {
        return success().setData(data);
    }

    public static JsonResult<Object> successMsg(String msg) {
        return defaultSuccess().setMsg(msg);
    }

    public static <R> JsonResult<Object> successDataMsg(R data, String msg) {
        return defaultSuccess().setData(data).setMsg(msg);
    }

    public static <R> JsonResult<R> fail(String msg) {
        return new JsonResult<R>().setCode(-1).setMsg(msg);
    }

    public int getCode() {
        return code;
    }

    public JsonResult<R> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public JsonResult<R> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public R getData() {
        return data;
    }

    public JsonResult<R> setData(R data) {
        this.data = data;
        return this;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> maps = new HashMap<>(5);
        maps.put("code", this.code);
        maps.put("msg", this.msg);
        maps.put("data", this.data);
        return maps;
    }

}
