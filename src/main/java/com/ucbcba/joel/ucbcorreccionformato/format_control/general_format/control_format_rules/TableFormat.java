package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.control_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;

import java.util.List;

public class TableFormat extends Format {

    private String alignment;
    private float pageWidth;
    private boolean isBold;
    private long tableNumeration;

    public TableFormat(float fontSize, String alignment, float pageWidth, boolean isBold, long tableNumeration) {
        super(fontSize);
        this.alignment = alignment;
        this.pageWidth = pageWidth;
        this.isBold = isBold;
        this.tableNumeration = tableNumeration;
    }


    @Override
    public List<String> getFormatErrorComments(WordLine word) {
        List<String> comments =  super.getFormatErrorComments(word);
        boldControl(word, comments);
        aligmentControl(word, comments);
        tableNumerationControl(word, comments);
        tableTittleControl(word,comments);
        return comments;
    }

    private void tableNumerationControl(WordLine word, List<String> comments) {
        if (!word.toString().contains(Long.toString(tableNumeration))){
            comments.add("Número de tabla debería ser "+ tableNumeration);
        }
    }

    private void tableTittleControl(WordLine word, List<String> comments) {
        if (!word.toString().contains("Tabla")){
            comments.add("El título sea: 'Tabla'");
        }
    }

    private void aligmentControl(WordLine word, List<String> comments) {
        if(alignment.equals("Centrado") && (Math.abs((pageWidth - word.getXPlusWidth()) - word.getX()) >= 100)){
            comments.add("Tenga alineación centrada");
        }
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
}