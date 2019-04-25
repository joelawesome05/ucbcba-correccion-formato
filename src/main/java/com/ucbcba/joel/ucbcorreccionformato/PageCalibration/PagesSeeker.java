package com.ucbcba.joel.ucbcorreccionformato.PageCalibration;

import com.ucbcba.joel.ucbcorreccionformato.General.GeneralSeeker;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.IOException;

public class PagesSeeker {

    private PDDocument pdfdocument;
    private GeneralSeeker generalSeeker;

    public PagesSeeker(PDDocument pdfdocument){
        this.pdfdocument = pdfdocument;
        this.generalSeeker = new GeneralSeeker(pdfdocument);
    }

    public boolean isTheCoverInThisPage(int page) throws IOException {
        boolean bool1,bool2, bool3, bool4, bool5, bool6,bool7, bool8, bool9, bool10,bool11,bool12, bool13, bool14, bool15;
        bool1 = generalSeeker.isTheWordInThePage(page,"Boliviana");
        bool2 = generalSeeker.isTheWordInThePage(page,"boliviana");
        bool3 = generalSeeker.isTheWordInThePage(page,"BOLIVIANA");
        bool4 = generalSeeker.isTheWordInThePage(page,"Regional");
        bool5 = generalSeeker.isTheWordInThePage(page,"regional");
        bool6 = generalSeeker.isTheWordInThePage(page,"REGIONAL");
        bool7 = generalSeeker.isTheWordInThePage(page,"Departamento");
        bool8 = generalSeeker.isTheWordInThePage(page,"departamento");
        bool9 = generalSeeker.isTheWordInThePage(page,"DEPARTAMENTO");
        bool10 = generalSeeker.isTheWordInThePage(page,"Carrera");
        bool11 = generalSeeker.isTheWordInThePage(page,"carrera");
        bool12 = generalSeeker.isTheWordInThePage(page,"CARRERA");
        bool13 = generalSeeker.isTheWordInThePage(page,"– Bolivia");
        bool14 = generalSeeker.isTheWordInThePage(page,"– bolivia");
        bool15 = generalSeeker.isTheWordInThePage(page,"– BOLIVIA");
        return getNumberOfTrues(bool1,bool2, bool3, bool4, bool5, bool6,bool7, bool8, bool9, bool10,bool11,bool12, bool13, bool14, bool15) >= 3;
    }

    public int getCoverPage() throws IOException {
        int resp = 0;
        int page=1;
        if( page <= pdfdocument.getNumberOfPages()) {
            if ( isTheCoverInThisPage(page) ){
                return page;
            }
        }
        return resp;
    }


    public boolean isTheGeneralIndexInThisPage(int page) throws IOException {
        return generalSeeker.isTheWordInThePage(page,".....");
    }

    public int getFirstGeneralIndexPage() throws IOException {
        int resp = 0;
        for(int page=1;page<=pdfdocument.getNumberOfPages();page++){
            if ( isTheGeneralIndexInThisPage(page) ){
                return page;
            }
        }
        return resp;
    }


    public boolean isTheFigureTableIndexInThisPage(int page) throws IOException {
        boolean bool1,bool2,bool3,bool4;
        bool1 = generalSeeker.isTheWordInThePage(page,"Figura");
        bool2 = generalSeeker.isTheWordInThePage(page,"FIGURA");
        bool3 = generalSeeker.isTheWordInThePage(page,"TABLA");
        bool4 = generalSeeker.isTheWordInThePage(page,"Tabla");
        return getNumberOfTrues(bool1,bool2,bool3,bool4)>=1;
    }

    public boolean isTheFigureIndexInThisPage(int page) throws IOException {
        boolean bool1,bool2,bool3,bool4;
        bool1 = generalSeeker.isTheWordInThePage(page,"Figura");
        bool2 = generalSeeker.isTheWordInThePage(page,"FIGURA");
        return getNumberOfTrues(bool1,bool2)>=1;
    }

    public boolean isTheTableIndexInThisPage(int page) throws IOException {
        boolean bool3,bool4;
        bool3 = generalSeeker.isTheWordInThePage(page,"TABLA");
        bool4 = generalSeeker.isTheWordInThePage(page,"Tabla");
        return getNumberOfTrues(bool3,bool4)>=1;
    }

    public int getLastGeneralIndexPage(int generalIndexPageStart,int lastIndexPage) throws IOException {
        int resp = 0;
        if(generalIndexPageStart == 0){
            return resp;
        }
        for (int page = generalIndexPageStart; page <= lastIndexPage; page++) {
            if ( isTheGeneralIndexInThisPage(page) && !isTheFigureTableIndexInThisPage(page)){
                resp = page;
            }else{
                return resp;
            }
        }
        return resp;
    }

    public int getFirstFigureIndexPage(int generalIndexPageEnd,int lastIndexPage) throws IOException {
        int resp = 0;
        if (generalIndexPageEnd+1 <= pdfdocument.getNumberOfPages()){
            for (int page = generalIndexPageEnd+1; page <= lastIndexPage; page++) {
                if ( isTheFigureIndexInThisPage(page) ){
                    return page;
                }
            }
        }
        return resp;
    }

