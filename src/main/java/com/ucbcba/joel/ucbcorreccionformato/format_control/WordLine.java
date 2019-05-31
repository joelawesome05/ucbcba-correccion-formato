package com.ucbcba.joel.ucbcorreccionformato.format_control;

import java.util.ArrayList;
import java.util.List;

public class WordLine {
    private List<SingleLine> lines;

    public WordLine(List<SingleLine> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < lines.size(); i++) {
            builder.append(lines.get(i).toString()).append(" ");
        }
        return builder.toString();
    }

    public float getX() {
        return lines.get(0).getX();
    }

    public float getXPlusWidth() {
        return lines.get(0).getXPlusWidth();
    }

    public float getY() {
        return lines.get(lines.size()-1).getY();
    }

    public float getYPlusHeight() {
        return lines.get(0).getYPlusHeight();
    }

    public WordLine getTittle(){
        List<SingleLine> wordLines = new ArrayList<>();
        for(SingleLine line:lines){
            int index = line.toString().indexOf("..");
            SingleLine newLine = line;
            if(index!=-1){
                newLine = line.subSequence(0,index);
            }
            wordLines.add(newLine);
        }
        return new WordLine(wordLines);
    }

    public String getNumeration(){
        String[] arr = toString().split(" ", 2);
        return arr[0];
    }

    public boolean isNotFontName(String[] fontName){
        boolean resp = false;
        for (SingleLine line : lines) {
            if (line.isNotFontName(fontName)) {
                resp = true;
            }
        }
        return resp;
    }

    public boolean isNotFontSize(float fontSize){
        boolean resp = false;
        for (SingleLine line : lines) {
            if (line.isNotFontSize(fontSize)) {
                resp = true;
            }
        }
        return resp;
    }

    public boolean isBold(){
        boolean resp = false;
        for (SingleLine line : lines) {
            if (line.isStyle("Bold")) {
                resp = true;
            }
        }
        return resp;
    }

    public boolean isNotBold(){
        boolean resp = false;
        for (SingleLine line : lines) {
            if (line.isNotStyle("Bold")) {
                resp = true;
            }
        }
        return resp;
    }

    public boolean isItalic(){
        boolean resp = false;
        for (SingleLine line : lines) {
            if (line.isStyle("Italic")) {
                resp = true;
            }
        }
        return resp;
    }

    public boolean isNotItalic(){
        boolean resp = false;
        for (SingleLine line : lines) {
            if (line.isNotStyle("Italic")) {
                resp = true;
            }
        }
        return resp;
    }

    public List<SingleLine> getLines() {
        return lines;
    }

    public void setLines(List<SingleLine> lines) {
        this.lines = lines;
    }
}
