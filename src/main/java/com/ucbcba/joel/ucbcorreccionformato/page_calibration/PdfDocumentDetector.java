package com.ucbcba.joel.ucbcorreccionformato.page_calibration;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class PdfDocumentDetector {
    private PDDocument pdfdocument;

    public PdfDocumentDetector(PDDocument pdfdocument) throws IOException {
        this.pdfdocument = pdfdocument;
    }

    public PdfDocumentResponse getPdfPages() throws IOException {
        PagesFinder pagesFinder = new PagesFinder(pdfdocument);
        int coverPage = pagesFinder.getCoverPage();;
        int generalIndexStartPage =  pagesFinder.getGeneralIndexStartPage();
        int lastIndexPage = pagesFinder.getLastIndexPage(generalIndexStartPage);
        int generalIndexEndPage = pagesFinder.getGeneralIndexEndPage(generalIndexStartPage,lastIndexPage);
        int figureIndexStartPage = pagesFinder.getFigureIndexStartPage(generalIndexEndPage,lastIndexPage);
        int figureIndexEndPage = pagesFinder.getFigureIndexEndPage(figureIndexStartPage,lastIndexPage);;
        int tableIndexStartPage = pagesFinder.getTableIndexStartPage(generalIndexEndPage,lastIndexPage);;
        int tableIndexEndPage = pagesFinder.getTableIndexEndPage(tableIndexStartPage,lastIndexPage);;
        int bibliographyStartPage = pagesFinder.getBibliographyStartPage();
        int annexesStartPage = pagesFinder.getAnnexesStartPage(bibliographyStartPage);;
        int bibliographyEndPage = pagesFinder.getBibliographyEndPage(bibliographyStartPage, annexesStartPage);
        int annexesEndPage = pagesFinder.getAnnexesEndPage(annexesStartPage);;
        return new PdfDocumentResponse(coverPage,generalIndexStartPage,generalIndexEndPage,figureIndexStartPage,figureIndexEndPage,
                tableIndexStartPage,tableIndexEndPage,bibliographyStartPage,bibliographyEndPage,annexesStartPage,annexesEndPage);
    }

}
