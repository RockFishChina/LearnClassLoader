package com.rock.classLoader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


@RunWith(JUnit4.class)
public class TestCustomLoadClass {

    @Test
    public void testLoadClass(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            Class<?> aClass = customClassLoader01.loadClass("com.rock.Man");
            Class<?> aClass1 = customClassLoader01.loadClass("com.rock.Man");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}
