package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class PageFormat extends Format {
    private String alignment;
    private int correctPageNumeration;
    public PageFormat(WordsProperties word, float fontSize, String alignment, int correctPageNumeration) {
        super(word, fontSize);
        this.alignment = alignment;
        this.correctPageNumeration = correctPageNumeration;
    }

    @Override
    public List<String> getFormatErrorComments(float pageWidth) {
        List<String> comments =  super.getFormatErrorComments(pageWidth);
        if(alignment.equals("Derecho")){
            if (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) <= 20 || word.getXPlusWidth() < 500){
                comments.add("Tenga alineación al margen derecho");
            }
        }
        if (!word.toString().contains(Integer.toString(correctPageNumeration))){
            comments.add("Número de página debería ser "+ correctPageNumeration);
        }
        return comments;
    }
}
