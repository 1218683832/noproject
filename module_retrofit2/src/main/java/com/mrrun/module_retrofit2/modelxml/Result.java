package com.mrrun.module_retrofit2.modelxml;

import org.simpleframework.xml.Root;
import org.simpleframework.xml.Text;

@Root(name = "result", strict = false)
public class Result {

    @Text
    public String text;

    @Override
    public String toString() {
        return "Result{" +
                "text='" + text + '\'' +
                '}';
    }
}
