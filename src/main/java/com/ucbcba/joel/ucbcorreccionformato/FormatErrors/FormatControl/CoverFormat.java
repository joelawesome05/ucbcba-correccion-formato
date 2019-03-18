package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class CoverFormat  extends  Format{
    private boolean isAllUpperCase;
    private boolean isFirstLetterUpperCase;

    public CoverFormat(WordsProperties word, float fontSize, String alignment, boolean isBold, boolean isItalic, boolean isAllUpperCase,boolean isFirstLetterUpperCase) {
        super(word, fontSize, alignment, isBold, isItalic);
        this.isAllUpperCase = isAllUpperCase;
        this.isFirstLetterUpperCase = isFirstLetterUpperCase;
    }

    @Override
    public List<String> getFormatErrors(float pageWidth){
        List<String> comments = super.getFormatErrors(pageWidth);;
        if (isAllUpperCase) {
            if (!isAllUpperCase(word.toString())) {
                comments.add("Todo en mayúscula");
            }
        }
        if (isFirstLetterUpperCase) {
            if (!isFirstLetterUpperCase(word.toString())) {
                comments.add("Las mayúsculas de las primeras letras");
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
