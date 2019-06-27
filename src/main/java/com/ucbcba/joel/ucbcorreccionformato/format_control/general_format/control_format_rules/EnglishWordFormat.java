package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.List;

public class EnglishWordFormat extends Format {

    private boolean isItalic;

    public EnglishWordFormat( float fontSize, boolean isItalic) {
        super(fontSize);
        this.isItalic = isItalic;
    }

    @Override
    public List<String> getFormatErrorComments(WordLine word){
        List<String> comments = super.getFormatErrorComments(word);
        italicControl(word, comments);
        return comments;
    }

    private void italicControl(WordLine word, List<String> comments) {
        if (isItalic) {
            if (word.isNotItalic()) {
                comments.add("Tener Cursiva");
            }
        }else{
            if (word.isItalic()){
                comments.add("No tenga Cursiva");
            }
        }
    }

}
