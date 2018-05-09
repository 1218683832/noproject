package com.mrrun.module_retrofit2;

public class Translation1 {

    private int status;
    private Content content;

    public static class Content {
        private String from;
        private String to;
        private String out;
        private String vendor;

        @Override
        public String toString() {
            return "Content{" +
                    "from='" + from + '\'' +
                    ", to='" + to + '\'' +
                    ", out='" + out + '\'' +
                    ", vendor='" + vendor + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "Translation1{" +
                "status=" + status +
                ", content=" + content +
                '}';
    }
}
