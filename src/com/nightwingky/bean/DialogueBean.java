package com.nightwingky.bean;

import java.util.List;

public class DialogueBean {

    private Object log_id;
    private String words_result_num;
    private List<WordsBean> words_result;

    public static class WordsBean {
        private ProbabilityBean probability;
        private String words;

        public ProbabilityBean getProbability() {
            return probability;
        }

        public void setProbability(ProbabilityBean probability) {
            this.probability = probability;
        }

        public String getWords() {
            return words;
        }

        public void setWords(String words) {
            this.words = words;
        }

        @Override
        public String toString() {
            return "WordsBean{" +
                    "probability=" + probability +
                    ", words='" + words + '\'' +
                    '}';
        }
    }

    public static class ProbabilityBean {
        private String variance;
        private String average;
        private String min;

        public String getVariance() {
            return variance;
        }

        public void setVariance(String variance) {
            this.variance = variance;
        }

        public String getAverage() {
            return average;
        }

        public void setAverage(String average) {
            this.average = average;
        }

        public String getMin() {
            return min;
        }

        public void setMin(String min) {
            this.min = min;
        }

        @Override
        public String toString() {
            return "ProbabilityBean{" +
                    "variance='" + variance + '\'' +
                    ", average='" + average + '\'' +
                    ", min='" + min + '\'' +
                    '}';
        }
    }

    public Object getLog_id() {
        return log_id;
    }

    public void setLog_id(Object log_id) {
        this.log_id = log_id;
    }

    public String getWords_result_num() {
        return words_result_num;
    }

    public void setWords_result_num(String words_result_num) {
        this.words_result_num = words_result_num;
    }

    public List<WordsBean> getWords_result() {
        return words_result;
    }

    public void setWords_result(List<WordsBean> words_result) {
        this.words_result = words_result;
    }

    @Override
    public String toString() {
        return "DialogueBean{" +
                "log_id='" + log_id + '\'' +
                ", words_result_num='" + words_result_num + '\'' +
                ", words_result=" + words_result +
                '}';
    }
}
