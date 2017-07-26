package eric.learnkotlin.rx;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Function;

/**
 * Created by wenjun.zhong on 2017/7/13.
 */

public class TestRx {

    public static void testObservable(){
        Observable<String> observable = Observable.just("hello", "eric");
        observable.map(new Function<String, String>() {
            @Override
            public String apply(String s) throws Exception {
                return s + " grace";
            }
        }).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(String value) {
                System.out.println(value);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }
}
