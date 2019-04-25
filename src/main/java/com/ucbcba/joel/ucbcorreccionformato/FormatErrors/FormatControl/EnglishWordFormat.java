package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class EnglishWordFormat extends Format {

    private boolean isItalic;

    public EnglishWordFormat( float fontSize, boolean isItalic) {
        super(fontSize);
        this.isItalic = isItalic;
    }

    @Override
    public List<String> getFormatErrorComments(WordsProperties word){
        List<String> comments = super.getFormatErrorComments(word);

        if (isItalic) {
            if (!word.getFontBassic().contains("Italic")) {
                comments.add("Tenga Cursiva");
            }
        }else{
            if (word.getFontBassic().contains("Italic")){
                comments.add("No tenga cursiva");
            }
        }

        return comments;
    }

}
