package com.rock.classLoader;


import com.rock.ClassDemo;
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

        System.out.println();

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



    @Test
    public void testRewriteFindClassClassDemo4(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            System.out.println("....");
            Class<?> aClass = Class.forName("java.lang.reflect.GenericSignatureFormatError");
            System.out.println(aClass.getClassLoader());
            Class<?> aClass1 = Class.forName("java.lang.reflect.GenericSignatureFormatError");
            Class<?> aClass2 = customClassLoader01.loadClass("java.lang.reflect.GenericSignatureFormatError");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }




}