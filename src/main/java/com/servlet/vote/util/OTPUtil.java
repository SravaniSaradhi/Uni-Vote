package com.servlet.vote.util;

public class OTPUtil {

    public static String generateOTP() {
        return String.valueOf((int)(Math.random() * 900000) + 100000);
    }
}
