package com.mrrun.rajava1x.operations;

import com.mrrun.rajava1x.base.BaseObserver;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * 变换操作符
 */
public class ConversionOperation {

    Observable observable1 = Observable.just(6, 2, 3, 4, 5);

    /**
     * map： 对Observable发射的每一项数据都应用一个函数来变换。
     */
    @Test
    public void map() {
        observable1.map(new Func1<Integer, String>() {
            @Override
            public String call(Integer o) {
                return "item" + o;
            }
        }).subscribe(new BaseObserver() {
        });
    }

    /**
     * cast： 在发射之前强制将Observable发射的所有数据转换为指定类型
     */
    @Test
    public void cast() {
        observable1.cast(Float.class)
                .subscribe(new BaseObserver() {
                });
    }

    /**
     * flatMap： 将Observable发射的数据变换为Observables集合，然后将这些Observable发射的数据平坦化的放进一个单独的Observable，
     * 内部采用merge合并。
     */
    @Test
    public void flatMap() {
        observable1.flatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer o) {
                return Observable.just(o + "-a");
            }
        }).subscribe(new BaseObserver() {
        });
    }

    /**
     * flatMapIterable： 和flatMap的作用一样，只不过生成的是Iterable而不是Observable。
     */
    @Test
    public void flatMapIterable() {
        observable1.flatMapIterable(new Func1<Integer, List<String>>() {
            @Override
            public List<String> call(Integer o) {
                ArrayList<String> arrayList = new ArrayList<String>();
                arrayList.add(o + "-a");
                return arrayList;
            }
        }).subscribe(new BaseObserver() {
        });
    }

    /**
     * concatMap： 类似于flatMap，由于内部使用concat合并，所以是按照顺序连接发射。
     */
    @Test
    public void concatMap() {
        observable1.concatMap(new Func1<Integer, Observable<String>>() {
            @Override
            public Observable<String> call(Integer o) {
                return Observable.just(o + "-a");
            }
        }).subscribe(new BaseObserver() {
        });
    }

    /**
     * switchMap： 和flatMap很像，将Observable发射的数据变换为Observables集合，当原始Observable发射一个新的数据（Observable）时，
     * 它将取消订阅前一个Observable。
     */
    @Test
    public void switchMap() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                for (int i = 0; i < 4; i++) {
                    subscriber.onNext(i);
                    try {
                        Thread.sleep(1000);// 线程休眠
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                subscriber.onCompleted();
            }
        })
                .switchMap(new Func1<Integer, Observable<Integer>>() {
                    @Override
                    public Observable<Integer> call(final Integer integer) {
                        // 每当接收到新的数据，之前的Observable将会被取消订阅
                        return Observable.just(integer * 10, integer * 100);
                    }
                }).subscribe(new BaseObserver<Integer>() {
        });
    }

    /**
     * scan： 与reduce很像，对Observable发射的每一项数据应用一个函数，然后按顺序依次发射每一个值。
     */
    @Test
    public void scan() {
        observable1.scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer o, Integer o2) {
                return o + o2;
            }
        }).subscribe(new BaseObserver() {
        });
    }

    /**
     * groupBy： 将Observable分拆为Observable集合，将原始Observable发射的数据按Key分组，每一个Observable发射一组不同的数据。
     */
    @Test
    public void groupBy() {
        observable1.groupBy(new Func1<Integer, String>() {
            @Override
            public String call(Integer o) {// 分组
                return o % 2 == 0 ? "偶数":"奇数";
            }
        }).subscribe(new BaseObserver() {
        });
    }

    /**
     * buffer： 它定期从Observable收集数据到一个集合，然后把这些数据集合打包发射，而不是一次发射一个.
     */
    @Test
    public void buffer() {
        Observable.just(2,3,4,5,6)
                .buffer(2)
                .subscribe(new BaseObserver<List<Integer>>() {
                });
    }

    /**
     * window： 定期将来自Observable的数据分拆成一些Observable窗口，然后发射这些窗口，而不是每次发射一项。
     */
    @Test
    public void window() {
        Observable.just(2,3,4,5,6)
                .window(2)
                .subscribe(new BaseObserver<Observable<Integer>>() {
                });
    }
}
