package com.rock.classLoader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestForName {

    /**
     * forName 内部也会执行到ClassLoader 的 loadClass方法
     */
    @Test
    public void testForName(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            System.out.println("....");
            Class<?> aClass = Class.forName("java.lang.reflect.GenericSignatureFormatError",false,customClassLoader01);
            System.out.println(aClass.getClassLoader());
            Class<?> aClass1 = Class.forName("java.lang.reflect.GenericSignatureFormatError",false,customClassLoader01);
            Class<?> aClass2 = customClassLoader01.loadClass("java.lang.reflect.GenericSignatureFormatError");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
