package com.fh.shop.admin;

import com.fh.shop.admin.po.book.Book;
import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestLambda {

    @Test
    public void test1() {
        List<String> names = new ArrayList<>();
        names.add("zhangsan");
        names.add("lisi");
        names.add("wangwu");



        names.removeIf(x -> x.equals("lisi"));

        names.forEach(x -> System.out.println(x));


//        names.forEach(y -> {
//            System.out.println(y);
//            System.out.println(y+"a");
//        });
    }

    @Test
    public void test2() {
        Map bookMap = new HashMap();
        bookMap.put("bookName", "三国");
        bookMap.put("price", "2000");
        bookMap.put("createDate", "2020-10-1");

        bookMap.forEach((x,y) -> System.out.println(x+":"+y));
    }

    @Test
    public void test3() {
        Map book1 = new HashMap();
        book1.put("bookName", "三国");
        book1.put("price", "2000");

        Map book2 = new HashMap();
        book2.put("bookName", "西游");
        book2.put("price", "3000");

        List<Map> books = new ArrayList();
        books.add(book1);
        books.add(book2);
        Info info = new Info();
//        books.forEach({
//               System.out.println(Info::show2);
//        });


    }

    public void show(Map m) {
        System.out.println("=============");
        m.forEach((y,z) -> System.out.println(y+":"+z));
    }

    @Test
    public void test4() {
        Book book1 = new Book();
        book1.setBookName("三国");
        book1.setPrice(3000);

        Book book2 = new Book();
        book2.setBookName("西游");
        book2.setPrice(2000);

        List<Book> books = new ArrayList<>();
        books.add(book1);
        books.add(book2);

        books.forEach(x -> {
            System.out.println(x.getBookName());
        });
    }




}
