package org.lf.community.enums;

public enum  NotificationEnum {
    REPLY_QUESTION(1,"回复了问题"),
    REPLY_COMMENT(2,"回复了评论");
    private int type;
    private String name;

    NotificationEnum(int status, String name) {
        this.type = status;
        this.name = name;
    }

    public static String nameOfType(int type) {
        for (NotificationEnum notificationEnum : NotificationEnum.values()) {
            if(notificationEnum.getType() == type) {
                return notificationEnum.getName();
            }
        }
        return "";
    }


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
