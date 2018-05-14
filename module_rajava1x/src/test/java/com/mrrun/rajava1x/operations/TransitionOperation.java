package com.mrrun.rajava1x.operations;

import com.mrrun.rajava1x.base.BaseObserver;

import org.junit.Test;

import rx.Observable;
import rx.functions.Func1;

/**
 * 转换操作符
 */
public class TransitionOperation {

    Observable observable1 = Observable.just(3, 1, 5, 0, -5, 2, 7, 0);

    /**
     * toList： 收集原始Observable发射的所有数据到一个列表，然后返回这个列表.
     */
    @Test
    public void toList() {
        observable1.toList()
                .subscribe(new BaseObserver() {
                });
    }

    /**
     * toSortedList： 收集原始Observable发射的所有数据到一个有序列表(可以自定义排序)，然后返回这个列表。
     */
    @Test
    public void toSortedList() {
        observable1.toSortedList()// 也可以自定义排序
                .subscribe(new BaseObserver() {
                });
    }

    /**
     * toMap： 将序列数据转换为一个Map。我们可以根据数据项生成key和生成value。
     */
    @Test
    public void toMap() {
        observable1.toMap(new Func1() {
            @Override
            public Object call(Object o) {
                return "key=" + o;// 根据数据项生成map的key
            }
        }, new Func1() {
            @Override
            public Object call(Object o) {
                return "value=" + o;// 根据数据项生成map的value
            }
        }).subscribe(new BaseObserver() {
        });
    }

    /**
     * toMultiMap： 类似于toMap，不同的地方在于map的value是一个集合。
     */
    @Test
    public void toMultiMap() {
        Observable.just(3, 1, 5, 0, -5, 2, 7, 0)
                .toMultimap(new Func1() {
                    @Override
                    public Object call(Object o) {
                        return o;// 根据数据项生成map的key
                    }
                }, new Func1() {
                    @Override
                    public Object call(Object o) {
                        return o;// 根据数据项生成map的value
                    }
                }).subscribe(new BaseObserver() {
        });
    }
}
