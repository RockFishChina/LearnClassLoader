package com.rock.classLoader;


import com.oracle.util.Checksums;
import com.rock.ClassDemo;
import com.sun.org.apache.xpath.internal.axes.AxesWalker;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.lang.reflect.Method;

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

    /**
     * 父加载器加载不了,子加载器所加载的类，
     * 父加载器加载Parent
     * 子加载器加载son
     * Parent#getSon 方法里 new Son()对象.//报错,找不到Son的类定义.
     */
    @Test
    public void testParentCanntFindSon(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            Class<?> classParent = customClassLoader01.loadClass("com.rock.Parent");
            System.out.println("classParent:" + classParent.getClassLoader());
            Class<?> classSon = customClassLoader01.loadClass("com.rock.Son");
            System.out.println("classSon:" + classSon.getClassLoader());
            Object objParent = classParent.newInstance();

            Object objSon = classSon.newInstance();

            Method setSon = classParent.getMethod("setSon",Object.class);
            Object resultSon = setSon.invoke(objParent, objSon);
            System.out.println(resultSon.getClass());
            System.out.println(resultSon.getClass().getClassLoader());

            try {
                Method getSon = classParent.getMethod("getSon");
                Object invoke = getSon.invoke(objParent);
                System.out.println(invoke.getClass().getClassLoader());
            }catch (Exception exp){
                //java.lang.NoClassDefFoundError: com/rock/Son
                exp.printStackTrace();
            }
        }catch (Exception exp){
            exp.printStackTrace();
        }
    }

    /**
     * 不同命名空间的类互相不可见，
     */
    @Test
    public void testSifferentNamespaceClass(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        CustomClassLoader01 customClassLoader02 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            Class<?> aClass1 = customClassLoader01.loadClass("com.rock.Man");
            System.out.println("class1 : " + aClass1.getClassLoader());
            System.out.println("class1 : " + aClass1.hashCode());

            Class<?> aClass2 = customClassLoader02.loadClass("com.rock.Man");
            System.out.println("class2 : " + aClass1.getClassLoader());
            System.out.println("class2 : " + aClass2.hashCode());
            Object man1 = aClass1.newInstance();
            Object man2 = aClass2.newInstance();
            Method setFather = aClass1.getMethod("setFather", Object.class);
            setFather.invoke(man1,man2);


        }catch (Exception exp){
            exp.printStackTrace();
        }
    }


}