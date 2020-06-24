package com.mjc.translate.Latest;

public class LatestRecyclerViewItem {
    private int id;
    private String input;
    private String output;
    private String language;
    private String toLanguage;

    public int getId() {
        return id;
    }

    public String getInput() {
        return input;
    }

    public String getLanguage() {
        return language;
    }

    public String getOutput() {
        return output;
    }

    public String getToLanguage() {
        return toLanguage;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setOutput(String output) {
        this.output = output;
    }

    public void setToLanguage(String toLanguage) {
        this.toLanguage = toLanguage;
    }
}
