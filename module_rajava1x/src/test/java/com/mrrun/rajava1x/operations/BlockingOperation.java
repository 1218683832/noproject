package com.mrrun.rajava1x.operations;

import com.mrrun.rajava1x.base.BaseObserver;
import com.mrrun.rajava1x.util.ConsoleUtils;

import org.junit.Test;

import java.util.Iterator;

import rx.Observable;
import rx.functions.Action1;
import rx.observables.BlockingObservable;

/**
 * 阻塞操作符
 * BlockingObservable是一个阻塞的Observable。普通的Observable 转换为 BlockingObservable，
 * 可以使用 Observable.toBlocking( )方法或者BlockingObservable.from( )方法。
 * 内部通过CountDownLatch实现了阻塞操作。
 *
 * 以下的操作符可以用于BlockingObservable，如果是普通的Observable，务必使用Observable.toBlocking()转为阻塞Observable后使用，
 * 否则达不到预期的效果。
 */
public class BlockingOperation {

    /**
     * forEach： 对BlockingObservable发射的每一项数据调用一个方法，会阻塞直到Observable完成。
     */
    @Test
    public void forEach(){
        Observable.just(1,2,3,4)
                .toBlocking()
                .forEach(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        ConsoleUtils.println("Blocking......");
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        ConsoleUtils.println("" + integer);
                    }
                });
    }

    /**
     * first/firstOrDefault/last/lastOrDefault：这几个操作符之前有介绍过。也可以用于阻塞操作。
     */

    /**
     * single/singleOrDefault：如果Observable终止时只发射了一个值，返回那个值，否则抛出异常或者发射默认值。
     */
    @Test
    public void single(){
        Observable.just(1,2)
                .single()
                .subscribe(new BaseObserver<Integer>() {
                });
    }

    /**
     * mostRecent：返回一个总是返回Observable最近发射的数据的Iterable。
     */
    @Test
    public void mostRecent(){
        BlockingObservable observable = BlockingObservable.from(Observable.just(1,2,3,4,5,6));
        Iterator < Integer > it = observable.mostRecent(3).iterator();
        observable.subscribe(new BaseObserver() {});
        ConsoleUtils.println(it.toString());
        while (it.hasNext()) {
           ConsoleUtils.println("" + it.next());
       }
    }
}
