package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class SourceTableTittleFormat extends Format {

    private String alignment;
    private boolean isBold;
    public SourceTableTittleFormat(WordsProperties word, float fontSize, String alignment, boolean isBold) {
        super(word, fontSize);
        this.alignment = alignment;
        this.isBold = isBold;
    }

    @Override
    public List<String> getFormatErrorComments(float pageWidth){
        List<String> comments = super.getFormatErrorComments(pageWidth);

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

        return comments;
    }

}
