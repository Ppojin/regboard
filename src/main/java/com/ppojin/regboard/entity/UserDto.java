package com.ppojin.regboard.entity;

import lombok.Getter;

public class UserDto {
    @Getter
    public static class Create{
        private String uid;
        private String name;
    }
}
