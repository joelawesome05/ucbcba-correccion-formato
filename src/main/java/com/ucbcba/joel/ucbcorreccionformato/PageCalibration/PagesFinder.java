package com.ucbcba.joel.ucbcorreccionformato.PageCalibration;

import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class PagesFinder {

    private PDDocument pdfdocument;
    private WordsFinder wordsFinder;

    public PagesFinder(PDDocument pdfdocument){
        this.pdfdocument = pdfdocument;
        this.wordsFinder = new WordsFinder(pdfdocument);
    }

    public boolean isTheCoverInThePage(int page) throws IOException {
        boolean bool1,bool2, bool3, bool4, bool5, bool6,bool7, bool8, bool9, bool10,bool11,bool12, bool13, bool14, bool15;
        bool1 = wordsFinder.isTheWordInThePage(page,"Boliviana");
        bool2 = wordsFinder.isTheWordInThePage(page,"boliviana");
        bool3 = wordsFinder.isTheWordInThePage(page,"BOLIVIANA");
        bool4 = wordsFinder.isTheWordInThePage(page,"Regional");
        bool5 = wordsFinder.isTheWordInThePage(page,"regional");
        bool6 = wordsFinder.isTheWordInThePage(page,"REGIONAL");
        bool7 = wordsFinder.isTheWordInThePage(page,"Departamento");
        bool8 = wordsFinder.isTheWordInThePage(page,"departamento");
        bool9 = wordsFinder.isTheWordInThePage(page,"DEPARTAMENTO");
        bool10 = wordsFinder.isTheWordInThePage(page,"Carrera");
        bool11 = wordsFinder.isTheWordInThePage(page,"carrera");
        bool12 = wordsFinder.isTheWordInThePage(page,"CARRERA");
        bool13 = wordsFinder.isTheWordInThePage(page,"– Bolivia");
        bool14 = wordsFinder.isTheWordInThePage(page,"– bolivia");
        bool15 = wordsFinder.isTheWordInThePage(page,"– BOLIVIA");
        return getNumberOfTrues(bool1,bool2, bool3, bool4, bool5, bool6,bool7, bool8, bool9, bool10,bool11,bool12, bool13, bool14, bool15) >= 3;
    }

    public int getCoverPage() throws IOException {
        int resp = 0;
        int page=1;
        if( page <= pdfdocument.getNumberOfPages()) {
            if ( isTheCoverInThePage(page) ){
                return page;
            }
        }
        return resp;
    }


    public boolean isTheGeneralIndexInThePage(int page) throws IOException {
        return wordsFinder.isTheWordInThePage(page,".....") || wordsFinder.isTheWordInThePage(page,"……");
    }

    public int getGeneralIndexStartPage() throws IOException {
        int resp = 0;
        for(int page=1;page<=pdfdocument.getNumberOfPages();page++){
            if ( isTheGeneralIndexInThePage(page) ){
                return page;
            }
        }
        return resp;
    }


    public boolean isTheFigureTableIndexInThePage(int page) throws IOException {
        boolean bool1,bool2,bool3,bool4;
        bool1 = wordsFinder.isTheWordInThePage(page,"Figura");
        bool2 = wordsFinder.isTheWordInThePage(page,"FIGURA");
        bool3 = wordsFinder.isTheWordInThePage(page,"TABLA");
        bool4 = wordsFinder.isTheWordInThePage(page,"Tabla");
        return getNumberOfTrues(bool1,bool2,bool3,bool4)>=1;
    }

    public boolean isTheFigureIndexInThePage(int page) throws IOException {
        boolean bool1,bool2;
        bool1 = wordsFinder.isTheWordInThePage(page,"Figura");
        bool2 = wordsFinder.isTheWordInThePage(page,"FIGURA");
        return getNumberOfTrues(bool1,bool2)>=1;
    }

    public boolean isTheTableIndexInThePage(int page) throws IOException {
        boolean bool3,bool4;
        bool3 = wordsFinder.isTheWordInThePage(page,"TABLA");
        bool4 = wordsFinder.isTheWordInThePage(page,"Tabla");
        return getNumberOfTrues(bool3,bool4)>=1;
    }

    public int getGeneralIndexEndPage(int generalIndexPageStart, int lastIndexPage) throws IOException {
        int resp = 0;
        if(generalIndexPageStart == 0){
            return resp;
        }
        resp = generalIndexPageStart;
        for (int page = generalIndexPageStart; page <= lastIndexPage; page++) {
            if ( isTheGeneralIndexInThePage(page) && !isTheFigureTableIndexInThePage(page)){
                resp = page;
            }else{
                return resp;
            }
        }
        return resp;
    }

    public int getFigureIndexStartPage(int generalIndexPageEnd, int lastIndexPage) throws IOException {
        int resp = 0;
        if (generalIndexPageEnd+1 <= pdfdocument.getNumberOfPages()){
            for (int page = generalIndexPageEnd+1; page <= lastIndexPage; page++) {
                if ( isTheFigureIndexInThePage(page) ){
                    return page;
                }
            }
        }
        return resp;
    }

    public int getFigureIndexEndPage(int figureIndexPageStart, int lastIndexPage) throws IOException {
        int resp = 0;
        if(figureIndexPageStart == 0){
            return resp;
        }
        for (int page = figureIndexPageStart; page <= lastIndexPage; page++) {
            if ( isTheFigureIndexInThePage(page) ){
                resp = page;
            }
        }
        return resp;
    }

    public int getTableIndexStartPage(int generalIndexPageEnd, int lastIndexPage) throws IOException {
        int resp = 0;
        if (generalIndexPageEnd+1 <= pdfdocument.getNumberOfPages()){
            for (int page = generalIndexPageEnd+1; page <= lastIndexPage; page++) {
                if ( isTheTableIndexInThePage(page) ){
                    return page;
                }
            }
        }
        return resp;
    }

    public int getTableIndexEndPage(int tableIndexPageStart, int lastIndexPage) throws IOException {
        int resp = 0;
        if(tableIndexPageStart == 0){
            return resp;
        }
        for (int page = tableIndexPageStart; page <= lastIndexPage; page++) {
            if ( isTheTableIndexInThePage(page) ){
                resp = page;
            }
        }
        return resp;
    }


    public int getLastIndexPage(int generalIndexPageStart) throws IOException {
        int resp = 0;
        if(generalIndexPageStart == 0){
            return resp;
        }
        for (int page = generalIndexPageStart; page <= pdfdocument.getNumberOfPages(); page++) {
            if ( isTheGeneralIndexInThePage(page) ){
                resp = page;
            }else{
                return resp;
            }
        }
        return resp;
    }

    public boolean isTheBibliographyInThePage(int page) throws IOException {
        boolean bool1,bool2,bool3,bool4;
        bool1 = wordsFinder.isTheWordInThePageAdvanced(page,"BIBLIOGRAFÍA");
        bool2 = wordsFinder.isTheWordInThePageAdvanced(page,"Bibliografía");
        return getNumberOfTrues(bool1,bool2) >= 1;
    }

    public int getBibliographyStartPage() throws IOException {
        int resp = 0;
        for (int page = pdfdocument.getNumberOfPages(); page >= 1; page--) {
            if ( isTheBibliographyInThePage(page) ){
                return page;
            }
        }
        return resp;
    }

    public int getBibliographyEndPage(int biographyPageStart, int annexedPageStart){
        int resp = 0;
        if (biographyPageStart == resp){
            return resp;
        }
        if (annexedPageStart == resp){
            return pdfdocument.getNumberOfPages();
        }
        return annexedPageStart-1;
    }


    public boolean isTheAnnexesStartInThePage(int page) throws IOException {
        boolean bool1,bool2,bool3,bool4,bool5,bool6,bool7,bool8,bool9,bool10,bool11,bool12,bool13,bool14;
        bool1 = wordsFinder.isTheWordInThePage(page,"Anexo 1 ");
        bool2 = wordsFinder.isTheWordInThePage(page,"anexo 1 ");
        bool3 = wordsFinder.isTheWordInThePage(page,"ANEXO 1 ");
        bool4 = wordsFinder.isTheWordInThePage(page,"Anexo 1:");
        bool5 = wordsFinder.isTheWordInThePage(page,"anexo 1:");
        bool6 = wordsFinder.isTheWordInThePage(page,"ANEXO 1:");
        bool7 = wordsFinder.isTheWordInThePage(page,"Anexo 1,");
        bool8 = wordsFinder.isTheWordInThePage(page,"anexo 1,");
        bool9 = wordsFinder.isTheWordInThePage(page,"ANEXO 1,");
        bool10 = wordsFinder.isTheWordInThePage(page,"Anexo 1.");
        bool11 = wordsFinder.isTheWordInThePage(page,"anexo 1.");
        bool12 = wordsFinder.isTheWordInThePage(page,"ANEXO 1.");
        bool13 = wordsFinder.isTheWordInThePage(page,"ANEXO A");
        bool14 = wordsFinder.isTheWordInThePage(page,"Anexo A.");
        return getNumberOfTrues(bool1,bool2,bool3,bool4,bool5,bool6,bool7,bool8,bool9,bool10,bool11,bool12,bool13,bool14) >= 1;
    }


    public int getAnnexesStartPage() throws IOException {
        int resp = 0;
        for (int page = pdfdocument.getNumberOfPages(); page >= 1; page--) {
            if ( isTheAnnexesStartInThePage(page) ){
                return page;
            }
        }
        return resp;
    }

    public int getAnnexesEndPage(int annexedPageStart){
        int resp = 0;
        if (annexedPageStart == resp){
            return resp;
        }
        return pdfdocument.getNumberOfPages();
    }



    public int getNumberOfTrues(boolean... vars) {
        int count = 0;
        for (boolean var : vars) {
            count += (var ? 1 : 0);
        }
        return count;
    }
}
