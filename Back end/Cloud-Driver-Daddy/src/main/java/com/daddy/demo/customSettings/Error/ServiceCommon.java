package com.daddy.demo.customSettings.Error;

//业务常见错误枚举类
public enum ServiceCommon implements BaseErrorInfoInterface{
//    数据操作错误定义
    SUCCESS("200", "成功!"),
    BODY_NOT_MATCH("400","请求的数据格式不符!"),
    VALUE_IS_NULL("400","值为空"),
    TEL_NOT_ONLY("400","手机号不唯一！"),
    WID_NOT_ONLY("400","员工号不唯一！"),
    NAME_NOT_ONLY("400","名称不唯一！"),
    INTE_OVER_100("400","所加积分过高致使总额超过100！"),
    EXIST_LOWERO("400","存在下级O，不允许删除O记录！"),
    WEIGHT_OVER_1("400","O下总KR权重超过1！"),
    DEGREE_OVER_1("400","KR的完成度超过1！"),
    LOGIN_NOT_PASSED("403","登录失败，密码错误！"),
    NOT_FOUND("404", "未找到该资源!"),
    INTERNAL_SERVER_ERROR("500", "服务器内部错误!"),
    FILE_UPLOAD_FAIL("500","文件上传失败！"),
    FILE_DOWNLOAD_FAIL("500","文件下载失败")
    ;

    private String code;
    private String msg;

    ServiceCommon(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