    public int getLastFigureIndexPage(int figureIndexPageStart,int lastIndexPage) throws IOException {
        int resp = 0;
        if(figureIndexPageStart == 0){
            return resp;
        }
        for (int page = figureIndexPageStart; page <= lastIndexPage; page++) {
            if ( isTheFigureIndexInThisPage(page) ){
                resp = page;
            }
        }
        return resp;
    }

    public int getFirstTableIndexPage(int generalIndexPageEnd,int lastIndexPage) throws IOException {
        int resp = 0;
        if (generalIndexPageEnd+1 <= pdfdocument.getNumberOfPages()){
            for (int page = generalIndexPageEnd+1; page <= lastIndexPage; page++) {
                if ( isTheTableIndexInThisPage(page) ){
                    return page;
                }
            }
        }
        return resp;
    }

    public int getLastTableIndexPage(int tableIndexPageStart,int lastIndexPage) throws IOException {
        int resp = 0;
        if(tableIndexPageStart == 0){
            return resp;
        }
        for (int page = tableIndexPageStart; page <= lastIndexPage; page++) {
            if ( isTheTableIndexInThisPage(page) ){
                resp = page;
            }
        }
        return resp;
    }


    public int getIndexPage(int generalIndexPageStart) throws IOException {
        int resp = 0;
        if(generalIndexPageStart == 0){
            return resp;
        }
        for (int page = generalIndexPageStart; page <= pdfdocument.getNumberOfPages(); page++) {
            if ( isTheGeneralIndexInThisPage(page) ){
                resp = page;
            }else{
                return resp;
            }
        }
        return resp;
    }

    public boolean isTheFirsBiographyInThisPage(int page) throws IOException {
        boolean bool1,bool2,bool3,bool4;
        bool1 = generalSeeker.isTheWordInThePageAdvanced(page,"BIBLIOGRAFÍA");
        bool2 = generalSeeker.isTheWordInThePageAdvanced(page,"Bibliografía");
        return getNumberOfTrues(bool1,bool2) >= 1;
    }

    public int getFirstBiographyPage() throws IOException {
        int resp = pdfdocument.getNumberOfPages()+1;
        for (int page = pdfdocument.getNumberOfPages(); page >= 1; page--) {
            if ( isTheFirsBiographyInThisPage(page) ){
                return page;
            }
        }
        return resp;
    }

    public int getLastBiographyPage(int biographyPageStart, int annexedPageStart){
        int resp = pdfdocument.getNumberOfPages()+1;
        if (biographyPageStart == pdfdocument.getNumberOfPages()+1){
            return resp;
        }
        return annexedPageStart-1;
    }


    public boolean isTheFirstAnnexInThisPage(int page) throws IOException {
        boolean bool1,bool2,bool3,bool4,bool5,bool6,bool7,bool8,bool9,bool10,bool11,bool12;
        bool1 = generalSeeker.isTheWordInThePage(page,"Anexo 1 ");
        bool2 = generalSeeker.isTheWordInThePage(page,"anexo 1 ");
        bool3 = generalSeeker.isTheWordInThePage(page,"ANEXO 1 ");
        bool4 = generalSeeker.isTheWordInThePage(page,"Anexo 1:");
        bool5 = generalSeeker.isTheWordInThePage(page,"anexo 1:");
        bool6 = generalSeeker.isTheWordInThePage(page,"ANEXO 1:");
        bool7 = generalSeeker.isTheWordInThePage(page,"Anexo 1,");
        bool8 = generalSeeker.isTheWordInThePage(page,"anexo 1,");
        bool9 = generalSeeker.isTheWordInThePage(page,"ANEXO 1,");
        bool10 = generalSeeker.isTheWordInThePage(page,"Anexo 1.");
        bool11 = generalSeeker.isTheWordInThePage(page,"anexo 1.");
        bool12 = generalSeeker.isTheWordInThePage(page,"ANEXO 1.");
        return getNumberOfTrues(bool1,bool2,bool3,bool4,bool5,bool6,bool7,bool8,bool9,bool10,bool11,bool12) >= 1;
    }


    public int getFirstAnnexedPage() throws IOException {
        int resp = pdfdocument.getNumberOfPages()+1;
        for (int page = pdfdocument.getNumberOfPages(); page >= 1; page--) {
            if ( isTheFirstAnnexInThisPage(page) ){
                return page;
            }
        }
        return resp;
    }

    public int getLastAnnexedPage(int annexedPageStart){
        int resp = pdfdocument.getNumberOfPages()+1;
        if (annexedPageStart == resp){
            return resp;
        }
        return resp-1;
    }



    public int getNumberOfTrues(boolean... vars) {
        int count = 0;
        for (boolean var : vars) {
            count += (var ? 1 : 0);
        }
        return count;
    }
}
