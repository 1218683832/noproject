package com.mrrun.module_retrofit2.modelxml;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "reslutmsg", strict = false)
public class ReslutMsg {

    @Text
    public String text;

    @Override
    public String toString() {
        return "ReslutMsg{" +
                "text='" + text + '\'' +
                '}';
    }
}
