package com.bean;

public class TimeService {
    private String time;


    public TimeService(String time) {
        super();
        this.time = time;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


}

class Test {
    public static void main(String[] args) {
        String s1="2020-07-22";
        String s2="2020-07-22";
        System.out.println(s2.compareTo(s1));

    }
}