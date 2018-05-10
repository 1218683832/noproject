package com.mrrun.module_retrofit2.modelxml;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "vvm", strict = false)
public class VVM {

    @Element(required = false, name = "result")
    public Result result;

    @Element(required = false, name = "state")
    public State state;

    @Element(required = false, name = "reslutmsg")
    public ReslutMsg reslutMsg;

    @Override
    public String toString() {
        return "VVM{" +
                "result=" + result +
                ", state=" + state +
                ", reslutMsg=" + reslutMsg +
                '}';
    }
}
