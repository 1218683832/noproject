package com.mrrun.rajava1x.operations;

import com.mrrun.rajava1x.base.BaseObserver;

import org.junit.Test;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.functions.Func1;

/**
 * 错误处理/重试机制操作符
 */
public class ErrorRetryOperation {

    /**
     * onErrorResumeNext： 当原始Observable在遇到错误时，使用备用Observable。
     */
    @Test
    public void onErrorResumeNext() {
        Observable.just(1, "2", 3)
                .cast(Integer.class)
                .onErrorResumeNext(Observable.just(1, 2, 3))
                .subscribe(new BaseObserver() {
                });
    }

    /**
     * onExceptionResumeNext： 当原始Observable在遇到异常时，使用备用的Observable。与onErrorResumeNext类似，
     * 区别在于onErrorResumeNext可以处理所有的错误，onExceptionResumeNext只能处理异常。
     */
    @Test
    public void onExceptionResumeNext() {
        Observable.just(1, "2", 3)
                .cast(Integer.class)
                .onErrorResumeNext(Observable.just(1, 2, 3))
                .subscribe(new BaseObserver() {
                });
    }

    /**
     * onErrorReturn： 当原始Observable在遇到错误时发射一个特定的数据。
     */
    @Test
    public void onErrorReturn() {
        Observable.just(1, "2", 3, 4, "5")
                .cast(Integer.class)
                .onErrorReturn(new Func1<Throwable, Integer>() {
                    @Override
                    public Integer call(Throwable throwable) {
                        return -1;
                    }
                }).subscribe(new BaseObserver<Integer>() {
        });
    }

    /**
     * retry： 当原始Observable在遇到错误时进行重试。
     */
    @Test
    public void retry() {
        Observable.just(1, 2, "3", 4)
                .cast(Integer.class)
                .retry(3)
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * retryWhen： 当原始Observable在遇到错误，将错误传递给另一个Observable来决定是否要重新订阅这个Observable,内部调用的是retry。
     */
    @Test
    public void retryWhen() {
        Observable.just(1, 2, 3, "4")
                .cast(Integer.class)
                .retryWhen(new Func1<Observable<? extends Throwable>, Observable<Long>>() {
                    @Override
                    public Observable<Long> call(Observable<? extends Throwable> observable) {
                        return Observable.timer(1, TimeUnit.SECONDS);
                    }
                }).subscribe(new BaseObserver<Integer>() {
        });
    }
}
