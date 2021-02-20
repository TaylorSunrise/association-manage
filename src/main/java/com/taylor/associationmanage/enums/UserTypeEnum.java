package com.taylor.associationmanage.enums;

import lombok.Getter;

@Getter
public enum UserTypeEnum {

    ROOT(1,"系统管理员"),
    ADMIN(2,"社团负责人"),
    MEMBER(3,"普通用户");

    private Integer value;
    private String role;

    UserTypeEnum(Integer value,String role) {
        this.value = value;
        this.role = role;
    }
}
