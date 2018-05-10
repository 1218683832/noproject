package com.mrrun.rajava1x;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void test1() {
//        Observable.create(new Observable.OnSubscribe<String>() {
//            @Override
//            public void call(Subscriber<? super String> subscriber) {
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                subscriber.onNext("ss");
//                subscriber.onNext("test1");
//                subscriber.onCompleted();
//            }
//        })
        Observable.just("ss", "test1")
//                .subscribeOn(Schedulers.newThread())
//                .observeOn(Schedulers.newThread())
                .map(new Func1<String, String>() {
                    @Override
                    public String call(String string) {
                        return "1" + string;
                    }
                })
//                .observeOn(Schedulers.newThread())
//                .observeOn(Schedulers.io())
//        .subscribe(new Action1<String>() {
//            @Override
//            public void call(String o) {
//                ConsoleUtils.println(o.toString());
//            }
//        });
        .subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                ConsoleUtils.println("onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                ConsoleUtils.println("onError");
            }

            @Override
            public void onNext(String s) {
                ConsoleUtils.println(s);
            }
        });
    }
}