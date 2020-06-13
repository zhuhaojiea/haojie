package com.fh.shop.admin;

import com.fh.shop.admin.po.book.Book;
import org.junit.Test;

import java.util.Optional;

public class TestOptionalInfo {

    @Test
    public void test1() {
        Book book = new Book();
        book.setBookName("z");
//        book.setBookName("kkkk");

//        if (book != null) {
//            String bookName = book.getBookName();
//            if (null != bookName) {
//                System.out.println(bookName);
//            } else {
//                System.out.println("图书名为null");
//            }
//        } else {
//            System.out.println("图书为null");
//        }

//        String bookName = Optional.ofNullable(book).orElseGet(() -> {
//            Book book1 = new Book();
//            book1.setBookName("cccc");
//            return book1;
//        }).getBookName();
//        System.out.println(bookName);

        String s = Optional.ofNullable(book)
                .map(x -> x.getBookName())
                .map(y -> y.toUpperCase())
//                .filter(z -> z.length() > 2)
                .orElse("-------");
        System.out.println(s);


    }
}
