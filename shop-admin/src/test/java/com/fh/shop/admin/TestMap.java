package com.fh.shop.admin;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class TestMap {

    @Test
    public void test1() {
        Map<String, String[]> userMap = new HashMap<>();
        userMap.put("userName", new String[]{"aaa", "bbb"});
        userMap.put("addr", new String[]{"高新区"});
        userMap.put("xinzi", new String[]{"2000"});
        userMap.put("sex", new String[]{"男"});

        Iterator<Map.Entry<String, String[]>> iterator = userMap.entrySet().iterator();
        StringBuilder stringBuilder = new StringBuilder();
        while (iterator.hasNext()) {
            Map.Entry<String, String[]> next = iterator.next();
            String[] value = next.getValue();
            String key = next.getKey();
            stringBuilder.append(key).append("=").append(StringUtils.join(value, ",")).append("|");
        }
        System.out.println(stringBuilder.toString());
        // jdk1.8新特性 lambda表达式
    }
}
