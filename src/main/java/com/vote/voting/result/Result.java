package com.vote.voting.result;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 10:50
 * @package com.vote.voting.result
 **/
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    private Result(T data){
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg serverError){
        if(serverError == null) return;
        this.code = serverError.getCode();
        this.msg = serverError.getMsg();
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    /**
     * 成功时返回调用结果
     */
    public static<T> Result<T> success(T data){
        return new Result<T>(data);
    }

    public static<T> Result<T> error(CodeMsg serverError){
        return new Result<T>(serverError);
    }
}
