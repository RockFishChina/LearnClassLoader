package com.rock.classLoader;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestUnloadClass {

    @Test
    public void unloadClass() throws ClassNotFoundException {
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        Class<?> aclass = customClassLoader01.loadClass("com.rock.Parent");
        System.out.println("aclass:" + aclass.getClassLoader());
        aclass = null;
        customClassLoader01 = null;

        System.gc();

        System.out.println("after gc");


    }
}
