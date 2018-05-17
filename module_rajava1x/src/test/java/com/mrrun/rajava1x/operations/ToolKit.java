package com.mrrun.rajava1x.operations;

import com.mrrun.rajava1x.base.BaseObserver;
import com.mrrun.rajava1x.util.ConsoleUtils;

import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import rx.Notification;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.schedulers.TimeInterval;
import rx.schedulers.Timestamped;

/**
 * 工具集
 */
public class ToolKit {

    /**
     * materialize： 将Observable转换成一个通知列表。
     */
    @Test
    public void materialize() {
        Observable.just(1, 2, 3, 4, 5, 6)
                .materialize()
                .subscribe(new BaseObserver<Notification<Integer>>() {
                });
    }

    /**
     * dematerialize： 与上面的作用相反，将通知逆转回一个Observable。
     */
    @Test
    public void dematerialize() {
        Observable.just(Notification.createOnNext(1), Notification.createOnNext(2), Notification.createOnNext(3))
                .dematerialize()
                .subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * timestamp： 给Observable发射的每个数据项添加一个时间戳。
     */
    @Test
    public void timestamp() {
        Observable.just(1, 2, 3, 4, 5)
                .timestamp()
                .subscribe(new BaseObserver<Timestamped<Integer>>() {
                });
    }

    /**
     * timeInterval：给Observable发射的两个数据项间添加一个时间差，在OperatorTimeInterval中实现
     */
    @Test
    public void timeInterval() {
        Observable.just(1,2,3,4,5,6)
                .timeInterval()
                .subscribe(new BaseObserver<TimeInterval<Integer>>() {
                });
    }

    /**
     * serialize： 强制Observable按次序发射数据并且要求功能是完好的
     */
    @Test
    public void serialize(){
        Observable.just(2,1,4,8,5,-1)
                .serialize()
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * cache： 缓存Observable发射的数据序列并发射相同的数据序列给后续的订阅者
     */
    @Test
    public void cache(){
        Observable.just(2,1,4,8,5,-1)
                .cache()
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * observeOn： 指定观察者观察Observable的调度器
     */
    @Test
    public void observeOn(){
        Observable.just(2,1,4,8,5,-1)
                .observeOn(Schedulers.newThread())
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * subscribeOn： 指定Observable执行任务的调度器
     */
    @Test
    public void subscribeOn(){
        Observable.just(2,1,4,8,5,-1)
                .subscribeOn(Schedulers.newThread())
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * doOnEach： 注册一个动作，对Observable发射的每个数据项之前触发
     */
    @Test
    public void doOnEach() {
        Observable.just(2,1,4,8,5,-1)
                .doOnEach(new Action1<Notification<? super Integer>>() {
                    @Override
                    public void call(Notification<? super Integer> notification) {
                        ConsoleUtils.println(notification.toString());
                    }
                })
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * doOnCompleted： 注册一个动作，对正常完成的Observable调用onCompleted()之前触发
     */
    @Test
    public void doOnCompleted() {
        Observable.just(2,1,4,8,5,-1)
                .doOnCompleted(new Action0() {
                    @Override
                    public void call() {
                        ConsoleUtils.println("doOnCompleted");
                    }
                })
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * doOnError： 注册一个动作，对发生错误的Observable调用onError()之前触发
     */
    @Test
    public void doOnError() {
        Observable.just(2,1,4,8,5,-1,"0")
                .cast(Integer.class)
                .doOnError(new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        ConsoleUtils.println("doOnError");
                    }
                })
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * doOnTerminate：注册一个动作，对完成的Observable触发，无论是否发生错误
     */
    @Test
    public void doOnTerminate(){
        Observable.just(2,1,4,8,5,-1)
                .doOnTerminate(new Action0() {
                    @Override
                    public void call() {
                        ConsoleUtils.println("doOnTerminate");
                    }
                })
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * doOnSubscribe： 注册一个动作，在观察者订阅时触发。内部由OperatorDoOnSubscribe实现
     */
    @Test
    public void doOnSubscribe(){
        Observable.just(2,1,4,8,5,-1)
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        ConsoleUtils.println("doOnSubscribe");
                    }
                })
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * doOnUnsubscribe： 注册一个动作，在观察者取消订阅时触发。内部由OperatorDoOnUnsubscribe实现，在call中加入一个解绑动作。
     */
    @Test
    public void doOnUnsubscribe() {
        Observable.just(2,1,4,8,5,-1)
                .doOnUnsubscribe(new Action0() {
                    @Override
                    public void call() {
                        ConsoleUtils.println("doOnUnsubscribe");
                    }
                })
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * finallyDo/doAfterTerminate： 注册一个动作，在Observable完成时触发
     */
    @Test
    public void doAfterTerminate(){
        Observable.just(2,1,4,8,5,-1)
                .doAfterTerminate(new Action0() {
                    @Override
                    public void call() {
                        ConsoleUtils.println("doAfterTerminate");
                    }
                })
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * delay： 延时发射Observable的结果。即让原始Observable在发射每项数据之前都暂停一段指定的时间段。
     * 效果是Observable发射的数据项在时间上向前整体平移了一个增量（除了onError会即时通知）。
     */
    @Test
    public void delay(){
        Observable.just(2,1,4,8,5,-1)
                .delay(3, TimeUnit.SECONDS)
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * delaySubscription： 延时处理订阅请求。在OnSubscribeDelaySubscription中实现
     */
    @Test
    public void delaySubscription(){
        Observable.just(2,1,4,8,5,-1)
                .delaySubscription(3, TimeUnit.SECONDS)
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * using： 创建一个只在Observable生命周期存在的资源，当Observable终止时这个资源会被自动释放。
     */
    @Test
    public void using() {
        Observable.using(new Func0<File>() {
            @Override
            public File call() {
                File file = new File("a.txt");
                if (file != null && file.exists()) {
                } else {
                    try {
                        ConsoleUtils.println("--create file--");
                        file.createNewFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                return file;
            }
        }, new Func1<File, Observable<String>>() {
            @Override
            public Observable<String> call(File file) {
                 return Observable.just(file.exists() ? "exist" : "no exist");
            }
        }, new Action1<File>() {
            @Override
            public void call(File file) {
                if(file!=null&&file.exists()){
                    ConsoleUtils.println("--delete file--");
                    file.delete();
                }
            }
        }).subscribe(new BaseObserver<String>() {
        });
    }

    /**
     * single/singleOrDefault： 强制返回单个数据，否则抛出异常或默认数据。
     */
    @Test
    public void single() {
        Observable.just(2)
                .single()
                .subscribe(new BaseObserver<Integer>() {
                });
    }
}
