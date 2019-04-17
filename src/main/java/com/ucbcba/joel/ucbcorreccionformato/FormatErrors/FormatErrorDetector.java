package com.ucbcba.joel.ucbcorreccionformato.FormatErrors;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatRules.*;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.HighlightsReport.FormatErrorReport;
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


    public FormatErrorDetector(PDDocument pdfdocument, AtomicLong idHighlights) throws IOException {
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
        figureNumeration.incrementAndGet();
        tableNumeration.incrementAndGet();
    }


    public List<FormatErrorReport> getCoverPageFormatErrors(Integer coverPage) throws IOException {
        List<FormatErrorReport> coverPageFormatErrors = new ArrayList<>();
        FormatRule coverPageFormat = new CoverPageFormat(pdfdocument, idHighlights);
        if (coverPage > 0  && coverPage <= pdfdocument.getNumberOfPages()) {
            coverPageFormatErrors.addAll(coverPageFormat.getFormatErrors(coverPage));
        }
        return coverPageFormatErrors;
    }

    public List<FormatErrorReport> getGeneralIndexFormatErrors(Integer generalIndexPageStart, Integer generalIndexPageEnd) throws IOException {
        List<FormatErrorReport> generaIndexFormatErrors = new ArrayList<>();
        if (generalIndexPageStart > 0  && generalIndexPageStart <= pdfdocument.getNumberOfPages() && generalIndexPageEnd <= pdfdocument.getNumberOfPages()) {
            FormatRule generalIndexPageFormat = new GeneralIndexPageFormat(pdfdocument, idHighlights,generalIndexPageStart,generalIndexPageEnd);
            for (int page=generalIndexPageStart; page <= generalIndexPageEnd; page++){
                generaIndexFormatErrors.addAll(generalIndexPageFormat.getFormatErrors(page));
            }
        }
        return generaIndexFormatErrors;
    }

    public List<FormatErrorReport> getFigureIndexFormatErrors(Integer figureIndexPageStart, Integer figureIndexPageEnd) throws IOException {
        List<FormatErrorReport> figureIndexFormatErrors = new ArrayList<>();
        if (figureIndexPageStart > 0  && figureIndexPageStart <= pdfdocument.getNumberOfPages() && figureIndexPageEnd <= pdfdocument.getNumberOfPages()) {
            FormatRule generalIndexPageFormat = new TableFigureIndexFormat(pdfdocument, idHighlights,figureIndexPageStart,"FIGURAS");
            for (int page=figureIndexPageStart; page <= figureIndexPageEnd; page++){
                figureIndexFormatErrors.addAll(generalIndexPageFormat.getFormatErrors(page));
            }
        }
        return figureIndexFormatErrors;
    }

    public List<FormatErrorReport> getTableIndexFormatErrors(Integer tableIndexPageStart, Integer tableIndexPageEnd) throws IOException {
        List<FormatErrorReport> figureIndexFormatErrors = new ArrayList<>();
        if (tableIndexPageStart > 0  && tableIndexPageStart <= pdfdocument.getNumberOfPages() && tableIndexPageEnd <= pdfdocument.getNumberOfPages()) {
            FormatRule generalIndexPageFormat = new TableFigureIndexFormat(pdfdocument, idHighlights,tableIndexPageStart,"TABLAS");
            for (int page=tableIndexPageStart; page <= tableIndexPageEnd; page++){
                figureIndexFormatErrors.addAll(generalIndexPageFormat.getFormatErrors(page));
            }
        }
        return figureIndexFormatErrors;
    }


    public List<FormatErrorReport> getPageNumerationFormatErrors(Integer indexPageEnd, Integer annexedPage) throws IOException {
        List<FormatErrorReport> pageNumerationFormatErrors = new ArrayList<>();
        if (indexPageEnd > 0  && indexPageEnd <= pdfdocument.getNumberOfPages() && annexedPage - 1 <= pdfdocument.getNumberOfPages()) {
            for (int page=indexPageEnd+1; page < annexedPage; page++){
                FormatRule pageNumerationFormat = new PageNumerationFormat(pdfdocument, idHighlights,page);
                pageNumerationFormatErrors.addAll(pageNumerationFormat.getFormatErrors(page));
            }
            int pageNumerationAnnexes = 1;
            for (int page=annexedPage; page <= pdfdocument.getNumberOfPages(); page++){
                FormatRule pageNumerationFormat = new PageNumerationFormat(pdfdocument, idHighlights,pageNumerationAnnexes);
                pageNumerationFormatErrors.addAll(pageNumerationFormat.getFormatErrors(page));
                pageNumerationAnnexes++;
            }
        }
        return pageNumerationFormatErrors;
    }

    public List<FormatErrorReport> getFigureTableFormatErrors(Integer indexPageEnd, Integer annexedPage) throws IOException {
        List<FormatErrorReport> figureFormatErrors = new ArrayList<>();
        if (indexPageEnd > 0  && indexPageEnd <= pdfdocument.getNumberOfPages() && annexedPage - 1 <= pdfdocument.getNumberOfPages()) {
            FormatRule figureTablesFormat = new FiguresTablesFormat(pdfdocument, idHighlights, tableNumeration,figureNumeration);
            for (int page=indexPageEnd+1; page < annexedPage; page++){
                figureFormatErrors.addAll(figureTablesFormat.getFormatErrors(page));
            }
        }
        return figureFormatErrors;
    }

    public List<FormatErrorReport> getEnglishWordsFormatErrors(Integer indexPageEnd, Integer annexedPage) throws IOException {
        List<FormatErrorReport> englishWordsFormatErrors = new ArrayList<>();
        if (indexPageEnd > 0  && indexPageEnd <= pdfdocument.getNumberOfPages() && annexedPage - 1 <= pdfdocument.getNumberOfPages()) {
            FormatRule englishWordsFormat = new EnglishWordsFormat(pdfdocument, idHighlights);
            for (int page=indexPageEnd+1; page < annexedPage; page++){
                englishWordsFormatErrors.addAll(englishWordsFormat.getFormatErrors(page));
            }
        }
        return englishWordsFormatErrors;
    }

    public List<FormatErrorReport> getBiographyFormatErrors(Integer biographyPage, Integer biographyPageEnd) throws IOException {
        List<FormatErrorReport> biographyFormatErrors = new ArrayList<>();
        if (biographyPage > 0  && biographyPage <= pdfdocument.getNumberOfPages() && biographyPageEnd <= pdfdocument.getNumberOfPages()) {
            for (int page=biographyPage; page <= biographyPageEnd; page++){
                FormatRule biographyFormat = new BiographyPageFormat(pdfdocument, idHighlights);
                biographyFormatErrors.addAll(biographyFormat.getFormatErrors(page));
            }
        }
        return biographyFormatErrors;
    }

    public List<FormatErrorReport> getFigureFormatErrors(Integer figureTableIndexPageEnd, Integer annexedPage) throws IOException {
        List<FormatErrorReport> figureFormatErrors = new ArrayList<>();
        int page = 0;
        for( PDPage pdfPage : pdfdocument.getPages() ) {
            page++;
            if (page > figureTableIndexPageEnd && page < annexedPage) {
                FormatRule figuresFormat = new FiguresFormat(pdfdocument, idHighlights, pdfPage, figureNumeration);
                figureFormatErrors.addAll(figuresFormat.getFormatErrors(page));
            }
        }
        return figureFormatErrors;
    }




}
