package com.example.bitcamptiger.Util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    public static boolean isRegexEmail(String target) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }

    public static boolean isRegexPassword(String target) {
//       최소 8글자, 대문자 1개, 소문자 1개, 숫자 1개
        String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isjointype(String target) {
        return "kakao".equals(target) || "naver".equals(target)
                || "google".equals(target) || "apple".equals(target)||"local".equals(target);
    }
    public static boolean privary(boolean target) {
        if(target){
            return true;
        }
        else {
            return false;
        }
    }
    public static boolean isname(String target) {
        String regex = "^.{0,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(target);
        return matcher.find();
    }
    public static boolean isphone(int target) {
        String targetString = Integer.toString(target);  // 숫자를 문자열로 변환
        String regex = "^.{1,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(targetString);
        return matcher.find();
    }




}
