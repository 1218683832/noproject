package com.mrrun.rajava1x;

import rx.Observer;

public abstract class BaseObserver<T> implements Observer<T> {

    @Override
    public void onCompleted() {
        ConsoleUtils.showThreadName("BaseObserver---" + this.toString());
        ConsoleUtils.println("onCompleted");
    }

    @Override
    public void onError(Throwable e) {
        ConsoleUtils.showThreadName("BaseObserver---" + this.toString());
        ConsoleUtils.println("onError:" + e.toString());
    }

    @Override
    public void onNext(T t) {
        ConsoleUtils.println("onNext:" + t);
    }
}
