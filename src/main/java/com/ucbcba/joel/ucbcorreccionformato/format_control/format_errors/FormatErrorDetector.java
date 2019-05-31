package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors;

import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_rules.*;
import com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.format_error_response.FormatErrorResponse;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class FormatErrorDetector {
    private PDDocument pdfdocument;
    private AtomicLong idHighlights;
    private final AtomicLong figureNumeration = new AtomicLong();
    private final AtomicLong tableNumeration = new AtomicLong();


    public FormatErrorDetector(PDDocument pdfdocument, AtomicLong idHighlights){
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
        figureNumeration.incrementAndGet();
        tableNumeration.incrementAndGet();
    }


    public List<FormatErrorResponse> getCoverPageFormatErrors(Integer coverPage) throws IOException {
        List<FormatErrorResponse> coverPageFormatErrors = new ArrayList<>();
        if (coverPage > 0 && coverPage <= pdfdocument.getNumberOfPages()) {
            FormatRule coverPageFormat = new CoverPageFormat(pdfdocument, idHighlights);
            coverPageFormatErrors.addAll(coverPageFormat.getFormatErrors(coverPage));
        }
        return coverPageFormatErrors;
    }

    public List<FormatErrorResponse> getGeneralIndexFormatErrors(Integer generalIndexStartPage, Integer generalIndexEndPage) throws IOException {
        List<FormatErrorResponse> generaIndexFormatErrors = new ArrayList<>();
        if(generalIndexStartPage > 0 && generalIndexStartPage <= pdfdocument.getNumberOfPages() && generalIndexEndPage <= pdfdocument.getNumberOfPages()) {
            FormatRule generalIndexPageFormat = new GeneralIndexPageFormat(pdfdocument, idHighlights, generalIndexStartPage, generalIndexEndPage);
            for (int page = generalIndexStartPage; page <= generalIndexEndPage; page++) {
                generaIndexFormatErrors.addAll(generalIndexPageFormat.getFormatErrors(page));
            }
        }
        return generaIndexFormatErrors;
    }

    public List<FormatErrorResponse> getFigureIndexFormatErrors(Integer figureIndexStartPage, Integer figureIndexEndPage) throws IOException {
        List<FormatErrorResponse> figureIndexFormatErrors = new ArrayList<>();
        if(figureIndexStartPage > 0 && figureIndexStartPage <= pdfdocument.getNumberOfPages() && figureIndexEndPage <= pdfdocument.getNumberOfPages()) {
            FormatRule generalIndexPageFormat = new TableFigureIndexFormat(pdfdocument, idHighlights, figureIndexStartPage, "FIGURAS");
            for (int page = figureIndexStartPage; page <= figureIndexEndPage; page++) {
                figureIndexFormatErrors.addAll(generalIndexPageFormat.getFormatErrors(page));
            }

        }
        return figureIndexFormatErrors;
    }

    public List<FormatErrorResponse> getTableIndexFormatErrors(Integer tableIndexStartPage, Integer tableIndexEndPage) throws IOException {
        List<FormatErrorResponse> figureIndexFormatErrors = new ArrayList<>();
        if(tableIndexStartPage > 0 && tableIndexStartPage <= pdfdocument.getNumberOfPages() && tableIndexEndPage <= pdfdocument.getNumberOfPages()) {
            FormatRule generalIndexPageFormat = new TableFigureIndexFormat(pdfdocument, idHighlights, tableIndexStartPage, "TABLAS");
            for (int page = tableIndexStartPage; page <= tableIndexEndPage; page++) {
                figureIndexFormatErrors.addAll(generalIndexPageFormat.getFormatErrors(page));
            }
        }
        return figureIndexFormatErrors;
    }

    public List<FormatErrorResponse> getPageNumerationFormatErrors(Integer indexEndPage, Integer annexesStartPage, Integer annexesEndPage) throws IOException {
        if(annexesStartPage==0){
            annexesStartPage = pdfdocument.getNumberOfPages()+1;
        }
        if(annexesEndPage==0){
            annexesEndPage = pdfdocument.getNumberOfPages()+1;
        }
        List<FormatErrorResponse> pageNumerationFormatErrors = new ArrayList<>();
        if (indexEndPage >= 0  && indexEndPage <= pdfdocument.getNumberOfPages() && annexesStartPage -1 <= pdfdocument.getNumberOfPages() ) {
            for (int page=indexEndPage+1; page < annexesStartPage; page++){
                FormatRule pageNumerationFormat = new PageNumerationFormat(pdfdocument, idHighlights,page);
                pageNumerationFormatErrors.addAll(pageNumerationFormat.getFormatErrors(page));
            }
            if(annexesEndPage <= pdfdocument.getNumberOfPages()) {
                int pageNumerationAnnexes = 1;
                for (int page = annexesStartPage; page <= annexesEndPage; page++) {
                    FormatRule pageNumerationFormat = new PageNumerationFormat(pdfdocument, idHighlights, pageNumerationAnnexes);
                    pageNumerationFormatErrors.addAll(pageNumerationFormat.getFormatErrors(page));
                    pageNumerationAnnexes++;
                }
            }
        }
        return pageNumerationFormatErrors;
    }

    public List<FormatErrorResponse> getFigureTableFormatErrors(Integer indexEndPage, Integer bibliographyStartPage) throws IOException {
        if(bibliographyStartPage==0){
            bibliographyStartPage = pdfdocument.getNumberOfPages()+1;
        }
        List<FormatErrorResponse> figureFormatErrors = new ArrayList<>();
        if (indexEndPage >= 0  && indexEndPage <= pdfdocument.getNumberOfPages() && bibliographyStartPage -1 <= pdfdocument.getNumberOfPages()) {
            FormatRule figureTablesFormat = new FiguresTablesFormat(pdfdocument, idHighlights, tableNumeration,figureNumeration,bibliographyStartPage);
            for (int page=indexEndPage+1; page < bibliographyStartPage; page++){
                figureFormatErrors.addAll(figureTablesFormat.getFormatErrors(page));
            }
            figureFormatErrors.addAll(getFigureFormatErrors(indexEndPage,bibliographyStartPage));
        }
        return figureFormatErrors;
    }

    public List<FormatErrorResponse> getEnglishWordsFormatErrors(Integer indexEndPage, Integer bibliographyStartPage) throws IOException {
        if(bibliographyStartPage==0){
            bibliographyStartPage = pdfdocument.getNumberOfPages()+1;
        }
        List<FormatErrorResponse> englishWordsFormatErrors = new ArrayList<>();
        if (indexEndPage >= 0  && indexEndPage <= pdfdocument.getNumberOfPages() && bibliographyStartPage - 1 <= pdfdocument.getNumberOfPages()) {
            FormatRule englishWordsFormat = new EnglishWordsFormat(pdfdocument, idHighlights);
            for (int page=indexEndPage+1; page < bibliographyStartPage; page++){
                englishWordsFormatErrors.addAll(englishWordsFormat.getFormatErrors(page));
            }
        }
        return englishWordsFormatErrors;
    }

    public List<FormatErrorResponse> getBibliographyFormatErrors(Integer bibliographyStartPage, Integer bibliographyEndPage) throws IOException {
        List<FormatErrorResponse> biographyFormatErrors = new ArrayList<>();
        if(bibliographyStartPage > 0 && bibliographyStartPage <= pdfdocument.getNumberOfPages() && bibliographyEndPage <= pdfdocument.getNumberOfPages()) {
            FormatRule bibliographyFormat = new BibliographyPageFormat(pdfdocument, idHighlights, bibliographyStartPage);
            for (int page = bibliographyStartPage; page <= bibliographyEndPage; page++) {
                biographyFormatErrors.addAll(bibliographyFormat.getFormatErrors(page));
            }
        }
        return biographyFormatErrors;
    }

    public List<FormatErrorResponse> getFigureFormatErrors(Integer indexEndPage, Integer bibliographyStartPage) throws IOException {
        List<FormatErrorResponse> figureFormatErrors = new ArrayList<>();
        int page = 0;
        for( PDPage pdfPage : pdfdocument.getPages() ) {
            page++;
            if (page > indexEndPage && page < bibliographyStartPage) {
                FormatRule figuresFormat = new FiguresFormat(pdfdocument, idHighlights, pdfPage);
                figureFormatErrors.addAll(figuresFormat.getFormatErrors(page));
            }
        }
        return figureFormatErrors;
    }




}
