package com.fh.shop.admin.demo;

import com.fh.shop.admin.po.book.Book;
import org.junit.Test;

import java.util.Optional;

public class TestOptional {

    @Test
    public void test1() {



        // 老方法
//        if (book != null) {
//            String bookName = book.getBookName();
//            if (bookName != null) {
//                System.out.println(bookName.toUpperCase());
//            } else {
//                System.out.println("信息为空");
//            }
//        } else {
//            System.out.println("信息为空");
//        }

        // optional
//        boolean present = Optional.ofNullable(book).isPresent();
//        System.out.println(present);
        Book book = new Book();
        book.setBookName("aaa");
//        Optional:[进行非空判断，避免空指针]
//        ofNullable(参数) : 可以接收一个 为空或者不为空 的参数
//        isPresent() : 返回一个布尔值，为true，则证明参数不为空，否则参数为空
//        map() : 进行数据的转换，如果参数为空，则返回一个空信息，如果不为空则返回一个对应的数据
//        orElse(): 如果之前的数据为空，则输出orElse中的数据信息
//        String info = Optional.ofNullable(book).map(x -> x.getBookName()).orElse("信息为空");
//        if (book != null) {
//            String bookName = book.getBookName();
//            if (bookName != null) {
//                System.out.println(bookName);
//            } else {
//                System.out.println("信息为空");
//            }
//        } else {
//            System.out.println("信息为空");
//        }
//        System.out.println(info);

        String name = null;

//        if (name != null) {
//            System.out.println(name);
//        } else {
//            System.out.println("信息为空");
//        }

        // optional
        //orElse(): 如果之前的数据为空，则输出orElse中的数据信息
//        String info = Optional.ofNullable(name).orElse("信息为空");
//        System.out.println(info);

        //  map() : 进行数据的转换，如果参数为空，则返回一个空信息，如果不为空则返回一个对应的数据
//        Integer integer = Optional.ofNullable(name).map(c -> c.length()).orElse(0);
//        System.out.println(integer);

        String s = Optional.ofNullable(name).map(b -> b.substring(1)).orElse("111");
        System.out.println(s);




    }

    @Test
    public void test2() {
        String name = null;
        Integer integer = Optional.ofNullable(name).map(x -> x.substring(1))
                                                   .map(y -> y.length())
                                                   .orElse(-1);
        System.out.println(integer);
    }

    @Test
    public void test3() {
        Book book = new Book();
        book.setBookName("aaa");
        // Book --> "aaa" --> AAA
        String info = Optional.ofNullable(book).map(x -> x.getBookName())
                .map(y -> y.toUpperCase())
                .orElse("空信息");
        System.out.println(info);
    }

    @Test
    public void test4() {
        Book book = new Book();
        book.setBookName("aaa");
        // Book --> "aaa" --> AAA
        String info = Optional.ofNullable(book).map(x -> x.getBookName())
                .map(y -> y = null)
                .orElse("空信息");
        System.out.println(info);
    }

    @Test
    public void test5() {
        Book book = new Book();
        book.setBookName("三国");
        book.setPrice(100);
        // 一种是 新年快乐
        // 一种是 飞狐教育null:null
        String info = Optional.ofNullable(book).map(x -> x.getBookName() + ":" + x.getPrice())
                .map(y -> "飞狐教育" + y)
                .orElse("新年快乐");
        System.out.println(info);
    }

    @Test
    public void test6() {
        Book book = new Book();
        book.setBookName("kkk");
        String info = Optional.ofNullable(book).map(x -> x.getBookName()).map(y -> y.toUpperCase()).orElse("信息为空");
        System.out.println(info);
    }
}
