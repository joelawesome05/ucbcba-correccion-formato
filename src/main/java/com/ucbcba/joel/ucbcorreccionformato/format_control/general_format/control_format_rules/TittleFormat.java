package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

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
    public List<String> getFormatErrorComments(WordLine word){
        List<String> comments = super.getFormatErrorComments(word);
        String wordNormalized = normalizerWord(word.toString());
        String correctTittleNormalized = normalizerWord(correctTittle);

        tittleNameControl(comments, wordNormalized, correctTittleNormalized);
        boldControl(word, comments);
        algimentControl(word, comments);
        return comments;
    }

    private void tittleNameControl(List<String> comments, String wordNormalized, String correctTittleNormalized) {
        if (!wordNormalized.contains(correctTittleNormalized)) {
            comments.add("El título sea: "+correctTittle);
        }
    }

    private void boldControl(WordLine word, List<String> comments) {
        if (isBold) {
            if (word.isNotBold()) {
                comments.add("Tenga Negrilla");
            }
        }else{
            if (word.isBold()){
                comments.add("No tenga negrilla");
            }
        }
    }


    private void algimentControl(WordLine word, List<String> comments) {
        if(alignment.equals("Centrado") && (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) >= 100)){
            comments.add("Tenga alineación centrada");
        }

        if (alignment.equals("Izquierdo") && (word.getX() < 95 || word.getX() > 105)) {
            comments.add("Alineado al margen izquierdo");
        }
    }

    private String normalizerWord(String word){
        String wordString = Normalizer.normalize(word, Normalizer.Form.NFD);
        wordString = wordString.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
        return wordString;
    }
}
