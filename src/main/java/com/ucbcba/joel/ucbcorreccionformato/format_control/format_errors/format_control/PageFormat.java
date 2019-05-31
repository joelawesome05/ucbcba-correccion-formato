package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.List;

public class PageFormat extends Format {
    private String alignment;
    private float pageWidth;
    private int correctPageNumeration;
    public PageFormat(float fontSize, String alignment, float pageWidth, int correctPageNumeration) {
        super(fontSize);
        this.alignment = alignment;
        this.pageWidth = pageWidth;
        this.correctPageNumeration = correctPageNumeration;
    }

    @Override
    public List<String> getFormatErrorComments(WordLine word) {
        List<String> comments =  super.getFormatErrorComments(word);
        algimentControl(word, comments);
        pageNumerationControl(word, comments);
        return comments;
    }

    private void algimentControl(WordLine word, List<String> comments) {
        if(alignment.equals("Derecho") && (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) <= 20 || word.getXPlusWidth() < 500)){
            comments.add("Tenga alineación al margen derecho");
        }
    }

    private void pageNumerationControl(WordLine word, List<String> comments) {
        if (!word.toString().contains(Integer.toString(correctPageNumeration))){
            comments.add("Número de página debería ser "+ correctPageNumeration);
        }
    }
}
