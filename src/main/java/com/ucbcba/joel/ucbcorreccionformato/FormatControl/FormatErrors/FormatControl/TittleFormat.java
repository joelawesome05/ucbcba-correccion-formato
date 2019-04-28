package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.FormatControl.WordsProperties;

import java.text.Normalizer;
import java.util.List;

public class TittleFormat extends  Format {

    private String alignment;
    private float pageWidth;
    private boolean isBold;
    private String correctTittle;
    public TittleFormat(float fontSize, String alignment, float pageWidth, boolean isBold, String correctTittle) {
        super(fontSize);
        this.alignment = alignment;
        this.pageWidth = pageWidth;
        this.isBold = isBold;
        this.correctTittle = correctTittle;
    }

    @Override
    public List<String> getFormatErrorComments(WordsProperties word){
        List<String> comments = super.getFormatErrorComments(word);
        String correctTittleNormalized = Normalizer.normalize(correctTittle, Normalizer.Form.NFD);
        correctTittleNormalized = correctTittleNormalized.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");

        if (!word.toString().contains(correctTittle) && !word.toString().contains(correctTittleNormalized)) {
            comments.add("El título sea: "+correctTittle);
        }

        if (isBold) {
            if (!word.allCharsHaveFontTypeOf("Bold")) {
                comments.add("Tenga Negrilla");
            }
        }else{
            if (word.someCharsHaveFontTypeOf("Bold")){
                comments.add("No tenga negrilla");
            }
        }

        if(alignment.equals("Centrado")){
            if (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) >= 100){
                comments.add("Tenga alineación centrada");
            }
        }

        if (alignment.equals("Izquierdo")) {
            if (word.getX() < 95 || word.getX() > 105) {
                comments.add("Alineado al margen izquierdo");
            }
        }


        return comments;
    }
}
