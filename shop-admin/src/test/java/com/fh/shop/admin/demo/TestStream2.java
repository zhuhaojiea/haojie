package com.fh.shop.admin.demo;

import org.junit.Test;

import java.util.ArrayList;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TestStream2 {

    @Test
    public void test1() {
        Student s1 = new Student(1L, "zhangsan", 1, 20, "1906", 90);
        Student s2 = new Student(2L, "lisi", 1, 26, "1906", 60);
        Student s3 = new Student(3L, "xiaohua", 0, 19, "1907", 75);

        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);

        // 求出来最小年龄的那个学生
        // stream
        Student student = studentList.stream().min((a, b) -> (a.getAge() - b.getAge())).get();
        System.out.println(student);

        // 求出来得分最低的那个学生
        Student student1 = studentList.stream().min((x, y) -> (x.getScore() - y.getScore())).get();
        System.out.println(student1);

        // 求出年龄最大的那个学生
        Student student2 = studentList.stream().max((x, y) -> (x.getAge() - y.getAge())).get();
        System.out.println(student2);

        // 求分数最高的那个学生
        Student student3 = studentList.stream().max((x, y) -> (x.getScore() - y.getScore())).get();
        System.out.println(student3);
    }

    @Test
    public void test2() {
        Student s1 = new Student(1L, "zhangsan", 1, 20, "1906", 90);
        Student s2 = new Student(2L, "lisi", 1, 26, "1906", 60);
        Student s3 = new Student(3L, "xiaohua", 0, 19, "1907", 75);
        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);

        // 所有学生的得分总和
        Integer collect = studentList.stream().collect(Collectors.summingInt(Student::getScore));
        System.out.println(collect);

//        // 所有学生的平均分
//        Double collect1 = studentList.stream().collect(Collectors.averagingDouble(x -> x.getScore()));
//        System.out.println(collect1);
//
//        Double collect2 = studentList.stream().collect(Collectors.averagingInt(x -> x.getScore()));
//        System.out.println(collect2);
//
//        // 所有学生的平均年龄
//        Double collect3 = studentList.stream().collect(Collectors.averagingInt(x -> x.getAge()));
//        System.out.println(collect3);

    }

    @Test
    public void test3() {
        Student s1 = new Student(1L, "zhangsan", 1, 20, "1906", 90);
        Student s2 = new Student(2L, "lisi", 1, 26, "1906", 60);
        Student s3 = new Student(3L, "xiaohua", 0, 19, "1907", 75);
        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);

        // 按照班级进行分组
//        {
//            1906:[{id:1,stuName:zhangsan},{id:2,stuName:lisi}],
//            1907:[{id:3,stuName:xiaohua}]
//        }
        Map<String, List<Student>> collect = studentList.stream().collect(Collectors.groupingBy(x -> x.getClassName()));
        System.out.println(collect);
    }

    @Test
    public void test4() {
        Student s1 = new Student(1L, "zhangsan", 1, 20, "1906", 90);
        Student s2 = new Student(2L, "lisi", 1, 26, "1906", 60);
        Student s3 = new Student(3L, "xiaohua", 0, 19, "1907", 75);
        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);



//        Integer collect = studentList.stream().collect(Collectors.summingInt(x -> x.getAge()));
//        System.out.println(collect);
//
//        Double collect1 = studentList.stream().collect(Collectors.averagingInt(x -> x.getAge()));
//        System.out.println(collect1);

        // 通过一个方法获取 年龄 的最大值，最小值，平均值，总和
        IntSummaryStatistics collect2 = studentList.stream().collect(Collectors.summarizingInt(x -> x.getAge()));
        // 平均值
        double average = collect2.getAverage();
        // 个数
        long count = collect2.getCount();
        // 最大值
        int max = collect2.getMax();
        // 最小值
        int min = collect2.getMin();
        // 总和
        long sum = collect2.getSum();

        System.out.println(average+":"+count+":"+max+":"+min+":"+sum);
    }

    @Test
    public void test5() {
        Student s1 = new Student(1L, "zhangsan", 1, 20, "1906", 90);
        Student s2 = new Student(2L, "lisi", 1, 26, "1906", 60);
        Student s3 = new Student(3L, "xiaohua", 0, 19, "1907", 75);
        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);

        // 按照班级进行分组
