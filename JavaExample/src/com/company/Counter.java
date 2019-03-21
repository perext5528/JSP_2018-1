package com.company;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;

import static java.lang.System.out;

/*class Updater {
    public void update(Counter counter) {
        counter.count++;
    }
}*/

public class Counter {

    /*int count = 0;  // 객체변수*/

    /*String name;
    public void setName(String name){
        this.name = name;
    }
     int out1(){
        return 1;
    }
    String outa(){
        return "ABC";
    }
    int sum(int a, int b){
        return a+b;
    }
    public String say(){
        return "hi";
    }*/

    public static void main(String[] args) throws IOException {
        FileWriter fw = new FileWriter("C:/Users/azure/OneDrive/Desktop/a.txt");
        for(int i=1; i<11; i++){
            String data = i+" 번째 줄입니다. \r\n";
            fw.write(data);
        }
        fw.close();
        /*Counter myCounter = new Counter();
        System.out.println("before update:"+myCounter.count);
        Updater myUpdater = new Updater();
        myUpdater.update(myCounter);
        System.out.println("after update:"+myCounter.count);
*/


        /*Animal cat = new Animal();
        cat.setName("Tom");
        out.println(cat.name);

        *//*int a=3;
        int b=4;*//*
        int c = cat.sum(3, 4);
        out.println(c);
        out.print(cat.say());*/


       /* int i = 9;
        //i=0;
        for (i = 0; i <= 1; i++) {
            out.print("asdasd");
        }

        out.print(i);
*/
        /*int treeHit = 0;
        while (treeHit >= 0) {
            treeHit++;
            System.out.println("나무를  " + treeHit + "번 찍었습니다.");
            if (treeHit == 10) {
                System.out.println("나무 넘어갑니다.");
            }
        }
        System.out.print("코드 끝");
*/

        /*int month = 69;
        String monthString = "";
        switch (month) {
            case 1:
                monthString = "January";
                break;
            case 2:
                monthString = "February";
                break;
            case 3:
                monthString = "March";
                break;
            case 4:
                monthString = "April";
                break;
            case 5:
                monthString = "May";
                break;
            case 6:
                monthString = "June";
                break;
            case 7:
                monthString = "July";
                break;
            case 8:
                monthString = "August";
                break;
            case 9:
                monthString = "September";
                break;
            case 10:
                monthString = "October";
                break;
            case 11:
                monthString = "November";
                break;
            case 12:
                monthString = "December";
                break;
            default:
                monthString = "Invalid month";
                break;
        }
        System.out.println(monthString);
    }*/


        /*String[] weeks = {"월", "화", "수", "목", "금", "토", "일"};
        System.out.print(weeks.length);*/



        /*int b = 180;
        int h = 185;
        boolean istall = h>b;
        if(istall){
            System.out.print("키가 큽니다");
        }*/


        /*System.out.println("Hello world");
        double asd = 23.23;*/



        /*int a;    //정수를 넣는 변수

        // 문자열을 넣는 변수
        String b;
*//*
프로그램의 저작권

이 프로그램의 저작권은 홍길동에게 있습니다.
Copyright 2013.
*//*
        a = 1;
        b = "hello java";

        System.out.print(a);
        System.out.print(b);*/


    }
}