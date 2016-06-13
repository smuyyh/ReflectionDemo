package com.yuyh.reflection.java;

/**
 * @author yuyh.
 * @date 2016/6/13.
 */
public class Student extends People implements Exam{

    public String sno;

    public Student(String sno) {
        this.sno = sno;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    @Override
    public void doExam() {
        // TODO exam
    }
}
