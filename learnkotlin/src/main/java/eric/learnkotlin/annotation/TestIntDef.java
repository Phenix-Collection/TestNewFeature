package eric.learnkotlin.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by wenjun.zhong on 2017/7/11.
 */

public class TestIntDef {

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({INT_DEF_CODE_1, INT_DEF_CODE_2, INT_DEF_CODE_3})
    public @interface CodeValue {}

    public static final int INT_DEF_CODE_1 = 1;
    public static final int INT_DEF_CODE_2 = 2;
    public static final int INT_DEF_CODE_3 = 3;

    private int code;

    public void setCode(@CodeValue int code) {
        this.code = code;
    }

    @CodeValue
    public int getCode() {
        return code;
    }
}
