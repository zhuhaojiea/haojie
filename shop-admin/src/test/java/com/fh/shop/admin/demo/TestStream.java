package com.fh.shop.admin.demo;

import org.junit.Test;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestStream {

    @Test
    public void test1() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");


        // 我想把list中所有的图书名都转换为大写
//        List<String> infoList = new ArrayList<>();
//        for (String bookName : bookNames) {
//            infoList.add(bookName.toUpperCase());
//        }
//        System.out.println(infoList);

        // stream
//          stream() ： 将集合转换伟stream流
////        map(): 数据转换
////        collect：收集，
////        Collectors.toList()：将结果转换为list
        List<String> booksNameList = bookNames.stream().map(x -> x.toUpperCase()).collect(Collectors.toList());
        System.out.println(booksNameList);
    }

    @Test
    public void test2() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");

        // 获取图书名的长度大于2的集合
//        List<String> nameList = new ArrayList<>();
//        for (String bookName : bookNames) {
//            if (bookName.length() > 2) {
//                nameList.add(bookName);
//            }
//        }
//        System.out.println(nameList);

        // stream
        List<String> collect = bookNames.stream().filter(y -> y.length() > 2).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test3() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");

        // 获取图书名的长度大于2的集合,并把集合中的每个图书名都转换大写
        // 老方法
//        List<String> nameList = new ArrayList<>();
//        for (String bookName : bookNames) {
//            if (bookName.length() > 2) {
//                String s = bookName.toUpperCase();
//                nameList.add(s);
//            }
//        }
//        System.out.println(nameList);

        // stream怎么写? 一行搞定!!!
        List<String> collect = bookNames.stream().filter(x -> x.length() > 2)
                                                 .map(y -> y.toUpperCase())
                                                 .collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test4() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");
        bookNames.add("aaa");
        bookNames.add("cc");

        // 把list集合中重复的数据过滤掉

        //老方法 把list转换为set
//        Set<String> bookNameSet = new HashSet<>();
//        for (String bookName : bookNames) {
//            bookNameSet.add(bookName);
//        }
//        System.out.println(bookNameSet);

        // stream
        List<String> collect = bookNames.stream().distinct().collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test5() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");
        bookNames.add("aaa");
        bookNames.add("cc");

        // 过滤集合中的重复数据，之后把过滤后的数据转为 长度:原有字符串 的格式，并将长度大于6的给过滤掉,最终结果放到list中
        // 3:aaa 4:bbbb 5:ddddd

        // 老方法
//        List<String> resultList = new ArrayList<>();
//        Set<String> bookNameSet = new HashSet<>();
//        // 过滤重复数据
//        for (String bookName : bookNames) {
//            bookNameSet.add(bookName);
//        }
//        Iterator<String> iterator = bookNameSet.iterator();
//        while (iterator.hasNext()) {
//            String next = iterator.next();
//            // 长度:原有字符串
//            String temp = next.length()+":"+next;
//            // 长度大于6的给过滤掉
//            if (temp.length() <= 6) {
//                resultList.add(temp);
//            }
//        }
//        System.out.println(resultList);

        // stream
        List<String> collect = bookNames.stream().distinct().map(x -> x.length() + ":" + x).filter(y -> y.length() <= 6).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test6() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");
        bookNames.add("aaa");
        bookNames.add("cc");

        // 将list中的数据进行排序 【字典排序】
        // stream
//        List<String> collect = bookNames.stream().sorted().collect(Collectors.toList());
//        System.out.println(collect);
        // aaa aaa bbbb cc cc ddddd
        // 3 3 4 2 2 5
        //
//        bookNames.stream().sorted().map(x -> x.length()).forEach(y -> System.out.println(y));
        // 3 4 5 2 3 2
        // 2 2 3 3 4 5
        bookNames.stream().map(x -> x.length()).sorted().forEach(y -> System.out.println(y));
    }

    @Test
    public void test7() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");
        bookNames.add("aaa");
        bookNames.add("cc");

        // 过滤掉重复的数据后的个数
//        老方法 把list转换为set
//        Set<String> bookNameSet = new HashSet<>();
//        for (String bookName : bookNames) {
//            bookNameSet.add(bookName);
//        }
//        System.out.println(bookNameSet.size());

        // stream
        long count = bookNames.stream().distinct().count();
        System.out.println(count);
    }

    @Test
    public void test8() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");
        bookNames.add("aaa");
        bookNames.add("cc");

        // 想要获取list集合中的前两项数据
        List<Integer> countList = new ArrayList<>();
        bookNames.stream().limit(2).forEach(x -> {
            int len = x.length();
            countList.add(len);
        });
        countList.forEach(x -> System.out.println(x));
//        List<String> collect = bookNames.stream().limit(2).collect(Collectors.toList());
//        System.out.println(collect);
    }

    @Test
    public void test9() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");
        bookNames.add("aaa");
        bookNames.add("cc");

        // 想取出 中间的数据
//        bookNames.stream().skip(2).limit(2).forEach(x -> System.out.println(x));

        List<String> collect = bookNames.stream().skip(2).limit(2).collect(Collectors.toList());
        System.out.println(collect);
    }

    @Test
    public void test10() {
        List<String> bookNames = new ArrayList<>();
        bookNames.add("aaaaaa");
        bookNames.add("bbbb");
        bookNames.add("ddddd");
        bookNames.add("cc");
        bookNames.add("aaa");
        bookNames.add("cc");
        // aaaaaa bbbb ddddd aaa
        // aaaaaa aaa bbbb ddddd
        // 6 3 4 5
        // 3 4 5 6
        bookNames.stream().filter(x -> x.length() > 2).sorted().map(y -> y.length()).sorted().map(a -> "飞狐:"+a).forEach(x -> System.out.println(x));
    }
}
