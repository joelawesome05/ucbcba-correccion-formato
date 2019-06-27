package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.List;

public class GeneralIndexFormat extends Format {
    private String alignment;
    private boolean isBold;
    private boolean isItalic;
    private int nroBleeding;
    private boolean isAllUpperCase;

    public GeneralIndexFormat(float fontSize, String alignment, boolean isBold, boolean isItalic, boolean isAllUpperCase, int nroBleeding) {
        super(fontSize);
        this.alignment = alignment;
        this.isBold = isBold;
        this.isItalic = isItalic;
        this.nroBleeding = nroBleeding;
        this.isAllUpperCase = isAllUpperCase;
    }


    @Override
    public List<String> getFormatErrorComments(WordLine word) {
        List<String> comments = super.getFormatErrorComments(word);
        WordLine wordTittle = word.getTittle();
        boldControl(wordTittle, comments);
        italicControl(wordTittle, comments);
        allUpperCaseControl(wordTittle, comments);
        algimentControl(word, comments);
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

    private void algimentControl(WordLine word, List<String> comments) {
        if (alignment.equals("Izquierdo")) {
            if (nroBleeding == 0 && (word.getX() < 95 || word.getX() > 105)) {
                comments.add("Alineado al margen izquierdo");
            }
            if (nroBleeding == 1 && word.getX() < 106) {
                comments.add("Tenga una sangría pequeña");
            }
            if (nroBleeding == 2 && word.getX() < 117) {
                comments.add("Tenga dos sangrías pequeñas");
            }
            if (nroBleeding == 3 && word.getX() < 129) {
                comments.add("Tenga tres sangrías pequeñas");
            }
        }
    }

    private boolean isAllUpperCase(String lineWord){
        boolean resp = true;
        String[] words = lineWord.split("\\s+");
        for (String currentWord : words) {
            String word = currentWord.replaceAll("[^\\w]", "");
            if (word.length() > 2 && Character.isLetter(word.charAt(0)) && !Character.isUpperCase(word.charAt(1))) {
                return false;
            }
        }
        return resp;
    }


}
