package com.rock.classLoader;

import com.rock.MsgCenter;
import com.rock.MsgCenterImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

@RunWith(JUnit4.class)
public class TestImportLoadClass {

    /**
     * 把MsgCenterImpl.class 移动到子加载器的加载路径中.保证只有子加载器可加载.
     */
    @Test
    public void testImportLoadClass(){
        CustomClassLoader01 customClassLoader01 = new CustomClassLoader01(ClassLoader.getSystemClassLoader());
        try {
            //appClassloader 加载 接口
            MsgCenter msgCenter = null;
            //子加载器,加载 实现类
            Class<?> aClass = Class.forName("com.rock.MsgCenterImpl",false,customClassLoader01);
            //赋值 接口 = 实现类 ;这是可以的,因为子加载器可见父加载器所加载的类.
            msgCenter =(MsgCenter)aClass.newInstance();
            boolean hello = msgCenter.sendMsg("hello");
            System.out.println("after sendMsg");

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
    }
}
