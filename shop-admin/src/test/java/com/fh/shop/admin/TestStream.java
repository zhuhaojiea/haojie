package com.fh.shop.admin;

import com.fh.shop.admin.po.book.Book;
import org.apache.poi.util.SystemOutLogger;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStream {

    @Test
    public void test1() {
        List<String> names = Arrays.asList("aaa", "bbbbb", "aabbccc", "cccc");
        names.stream().filter(x -> x.length() > 3).map(String::length).sorted().forEach(System.out::println);
    }

    @Test
    public void test2() {
        List<Integer> numbers = Arrays.asList(3, 2, 5);
//        Stream<Integer> stream = numbers.stream();
//        stream.map(x -> x*2).forEach(y -> System.out.println(y));

//        Stream<List<Integer>> numbers1 = Stream.of(numbers);
//        numbers1.forEach(x -> System.out.println(x.size()));
//        numbers1.map(x -> x*2).forEach(y -> System.out.println(y));
        Map<String, String> collect = numbers.stream().collect(Collectors.toMap(x -> "x" + x, y -> "111" + y));
        System.out.println(collect);
    }

    @Test
    public void test3() {
        Book book = new Book();
        book.setId(1L);
        book.setBookName("aaa");
        book.setPrice(200);

        Book book1 = new Book();
        book1.setId(2L);
        book1.setBookName("bbb");
        book1.setPrice(1000);

        Book book2 = new Book();
        book2.setId(3L);
        book2.setBookName("aaa");
        book2.setPrice(1000);

        List<Book> books = new ArrayList<>();
        books.add(book);
        books.add(book1);
        books.add(book2);

//        List<Book> bookList = books.stream().filter(x -> x.getPrice() > 100).collect(Collectors.toList());
//
//        bookList.forEach(b -> System.out.println(b.getBookName()));

//        Map<String, List<Book>> collect = books.stream().collect(Collectors.groupingBy(Book::getBookName));
//        System.out.println(collect);

        Map<Long, Book> collect = books.stream().collect(Collectors.toMap(Book::getId, Function.identity()));


        System.out.println(collect);


    }


}
