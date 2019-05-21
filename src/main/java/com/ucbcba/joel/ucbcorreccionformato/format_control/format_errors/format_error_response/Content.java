package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response;

public class Content {
    private String text;

    public Content(String text){
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
