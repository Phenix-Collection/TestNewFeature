package eric.learnkotlin.annotation;

/**
 * Created by wenjun.zhong on 2017/6/13.
 */

@Description(value = "这是一个有用的工具类")
public class Utility {

    @Author(name = "eric", group = "developer team")
    public String work(){
        return "work over!";
    }
}
