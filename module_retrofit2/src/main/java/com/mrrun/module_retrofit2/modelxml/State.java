package com.mrrun.module_retrofit2.modelxml;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "state", strict = false)
public class State {

    @Text
    public String text;

    @Override
    public String toString() {
        return "State{" +
                "text='" + text + '\'' +
                '}';
    }
}
