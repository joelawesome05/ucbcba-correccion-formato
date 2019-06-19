package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

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
    public List<String> getFormatErrorComments(WordLine word){
        List<String> comments = super.getFormatErrorComments(word);
        boldControl(word, comments);
        italicControl(word, comments);
        algimentControl(word, comments);
        allUpperCaseControl(word, comments);
        firstLetterUpperCase(word, comments);
        return comments;
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

    private void italicControl(WordLine word, List<String> comments) {
        if (isItalic) {
            if (word.isNotItalic()) {
                comments.add("Tenga Cursiva");
            }
        }else{
            if (word.isItalic()){
                comments.add("No tenga cursiva");
            }
        }
    }

    private void algimentControl(WordLine word, List<String> comments) {
        if(alignment.equals("Centrado") && (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) >= 100)){
            comments.add("Tenga alineación centrada");
        }

        if(alignment.equals("Derecho") && (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) <= 20 || word.getXPlusWidth() < 500)){
            comments.add("Tenga alineación al margen derecho");
        }
    }

    private void allUpperCaseControl(WordLine word, List<String> comments) {
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

    private void firstLetterUpperCase(WordLine word, List<String> comments) {
        if (isFirstLetterUpperCase && (!isFirstLetterUpperCase(word.toString()))) {
            comments.add("Las letras iniciales tenga mayúscula");
        }
    }

    private boolean isAllUpperCase(String lineWord){
        boolean resp = true;
        String[] words = lineWord.split("\\s+");
        for (String currentWord : words) {
            String word = currentWord.replaceAll("[^\\w]", "");
            if (word.length() > 3 && !Character.isUpperCase(word.charAt(1))) {
                return false;
            }
        }
        return resp;
    }

    private boolean isFirstLetterUpperCase(String lineWord){
        boolean resp = true;
        String[] words = lineWord.split("\\s+");
        for (String currentWord : words) {
            String word = currentWord.replaceAll("[^\\w]", "");
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
