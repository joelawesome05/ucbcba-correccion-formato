package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules;

import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.format_error_response.*;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.others.images_pdf.GetterPdfImages;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.others.images_pdf.PdfImage;
import com.ucbcba.joel.ucbcorreccionformato.format_control.GetterWordLines;
import com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.ReportFormatError;
import com.ucbcba.joel.ucbcorreccionformato.format_control.SingleLine;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FiguresPageFormat implements PageFormatRule {

    private PDDocument pdfdocument;
    private PDPage page;
    private AtomicLong idHighlights;

    public FiguresPageFormat(PDDocument pdfdocument, AtomicLong counter, PDPage page){
        this.pdfdocument = pdfdocument;
        this.page = page;
        this.idHighlights = counter;
    }

    @Override
    public List<FormatErrorResponse> getFormatErrors(int pageNum) throws IOException {

        float pageWidth = pdfdocument.getPage(pageNum-1).getMediaBox().getWidth();
        float pageHeight = pdfdocument.getPage(pageNum-1).getMediaBox().getHeight();

        List<FormatErrorResponse> formatErrors = new ArrayList<>();

        List<PdfImage> pdfImages = new GetterPdfImages().getPdfImages(pageNum, pageHeight,page);

        for (PdfImage image : pdfImages) {
            List<String> commentsFigureError = new ArrayList<>();
            List<String> commentsFigureWarnings = new ArrayList<>();
            image.setCorrectCoordinates(pageHeight);
            if (!image.isFigureHorizontal()) {
                commentsFigureError.add("Figura en sentido vertical, por favor verificar que la presente hoja tenga orientación horizontal");
            }
            if (!isInTheCorrectMargins(image,pageWidth,pageHeight)) {
                commentsFigureError.add("Por favor verificar que la figura se encuentre entre los márgenes establecidos según la guía");
            }
            reportFigureErrors(formatErrors, image, commentsFigureError, pageWidth, pageHeight, pageNum);
            if(!doesTheFigureHasTittle(image, pageNum)){
                commentsFigureWarnings.add("El título 'Figura'");
            }
            if (!doesTheFigureHasSource(image, pageNum)) {
                commentsFigureWarnings.add("La fuente correspondiente");
            }
            reportFigureWarnings(formatErrors, image, commentsFigureWarnings, pageWidth, pageHeight, pageNum);

        }

        return formatErrors;
    }

    private boolean isInTheCorrectMargins(PdfImage image,float pageWidth,float pageHeight) {
        boolean resp = true;
        if(pageWidth < pageHeight) {
            if (image.getX() < 95 || image.getY() < 75 || image.getEndX() > 535 || image.getEndY() > 705) {
                resp = false;
            }
        }else{
            if (image.getY() < 95 || image.getX() < 75 || image.getEndY() > 535 || image.getEndX() > 710) {
                resp = false;
            }
        }
        return resp;
    }


    private boolean doesTheFigureHasTittle(PdfImage image, int pageNum) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> wordsLines = getterWordLines.getSingleLines(pageNum);

        for(SingleLine wordLine:wordsLines){
            String[] arr = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Figura") && (image.getEndY() > wordLine.getY()) && (image.getY() - 200 < wordLine.getY())){
                return true;
            }
        }
        return resp;
    }

    private boolean doesTheFigureHasSource(PdfImage image, int pageNum) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<SingleLine> wordsLines = getterWordLines.getSingleLines(pageNum);

        for(SingleLine wordLine:wordsLines){
            String[] arr = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Fuente:") && (image.getY() < wordLine.getY()) && (image.getEndY() + 100 > wordLine.getY())){
                return true;
            }
        }
        return resp;
    }


    private void reportFigureWarnings(List<FormatErrorResponse> formatErrors, PdfImage image, List<String> commentsFigure, float pageWidth, float pageHeight, int page) {
        if (!commentsFigure.isEmpty()) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFigureFormatWarning(commentsFigure, image, pageWidth, pageHeight, page,"tablaFigura"));
        }
    }

    private void reportFigureErrors(List<FormatErrorResponse> formatErrors, PdfImage image, List<String> commentsFigure, float pageWidth, float pageHeight, int page) {
        if (!commentsFigure.isEmpty()) {
            formatErrors.add(new ReportFormatError(idHighlights).reportFigureFormatError(commentsFigure, image, pageWidth, pageHeight, page,"tablaFigura"));
        }
    }




}