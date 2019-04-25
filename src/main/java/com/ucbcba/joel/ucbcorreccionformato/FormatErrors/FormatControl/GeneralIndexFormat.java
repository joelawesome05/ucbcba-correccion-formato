package com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl;

import com.ucbcba.joel.ucbcorreccionformato.General.WordsProperties;

import java.util.List;

public class GeneralIndexFormat extends Format {
    private String alignment;
    private float pageWidth;
    private boolean isBold;
    private boolean isItalic;
    private int nroBleeding;
    private boolean isAllUpperCase;

    public GeneralIndexFormat(float fontSize, String alignment, float pageWidth, boolean isBold, boolean isItalic, boolean isAllUpperCase, int nroBleeding) {
        super(fontSize);
        this.alignment = alignment;
        this.pageWidth = pageWidth;
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

        if (alignment.equals("Izquierdo")) {
            if (nroBleeding == 0) {
                if (word.getX() < 95 || word.getX() > 105) {
                    comments.add("Alineado al margen izquierdo");
                }
            }

            if (nroBleeding == 1) {
                if (word.getX() < 106 || word.getX() > 116) {
                    comments.add("Tenga una sangría pequeña");
                }
            }

            if (nroBleeding == 2) {
                if (word.getX() < 117 || word.getX() > 127) {
                    comments.add("Tenga dos sangrías pequeñas");
                }
            }

            if (nroBleeding == 3) {
                if (word.getX() < 129 || word.getX() > 139) {
                    comments.add("Tenga tres sangrías pequeñas");
                }
            }
        }
        return comments;
    }
}
