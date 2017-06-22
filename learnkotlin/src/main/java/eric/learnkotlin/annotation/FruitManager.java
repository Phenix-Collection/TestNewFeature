package eric.learnkotlin.annotation;

/**
 * Created by wenjun.zhong on 2017/6/9.
 */

public class FruitManager {
    private static final FruitManager ourInstance = new FruitManager();

    public static FruitManager getInstance() {
        return ourInstance;
    }

    private FruitManager() {
    }
}
