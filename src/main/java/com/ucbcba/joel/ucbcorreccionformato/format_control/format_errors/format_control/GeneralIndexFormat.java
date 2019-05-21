package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_control;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordsProperties;

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
    public List<String> getFormatErrorComments(WordsProperties word) {
        List<String> comments = super.getFormatErrorComments(word);
        int indexFirstCharacter = 0;
        if (word.length() > 0) {
            while (!Character.isLetter(word.charAt(indexFirstCharacter)) && word.length() > indexFirstCharacter+1) {
                indexFirstCharacter++;
            }
        }
        boldControl(word, comments);
        italicControl(word, comments);
        allUpperCaseControl(word, comments, indexFirstCharacter);
        aligmentControl(word, comments);
        return comments;
    }

    private void aligmentControl(WordsProperties word, List<String> comments) {
        if (alignment.equals("Izquierdo")) {
            if (nroBleeding == 0 && word.getX() < 95) {
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

    private void allUpperCaseControl(WordsProperties word, List<String> comments, int indexFirstCharacter) {
        if(indexFirstCharacter+1 < word.length()) {
            if (isAllUpperCase) {
                if (!Character.isUpperCase(word.charAt(indexFirstCharacter + 1))) {
                    comments.add("Todo esté en mayúsculas");
                }
            } else {
                if (Character.isUpperCase(word.charAt(indexFirstCharacter + 1))) {
                    comments.add("No todo esté en mayúscula");
                }
            }
        }
    }

    private void italicControl(WordsProperties word, List<String> comments) {
        if (isItalic) {
            if (!word.getFontName(0).contains("Italic")) {
                comments.add("Tenga Cursiva");
            }
        }else{
            if (word.getFontName(0).contains("Italic")){
                comments.add("No tenga cursiva");
            }
        }
    }

    private void boldControl(WordsProperties word, List<String> comments) {
        if (isBold) {
            if (!word.getFontName(0).contains("Bold")) {
                comments.add("Tenga Negrilla");
            }
        }else{
            if (word.getFontName(0).contains("Bold")){
                comments.add("No tenga negrilla");
            }
        }
    }
}
