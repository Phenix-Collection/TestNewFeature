package eric.aop.aspectj;

import android.util.Log;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import eric.aop.DebugLog;

/**
 *
 * Created by wenjun.zhong on 2017/7/27.
 */

@Aspect
public final class TraceAspect {
    private static final String TAG = "TraceAspect";

    /**
     * 截获android.app.Activity中以on开头的所有方法之前
     * 
     * @param joinPoint
     * @throws Throwable
     */
    @Before("execution(* android.app.Activity.on**(..))")
    public void activityOnMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toLongString();
        DebugLog.d(TAG, String.format("activityOnMethodBefore: %s \n %s", key, joinPoint.getThis()));
    }


    /**
     * 截获android.app.Activity中以on开头的所有方法最后
     * 
     * @param joinPoint
     * @throws Throwable
     */
    @After("execution(* android.app.Activity.on**(..))")
    public void activityOnMethodAfter(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toLongString();
        DebugLog.d(TAG, String.format("activityOnMethodAfter: %s \n %s", key, joinPoint.getThis()));
    }

/*    *//**
     * 截获任何包中类名以Activity、Fragment开头class的所有方法
     *//*
    @Before("execution(* *..Activity*.*(..)) ||execution(* *..Fragment*.*(..))")
    public void activityFragmentPrefixAllMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        DebugLog.d(TAG, "activityFragmentPrefixAllMethodBefore: " + key + "\n" + joinPoint.getThis());
    }*/


/*    *//**
     * 截获任何包中以类名以Activity、Layout结尾class的所有方法，并且该目标类和当前类是一个Object的对象的所有方法
     * 
     *
     * @throws Throwable
     *//*
    @Before("(execution(* *..*Activity.*(..)) || execution(* *..*Layout.*(..))) && target(Object) && this(Object)")
    public void activityLayoutSuffixAllMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        DebugLog.d(TAG, "activityLayoutSuffixAllMethodBefore: " + key + "\n" + joinPoint.getThis());
    }*/

 /*   // Before和After的用法
    @Before("execution(* android.app.Activity.on*(android.os.Bundle))")
    public void activityOnCreateMethodBefore(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        DebugLog.d(TAG, "activityOnCreateMethodBefore: " + key);
    }

    @After("execution(* android.app.Activity.on*(android.os.Bundle))")
    public void activityOnCreateMethodAfter(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        DebugLog.d(TAG, "activityOnCreateMethodAfter: " + key);
    }*/

    /**
     * Around的用法
     *
     * around可以改变原始函数的执行，
      */
    @Around("execution(* eric.aop.aspectj.TestAround.testAop(..))")
    public void testAroundMethodAround(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String key = proceedingJoinPoint.getSignature().toString();
        DebugLog.d(TAG, "testAroundMethodAroundFirst: " + key);
        /**
         * warning:
         * 调用原始方法执行, 这里就是执行TestAround.testAop()函数
         */
        proceedingJoinPoint.proceed();
        DebugLog.d(TAG, "testAroundMethodAroundSecond: " + key);
    }

   /* // call用法
    @Before("call(* eric.aop.aspectj.TestAround.testCall())")
    public void testCallMethodCall(JoinPoint joinPoint) throws Throwable {
        String key = joinPoint.getSignature().toString();
        DebugLog.d(TAG, "testCallMethodCall: " + key);
    }*/


    /**
     * 利用 DebugTrace注解打印函数执行时间
     *
     *eg.
     * @DebugTrace
     * void a(){
     *
     * }
     *
     */

    private static final String POINTCUT_METHOD =
            "execution(@eric.aop.aspectj.annotation.DebugTrace * *(..))";

    private static final String POINTCUT_CONSTRUCTOR =
            "execution(@eric.aop.aspectj.annotation.DebugTrace *.new(..))";

    @Pointcut(POINTCUT_METHOD)
    public void methodAnnotatedWithDebugTrace() {}

    @Pointcut(POINTCUT_CONSTRUCTOR)
    public void constructorAnnotatedDebugTrace() {}

    @Around("methodAnnotatedWithDebugTrace() || constructorAnnotatedDebugTrace()")
    public Object weaveJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取方法信息对象
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        //获取当前对象
        String className = methodSignature.getDeclaringType().getSimpleName();
        String methodName = methodSignature.getName();

        //获取当前对象，通过反射获取类别详细信息
        String className2 = joinPoint.getThis().getClass().getName();

        //初始化计时器
        final StopWatch stopWatch = new StopWatch();
        //开始监听
        stopWatch.start();
        //调用原方法的执行。
        Object result = joinPoint.proceed();
        //监听结束
        stopWatch.stop();

        DebugLog.d(TAG, buildLogMessage(className2, methodName, stopWatch.getTotalTimeMillis()));

        return result;
    }

    /**
     * Create a log message.
     *
     * @param methodName A string with the method name.
     * @param methodDuration Duration of the method in milliseconds.
     * @return A string representing message.
     */
    private static String buildLogMessage(String className, String methodName, long methodDuration) {
        StringBuilder message = new StringBuilder();
        message.append("TraceAspect --> ");
        message.append(className).append(".");
        message.append(methodName);
        message.append(" --> ");
        message.append("[");
        message.append(methodDuration);
        message.append("ms");
        message.append("]");

        return message.toString();
    }

}
