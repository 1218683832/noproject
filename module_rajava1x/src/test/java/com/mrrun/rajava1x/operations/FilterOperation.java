package com.mrrun.rajava1x.operations;

import com.mrrun.rajava1x.base.BaseObserver;

import org.junit.Test;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;
import rx.functions.Func1;

/**
 * 过滤操作符，用于过滤数据
 */
public class FilterOperation {

    /**
     * filter() 过滤数据。内部通过OnSubscribeFilter过滤数据。
     */
    @Test
    public void filter() {
        Observable.just(1,2,3,4,5,6,7,8)
                .filter(new Func1<Integer, Boolean>() {
                    @Override
                    public Boolean call(Integer integer) {
                        return integer > 4;
                    }
                }).subscribe(new BaseObserver<Integer>() {
        });
    }

    /**
     * ofType() 过滤指定类型的数据，与filter类似.
     */
    @Test
    public void ofType() {
        Observable.just(1, 2, 3, "中", 4, 5, "国")
                .ofType(Integer.class)
                .subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * take() 只发射开始的N项数据或者一定时间内的数据。内部通过OperatorTake和OperatorTakeTimed过滤数据。
     */
    @Test
    public void take() {
        Observable.just(1, 2, 3, "中", 4, 5, "国", 6)
                .take(5)
                .subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * takeLast() 只发射最后的N项数据或者一定时间内的数据。内部通过OperatorTakeLast和OperatorTakeLastTimed过滤数据。
     * takeLastBuffer和takeLast类似，不同点在于takeLastBuffer会收集成List后发射。
     */
    @Test
    public void takeLast(){
        Observable.just(1, 2, 3, "中", 4, 5, "国", 6)
                .takeLast(5)
                .subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * takeFirst() 提取满足条件的第一项。
     */
    @Test
    public void takeFirst() {
        Observable.just(1, 2, 3, "中", 4, 5, "国", 6)
                .takeFirst(new Func1<Serializable, Boolean>() {
                    @Override
                    public Boolean call(Serializable serializable) {
                        return null;
                    }
                }).subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * first/firstOrDefaul() 只发射第一项（或者满足某个条件的第一项）数据，可以指定默认值。
     */
    @Test
    public void first() {
        Observable.just(1, 2, 3, "中", 4, 5, "国", 6)
                .first()
                .subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * last/lastOrDefaul() 只发射最后一项（或者满足某个条件的最后一项）数据，可以指定默认值。
     */
    @Test
    public void last() {
        Observable.just(1, 2, 3, "中", 4, 5, "国", 6)
                .last()
                .subscribe(new BaseObserver<Object>() {
                });
    }

    /**
     * skip() 跳过开始的N项数据或者一定时间内的数据。内部通过OperatorSkip和OperatorSkipTimed实现过滤。
     */
    @Test
    public void skip(){
        Observable.just(3,4,5,6)
                .skip(1)
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * skipLast() 跳过最后的N项数据或者一定时间内的数据。内部通过OperatorSkipLast和OperatorSkipLastTimed实现过滤。
     */
    @Test
    public void skipLast() {
        Observable.just(3,4,5,6)
                .skipLast(1)
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * elementAt/elementAtOrDefaul() 发射某一项数据，如果超过了范围可以指定默认值。内部通过OperatorElementAt过滤。
     */
    @Test
    public void elementAt() {
        Observable.just(1, 8, 3, 7, 5)
                .elementAt(2)
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * ignoreElement() 丢弃所有数据，只发射错误或正常终止的通知。内部通过OperatorIgnoreElements实现。
     */
    @Test
    public void ignoreElement(){
        Observable.just(1, 8, 3, 7, 5)
                .ignoreElements()
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * distinc() 过滤重复数据，内部通过OperatorDistinct实现。
     */
    @Test
    public void distinc(){
        Observable.just(1, 8, 3, 7, 5, 8, 7 , 7)
                .distinct()
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * distinctUntilChanged() 过滤掉连续重复的数据。内部通过OperatorDistinctUntilChanged实现
     */
    @Test
    public void distinctUntilChanged() {
        Observable.just(1, 8, 3, 7, 5, 8, 7 , 7)
                .distinctUntilChanged()
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * throttleFirst() 定期发射Observable发射的第一项数据。内部通过OperatorThrottleFirst实现。
     */
    @Test
    public void throttleFirst() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(2);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(3);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(4);
                subscriber.onNext(5);
                subscriber.onCompleted();
            }
        }).throttleFirst(999, TimeUnit.MILLISECONDS)
                .subscribe(new BaseObserver<Integer>() {
                }); //结果为1,3,4
    }

    /**
     * throttleWithTimeout/debounc() 发射数据时，如果两次数据的发射间隔小于指定时间，就会丢弃前一次的数据,直到指定时间内都没有新数据发射时
     * 才进行发射
     */
    @Test
    public void throttleWithTimeout() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(2);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(3);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(4);
                subscriber.onNext(5);
                subscriber.onCompleted();
            }
        }).throttleWithTimeout(999, TimeUnit.MILLISECONDS)
                .subscribe(new BaseObserver<Integer>() {
                }); //结果为3,5
    }

    /**
     * sample/throttleLas() 定期发射Observable最近的数据。内部通过OperatorSampleWithTime实现。
     */
    @Test
    public void sample() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(2);
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(3);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(4);
                subscriber.onNext(5);
                subscriber.onCompleted();
            }
        }).sample(999, TimeUnit.MILLISECONDS)
                .subscribe(new BaseObserver<Integer>() {
                }); //结果为2,3,5
    }

    /**
     * timeout() 如果原始Observable过了指定的一段时长没有发射任何数据，就发射一个异常或者使用备用的Observable。
     */
    @Test
    public void timeout() {
        Observable.create(new Observable.OnSubscribe<Integer>() {
            @Override
            public void call(Subscriber<? super Integer> subscriber) {
                subscriber.onNext(1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw Exceptions.propagate(e);
                }
                subscriber.onNext(2);
                subscriber.onCompleted();
            }
        }).timeout(999, TimeUnit.MILLISECONDS, Observable.just(99 ,111))
                .subscribe(new BaseObserver<Integer>() {
                });
        //结果为1,99,111  如果不指定备用Observable结果为1,onError
    }
}
