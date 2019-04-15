package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class TableFormat extends Format {

    private long tableNumeration;

    public TableFormat(WordsProperties word, float fontSize, String alignment, boolean isBold, boolean isItalic, long tableNumeration) {
        super(word, fontSize, alignment, isBold, isItalic);
        this.tableNumeration = tableNumeration;
    }

    @Override
    public List<String> getFormatErrorComments(float pageWidth) {
        List<String> comments =  super.getFormatErrorComments(pageWidth);
        if (!word.toString().contains("Tabla "+Long.toString(tableNumeration))){
            comments.add("Número de tabla debería ser "+ tableNumeration);
        }
        return comments;
    }
}