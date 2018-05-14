package com.mrrun.rajava1x.operations;

import com.mrrun.rajava1x.base.BaseObserver;

import org.junit.Test;

import rx.Observable;
import rx.functions.Func2;

/**
 * 合并操作符,用于组合多个Observable
 */
public class MergeOperation {

    Observable<Integer> observable1 = Observable.just(1, 2, 3, 4);
    Observable<Integer> observable2 = Observable.just(5, 6, 7);

    /**
     * concat() 按顺序连接多个Observables。需要注意的是Observable.concat(a,b)等价于a.concatWith(b)
     */
    @Test
    public void concat(){
        Observable.concat(observable1, observable2)
                .subscribe(new BaseObserver<Integer>() {
                });
        // 1,2,3,4,4,5,6
    }

    /**
     * startWith() 在数据序列的开头增加一项数据。startWith的内部是调用了concat
     */
    @Test
    public void startWith() {
        Observable.just(1,2,3)
                .startWith(4,5,6)
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * marge()  将多个Observable合并为一个。不同于concat，merge不是按照添加顺序连接，而是按照时间线来连接。
     * 其中mergeDelayError将异常延迟到其它没有错误的Observable发送完毕后才发射。而merge则是一遇到异常将停
     * 止发射数据，发送onError通知。
     */
    @Test
    public void marge(){
        Observable.merge(observable2, observable1)
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * zip() 使用一个函数组合多个Observable发射的数据集合，然后再发射这个结果。如果多个Observable发射的数据量不一样，
     * 则以最少的Observable为标准进行压合。内部通过OperatorZip进行压合.
     */
    @Test
    public void zip() {
        Observable.zip(observable1, observable2, new Func2<Integer, Integer, String>() {
            @Override
            public String call(Integer integer, Integer integer2) {
                return integer+"and"+integer2;
            }
        }).subscribe(new BaseObserver<String>() {
        });
    }

    /**
     * combineLa() 当两个Observables中的任何一个发射了一个数据时，通过一个指定的函数组合每个Observable发射的最新数据（一共两个数据），
     * 然后发射这个函数的结果。类似于zip，但是，不同的是zip只有在每个Observable都发射了数据才工作，而combineLa任何一个发射了数据都
     * 可以工作，每次与另一个Observable最近的数据压合.
     */
    @Test
    public void combineLa() {
        Observable.combineLatest(observable1, observable2, new Func2<Integer, Integer, String>() {
            @Override
            public String call(Integer integer, Integer integer2) {
                return integer+"and"+integer2;
            }
        }).subscribe(new BaseObserver<String>() {
        });
    }
}
