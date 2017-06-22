package eric.learnkotlin.reflection;

import java.net.URL;

/**
 * Created by wenjun.zhong on 2017/6/13.
 */

public class MyClass {
    public static int LENGTH = 1;
    private static int WIDTH = 2;
    private int count;
    public int number;
    public MyClass(int init){
        count = init;
        number = 12;

        ClassLoader loader = ClassLoader.getSystemClassLoader();
        while (loader != null){
            System.out.println(loader.toString());
            loader = loader.getParent();
        }


        ClassLoader classLoader = MyClass.class.getClassLoader();
        while (classLoader != null){
            System.out.println(classLoader.toString());
            classLoader = classLoader.getParent();
        }
    }

    private void increase(int step){
        count += step;
    }

    public void increaseNumber(int step){
        number += step;
    }
}
