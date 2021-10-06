package com.daddy.demo.customSettings.Error;

//token常见错误
public enum TokenCommon implements BaseErrorInfoInterface{
    //    数据操作错误定义
    SUCCESS("200", "成功!"),
    SIGNATURE_NOT_MATCH("401","请求的数字签名不匹配!"),
    TOKEN_OVERDUE("401","token过期"),
    ALGORITHM_ERROR("401","token算法出错"),
    TOKEN_VOID("401","token无效"),
    NOT_TOKEN("401","无token！")
    ;

    private String code;
    private String msg;

    TokenCommon(String code, String msg) {
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
