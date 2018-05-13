package com.mrrun.rajava1x;

import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func0;

/**
 * 创建操作符,用于创建Observable
 */
public class CreateOperation {

    @Test
    public void create() {
        /**
         * create() 方法是RxJava最基本的创造事件序列的方法
         */
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                ConsoleUtils.showThreadName(this.toString());
                subscriber.onNext("打招呼---");
                subscriber.onNext("交谈-----");
                subscriber.onNext("分别-----");
                subscriber.onCompleted();
            }
        }).subscribe(new BaseObserver<String>() {
        });
    }

    /**
     * just() 快捷创建事件队列，将传入的参数依次发送出来。
     */
    @Test
    public void just() {
        Observable.just("111", "222", "333")
                .subscribe(new BaseObserver<String>() {
                });
    }

    /**
     * frim() 快捷创建事件队列，将传入的数组或 Iterable 拆分成具体对象后，依次发送出来。
     */
    @Test
    public void from() {
        String[] words = {"Hello", "Hi", "Aloha"};
        Observable.from(words)
                .subscribe(new BaseObserver<String>() {
                });
    }

    @Test
    public void from1() {
        Future<String> futrue = Executors.newSingleThreadExecutor().submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                ConsoleUtils.showThreadName("Future");
                return "maplejaw";
            }
        });
        Observable.from(futrue)
                .subscribe(new BaseObserver<String>() {
                });
    }

    /**
     * emptry() 创建一个什么都不做直接通知完成的Observable
     */
    @Test
    public void empty() {
        Observable.empty()// 直接调用onCompleted
                .subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * error() 创建一个什么都不做直接通知错误的Observable
     */
    @Test
    public void error() {
        Observable.error(new NullPointerException())// 直接调用onError。这里可以自定义异常
                .subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * never()  创建一个什么都不做的Observable
     */
    public void never() {
        Observable.never()// 啥都不做
                .subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * timer() 创建一个在给定的延时之后发射数据项为0的Observable<Long>
     * 注意timer与interval都是默认运行在一个新线程上面
     */
    @Test
    public void timer() {
        Observable.timer(5, TimeUnit.SECONDS)// 延迟5秒
                .subscribe(new BaseObserver<Long>() {
                });
    }

    /**
     * timer() 创建一个在给定的延时之后发射数据项为0的Observable<Long>
     * 注意timer与interval都是默认运行在一个新线程上面
     */
    @Test
    public void timer1() {
        Observable.timer(0, 5, TimeUnit.SECONDS)// 间隔5秒执行
                .subscribe(new BaseObserver<Long>() {
                });
    }

    /**
     * interval() 创建一个按照给定的时间间隔发射从0开始的整数序列的
     * 注意timer与interval都是默认运行在一个新线程上面
     */
    @Test
    public void interval() {
        Observable.interval(5, TimeUnit.SECONDS)//每隔5秒发送数据项，从0开始计数0,1,2,3....
                .subscribe(new BaseObserver<Long>() {
                });
    }

    /**
     * range()  创建一个发射指定范围的整数序列的Observable<Integer>
     */
    @Test
    public void range() {
        Observable.range(1, 10)
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * defer() 只有当订阅者订阅才创建Observable，为每个订阅创建一个新的Observable。
     * 内部通过OnSubscribeDefer在订阅时调用Func0创建Observable
     */
    @Test
    public void defer() {
        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just("hello RxJava");
            }
        }).subscribe(new BaseObserver<String>() {
        });
    }
}