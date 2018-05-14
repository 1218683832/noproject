package com.mrrun.rajava1x.operations;

import com.mrrun.rajava1x.base.BaseObserver;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action2;
import rx.functions.Func0;
import rx.functions.Func2;

/**
 * 聚合操作符
 */
public class AggregateOperation {

    /**
     * reduce： 对序列使用reduce()函数并发射最终的结果,内部使用OnSubscribeReduce实现。
     */
    @Test
    public void reduce() {
        Observable.just(1, 2, 3, 4, 5)
                .reduce(new Func2<Integer, Integer, Integer>() {
                    @Override
                    public Integer call(Integer integer, Integer integer2) {
                        return integer + integer2;
                    }
                }).subscribe(new BaseObserver<Integer>() {
        });
        // 15
    }

    /**
     * collect： 使用collect收集数据到一个可变的数据结构，最终发送这个数据结构。
     */
    @Test
    public void collect() {
        Observable.just(3,4,5,6)
                .collect(new Func0<List<Integer>>() {
                    @Override
                    public List<Integer> call() {
                        return new ArrayList<Integer>();
                    }
                }, new Action2<List<Integer>, Integer>() {
                    @Override
                    public void call(List<Integer> integers, Integer integer) {
                        integers.add(integer);
                    }
                })
                .subscribe(new BaseObserver<List<Integer>>() {
                });
    }

    /**
     * count/countLong： 计算发射的数量，内部调用的是reduce.
     */
    @Test
    public void count(){
        Observable.just(1,2,3,4,5,6,7,8,9,0)
                .count()
                .subscribe(new BaseObserver<Integer>() {
                });
    }
}
