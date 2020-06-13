package com.fh.shop.admin;

import com.fh.shop.admin.po.product.Product;
import com.fh.shop.admin.po.user.User;
import org.apache.commons.collections.list.PredicatedList;
import org.junit.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class TestReflect {

    @Test
    public void test1() {
        Field[] declaredFields = User.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
            System.out.println(declaredField.getType());
        }
    }

    @Test
    public void test2() {
        User u1 = new User();
        Class<? extends User> userClass = u1.getClass();
        Method[] declaredMethods = userClass.getDeclaredMethods();
        for (Method declaredMethod : declaredMethods) {
            System.out.println(declaredMethod.getName());
        }
    }

    @Test
    public void test3() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
        Class<?> userClass = Class.forName("com.fh.shop.admin.po.user.User");
        String[] props = {"userName","shengId","shiId","xianId","password"};
        User u1 = new User();
        for (String prop : props) {
            Field declaredField = userClass.getDeclaredField(prop);
            // 暴力访问
            declaredField.setAccessible(true);
            Class<?> type = declaredField.getType();
            if (type == java.lang.String.class) {
                declaredField.set(u1, "dssdfa");
            }
            if (type == java.lang.Long.class) {
                declaredField.set(u1, 111111L);
            }
        }
        System.out.println(u1);
    }

    @Test
    public void test4() throws NoSuchFieldException, IllegalAccessException {
        List<Product> productList = new ArrayList<>();

        Product p1 = new Product();
        p1.setProductName("aaaa");
        p1.setPrice(new BigDecimal(2000));
        p1.setBrandName("111111111");
        p1.setStatus(1);

        Product p2 = new Product();
        p2.setProductName("bbb");
        p2.setPrice(new BigDecimal(3000));
        p2.setBrandName("2222222222");
        p2.setStatus(0);

        productList.add(p1);
        productList.add(p2);

//        for (Product product : productList) {
//            System.out.println(product.getProductName());
//            System.out.println(product.getPrice());
//        }
        String[] props = {"productName","status"};

        for (String prop : props) {
            for (Product product : productList) {
                Class<? extends Product> productClass = product.getClass();
                Field declaredField = productClass.getDeclaredField(prop);
                declaredField.setAccessible(true);
                Object o = declaredField.get(product);
                System.out.println(o);
            }
        }
    }
}
