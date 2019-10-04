package org.lf.community.exception;

public enum CustomizeCode implements CustomizeExceptionCode {

    QUESTION_NOT_FOUND(2001,"你找的问题不在了,要不换个试试?"),
    TARGET_PARAM_NOT_FOUND(2002,"未选择任何问题进行回复!"),
    NO_LOGIN(2003,"未进行登录，请先登录后重试!"),
    SYSTEM_ERROR(2004,"服务器出了点问题,请稍后试试!!!"),
    TYPE_PARAM_ERROR(2005,"类型出错!!"),
    COMMENT_NOT_FOUND(2006,"回复的评论不存在!!"),
    CONTENT_IS_EMPTY(2007,"输入内容不能为空!!!"),
    READ_NOTIFICATION_FAIL(2008,"兄弟你这是读别人的信息呢?"),
    NOTIFICATION_NOT_FOUND(2009,"消息莫名失踪了?");

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    private Integer code;
    private String message;


    CustomizeCode(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
