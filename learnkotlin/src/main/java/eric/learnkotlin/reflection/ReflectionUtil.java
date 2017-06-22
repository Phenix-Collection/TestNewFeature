package eric.learnkotlin.reflection;

import java.io.File;
import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * Created by wenjun.zhong on 2017/6/14.
 */

public final class ReflectionUtil {
    private ReflectionUtil(){}

    public static void MyClassReflection(){
        try {
            //反射构造函数
            Constructor constructor = MyClass.class.getConstructor(int.class);
            MyClass myClassReflect = (MyClass) constructor.newInstance(10);

            Field[] fields = MyClass.class.getFields();
            Field[] declaredFields = MyClass.class.getDeclaredFields();
            System.out.println("MyClass fields count: " + fields.length + "; declaredFields count: " + declaredFields.length);


            //反射获取公有方法
            Method method = MyClass.class.getMethod("increaseNumber", int.class);
            method.invoke(myClassReflect, 10);

            //反射获取私有方法
            Method method1 = MyClass.class.getDeclaredMethod("increase", int.class);
            method1.setAccessible(true);
            method1.invoke(myClassReflect, 11);

            //反射获取公有域
            Field publicField = MyClass.class.getField("number");
            System.out.println("reflect public number: " + publicField.getInt(myClassReflect));

            //反射获取私有域
            Field field = MyClass.class.getDeclaredField("count");
            field.setAccessible(true);
            System.out.println("reflect private count: " + field.getInt(myClassReflect));

            System.out.println("string array class name: " + String[].class.getName());
            System.out.println("int array class name: " + int[].class.getName());
            System.out.println("float array class name: " + float[].class.getName());


            Class stringArrayClass1 = Array.newInstance(String.class, 0).getClass();
            System.out.println("is array: " + stringArrayClass1.isArray());

            int[] strings = new int[3];
            Class stringArrayClass = strings.getClass();
            Class stringArrayComponentType = stringArrayClass.getComponentType();
            System.out.println(stringArrayComponentType);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
