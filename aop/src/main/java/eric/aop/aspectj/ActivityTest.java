package eric.aop.aspectj;

import org.aspectj.lang.JoinPoint;

/**
 * Created by wenjun.zhong on 2017/7/27.
 */

public class ActivityTest {

    /**
     * test {@link TraceAspect#activityFragmentPrefixAllMethodBefore(JoinPoint)}
     */
    private void a(){

    }

    private int testAround(){
        int a = 4;
        return a;
    }
}
