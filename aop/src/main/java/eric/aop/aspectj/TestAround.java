package eric.aop.aspectj;

import eric.aop.DebugLog;
import eric.aop.aspectj.annotation.DebugTrace;

/**
 *
 * Created by wenjun.zhong on 2017/7/27.
 */

public class TestAround {
    @DebugTrace
    public int testAround(){
        int a = 6;

        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        testCall();
        return a;
    }

    public  void testCall(){
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    void testAop(){
        DebugLog.d("test aop");
    }
}
