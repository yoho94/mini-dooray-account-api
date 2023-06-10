package com.nhn.minidooray.accountapi.domain.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ResultResponse<T> {

    private Header header;
    private List<T> result;

    public long getTotalCount() {
        return result == null ? 0 : result.size();
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
