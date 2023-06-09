package com.nhn.minidooray.accountapi.domain.response;

import java.util.Collection;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Builder
public class ResultResponse<T> {

    private Header header;
    private T result;

    public long getTotalCount() {
        if (result instanceof Collection<?>) {
            return ((Collection<?>) result).size();
        } else {
            return result == null ? 0 : 1;
        }
    }

    @Getter
    @Setter
    @Builder
    public static class Header {

        private boolean isSuccessful;
        private int resultCode;
        private String resultMessage;
    }
}
