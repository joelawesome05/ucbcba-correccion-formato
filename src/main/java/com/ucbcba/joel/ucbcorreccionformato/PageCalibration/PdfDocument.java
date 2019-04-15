package com.ucbcba.joel.ucbcorreccionformato.PageCalibration;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class PdfDocument {
    private PDDocument pdfdocument;
    private int coverPage;
    private int generalIndexPageStart;
    private int generalIndexPageEnd;
    private int figureIndexPageStart;
    private int figureIndexPageEnd;
    private int tableIndexPageStart;
    private int tableIndexPageEnd;
    private int figureTableIndexPageEnd;
    private int biographyPageStart;
    private int biographyPageEnd;
    private int annexedPageStart;
    private int annexedPageEnd;

    public PdfDocument(PDDocument pdfdocument) throws IOException {
        this.pdfdocument = pdfdocument;
        PagesSeeker seeker = new PagesSeeker(pdfdocument);
        this.coverPage = seeker.getCoverPage();
        this.generalIndexPageStart = seeker.getFirstGeneralIndexPage();
        int lastIndexPage = seeker.getIndexPage(generalIndexPageStart);
        this.generalIndexPageEnd = seeker.getLastGeneralIndexPage(generalIndexPageStart,lastIndexPage);
        this.figureIndexPageStart = seeker.getFirstFigureIndexPage(generalIndexPageEnd,lastIndexPage);
        this.figureIndexPageEnd = seeker.getLastFigureIndexPage(figureIndexPageStart,lastIndexPage);
        this.tableIndexPageStart = seeker.getFirstTableIndexPage(generalIndexPageEnd,lastIndexPage);
        this.tableIndexPageEnd = seeker.getLastTableIndexPage(tableIndexPageStart,lastIndexPage);
        this.biographyPageStart = seeker.getFirstBiographyPage();
        this.annexedPageStart = seeker.getFirstAnnexedPage();
        this.biographyPageEnd = seeker.getLastBiographyPage(biographyPageStart,annexedPageStart);
        this.annexedPageEnd = seeker.getLastAnnexedPage(annexedPageStart);
    }

    public int getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(int coverPage) {
        this.coverPage = coverPage;
    }

    public int getGeneralIndexPageStart() {
        return generalIndexPageStart;
    }

    public void setGeneralIndexPageStart(int generalIndexPageStart) {
        this.generalIndexPageStart = generalIndexPageStart;
    }

    public int getGeneralIndexPageEnd() {
        return generalIndexPageEnd;
    }

    public void setGeneralIndexPageEnd(int generalIndexPageEnd) {
        this.generalIndexPageEnd = generalIndexPageEnd;
    }

    public int getAnnexedPageStart() {
        return annexedPageStart;
    }

    public void setAnnexedPageStart(int annexedPageStart) {
        this.annexedPageStart = annexedPageStart;
    }

    public PDDocument getPdfdocument() {
        return pdfdocument;
    }

    public int getBiographyPageStart() {
        return biographyPageStart;
    }

    public void setBiographyPageStart(int biographyPageStart) {
        this.biographyPageStart = biographyPageStart;
    }

    public int getFigureTableIndexPageEnd() {
        return figureTableIndexPageEnd;
    }

    public void setFigureTableIndexPageEnd(int figureTableIndexPageEnd) {
        this.figureTableIndexPageEnd = figureTableIndexPageEnd;
    }

    public int getFigureIndexPageStart() {
        return figureIndexPageStart;
    }

    public int getFigureIndexPageEnd() {
        return figureIndexPageEnd;
    }

    public int getTableIndexPageStart() {
        return tableIndexPageStart;
    }

    public int getTableIndexPageEnd() {
        return tableIndexPageEnd;
    }

    public int getBiographyPageEnd() {
        return biographyPageEnd;
    }

    public int getAnnexedPageEnd() {
        return annexedPageEnd;
    }
}
