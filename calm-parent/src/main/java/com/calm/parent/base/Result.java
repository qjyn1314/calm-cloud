package com.calm.parent.base;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 通用返回结果
 *
 * @author wangjunming
 * @since 2020/10/12 13:24
 */
@ApiModel(description = "通用返回值对象")
public class Result<R> {

    @ApiModelProperty(value = "是否成功",example = "")
    private boolean success;

    @ApiModelProperty(value = "是否成功的编码",example = "")
    private int code;

    @ApiModelProperty(value = "返回的错误信息",example = "")
    private String msg;

    @ApiModelProperty(value = "返回的数据",example = "")
    private R data;

    public static <R> Result<R> ofSuccess(R data) {
        return new Result<R>().setSuccess(true).setMsg("success").setData(data);
    }

    public static <R> Result<R> ofSuccessMsg(String msg) {
        return new Result<R>().setSuccess(true).setMsg(msg);
    }

    public static <R> Result<R> ofFail(int code, String msg) {
        Result<R> result = new Result<>();
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

    public boolean isSuccess() {
        return success;
    }

    public Result<R> setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public int getCode() {
        return code;
    }

    public Result<R> setCode(int code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public Result<R> setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public R getData() {
        return data;
    }

    public Result<R> setData(R data) {
        this.data = data;
        return this;
    }

    @Override
    public String toString() {
        return "Result{" + "success=" + success + ", code=" + code + ", msg='" + msg + '\'' + ", data=" + data + '}';
    }

}
