package com.nhnacademy.westloverock.gateway.util;

public final class Times {
    public Times() {
        throw new IllegalStateException("It's util object");
    }
    public final static int MINUTE = 60;
    public final static int HOUR = MINUTE * 60;
    public final static int DAY = HOUR * 24;
    public final static int WEEK = DAY * 7;
}