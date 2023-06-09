package com.nhn.minidooray.accountapi.domain.response;

import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

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
