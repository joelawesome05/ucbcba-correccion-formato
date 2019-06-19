package com.ucbcba.joel.ucbcorreccionformato.format_control;

import org.apache.pdfbox.text.TextPosition;

import java.util.List;

public class SingleLine implements CharSequence{

    private List<TextPosition> textPositions;
    private int start;
    private int end;

    public SingleLine(List<TextPosition> textPositions) {
        this.textPositions = textPositions;
        this.start = 0;
        this.end = textPositions.size();
    }

    public SingleLine(List<TextPosition> textPositions, int start, int end) {
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
    public SingleLine subSequence(int start, int end) {
        return new SingleLine(textPositions, this.start + start, this.start + end);
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

    public boolean isNotFontName(String[] fontName){
        boolean resp = false;
        for (String font : fontName) {
            if (isNotFontName(font)) {
                resp = true;
            }
        }
        return resp;
    }

    private boolean isNotFontName(String fontName){
        boolean resp = false;
        for(int pos=start;pos<end;pos++){
            if(Character.isLetterOrDigit(charAt(pos)) && !getFontName(pos).contains(fontName)){
                return true;
            }
        }
        return resp;
    }

    public boolean isNotFontSize(float fontSize){
        boolean resp = false;
        for(int pos=start;pos<end;pos++){
            if(Character.isLetterOrDigit(charAt(pos)) && getFontSize(pos)!=fontSize){
                return true;
            }
        }
        return resp;
    }

    public boolean isNotStyle(String style){
        boolean resp = false;
        for(int pos=start;pos<end;pos++){
            if(Character.isLetterOrDigit(charAt(pos)) && !getFontName(pos).contains(style)){
                return true;
            }
        }
        return resp;
    }

    public boolean isStyle(String style){
        boolean resp = false;
        for(int pos=start;pos<end;pos++){
            if(Character.isLetterOrDigit(charAt(pos)) && getFontName(pos).contains(style)){
                return true;
            }
        }
        return resp;
    }
}
