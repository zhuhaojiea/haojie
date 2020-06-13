package com.fh.shop.admin;

import java.util.Map;

public class Info {

    public static String show2(Map m) {
        System.out.println("=============");
        m.forEach((y,z) -> System.out.println(y+":"+z));
        return "aaa";
    }
}
