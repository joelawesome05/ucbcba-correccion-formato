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

    private final AtomicLong idHighlights = new AtomicLong();
    private final AtomicLong figureNumeration = new AtomicLong();


    public FormatErrorDetector(PDDocument pdfdocument) throws IOException {
        this.pdfdocument = pdfdocument;
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

}
