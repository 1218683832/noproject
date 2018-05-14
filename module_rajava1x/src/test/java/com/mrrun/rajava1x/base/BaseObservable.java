package com.mrrun.rajava1x.base;

import com.mrrun.rajava1x.util.ConsoleUtils;

import rx.Observable;

public abstract class BaseObservable extends Observable {
    /**
     * Creates an Observable with a Function to execute when it is subscribed to.
     * <p>
     * <em>Note:</em> Use {@link #unsafeCreate(OnSubscribe)} to create an Observable, instead of this constructor,
     * unless you specifically have a need for inheritance.
     *
     * @param f {@link OnSubscribe} to be executed when {@link #subscribe(Subscriber)} is called
     */
    protected BaseObservable(OnSubscribe f) {
        super(f);
    }

    public BaseObservable currentThreadName(){
        ConsoleUtils.showThreadName("BaseObservable---" + this.toString());
        return this;
    }
}
