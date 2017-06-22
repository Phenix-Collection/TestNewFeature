package eric.learnkotlin.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 *
 * Created by wenjun.zhong on 2017/6/13.
 */

public class VehicalProxy {
    private IVehical vehical;

    public VehicalProxy(IVehical vehical){
        this.vehical = vehical;
    }

    public IVehical create(){
        return (IVehical) Proxy.newProxyInstance(vehical.getClass().getClassLoader(), new Class[]{IVehical.class}, new VehicalInvocationHandler(vehical));
    }


    public class VehicalInvocationHandler implements InvocationHandler{
        private final IVehical vehical;

        public VehicalInvocationHandler(IVehical vehical){
            this.vehical = vehical;
        }


        @Override
        public Object invoke(Object o, Method method, Object[] objects) throws Throwable {

            System.out.println("--before running...");
            method.invoke(vehical, objects);
            System.out.println("--after running...");

            return null;
        }
    }
}
