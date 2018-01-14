package com.astro.exceptions;

/**
 * Created by astro on 2018/1/11.
 */
public class ProductOperationException extends RuntimeException{

    public ProductOperationException(String msg){
            super(msg);
    }
}
