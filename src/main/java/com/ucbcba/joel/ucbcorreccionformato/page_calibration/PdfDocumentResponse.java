package com.ucbcba.joel.ucbcorreccionformato.page_calibration;

public class PdfDocumentResponse {
        private int coverPage;
        private int generalIndexStartPage;
        private int generalIndexEndPage;
        private int figureIndexStartPage;
        private int figureIndexEndPage;
        private int tableIndexStartPage;
        private int tableIndexEndPage;
        private int bibliographyStartPage;
        private int bibliographyEndPage;
        private int annexesStartPage;
        private int annexesEndPage;
        private int totalPages;

    public PdfDocumentResponse(int coverPage, int generalIndexStartPage, int generalIndexEndPage, int figureIndexStartPage, int figureIndexEndPage, int tableIndexStartPage, int tableIndexEndPage, int bibliographyStartPage, int bibliographyEndPage, int annexesStartPage, int annexesEndPage,int totalPages) {
        this.coverPage = coverPage;
        this.generalIndexStartPage = generalIndexStartPage;
        this.generalIndexEndPage = generalIndexEndPage;
        this.figureIndexStartPage = figureIndexStartPage;
        this.figureIndexEndPage = figureIndexEndPage;
        this.tableIndexStartPage = tableIndexStartPage;
        this.tableIndexEndPage = tableIndexEndPage;
        this.bibliographyStartPage = bibliographyStartPage;
        this.bibliographyEndPage = bibliographyEndPage;
        this.annexesStartPage = annexesStartPage;
        this.annexesEndPage = annexesEndPage;
        this.totalPages = totalPages;
    }

    public int getCoverPage() {
        return coverPage;
    }

    public void setCoverPage(int coverPage) {
        this.coverPage = coverPage;
    }

    public int getGeneralIndexStartPage() {
        return generalIndexStartPage;
    }

    public void setGeneralIndexStartPage(int generalIndexStartPage) {
        this.generalIndexStartPage = generalIndexStartPage;
    }

    public int getGeneralIndexEndPage() {
        return generalIndexEndPage;
    }

    public void setGeneralIndexEndPage(int generalIndexEndPage) {
        this.generalIndexEndPage = generalIndexEndPage;
    }

    public int getFigureIndexStartPage() {
        return figureIndexStartPage;
    }

    public void setFigureIndexStartPage(int figureIndexStartPage) {
        this.figureIndexStartPage = figureIndexStartPage;
    }

    public int getFigureIndexEndPage() {
        return figureIndexEndPage;
    }

    public void setFigureIndexEndPage(int figureIndexEndPage) {
        this.figureIndexEndPage = figureIndexEndPage;
    }

    public int getTableIndexStartPage() {
        return tableIndexStartPage;
    }

    public void setTableIndexStartPage(int tableIndexStartPage) {
        this.tableIndexStartPage = tableIndexStartPage;
    }

    public int getTableIndexEndPage() {
        return tableIndexEndPage;
    }

    public void setTableIndexEndPage(int tableIndexEndPage) {
        this.tableIndexEndPage = tableIndexEndPage;
    }

    public int getBibliographyStartPage() {
        return bibliographyStartPage;
    }

    public void setBibliographyStartPage(int bibliographyStartPage) {
        this.bibliographyStartPage = bibliographyStartPage;
    }

    public int getBibliographyEndPage() {
        return bibliographyEndPage;
    }

    public void setBibliographyEndPage(int bibliographyEndPage) {
        this.bibliographyEndPage = bibliographyEndPage;
    }

    public int getAnnexesStartPage() {
        return annexesStartPage;
    }

    public void setAnnexesStartPage(int annexesStartPage) {
        this.annexesStartPage = annexesStartPage;
    }

    public int getAnnexesEndPage() {
        return annexesEndPage;
    }

    public void setAnnexesEndPage(int annexesEndPage) {
        this.annexesEndPage = annexesEndPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}
