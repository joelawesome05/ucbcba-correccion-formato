package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors;

import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.*;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.others.images_pdf.PdfImage;
import com.ucbcba.joel.ucbcorreccionformato.format_control.WordsProperties;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ReportFormatError {
    private AtomicLong idHighlights;

    public ReportFormatError(AtomicLong idHighlights) {
        this.idHighlights = idHighlights;
    }

    public FormatErrorResponse reportFormatError(List<String> comments, WordsProperties word, float pageWidth, float pageHeight, int page,String type){
        StringBuilder commentStr = new StringBuilder("Por favor verficar: ");
        for (int i = 0; i < comments.size(); i++) {
            if (i != 0) {
                commentStr.append(" - ").append(comments.get(i));
            } else {
                commentStr.append(comments.get(i));
            }
        }
        commentStr.append(".");
        String comment = commentStr.toString();
        String content = word.toString();
        return createFormatError(word,content,comment,pageWidth,pageHeight,page,String.valueOf(idHighlights.incrementAndGet()),true,type);
    }

    public FormatErrorResponse reportFormatWarning(List<String> comments, WordsProperties word, float pageWidth, float pageHeight, int page,String type){
        StringBuilder commentStr = new StringBuilder("Por favor verifique si debería: ");
        for (int i = 0; i < comments.size(); i++) {
            if (i != 0) {
                commentStr.append(" - ").append(comments.get(i));
            } else {
                commentStr.append(comments.get(i));
            }
        }
        commentStr.append(".");
        String comment = commentStr.toString();
        String content = word.toString();
        return createFormatError(word,content,comment,pageWidth,pageHeight,page,String.valueOf(idHighlights.incrementAndGet()),false,type);
    }

    public FormatErrorResponse reportFormatError(List<String> comments, List<WordsProperties> word, float pageWidth, float pageHeight, int page,String type){
        StringBuilder commentStr = new StringBuilder("Por favor verficar: ");
        for (int i = 0; i < comments.size(); i++) {
            if (i != 0) {
                commentStr.append(" - ").append(comments.get(i));
            } else {
                commentStr.append(comments.get(i));
            }
        }
        commentStr.append(".");
        String comment = commentStr.toString();
        return createFormatError(word,comment,pageWidth,pageHeight,page,String.valueOf(idHighlights.incrementAndGet()),true,type);
    }

    public FormatErrorResponse reportFigureFormatError(List<String> comments, PdfImage image, float pageWidth, float pageHeight, int page,String type){
        StringBuilder commentStr = new StringBuilder();
        for (int i = 0; i < comments.size(); i++) {
            if (i != 0) {
                commentStr.append(" - ").append(comments.get(i));
            } else {
                commentStr.append(comments.get(i));
            }
        }
        commentStr.append(".");
        String comment = commentStr.toString();
        String content = "Imagen";
        return createFormatFigureError(image,content,comment,pageWidth,pageHeight,page,String.valueOf(idHighlights.incrementAndGet()),true,type);
    }


    public FormatErrorResponse reportFigureFormatWarning(List<String> comments, PdfImage image, float pageWidth, float pageHeight, int page,String type){
        StringBuilder commentStr = new StringBuilder("Por favor comprobar que la figura tenga: ");
        for (int i = 0; i < comments.size(); i++) {
            if (i != 0) {
                commentStr.append(" - ").append(comments.get(i));
            } else {
                commentStr.append(comments.get(i));
            }
        }
        commentStr.append(".");
        String comment = commentStr.toString();
        String content = "Imagen";
        return createFormatFigureError(image,content,comment,pageWidth,pageHeight,page,String.valueOf(idHighlights.incrementAndGet()),false,type);
    }

    public FormatErrorResponse createFormatError(WordsProperties word, String contentText, String commentText, float pageWidth, float pageHeight, int page, String id, boolean isError,String type  ){
        Content content = new Content(contentText);
        BoundingRect boundingRect = new BoundingRect(word.getX(), word.getYPlusHeight(), word.getXPlusWidth(),word.getY(),pageWidth,pageHeight);
        List<BoundingRect> boundingRects = new ArrayList<>();
        boundingRects.add(boundingRect);
        Position position = new Position(boundingRect,boundingRects,page);
        Comment comment = new Comment(commentText,"");
        return new FormatErrorResponse(content,position,comment,id,isError,type);
    }

    public FormatErrorResponse createFormatError(List<WordsProperties> word, String commentText, float pageWidth, float pageHeight, int page, String id, boolean isError,String type  ){
        List<BoundingRect> boundingRects = new ArrayList<>();
        float x = 0;
        float y=0;
        float endX=0;
        float upperY=0;
        String contentText = "";
        for(int pos=0; pos<word.size();pos++){
            WordsProperties currentWordLine = word.get(pos);
            BoundingRect boundingRect = new BoundingRect(currentWordLine.getX(), currentWordLine.getYPlusHeight(), currentWordLine.getXPlusWidth(),currentWordLine.getY(),pageWidth,pageHeight);
            boundingRects.add(boundingRect);
            if (pos==0){
                x = currentWordLine.getX();
                upperY = currentWordLine.getYPlusHeight();
                contentText = currentWordLine.toString();
            }
            if (pos==word.size()-1){
                endX = currentWordLine.getXPlusWidth();
                y = currentWordLine.getY();
            }
        }
        BoundingRect mainBoundingRect = new BoundingRect(x, upperY, endX,y,pageWidth,pageHeight);
        Content content = new Content(contentText);
        Position position = new Position(mainBoundingRect,boundingRects,page);
        Comment comment = new Comment(commentText,"");
        return new FormatErrorResponse(content,position,comment,id,isError,type);
    }



    public FormatErrorResponse createFormatFigureError(PdfImage image, String contentText, String commentText, float pageWidth, float pageHeight, int page, String id , boolean isError,String type ){
        Content content = new Content(contentText);
        BoundingRect boundingRect = new BoundingRect(image.getX(), image.getY() , image.getEndX(),image.getEndY(),pageWidth,pageHeight);
        List<BoundingRect> boundingRects = new ArrayList<>();
        boundingRects.add(boundingRect);
        Position position = new Position(boundingRect,boundingRects,page);
        Comment comment = new Comment(commentText,"");
        return new FormatErrorResponse(content,position,comment,id,isError,type);
    }


}
