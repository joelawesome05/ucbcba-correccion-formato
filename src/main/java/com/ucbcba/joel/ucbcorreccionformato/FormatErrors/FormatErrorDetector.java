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
    private List<FormatErrorReport> formatErrorReports = new ArrayList<>();

    private AtomicLong idHighlights;
    private final AtomicLong figureNumeration = new AtomicLong();


    public FormatErrorDetector(PDDocument pdfdocument, AtomicLong idHighlights) throws IOException {
        this.pdfdocument = pdfdocument;
        this.idHighlights = idHighlights;
        figureNumeration.incrementAndGet();
    }

    public List<FormatErrorReport> getFormatErrorReports() {
        return formatErrorReports;
    }

    public void analyzeFormatPdf(Integer coverPage, Integer generalIndexPageStart, Integer generalIndexPageEnd, Integer figureTableIndexPageEnd, Integer biographyPage, Integer annexedPage) throws IOException {

        int pageNumerationAnnexes = 1;
        int page = 0;
        for( PDPage pdfPage : pdfdocument.getPages() ) {
            page++;
            if (page == coverPage){
                FormatRule coverPageFormat = new CoverPageFormat(pdfdocument, idHighlights);
                formatErrorReports.addAll(coverPageFormat.getFormatErrors(page));
                continue;
            }

            if (page >= generalIndexPageStart && page <= generalIndexPageEnd){
                FormatRule generalIndexPageFormat = new GeneralIndexPageFormat(pdfdocument, idHighlights,generalIndexPageEnd);
                formatErrorReports.addAll(generalIndexPageFormat.getFormatErrors(page));
                continue;
            }

            if (page <= figureTableIndexPageEnd){
                continue;
            }

            if (page >= biographyPage && page <= annexedPage-1 ){
                FormatRule generalIndexPageFormat = new BiographyPageFormat(pdfdocument, idHighlights);
                formatErrorReports.addAll(generalIndexPageFormat.getFormatErrors(page));
                continue;
            }

            if ( page < annexedPage){
                FormatRule figuresFormat = new FiguresFormat(pdfdocument, idHighlights,pdfPage,figureNumeration);
                formatErrorReports.addAll(figuresFormat.getFormatErrors(page));


                FormatRule pageNumerationFormat = new PageNumerationFormat(pdfdocument, idHighlights,page);
                formatErrorReports.addAll(pageNumerationFormat.getFormatErrors(page));
            }else{
                FormatRule pageNumerationFormat = new PageNumerationFormat(pdfdocument, idHighlights,pageNumerationAnnexes);
                formatErrorReports.addAll(pageNumerationFormat.getFormatErrors(page));
                pageNumerationAnnexes++;
            }

        }
    }

    public List<FormatErrorReport> getCoverPageFormatErrors(Integer coverPage) throws IOException {
        List<FormatErrorReport> coverPageFormatErrors = new ArrayList<>();
        if (coverPage > 0  && coverPage <= pdfdocument.getNumberOfPages()) {
            FormatRule coverPageFormat = new CoverPageFormat(pdfdocument, idHighlights);
            coverPageFormatErrors.addAll(coverPageFormat.getFormatErrors(coverPage));
        }
        return coverPageFormatErrors;
    }

    public List<FormatErrorReport> getGeneralIndexFormatErrors(Integer generalIndexPageStart, Integer generalIndexPageEnd) throws IOException {
        List<FormatErrorReport> generaIndexFormatErrors = new ArrayList<>();
        if (generalIndexPageStart > 0  && generalIndexPageStart <= pdfdocument.getNumberOfPages() && generalIndexPageEnd <= pdfdocument.getNumberOfPages()) {
            for (int page=generalIndexPageStart; page <= generalIndexPageEnd; page++){
                FormatRule generalIndexPageFormat = new GeneralIndexPageFormat(pdfdocument, idHighlights,generalIndexPageEnd);
                generaIndexFormatErrors.addAll(generalIndexPageFormat.getFormatErrors(page));
            }
        }
        return generaIndexFormatErrors;
    }

    public List<FormatErrorReport> getPageNumerationFormatErrors(Integer figureTableIndexPageEnd, Integer annexedPage) throws IOException {
        List<FormatErrorReport> pageNumerationFormatErrors = new ArrayList<>();
        if (figureTableIndexPageEnd > 0  && figureTableIndexPageEnd <= pdfdocument.getNumberOfPages() && annexedPage - 1 <= pdfdocument.getNumberOfPages()) {
            for (int page=figureTableIndexPageEnd+1; page < annexedPage; page++){
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

    public List<FormatErrorReport> getBiographyFormatErrors(Integer biographyPage, Integer annexedPage) throws IOException {
        List<FormatErrorReport> biographyFormatErrors = new ArrayList<>();
        if (biographyPage > 0  && biographyPage <= pdfdocument.getNumberOfPages() && annexedPage - 1 <= pdfdocument.getNumberOfPages()) {
            for (int page=biographyPage; page < annexedPage; page++){
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
