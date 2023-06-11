package com.nhn.minidooray.accountapi.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 요청에 들어온 식별자 값을 ID, EMAIL로 분리
 */
public class IdOrEmailUtills {

    private static final String REGEX = "^[A-Za-z0-9+_.-]+@(.+)$";

    private IdOrEmailUtills() {

    }

    /**
     * @param target id Or email
     * @return true : email
     */
    public static boolean checkIdOrEmail(String target) {
        return checkEmail( target );
    }

    /**
     * @param target id Or email
     * @return true : id
     */
    public static boolean checkId(String target) {
        Pattern pattern = Pattern.compile( REGEX );  // Email pattern
        Matcher matcher = pattern.matcher( target );
        return !matcher.find();
    }

    /**
     * @param target id Or email
     * @return true : email
     */
    public static boolean checkEmail(String target) {
        Pattern pattern = Pattern.compile( REGEX );  // Email pattern
        Matcher matcher = pattern.matcher( target );
        return matcher.find();
    }

}
