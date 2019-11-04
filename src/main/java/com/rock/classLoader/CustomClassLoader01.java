package com.rock.classLoader;

import java.io.*;

/**
 * @Author rock
 * @create 2019-10-26 19:31
 * @Description
 */
public class CustomClassLoader01 extends ClassLoader {

    public CustomClassLoader01(ClassLoader parent) {
        super(parent);
    }

   /* @Override
    protected Object getClassLoadingLock(String className) {
        return super.getClassLoadingLock(className);
    }*/

    @Override
    protected Class<?> loadClass(String name, boolean resolve)throws ClassNotFoundException
    {
        synchronized (super.getClassLoadingLock(name)) {
            //判断是否已加载
            //指定加载器去加载
            //若还未加载,则调用findClass方法自己去加载
            System.out.println("start  load class :" + name + "  ; resolve : " + resolve);
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            System.out.println("findLoadedClass : " + c);
            if (c == null) {
                try {
                    c = getParent().loadClass(name);
                } catch (ClassNotFoundException e) {
                    System.out.println("getParent().loadClass(name) : ClassNotFoundException");
                    //父加载器加载不到会报错.
                }

                if (c == null) {
                    c = findClass(name);
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        byte[] data = loadClassData(className);
        return this.defineClass(className,data,0,data.length);
    }

    private byte[] loadClassData(String name) {
        System.out.println("loadClassData : " + name);
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] bytes = null;
        try {
            name = name.replace(".","/");
            name = "/Users/kris/Desktop/temp/"+ name;
            //name = "C:\\LearnClassLoader\\temp\\" + name;
            inputStream = new FileInputStream(new File(name + ".class"));
            byteArrayOutputStream = new ByteArrayOutputStream();
            int ch = 0;
            while( -1 != (ch= inputStream.read())){
                byteArrayOutputStream.write(ch);
            }

            bytes = byteArrayOutputStream.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                byteArrayOutputStream.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return bytes;
    }


}


