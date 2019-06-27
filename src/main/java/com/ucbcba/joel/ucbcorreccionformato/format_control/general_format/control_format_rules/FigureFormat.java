package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.List;

public class FigureFormat extends Format {

    private String alignment;
    private float pageWidth;
    private boolean isBold;
    private long figureNumeration;

    public FigureFormat(float fontSize, String alignment, float pageWidth, boolean isBold, long figureNumeration) {
        super(fontSize);
        this.alignment = alignment;
        this.pageWidth = pageWidth;
        this.isBold = isBold;
        this.figureNumeration = figureNumeration;
    }

    @Override
    public List<String> getFormatErrorComments(WordLine word) {
        List<String> comments =  super.getFormatErrorComments(word);
        boldControl(word, comments);
        aligmentControl(word, comments);
        figureNumerationControl(word, comments);
        figureTittleControl(word,comments);
        return comments;
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

    private void aligmentControl(WordLine word, List<String> comments) {
        if(alignment.equals("Centrado") && (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) >= 100)){
            comments.add("Tenga alineación centrada");
        }
    }

    private void figureNumerationControl(WordLine word, List<String> comments) {
        if (!word.toString().contains(Long.toString(figureNumeration))){
            comments.add("Número de figura debería ser "+ figureNumeration);
        }
    }

    private void figureTittleControl(WordLine word, List<String> comments) {
        if (!word.toString().contains("Figura")){
            comments.add("El título sea: 'Figura'");
        }
    }

}