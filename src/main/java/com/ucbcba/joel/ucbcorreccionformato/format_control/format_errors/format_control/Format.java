package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control;


import com.ucbcba.joel.ucbcorreccionformato.format_control.WordsProperties;

import java.util.ArrayList;
import java.util.List;

public class Format {

    protected String[] font;
    protected float fontSize;


    public Format(float fontSize) {
        this.font = new String[]{"Times", "New", "Roman"};
        this.fontSize = fontSize;
    }

    public List<String> getFormatErrorComments(WordsProperties word){
        List<String> comments = new ArrayList<>();
        if (!word.allCharsHaveFontNameOf(font)){
            comments.add("Fuente: "+font[0]+" "+font[1]+" "+font[2]);
        }

        if (!word.allCharsHaveFontSizeOf(fontSize)){
            comments.add("Tamaño de la letra: "+ (int) fontSize +" puntos");
        }
        return comments;
    }

    public List<String> getBasicFormatErrorComments(WordsProperties word){
        List<String> comments = new ArrayList<>();
        if (!word.hasAFontNameBasicOf(font)){
            comments.add("Fuente: "+font[0]+" "+font[1]+" "+font[2]);
        }

        if (!word.hasAFontSizeBasicOf(fontSize)){
            comments.add("Tamaño de la letra: "+ (int) fontSize +" puntos");
        }
        return comments;
    }


}