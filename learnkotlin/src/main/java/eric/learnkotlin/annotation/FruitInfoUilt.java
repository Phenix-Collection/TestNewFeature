package eric.learnkotlin.annotation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

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

    public static void getUtilityInfo(){
        try {
            Class<?> rtClass = Class.forName(Utility.class.getName());


            if(rtClass.isAnnotationPresent(Description.class)){
                Method[] methods = rtClass.getMethods();

                Description description = rtClass.getAnnotation(Description.class);
                System.out.println("description: " + description.value());

                for(Method method : methods){
                    if(method.isAnnotationPresent(Author.class)){
                        Author author = method.getAnnotation(Author.class);
                        System.out.println("author name:" + author.name() + "; group: " + author.group());
                    }
                }
            }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
