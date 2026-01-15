package com.ma.shopeasy.domain.model;

import java.io.Serializable;
import java.util.Objects;

public class FAQItem implements Serializable {
    private String question;
    private String answer;
    private long timestamp;
    private boolean answered;

    public FAQItem() {
        // Required for Firebase
    }

    public FAQItem(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.timestamp = System.currentTimeMillis();
        this.answered = answer != null && !answer.isEmpty();
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
        this.answered = answer != null && !answer.isEmpty();
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        FAQItem faqItem = (FAQItem) o;
        return timestamp == faqItem.timestamp &&
                answered == faqItem.answered &&
                Objects.equals(question, faqItem.question) &&
                Objects.equals(answer, faqItem.answer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(question, answer, timestamp, answered);
    }
}
