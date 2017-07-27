package eric.aop.aspectj;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import eric.aop.BaseActivity;
import eric.aop.R;

/**
 *
 * Created by wenjun.zhong on 2017/7/26.
 */

public class AspectJActivity extends BaseActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TestAround testAround = new TestAround();
        testAround.testAround();
        testAround.testAop();
    }
}
