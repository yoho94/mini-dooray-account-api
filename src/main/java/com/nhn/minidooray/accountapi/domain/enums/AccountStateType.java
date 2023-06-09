package com.nhn.minidooray.accountapi.domain.enums;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AccountStateType {
    REGISTER("01", "가입", false, false),
    WITHDRAW("02", "탈퇴", true, false),
    DORMANT("03", "휴면", false, true),
    ACTIVE("04", "활동", false, false);

    private final String code;
    private final String name;
    private final boolean disabled;
    private final boolean accountLocked;

    AccountStateType(String code, String name, boolean disabled, boolean accountLocked) {
        this.code = code;
        this.name = name;
        this.disabled = disabled;
        this.accountLocked = accountLocked;
    }

    public static AccountStateType valueOfCode(String code) {
        return Arrays.stream(values())
                .filter(accountStateType -> accountStateType.getCode().equals(code))
                .findAny()
                .orElseThrow();
    }
}
