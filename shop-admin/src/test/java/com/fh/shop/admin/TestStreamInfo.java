package com.fh.shop.admin;

import com.fh.shop.admin.po.book.Book;
import org.junit.Test;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestStreamInfo {

    @Test
    public void test1() {
        String[] bookNames = {"aaa","ddddddddd","ccccc","aaa"};

        // 获取长度大于3的数组,并输出
//        Arrays.asList(bookNames).stream().filter(x -> x.length() > 3).forEach(System.out::println);

        // 将数组转换为选项加上各自的长度，并输出
//        Arrays.asList(bookNames).stream().map(x -> x+":"+x.length()).forEach(System.out::println);

        // 获取长度大于3的数组,将数组转换为选项加上各自的长度，并输出
//        Arrays.asList(bookNames).stream().filter(x -> x.length() > 3).map(x -> x+":"+x.length()).forEach(System.out::println);

        // 获取长度大于3的数组，排序后，将其转为长度，再将其转换为长度*2，并输出
        Arrays.asList(bookNames).stream().filter(x -> x.length() > 3).sorted().forEach(System.out::println);
    }

    @Test
    public void test2() {
        List<Book> books = new ArrayList<>();

        Book b1 = new Book();
        b1.setBookName("三国");
        b1.setPrice(200);

        Book b4 = new Book();
        b4.setBookName("aaa");
        b4.setPrice(200);

        Book b2 = new Book();
        b2.setBookName("西游");
        b2.setPrice(260);

        Book b3 = new Book();
        b3.setBookName("红楼");
        b3.setPrice(150);

        books.add(b1);
        books.add(b2);
        books.add(b3);
        books.add(b4);

        // 计算总价格
//        Integer reduce = books.stream().map(x -> x.getPrice()).reduce(0, (x, y) -> x + y);
//        System.out.println(reduce);

        // 算出来最低价格的图书
//        Book book = books.stream().min((a, b) -> (a.getPrice() - b.getPrice())).get();
//        System.out.println(book);
//
//        // 算出来价格最高的图书
//        Book book1 = books.stream().max((a, b) -> (a.getPrice() - b.getPrice())).get();
//        System.out.println(book1);

        // 计算总价格
        Integer collect = books.stream().collect(Collectors.summingInt(b -> b.getPrice()));
        System.out.println(collect);

        IntSummaryStatistics collect1 = books.stream().collect(Collectors.summarizingInt(b -> b.getPrice()));
        long sum = collect1.getSum();
        System.out.println(sum);

        // 计算平均值
        Double collect7 = books.stream().collect(Collectors.averagingDouble(Book::getPrice));
        System.out.println(collect7);

        // 按照价格分组
        Map<Integer, List<Book>> collect2 = books.stream().collect(Collectors.groupingBy(Book::getPrice));
        System.out.println(collect2);


        // list转map
        Map<String, Book> collect3 = books.stream().collect(Collectors.toMap(Book::getBookName, b -> b));
        System.out.println(collect3);

        // list转map
        Map<String, Integer> collect4 = books.stream().collect(Collectors.toMap(b -> b.getBookName(), c -> c.getPrice(), (k1, k2) -> k1));

        Map<String, Book> collect5 = books.stream().collect(Collectors.toMap(a -> a.getBookName(), b -> b));

        Map<Boolean, Long> collect6 = books.stream().collect(Collectors.partitioningBy(x -> x.getPrice() > 300, Collectors.counting()));
        System.out.println(collect6);

    }

    @Test
    public void test3() {
        List<Book> books = new ArrayList<>();

        Book b1 = new Book();
        b1.setBookName("三国");
        b1.setPrice(200);

        Book b4 = new Book();
        b4.setBookName("aaa");
        b4.setPrice(200);

        Book b2 = new Book();
        b2.setBookName("西游");
        b2.setPrice(260);

        Book b3 = new Book();
        b3.setBookName("红楼");
        b3.setPrice(150);

        books.add(b1);
        books.add(b2);
        books.add(b3);
        books.add(b4);

        Map<Integer, IntSummaryStatistics> collect = books.stream().collect(Collectors.groupingBy(x -> x.getPrice(), Collectors.summarizingInt(y -> y.getPrice())));
        System.out.println(collect);
    }
}
