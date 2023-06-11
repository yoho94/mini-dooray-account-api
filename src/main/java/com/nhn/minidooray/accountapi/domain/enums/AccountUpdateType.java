package com.nhn.minidooray.accountapi.domain.enums;

import com.nhn.minidooray.accountapi.domain.request.AccountUpdateRequest;

public enum AccountUpdateType {
    NAME_UPDATE {
        @Override
        public boolean validate(AccountUpdateRequest request) {
            return request.getName() != null && request.getPassword() == null
                && request.getLastLoginAt() == null;
        }
    },
    PASSWORD_UPDATE {
        @Override
        public boolean validate(AccountUpdateRequest request) {
            return request.getName() == null && request.getPassword() != null
                && request.getLastLoginAt() == null;
        }
    },
    LAST_LOGIN_AT_UPDATE {
        @Override
        public boolean validate(AccountUpdateRequest request) {
            return request.getName() == null && request.getPassword() == null
                && request.getLastLoginAt() != null;
        }
    };

    public abstract boolean validate(AccountUpdateRequest request);
}
