package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;


import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

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
        if (!word.getFont().contains(font[0]) || !word.getFont().contains(font[1]) || !word.getFont().contains(font[2])){
            comments.add("Fuente: "+font[0]+" "+font[1]+" "+font[2]);
        }

        if (word.getFontSize() != fontSize){
            comments.add("Tamaño de la letra: "+ (int) fontSize +" puntos");
        }
        return comments;
    }

    public List<String> getBasicFormatErrorComments(WordsProperties word){
        List<String> comments = new ArrayList<>();
        if (!word.getFontBassic().contains(font[0]) || !word.getFontBassic().contains(font[1]) || !word.getFontBassic().contains(font[2])){
            comments.add("Fuente: "+font[0]+" "+font[1]+" "+font[2]);
        }

        if (word.getFontSizeBasic() != fontSize){
            comments.add("Tamaño de la letra: "+ (int) fontSize +" puntos");
        }
        return comments;
    }


}