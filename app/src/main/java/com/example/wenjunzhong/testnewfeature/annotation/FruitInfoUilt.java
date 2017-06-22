package com.example.wenjunzhong.testnewfeature.annotation;

import java.lang.reflect.Field;

/**
 * Created by wenjun.zhong on 2017/6/12.
 */

public final class FruitInfoUilt {

    public static void getFruitInfo(Class<?> clazz){
        String strFruitName=" 水果名称：";
        String strFruitColor=" 水果颜色：";
        String strFruitProvicer="供应商信息：";

        Field[] fields = clazz.getDeclaredFields();
        for(Field field : fields){
            if(field.isAnnotationPresent(FruitName.class)){
                FruitName fruitName = field.getAnnotation(FruitName.class);
                System.out.println("fruit name: " + fruitName.value());
            }else if(field.isAnnotationPresent(FruitColor.class)){
                FruitColor fruitColor = field.getAnnotation(FruitColor.class);
                System.out.println("fruit color: " + fruitColor.fruitColor().toString());
            }else if(field.isAnnotationPresent(FruitProvider.class)){
                FruitProvider fruitProvider = field.getAnnotation(FruitProvider.class);
                System.out.println("fruit provider: " + fruitProvider.address());
            }
        }
    }
}
