package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class TableFormat extends Format {

    private String alignment;
    private float pageWidth;
    private boolean isBold;
    private long tableNumeration;

    public TableFormat(float fontSize, String alignment, float pageWidth, boolean isBold, long tableNumeration) {
        super(fontSize);
        this.alignment = alignment;
        this.pageWidth = pageWidth;
        this.isBold = isBold;
        this.tableNumeration = tableNumeration;
    }

    @Override
    public List<String> getFormatErrorComments(WordsProperties word) {
        List<String> comments =  super.getFormatErrorComments(word);
        if (isBold) {
            if (!word.getFont().contains("Bold")) {
                comments.add("Tenga Negrilla");
            }
        }else{
            if (word.getFont().contains("Bold")){
                comments.add("No tenga negrilla");
            }
        }

        if(alignment.equals("Centrado")){
            if (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) >= 100){
                comments.add("Tenga alineación centrada");
            }
        }
        if (!word.toString().contains("Tabla "+Long.toString(tableNumeration))){
            comments.add("Número de tabla debería ser "+ tableNumeration);
        }
        return comments;
    }
}