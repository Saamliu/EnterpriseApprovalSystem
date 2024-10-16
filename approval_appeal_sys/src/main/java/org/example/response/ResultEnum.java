package org.example.response;

public enum ResultEnum {
    OK("10000","操作成功"),
    ERROR("20000", "操作失败"),
    INSERT_ERROR("20001","添加失败"),
    UPDATE_ERROR("20002","更新失败"),
    DELETE_ERROR("20003","删除失败"),
    SELECT_ERROR("20004","查询失败"),
    INVALID_TOKEN("20005","无效token"),
    BLACK_IP("20006", "您已被列入黑名单"),
    DATA_VALID_ERROR("20008","数据校验失败"),
    FILE_UPLOAD_ERROR("20009","文件上传失败"),
    FILE_SIZE_TOO_BIG("20010","文件超出大小"),
    PROJECT_ALREADY_EXISTS("20011","项目已存在"),
    PROJECT_NOT_EXISTS("20016","项目不存在"),
    PROJECT_ALREADY_APPROVAL("20017","项目已审批"),
    USER_NOT_EXISTS("20012","用户不存在"),
    NO_APPROVAL_AUTHORITY("20018","没有审批权限"),
    NO_APPEAL_AUTHORITY("20019","没有申诉权限"),
    APPEAL_CHANNEL_NOT_ENABLED("20020","未开启申诉通道"),
    APPEAL_CHANNEL_IS_CLOSED("20021","申诉通道已关闭"),
    APPEAL_ALREADY_EXISTS("20022","已存在该项目的申诉，请等待审批"),
    USER_ALREADY_LOCK("20013","用户被锁"),
    USER_PASSWORD_ERROR("20014","用户密码错误"),
    DATA_NOT_FOUND("10005","没有数据"),
    ;
    private String code;
    private String message;

    ResultEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
