package ru.netology.Data;

import lombok.Value;

public class UserData {

    private UserData() { }

    @Value
    public static class AuthInfo {
        private String login;
        private String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        private String code;
    }

    public static VerificationCode getCode(AuthInfo authInfo) {
        return new VerificationCode("12345");
    }
}
