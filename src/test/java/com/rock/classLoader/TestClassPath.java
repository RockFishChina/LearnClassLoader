package com.rock.classLoader;

import com.pay.PayCenter;
import com.sun.tools.javac.util.ArrayUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RunWith(JUnit4.class)
public class TestClassPath {

    @Test
    public void testPay(){
        PayCenter payCenter = new PayCenter();
        payCenter.pay();
        String classPath = System.getProperty("java.class.path");
        String[] pathAry = classPath.split(":");
        List<String> pathList = Arrays.asList(pathAry);
        pathList.stream().forEach(System.out::println);

    }
}
