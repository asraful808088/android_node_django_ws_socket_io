package com.example.myapplication;

public class Config {
    private static Config intence;
    public static  synchronized Config getIntence(){
        if (intence == null) intence = new Config();
        return intence;
    }
    public  String getNodeSocketIO(){
        return "http://192.168.0.104:5000";
    }
    public  String getDjangoSocket(){
        return "ws://192.168.0.104:8000/ws/crypto";
    }
}
