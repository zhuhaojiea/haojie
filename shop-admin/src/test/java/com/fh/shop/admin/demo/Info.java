package com.fh.shop.admin.demo;

import com.fh.shop.admin.po.book.Book;

public class Info {

    public void show(Book book) {
        System.out.println(book.getBookName()+"===="+book.getPrice());
    }
}
