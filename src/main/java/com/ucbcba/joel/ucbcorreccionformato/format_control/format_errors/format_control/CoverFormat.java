package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordsProperties;

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
        List<String> comments = super.getFormatErrorComments(word);
        boldControl(word, comments);
        italicControl(word, comments);
        algimentControl(word, comments);
        allUpperCaseControl(word, comments);
        firstLetterUpperCase(word, comments);
        return comments;
    }

    private void firstLetterUpperCase(WordsProperties word, List<String> comments) {
        if (isFirstLetterUpperCase && (!isFirstLetterUpperCase(word.toString()))) {
            comments.add("Las letras iniciales tenga mayúscula");
        }
    }

    private void allUpperCaseControl(WordsProperties word, List<String> comments) {
        if (isAllUpperCase) {
            if (!isAllUpperCase(word.toString())) {
                comments.add("Todo esté en mayúsculas");
            }
        }else{
            if (isAllUpperCase(word.toString())) {
                comments.add("No todo esté en mayúsculas");
            }
        }
    }

    private void algimentControl(WordsProperties word, List<String> comments) {
        if(alignment.equals("Centrado") && (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) >= 100)){
            comments.add("Tenga alineación centrada");
        }

        if(alignment.equals("Derecho") && (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) <= 20 || word.getXPlusWidth() < 500)){
            comments.add("Tenga alineación al margen derecho");
        }
    }

    private void italicControl(WordsProperties word, List<String> comments) {
        if (isItalic) {
            if (!word.allCharsHaveFontTypeOf("Italic")) {
                comments.add("Tenga Cursiva");
            }
        }else{
            if (word.someCharsHaveFontTypeOf("Italic")){
                comments.add("No tenga cursiva");
            }
        }
    }

    private void boldControl(WordsProperties word, List<String> comments) {
        if (isBold) {
            if (!word.allCharsHaveFontTypeOf("Bold")) {
                comments.add("Tenga Negrilla");
            }
        }else{
            if (word.someCharsHaveFontTypeOf("Bold")){
                comments.add("No tenga negrilla");
            }
        }
    }

    private boolean isAllUpperCase(String lineWord){
        boolean resp = true;
        String[] words = lineWord.split("\\s+");
        for (String word1 : words) {
            String word = word1.replaceAll("[^\\w]", "");
            if (word.length() > 3 && (!Character.isUpperCase(word.charAt(1)))) {
                return false;
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
