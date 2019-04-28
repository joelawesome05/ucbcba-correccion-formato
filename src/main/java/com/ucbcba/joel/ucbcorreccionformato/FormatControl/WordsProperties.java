package com.ucbcba.joel.ucbcorreccionformato.FormatControl;

import org.apache.pdfbox.text.TextPosition;

import java.util.List;

public class WordsProperties implements CharSequence{

    private List<TextPosition> textPositions;
    private int start, end;

    public WordsProperties(List<TextPosition> textPositions) {
        this.textPositions = textPositions;
        this.start = 0;
        this.end = textPositions.size();
    }

    public WordsProperties(List<TextPosition> textPositions, int start, int end) {
        this.textPositions = textPositions;
        this.start = start;
        this.end = end;
    }

    @Override
    public int length() {
        return end - start;
    }

    @Override
    public char charAt(int index) {
        TextPosition textPosition = textPositionAt(index);
        String text = textPosition.getUnicode();
        return text.charAt(0);
    }

    @Override
    public WordsProperties subSequence(int start, int end) {
        return new WordsProperties(textPositions, this.start + start, this.start + end);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder(length());
        for (int i = 0; i < length(); i++) {
            builder.append(charAt(i));
        }
        return builder.toString();
    }

    public TextPosition textPositionAt(int index) {
        return textPositions.get(start + index);
    }

    public float getX() {
        return textPositions.get(start).getXDirAdj();
    }

    public float getXPlusWidth() {
        return textPositions.get(end-1).getEndX();
    }


    public float getY() {
        return textPositions.get(start).getYDirAdj();
    }

    public float getYPlusHeight() {
        return textPositions.get(start).getYDirAdj() - textPositions.get(start).getYScale();
    }


    public String getFontName(int pos) {
        TextPosition first = textPositions.get(pos);
        return first.getFont().getName();
    }

    public float getFontSize(int pos) {
        TextPosition first = textPositions.get(pos);
        return Math.round(first.getYScale());
    }

    private boolean allCharsHaveFontNameOf(String fontName){
        boolean resp = false;
        if(getFontName(start).contains(fontName) && getFontName((start+end)/2).contains(fontName) ){
            resp = true;
        }
        return resp;
    }

    public boolean allCharsHaveFontNameOf(String[] fontName){
        boolean resp = true;
        for (String font : fontName) {
            if (!allCharsHaveFontNameOf(font)) {
                resp = false;
            }
        }
        return resp;
    }

    public boolean allCharsHaveFontSizeOf(float fontSize){
        boolean resp = false;
        if(getFontSize(start)==fontSize && getFontSize((start+end)/2)==fontSize ){
            resp = true;
        }
        return resp;
    }

    public boolean hasAFontNameBasicOf(String[] fontName){
        boolean resp = true;
        for (String font : fontName) {
            if (!hasAFontNameBasicOf(font)) {
                resp = false;
            }
        }
        return resp;
    }

    private boolean hasAFontNameBasicOf(String fontName){
        boolean resp = false;
        if(getFontName((start+end)/2).contains(fontName)){
            resp = true;
        }
        return resp;
    }

    public boolean hasAFontSizeBasicOf(float fontSize){
        boolean resp = false;
        if(getFontSize((start+end)/2)==fontSize){
            resp = true;
        }
        return resp;
    }

    public boolean allCharsHaveFontTypeOf(String type){
        boolean resp = false;
        if(getFontName(start).contains(type) && getFontName((start+end)/2).contains(type) ){
            resp = true;
        }
        return resp;
    }

    public boolean someCharsHaveFontTypeOf(String type){
        boolean resp = false;
        if(getFontName(start).contains(type) || getFontName((start+end)/2).contains(type) ){
            resp = true;
        }
        return resp;
    }

    public boolean hasAFontBasicTypeOf(String type){
        boolean resp = false;
        if(getFontName((start+end)/2).contains(type)){
            resp = true;
        }
        return resp;
    }
}
