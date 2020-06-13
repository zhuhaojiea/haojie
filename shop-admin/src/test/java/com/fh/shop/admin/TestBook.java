package com.fh.shop.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fh.shop.admin.mapper.book.IBookMapper;
import com.fh.shop.admin.po.book.Book;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-common.xml"})
public class TestBook {

    @Autowired
    private IBookMapper bookMapper;

    @Test
    public void testAdd() {
        Book book = new Book();
        book.setBookName("三国222");
        book.setCreateDate(new Date());
        book.setPrice(200);
        bookMapper.insert(book);
    }

    @Test
    public void testDel() {
        bookMapper.deleteById(1);
    }

    @Test
    public void testBatch() {
        List<Long> ids = new ArrayList<>();
        ids.add(1212663049978220547L);
        ids.add(1212663049978220548L);
        ids.add(1212663049978220549L);
        bookMapper.deleteBatchIds(ids);
    }

    @Test
    public void testUpdate() {
        Book book = new Book();
        book.setId(1212663049978220546L);
        book.setBookName("aaaaaaaaaaaa");
        bookMapper.updateById(book);
    }

    @Test
    public void testSelect() {
        Book book = bookMapper.selectById(1212663049978220546L);
        System.out.println(book);
    }

    @Test
    public void testSelect6() {
        List<Book> books = bookMapper.selectList(null);
        System.out.println(books);
    }

    @Test
    public void testSelect2() {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.like("bookname", "a");
        List<Book> books = bookMapper.selectList(bookQueryWrapper);
        System.out.println(books);
    }

    @Test
    public void testSelect3() {
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.between("price", 1000, 2000);
        List<Book> books = bookMapper.selectList(bookQueryWrapper);
        System.out.println(books);
    }

    @Test
    public void testSelect4() {
        // 查询价格在1000,3000之间，名字中保存c
        QueryWrapper<Book> bookQueryWrapper = new QueryWrapper<>();
        bookQueryWrapper.between("price", 1000, 3000);
        bookQueryWrapper.like("bookname", "c");
        List<Long> ids = new ArrayList<>();
        ids.add(1212663049978220552L);
        bookQueryWrapper.in("id", ids);
        List<Book> books = bookMapper.selectList(bookQueryWrapper);
        System.out.println(books);
    }

    @Test
    public void test8() {
        IPage<Book> bookIPage = new Page<>();
        bookIPage.setSize(3);
        bookIPage.setCurrent(2);
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("id");
        queryWrapper.between("price", 1000, 3000);
        IPage<Book> bookIPage1 = bookMapper.selectPage(bookIPage, queryWrapper);
        List<Book> records = bookIPage1.getRecords();
        for (Book record : records) {
            System.out.println(record.getBookName());
        }
    }

}
