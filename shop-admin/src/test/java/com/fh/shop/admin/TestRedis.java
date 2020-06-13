package com.fh.shop.admin;

import com.fh.shop.admin.util.RedisUtil;
import org.junit.Test;

public class TestRedis {

    @Test
    public void test1() {
//        RedisUtil.set("fhName", "郑州市区");

        String fhName = RedisUtil.get("fhName");
        System.out.println(fhName);

    }
}