//        {
//            1906:[{id:1,stuName:zhangsan},{id:2,stuName:lisi}],
//            1907:[{id:3,stuName:xiaohua}]
//        }
//        Map<String, List<Student>> collect = studentList.stream().collect(Collectors.groupingBy(x -> x.getClassName()));
//        System.out.println(collect);

        // 按照班级分组后，每班的平均得分
//        {
//            1906:22.3
//            1907:30.6
//        }

//        Map<String, Double> collect1 = studentList.stream()
//                                        .collect(Collectors.groupingBy(x -> x.getClassName(), Collectors.averagingInt(y -> y.getScore())));
//
//        System.out.println(collect1);

        // 按照班级分组后，获取各班的 总分
//        Map<String, Integer> collect = studentList.stream().collect(Collectors.groupingBy(y -> y.getClassName(), Collectors.summingInt(a -> a.getScore())));
//
//        System.out.println(collect);

        Map<String, IntSummaryStatistics> collect1 = studentList.stream()
                .collect(Collectors.groupingBy(x -> x.getClassName(), Collectors.summarizingInt(y -> y.getScore())));

        System.out.println(collect1);

        collect1.forEach((x,y) -> {
            System.out.println(x+":"+y.getMin()+":"+y.getMax()+":"+y.getCount()+":"+y.getAverage()+":"+y.getSum());
        });
    }

    @Test
    public void test6() {
        Student s1 = new Student(1L, "zhangsan", 1, 20, "1906", 90);
        Student s2 = new Student(2L, "lisi", 1, 26, "1906", 60);
        Student s3 = new Student(3L, "xiaohua", 0, 19, "1907", 75);
        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);

//        Collectors.partitioningBy(判断条件) : 只会返回 两组数据，
//        符合判断条件的 key:true,value:符合条件的数据集合
//        不符合判断条件的 key:false,value:不符合条件的数据集合
//        Map<Boolean, List<Student>> collect = studentList.stream().collect(Collectors.partitioningBy(x -> x.getAge() >= 20));
//        System.out.println(collect);


        Map<Boolean, List<Student>> collect1 = studentList.stream().collect(Collectors.partitioningBy(x -> x.getSex() == 1));
        System.out.println(collect1);
    }

    @Test
    public void test7() {
        Student s1 = new Student(1L, "zhangsan", 1, 20, "1906", 90);
        Student s2 = new Student(2L, "lisi", 1, 26, "1906", 60);
        Student s3 = new Student(3L, "xiaohua", 0, 19, "1907", 75);
        List<Student> studentList = new ArrayList<>();
        studentList.add(s1);
        studentList.add(s2);
        studentList.add(s3);

        // 将List转为Map,用id作为map的key,用名字作为map的值
//        Map<Long, String> collect = studentList.stream().collect(Collectors.toMap(x -> x.getId(), y -> y.getStuName()));
//        System.out.println(collect);

        // 将List转为Map,用id作为map的key,用 名字:得分 作为map的值
//        Map<Long, String> collect1 = studentList.stream().collect(Collectors.toMap(x -> x.getId(), y -> y.getStuName() + ":" + y.getScore()));
//        System.out.println(collect1);

        // 将List转为Map,用id作为map的key,用Student作为map的值
//        Map<Long, Student> collect = studentList.stream().collect(Collectors.toMap(x -> x.getId(), fh -> fh));
//        System.out.println(collect);
        Map<Long, String> collect = studentList.stream().collect(Collectors.toMap(x -> x.getId(), y -> y.getStuName()));
        Map<Long, String> collect1 = studentList.stream().collect(Collectors.toMap(Student::getId, Student::getStuName));
        System.out.println(collect1);


    }
}
