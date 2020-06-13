package com.fh.shop.admin;

import com.fh.shop.admin.po.book.Book;
import org.junit.Test;

import java.util.Optional;

public class TestOptional {

    @Test
    public  void test1() {
        Book b = null;

        Book b2 = new Book();
        b2.setBookName("00000000");
//        Optional<Book> b1 = Optional.of(b);
//
//        Book book = b1.get();
//        System.out.println(book);
//
//        System.out.println(b1.isPresent());

        Optional<Book> b1 = Optional.ofNullable(b);
        Book book = b1.orElse(b2);
        System.out.println(book);

    }

    @Test
    public void test2() {
        Book book = null;

        Book show = show3(book);
        System.out.println(show);
    }

    public Book show(Book book) {
        if (book == null) {
            book = new Book();
            book.setBookName("aaa");
        }
        return book;
    }

    public Book show2(Book book) {
        Optional<Book> book1 = Optional.ofNullable(book);
//        if (book1.isPresent()) {
//            return book1.get();
//        } else {
//            book = new Book();
//            book.setBookName("aaa");
//            return book;
//        }
        book = new Book();
        book.setBookName("aaa");
        Book book2 = book1.orElse(book);
        return book2;
    }

    public Book show3(Book book) {
        Optional<Book> book1 = Optional.ofNullable(book);
        Book book2 = book1.orElseGet(() -> {
            Book b = new Book();
            b.setBookName("aaa");
            return b;
        });
        return book2;
    }

    @Test
    public void show4() {
        Book book = new Book();
//        book.setBookName("c");
//        Optional<Book> book1 = Optional.ofNullable(book);
//        String c = book1.map(b -> b.getBookName()).orElse("aaaa");
//        System.out.println(c);
//        if (book != null) {
//            String bookName = book.getBookName();
//            if (bookName != null) {
//                System.out.println(bookName);
//            } else {
//                System.out.println("aaaa");
//            }
//
//        } else {
//            System.out.println("aaaa");
//        }

//        Optional.ofNullable(book).ifPresent(x -> System.out.println(x.getBookName()));

        Optional<Book> book1 = Optional.ofNullable(book).filter(x -> x.getBookName().length() > 2);
        System.out.println(book1.get().getBookName());
    }


}
