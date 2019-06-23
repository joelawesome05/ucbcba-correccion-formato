package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format;

import com.ucbcba.joel.ucbcorreccionformato.format_control.WordLine;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.format_error_response.*;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.others.images_pdf.PdfImage;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class ReportFormatError {
    private AtomicLong idHighlights;

    public ReportFormatError(AtomicLong idHighlights) {
        this.idHighlights = idHighlights;
    }

    public FormatErrorResponse reportFormatError(List<String> comments, WordLine word, float pageWidth, float pageHeight, int page, String type){
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

    public FormatErrorResponse reportFormatWarning(List<String> comments, WordLine word, float pageWidth, float pageHeight, int page, String type){
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
        return createFormatError(word,comment,pageWidth,pageHeight,page,String.valueOf(idHighlights.incrementAndGet()),false,type);
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
        StringBuilder commentStr = new StringBuilder("Por favor comprobar si la figura debería tener: ");
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


    public FormatErrorResponse createFormatError(WordLine word, String commentText, float pageWidth, float pageHeight, int page, String id, boolean isError, String type){
        List<BoundingRect> boundingRects = new ArrayList<>();
        List<SingleLine> singleLines = word.getLines();
        for(SingleLine line:singleLines){
            BoundingRect boundingRect = new BoundingRect(line.getX(), line.getYPlusHeight(), line.getXPlusWidth(),line.getY(),pageWidth,pageHeight);
            boundingRects.add(boundingRect);
        }
        BoundingRect mainBoundingRect = new BoundingRect(word.getX(), word.getYPlusHeight(), word.getXPlusWidth(),word.getY(),pageWidth,pageHeight);
        Content content = new Content(word.toString());
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
