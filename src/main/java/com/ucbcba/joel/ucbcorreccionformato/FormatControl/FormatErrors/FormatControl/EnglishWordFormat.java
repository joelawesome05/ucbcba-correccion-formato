package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.FormatControl.WordsProperties;

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
            if (!word.hasAFontBasicTypeOf("Italic")) {
                comments.add("Tenga Cursiva");
            }
        }else{
            if (word.hasAFontBasicTypeOf("Italic")){
                comments.add("No tenga cursiva");
            }
        }

        return comments;
    }

}
