package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatFormalAspects;

public class FormalAspectsResponse {
    private String format;
    private boolean isCorrect;

    public FormalAspectsResponse(String format, boolean isCorrect) {
        this.format = format;
        this.isCorrect = isCorrect;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public boolean isCorrect() {
        return isCorrect;
    }

    public void setCorrect(boolean correct) {
        isCorrect = correct;
    }
}
