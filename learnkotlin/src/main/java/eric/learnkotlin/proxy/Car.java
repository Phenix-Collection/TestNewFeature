package eric.learnkotlin.proxy;

/**
 * Created by wenjun.zhong on 2017/6/13.
 */

public class Car implements IVehical {
    @Override
    public void run() {
        System.out.println("Car is running");
    }
}
