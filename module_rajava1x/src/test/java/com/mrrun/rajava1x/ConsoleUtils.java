package com.mrrun.rajava1x;

public class ConsoleUtils {

    public static void println(String msg){
        System.out.println(msg);
    }

    public static void showThreadName(String msg){
        println(String.format("\033[31;4m%s\033[0m所在线程:\033[31;4m%s\033[0m",msg, Thread.currentThread().getName()));
    }
}
