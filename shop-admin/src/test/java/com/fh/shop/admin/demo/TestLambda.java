package com.fh.shop.admin.demo;

import com.fh.shop.admin.po.book.Book;
import org.junit.Test;

import java.util.*;

public class TestLambda {

    @Test
    public void test1() {
        List<String> names = new ArrayList<>();
        names.add("zhangsan");
        names.add("lisi");
        names.add("wangwu");

        // 循环遍历输出
//        for (String name : names) {
//            System.out.println(name);
//        }

        // lambda 循环遍历list
        names.forEach(a -> System.out.println(a));

//        names.forEach(System.out::println);
    }

    @Test
    public void test2() {
        List<Book> books = new ArrayList<>();

        Book b1 = new Book();
        b1.setBookName("三国");
        b1.setPrice(200);

        Book b2 = new Book();
        b2.setBookName("红楼");
        b2.setPrice(100);

        books.add(b1);
        books.add(b2);

//        for (Book book : books) {
//            System.out.println(book.getBookName()+":"+book.getPrice());
//        }

        // lambda
//        books.forEach(b -> System.out.println(b.getBookName()+":"+b.getPrice()));
        Info info = new Info();
        books.forEach(info::show);
    }

    public void show(Book book) {
        System.out.println(book.getBookName()+":"+book.getPrice());
    }

    @Test
    public void test3() {
        Map<String, String> bookMap = new HashMap<>();
        bookMap.put("bookName", "aaa");
        bookMap.put("price", "2000");
        bookMap.put("date", "2001-10-11");

        // 老方法循环遍历map
//        Iterator<Map.Entry<String, String>> iterator = bookMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, String> next = iterator.next();
//            String key = next.getKey();
//            String value = next.getValue();
//            System.out.println(key+":"+value);
//        }

        // lambda
        bookMap.forEach((a,b) -> System.out.println(a+":"+b));
    }

    @Test
    public void test4() {
        Map<String, Book> bookMap = new HashMap<>();
        Book b1 = new Book();
        b1.setBookName("三国");
        b1.setPrice(200);
        bookMap.put("sanguo", b1);
        Book b2 = new Book();
        b2.setBookName("红楼");
        b2.setPrice(100);
        bookMap.put("honglou", b2);

        // 老方法
//        Iterator<Map.Entry<String, Book>> iterator = bookMap.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, Book> next = iterator.next();
//            String key = next.getKey();
//            Book value = next.getValue();
//            System.out.println(key+":"+value.getBookName()+":"+value.getPrice());
//        }

        // lambda
        bookMap.forEach((x,y) -> System.out.println(x+":"+y.getBookName()+":"+y.getPrice()));
    }

    @Test
    public void test5() {
        List<Map<String, String>> bookList = new ArrayList<>();
        Map<String, String> b1 = new HashMap<>();
        b1.put("bookName", "三国");
        b1.put("price", "200");
        Map<String, String> b2 = new HashMap<>();
        b2.put("bookName", "西游");
        b2.put("price", "100");
        bookList.add(b2);
        bookList.add(b1);

        // 老方法循环遍历
//        for (Map<String, String> stringStringMap : bookList) {
//            Iterator<Map.Entry<String, String>> iterator = stringStringMap.entrySet().iterator();
//            while (iterator.hasNext()) {
//                Map.Entry<String, String> next = iterator.next();
//                String key = next.getKey();
//                String value = next.getValue();
//                System.out.println(key+"===="+value);
//            }
//        }

        // lambda
        bookList.forEach(this::print);
    }

    public void print(Map<String, String> v) {
        v.forEach((a,b) -> System.out.println(a+":"+b));
    }

    @Test
    public void test6() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("sanguo");
        bookNames.add("xiyou");
        bookNames.add("honglou");

        // 将list集合中名字等于xiyou的数据给删除了
//        for (int i = bookNames.size()-1; i >=0 ; i--) {
//            String s = bookNames.get(i);
//            if (s.equals("xiyou")) {
//                bookNames.remove(i);
//            }
//        }
//        System.out.println(bookNames);

        // lambda
        bookNames.removeIf(x -> "xiyou".equals(x));
        System.out.println(bookNames);
    }

    @Test
    public void test7() {

        List<Book> books = new ArrayList<>();

        Book b1 = new Book();
        b1.setBookName("三国");
        b1.setPrice(200);

        Book b2 = new Book();
        b2.setBookName("红楼");
        b2.setPrice(100);

        Book b3 = new Book();
        b3.setBookName("红楼222");
        b3.setPrice(50);

        books.add(b1);
        books.add(b2);
        books.add(b3);

        // 删除价格大于50的图书
//        for (int i = books.size()-1; i >= 0 ; i--) {
//            Book book = books.get(i);
//            if (book.getPrice() > 50) {
//                books.remove(i);
//            }
//        }
//        System.out.println(books);

        // lambda
        books.removeIf(x -> x.getPrice() >= 50 && x.getPrice() <= 100);

        System.out.println(books);

    }
}
