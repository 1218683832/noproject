package com.mrrun.module_retrofit2;

import java.util.List;

public class Translation2 {

    private String type;
    private int errorCode;
    private int elapsedTime;
    private List<List<TranslateResultBean>> translateResult;

    @Override
    public String toString() {
        return "Translation2{" +
                "type='" + type + '\'' +
                ", errorCode=" + errorCode +
                ", elapsedTime=" + elapsedTime +
                ", translateResult=" + translateResult +
                '}';
    }

    public static class TranslateResultBean {
        /**
         * src : merry me
         * tgt : 我快乐
         */
        public String src;
        public String tgt;

        @Override
        public String toString() {
            return "TranslateResultBean{" +
                    "src='" + src + '\'' +
                    ", tgt='" + tgt + '\'' +
                    '}';
        }
    }
}
