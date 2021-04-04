package com.calm.parent.base;

import com.google.common.collect.Maps;
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
    private int code = 200;
    @ApiModelProperty(value = "返回的错误信息")
    private String message;
    @ApiModelProperty(value = "返回的数据")
    private R data;

    private final static int SUCCESS_200 = 200;
    private final static int FAIL_500 = 500;
    private final static int SUCCESS_0 = 0;
    private final static int FAIL_1 = -1;

    public JsonResult() {
    }

    private static <R> JsonResult<R> defaultSuccess() {
        return new JsonResult<R>().setCode(SUCCESS_0);
    }

    public static JsonResult<Object> success() {
        return defaultSuccess().setMessage("success");
    }

    public static <R> JsonResult<Object> success(R data) {
        return success().setData(data);
    }

    public static JsonResult<Object> successMsg(String msg) {
        return defaultSuccess().setMessage(msg);
    }

    public static <R> JsonResult<Object> successDataMsg(R data, String msg) {
        return defaultSuccess().setData(data).setMessage(msg);
    }

    public static <R> JsonResult<R> fail(String msg) {
        return new JsonResult<R>().setCode(FAIL_1).setMessage(msg);
    }

    public int getCode() {
        return code;
    }

    public JsonResult<R> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public JsonResult<R> setMessage(String message) {
        this.message = message;
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
        Map<String, Object> maps = new HashMap<>(3);
        maps.put("code", this.code);
        maps.put("msg", this.message);
        maps.put("data", this.data);
        return maps;
    }

    /**
     * 查询树形菜单
     *
     * @author wangjunming
     * @since 2021/3/29 13:18
     * @return java.util.Map<java.lang.String,java.lang.Object>
     */
    public Map<String, Object> toPermissionsTreeResult() {
        Map<String, Object> maps = new HashMap<>(2);
        Map<String, Object> status = Maps.newHashMap();
        status.put("code", this.code == SUCCESS_0 ? SUCCESS_200 : FAIL_500);
        status.put("message", this.message);
        maps.put("status", status);
        maps.put("data", this.data);
        return maps;
    }

}
