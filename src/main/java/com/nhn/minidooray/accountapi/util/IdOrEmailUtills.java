package com.nhn.minidooray.accountapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 요청에 들어온 식별자 값을
 * ID, EMAIL로 분리
 */
// TODO 새로운 처리기능 추가로 인한 api에 기능 추가해야함.
public class IdOrEmailUtills {
    private static final String REGEX="^[A-Za-z0-9+_.-]+@(.+)$";
    /**
     *
     * @param target id Or email
     * @return true : email , false : id
     */
    public static boolean checkIdOrEmail(String target){
        Pattern pattern = Pattern.compile(REGEX);  // Email pattern
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     *
     * @param target id Or email
     * @return true : id
     */
    public static boolean checkId(String target){
        Pattern pattern = Pattern.compile(REGEX);  // Email pattern
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     *
     * @param target id Or email
     * @return true : email
     */
    public static boolean checkEmail(String target){
        Pattern pattern = Pattern.compile(REGEX);  // Email pattern
        Matcher matcher = pattern.matcher(target);
        if (matcher.find()) {
            return true;
        } else {
            return false;
        }
    }

}
