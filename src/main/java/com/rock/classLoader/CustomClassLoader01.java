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

    @Override
    protected Class<?> findClass(String className) throws ClassNotFoundException {
        byte[] data = loadClassData(className);
        return this.defineClass(className,data,0,data.length);
    }

    private byte[] loadClassData(String name) {
        InputStream inputStream = null;
        ByteArrayOutputStream byteArrayOutputStream = null;
        byte[] bytes = null;
        try {
            name = name.replace(".","/");
            name = "/Users/rockfish/Desktop/temp/"+ name;
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
