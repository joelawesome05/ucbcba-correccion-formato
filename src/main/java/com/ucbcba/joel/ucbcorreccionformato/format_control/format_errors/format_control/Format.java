package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.ArrayList;
import java.util.List;

public class Format {

    protected String[] font;
    protected float fontSize;

    public Format(float fontSize) {
        this.font = new String[]{"Times", "New", "Roman"};
        this.fontSize = fontSize;
    }

    public List<String> getFormatErrorComments(WordLine word){
        List<String> comments = new ArrayList<>();
        fontControl(word, comments);
        fontSizeControl(word, comments);
        return comments;
    }

    private void fontControl(WordLine word, List<String> comments) {
        if (word.isNotFontName(font)){
            comments.add("Fuente: "+font[0]+" "+font[1]+" "+font[2]);
        }
    }

    private void fontSizeControl(WordLine word, List<String> comments) {
        if (word.isNotFontSize(fontSize)){
            comments.add("Tamaño de la letra sea: "+ (int) fontSize +" puntos");
        }
    }

}