package com.calm.web.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * <p>
 * explain:
 * </p>
 *
 * @author wangjunming
 * @since 2021/2/25 0:16
 */
public class TestController {
    public static void main(String[] args) {
        String slat = "6as849";
        String password = "admin";
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String encode =  bCryptPasswordEncoder.encode(password + slat);
        System.out.println(encode);
        //$2a$10$O47bjg7dtR.ZRK.TEOym/uu4oinPgsNquFWMWu.uRSedmY3.ynsh2
        //$2a$10$U4ScwttRLM6z22xia6Q8Ve2gJ2emeGgn3KrVF4yFTwZDXrnu9xuUe
        String passwordParams =  "admin" + slat;
        boolean matches = bCryptPasswordEncoder.matches(passwordParams,encode);
        System.out.println(matches);

    }
}
