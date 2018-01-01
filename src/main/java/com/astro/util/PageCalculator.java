package com.astro.util;

public class PageCalculator {

    public static int caltulateRowIdex(int pageIndex,int pageSize){
        return (pageIndex>0) ? (pageIndex-1)*pageSize : 0;
    }
}
