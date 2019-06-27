package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.List;

public class PageFormat extends Format {
    private String alignment;
    private float pageWidth;
    private boolean isBold;
    private boolean isItalic;
    private int correctPageNumeration;

    public PageFormat(float fontSize, String alignment, float pageWidth, int correctPageNumeration, boolean isBold, boolean isItalic) {
        super(fontSize);
        this.alignment = alignment;
        this.pageWidth = pageWidth;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.correctPageNumeration = correctPageNumeration;
    }

    @Override
    public List<String> getFormatErrorComments(WordLine word) {
        List<String> comments =  super.getFormatErrorComments(word);
        algimentControl(word, comments);
        boldControl(word, comments);
        italicControl(word, comments);
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

    private void boldControl(WordLine word, List<String> comments) {
        if (isBold) {
            if (word.isNotBold()) {
                comments.add("Tenga Negrilla");
            }
        }else{
            if (word.isBold()){
                comments.add("No tenga Negrilla");
            }
        }
    }

    private void italicControl(WordLine word, List<String> comments) {
        if (isItalic) {
            if (word.isNotItalic()) {
                comments.add("Tenga Cursiva");
            }
        }else{
            if (word.isItalic()){
                comments.add("No tenga Cursiva");
            }
        }
    }
}
