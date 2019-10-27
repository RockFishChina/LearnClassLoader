package com.rock.classLoader;


import com.oracle.util.Checksums;
import com.rock.ClassDemo;
import com.sun.org.apache.xpath.internal.axes.AxesWalker;
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


    /**
     * StrictMath 是boostrap类加载器加载的。
     * loadClass的方式，AppClassLoader不是其初始类
     */
    @Test
    public void testInitialCL(){
        Class<?> StrictMath  = null;
        try {
            System.out.println("start");
            StrictMath = ClassLoader.getSystemClassLoader().loadClass("java.lang.StrictMath");
            System.out.println(StrictMath.getClassLoader());
            StrictMath = ClassLoader.getSystemClassLoader().loadClass("java.lang.StrictMath");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * trictMath 是boostrap类加载器加载的。
     * import的方式，AppClassLoader是其初始类
     */
    @Test
    public void testInitialCL1(){

        try {
            System.out.println("start");
            ClassDemo classDemo = new ClassDemo();
            System.out.println("ClassDemo classLoader ：" + ClassDemo.class.getClassLoader());
            classDemo.getMath();//此方法会触发StrictMath类的加载
            System.out.println("------");
            System.out.println(StrictMath.class.getClassLoader());
            Class<?> strictMathClass = ClassLoader.getSystemClassLoader().loadClass("java.lang.StrictMath");
            System.out.println(strictMathClass.getClassLoader());

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    //编译后生成的ClassDemo2 移动到 C:\LearnClassLoader\temp\ 目录下，
    @Test
    public void testRewriteFindClassClassDemo2(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            System.out.println("....");
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

    //    java.lang.reflect.GenericSignatureFormatError
    @Test
    public void testRewriteFindClassClassDemo3(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            System.out.println("....");
            Class<?> aClass = customClassLoader01.loadClass("java.lang.reflect.GenericSignatureFormatError");
            System.out.println(aClass.getClassLoader());
            //再加载一次，验证一下，初始类加载器的findLoadedClass方法
            //断点调试，发现 CustomClassLoader01的findLoadedClass返回还是null，为什么?
            Class<?> aClass1 = customClassLoader01.loadClass("java.lang.reflect.GenericSignatureFormatError");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


}