package com.fh.shop.admin.demo;

public class Student {

    private Long id;

    private String stuName;

    private int sex;

    private int age;

    private int score;

    private String className;

    public Student() {

    }

    public Student(Long id, String stuName, int sex, int age, String className, int score) {
        this.id = id;
        this.stuName = stuName;
        this.sex = sex;
        this.age = age;
        this.className = className;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
}
