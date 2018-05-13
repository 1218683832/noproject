package com.mrrun.rajava1x;

import android.util.Log;

import org.junit.Test;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 条件/布尔操作符
 */
public class BooleanOperation {

    Observable observable1 = Observable.just(1,3,5,7,2,5,3,8);

    /**
     * all() 判断所有的数据项是否满足某个条件，内部通过OperatorAll实现。
     */
    @Test
    public void all(){
        Observable.just(7,3,4,5)
                .all(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 2;
                    }
                })
                .subscribe(new BaseObserver<Boolean>() {
                });
    }

    /**
     * exists() 判断是否存在数据项满足某个条件。内部通过OperatorAny实现。
     */
    @Test
    public void exists() {
        observable1.exists(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer o) {
                return 0 > 5;
            }
        }).subscribe(new BaseObserver<Boolean>() {
        });
    }

    /**
     * contains() 判断在发射的所有数据项中是否包含指定的数据，内部调用的其实是exists
     */
    @Test
    public void contains() {
        observable1.contains(3)
                .subscribe(new BaseObserver() {
                });
    }

    /**
     * sequenceEqual() 用于判断两个Observable发射的数据是否相同（数据，发射顺序，终止状态）。
     */
    @Test
    public void sequenceEqual() {
        Observable.sequenceEqual(Observable.just(2,3,4,5), Observable.just(2,3,4))
                .subscribe(new BaseObserver<Boolean>() {
                });
    }

    /**
     * isEmpty() 用于判断Observable发射完毕时，有没有发射数据。有数据false，如果只收到了onComplete通知则为true。
     */
    @Test
    public void isEmpty() {
        observable1.isEmpty()
                .subscribe(new BaseObserver() {
                });
    }

    /**
     * amb() 给定多个Observable，只让第一个发射数据的Observable发射全部数据，其他Observable将会被忽略。
     */
    @Test
    public void amb() {
        Observable<Integer> observable1 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                }
                subscriber.onNext(1);
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        }).subscribeOn(Schedulers.computation());

        Observable<Integer> observable2 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(3);
                subscriber.onNext(4);
                subscriber.onCompleted();
            }
        });

        Observable.amb(observable1,observable2)
                .subscribe(new BaseObserver() {
                }); // 3,4
    }

    /**
     * switchIfEmpty() 如果原始Observable正常终止后仍然没有发射任何数据，就使用备用的Observable。
     */
    @Test
    public void switchIfEmpty() {
        Observable
                .empty()
                .switchIfEmpty(Observable.just(2,3,4))
                .subscribe(new BaseObserver() {
                }); //2,3,4
    }

    /**
     * defaultIfEmpty() 如果原始Observable正常终止后仍然没有发射任何数据，就发射一个默认值,内部调用的switchIfEmpty。
     */
    @Test
    public void defaultIfEmpty() {
        Observable
                .empty()
                .defaultIfEmpty(222)
                .subscribe(new BaseObserver() {
                }); //222
    }

    /**
     * takeUntil() 当发射的数据满足某个条件后（包含该数据），或者第二个Observable发送完毕，终止第一个Observable发送数据。
     */
    @Test
    public void takeUntil(){
        Observable.just(2,3,4,5)
                .takeUntil(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer == 4;
                    }
                }).subscribe(new BaseObserver() {
        }); //2,3,4
    }

    /**
     * takeWhile() 当发射的数据满足某个条件时（不包含该数据），Observable终止发送数据。
     */
    @Test
    public void takeWhile() {
        Observable.just(2,3,4,5)
                .takeWhile(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer == 4;
                    }
                })
                .subscribe(new BaseObserver() {
                }); //2,3
    }

    /**
     * skipUntil() 丢弃Observable发射的数据，直到第二个Observable发送数据。（丢弃条件数据）
     */
    @Test
    public void skipUntil() {
        Observable observable1 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(1);
                subscriber.onNext(2);
            }
        });

        Observable observable2 = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("中");
                subscriber.onNext("国");
            }
        });
        observable1
                .skipUntil(observable2)
                .subscribe(new BaseObserver() {
                });
    }

    /**
     * skipWhile() 丢弃Observable发射的数据，直到一个指定的条件不成立（不丢弃条件数据）
     */
    @Test
    public void skipWhile() {
        Observable observable1 = Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                subscriber.onNext(1);
                subscriber.onNext(2);
            }
        });

        Observable observable2 = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("中");
                subscriber.onNext("国");
            }
        });
        observable1
                .skipWhile(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer o) {
                        return o <= 2;
                    }
                })
                .subscribe(new BaseObserver() {
                });
    }
}
