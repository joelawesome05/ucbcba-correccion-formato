package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class CoverFormat  extends  Format{

    private String alignment;
    private float pageWidth;
    private boolean isBold;
    private boolean isItalic;
    private boolean isAllUpperCase;
    private boolean isFirstLetterUpperCase;

    public CoverFormat(float fontSize, String alignment,float pageWidth, boolean isBold, boolean isItalic, boolean isAllUpperCase,boolean isFirstLetterUpperCase) {
        super(fontSize);
        this.alignment = alignment;
        this.pageWidth = pageWidth;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.isAllUpperCase = isAllUpperCase;
        this.isFirstLetterUpperCase = isFirstLetterUpperCase;
    }

    @Override
    public List<String> getFormatErrorComments(WordsProperties word){
        List<String> comments = super.getFormatErrorComments(word);;

        if (isBold) {
            if (!word.getFont().contains("Bold")) {
                comments.add("Tenga Negrilla");
            }
        }else{
            if (word.getFont().contains("Bold")){
                comments.add("No tenga negrilla");
            }
        }

        if (isItalic) {
            if (!word.getFont().contains("Italic")) {
                comments.add("Tenga Cursiva");
            }
        }else{
            if (word.getFont().contains("Italic")){
                comments.add("No tenga cursiva");
            }
        }

        if(alignment.equals("Centrado")){
            if (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) >= 100){
                comments.add("Tenga alineación centrada");
            }
        }

        if(alignment.equals("Derecho")){
            if (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) <= 20 || word.getXPlusWidth() < 500){
                comments.add("Tenga alineación al margen derecho");
            }
        }

        if (isAllUpperCase) {
            if (!isAllUpperCase(word.toString())) {
                comments.add("Todo esté en mayúsculas");
            }
        }
        if (isFirstLetterUpperCase) {
            if (!isFirstLetterUpperCase(word.toString())) {
                comments.add("Las letras iniciales tenga mayúscula");
            }
        }

        return comments;
    }

    private boolean isAllUpperCase(String lineWord){
        boolean resp = true;
        String[] words = lineWord.split("\\s+");
        for (String word1 : words) {
            String word = word1.replaceAll("[^\\w]", "");
            if (word.length() > 3) {
                if (!Character.isUpperCase(word.charAt(1))) {
                    return false;
                }
            }
        }
        return resp;
    }

    private boolean isFirstLetterUpperCase(String lineWord){
        boolean resp = true;
        String[] words = lineWord.split("\\s+");
        for (String word1 : words) {
            String word = word1.replaceAll("[^\\w]", "");
            if (word.length() > 3) {
                if (Character.isUpperCase(word.charAt(0))) {
                    if (Character.isUpperCase(word.charAt(1))) {
                        return false;
                    }
                } else {
                    return false;
                }
            }
        }
        return resp;
    }
}
