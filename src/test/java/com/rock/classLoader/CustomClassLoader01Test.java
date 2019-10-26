package com.rock.classLoader;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @Author rock
 * @create 2019-10-26 19:48
 * @Description
 */
@RunWith(JUnit4.class)
public class CustomClassLoader01Test {

    @Test
    public void testRewriteFindClass(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            Class<?> aClass = customClassLoader01.loadClass("com.rock.ClassDemo");
            System.out.println(aClass.getClassLoader());
            //再加载一次，验证一下，初始类加载器的findLoadedClass方法
            //断点调试，发现 CustomClassLoader01的findLoadedClass返回是null，
            // 说明CustomClassLoader01 不是 com.rock.ClassDemo 的初始类加载器。
            // 虽然调用了 CustomClassLoader01 的loadClass,但是ClassDemo 已经被加载过了，
            // 所以现在即使调用了 loadClass也不算是初始类加载器。
            Class<?> aClass1 = customClassLoader01.loadClass("com.rock.ClassDemo");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    //编译后生成的ClassDemo2 移动到 C:\LearnClassLoader\temp\ 目录下，
    @Test
    public void testRewriteFindClassClassDemo2(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            Class<?> aClass = customClassLoader01.loadClass("com.rock.ClassDemo2");
            System.out.println(aClass.getClassLoader());
            //再加载一次，验证一下，初始类加载器的findLoadedClass方法
            //断点调试，发现 CustomClassLoader01的findLoadedClass返回不是null，
            // 说明CustomClassLoader01 是 com.rock.ClassDemo2 的初始类加载器。
            // 再找一个 rt.jar中一个比较生僻的类，来测试一下。
            Class<?> aClass1 = customClassLoader01.loadClass("com.rock.ClassDemo2");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}