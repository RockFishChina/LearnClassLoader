package com.rock;

public class Parent {

    private Object son;

    public Object getSon(){
        return new Son();
    }
    public Object setSon(Object son){
        this.son = son;
        return this.son;
    }
}
