package com.vote.voting.result;

/**
 * @author slyslyme
 * @version 1.0
 * @date 2019/11/17 10:50
 * @package com.vote.voting.result
 **/
public class CodeMsg {

    private int code;
    private String msg;

    //通用的错误码
    public static CodeMsg SUCCESS = new CodeMsg(200, "success");
    public static CodeMsg SERVER_ERROR = new CodeMsg(500100, "服务端异常");
    public static CodeMsg BIND_ERROR = new CodeMsg(500101, "参数校验异常：%s");
    public static CodeMsg REQUEST_ILLEGAL = new CodeMsg(500102, "请求非法");
    public static CodeMsg ACCESS_LIMIT_REACHED = new CodeMsg(500104, "访问太频繁");
    public static CodeMsg VERIFYCODE_ERROR = new CodeMsg(500105, "验证码错误");
    public static CodeMsg NO_DATA = new CodeMsg(500106, "没有数据");
    //登录模块 5002XX
    public static CodeMsg SESSION_ERROR = new CodeMsg(500210, "Session不存在或者已经失效");
    public static CodeMsg USER_NOT_EXIST = new CodeMsg(500211, "用户名不存在");
    public static CodeMsg PASSWORD_ERROR = new CodeMsg(500212, "密码错误");
    public static CodeMsg SUSPEND_ACCOUNT = new CodeMsg(500213, "账号冻结");
    public static CodeMsg PASS_ERROR = new CodeMsg(500214, "原密码错误");
    //注册模块 5003XX
    public static CodeMsg USER_EXIST = new CodeMsg(500300, "用户名已存在");
    public static CodeMsg PHONE_EXIST = new CodeMsg(500301, "手机号已存在");
    //用户投票模块 5004XX
    public static CodeMsg NOT_START = new CodeMsg(500400, "投票未开始");
    public static CodeMsg HAD_VOTED = new CodeMsg(500401, "不能重复投票");
    public static CodeMsg MULTIPLE_CHOICE = new CodeMsg(500402, "当前投票项为多选项");
    public static CodeMsg NO_PERMISSION = new CodeMsg(500403, "没有权限访问");
    public static CodeMsg NOTHING = new CodeMsg(500404, "未找到投票中的内容");
    public static CodeMsg VOTE_FAIL = new CodeMsg(500405, "投票失败");
    //管理员投票模块5005XX
    public static CodeMsg EXIST_TITLE = new CodeMsg(500500, "标题已存在");
    public static CodeMsg TOO_FEW_OPTIONS = new CodeMsg(500501, "选项太少，至少有两个选项");
    public static CodeMsg FAIL_DELETE_VOTE = new CodeMsg(500502, "删除投票失败");
    public static CodeMsg VOTE_OVER = new CodeMsg(500503, "投票已结束");
    public static CodeMsg VOTING = new CodeMsg(500504, "投票正在进行中");
    public static CodeMsg REPETE_OPTIONS = new CodeMsg(500505, "选项重复");
    public static CodeMsg ERROR_OPTION = new CodeMsg(500506, "请输入“单选”或者“多选”");
    //管理员管理用户模块5006XX
    public static CodeMsg NO_ACCOUNT = new CodeMsg(500600, "没有找到任何用户");
    public static CodeMsg FAIL_ADD_ACCOUNT = new CodeMsg(500601, "添加用户失败");
    public static CodeMsg FAIL_DELETE_ACCOUNT = new CodeMsg(500602, "删除用户失败");
    public static CodeMsg NO_ID = new CodeMsg(500603, "没有ID");
    public static CodeMsg ERROR_GENDER = new CodeMsg(500604, "性别格式出错");
    public static CodeMsg ERROR_AGE = new CodeMsg(500605, "年龄必须为整数");

    private CodeMsg(){}
    private CodeMsg(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public CodeMsg fillArgs(Object... args){
        int code = this.code;
        String message = String.format(this.msg, args);
        return new CodeMsg(code, message);
    }
}
