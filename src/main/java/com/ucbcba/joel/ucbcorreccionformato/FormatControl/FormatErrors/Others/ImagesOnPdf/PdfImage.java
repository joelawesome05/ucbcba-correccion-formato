package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.Others.ImagesOnPdf;

import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

public class PdfImage {
    private PDImageXObject pdImageXObject;
    private Matrix matrix;
    private Integer page;
    private float x;
    private float y;
    private float endX;
    private float endY;

    public PdfImage(PDImageXObject pdImageXObject,Matrix matrix,Integer page,float pageHeight){
        this.pdImageXObject = pdImageXObject;
        this.matrix = matrix;
        this.page = page;
        this.x = matrix.getTranslateX();
        this.y = pageHeight - (matrix.getTranslateY() + matrix.getScalingFactorY());
        this.endX = this.x +  matrix.getScalingFactorX();
        this.endY =  pageHeight - matrix.getTranslateY();
    }

    public int getPage(){
        return page;
    }

    public float getX(){
        return x;
    }

    public float getY(){
        return y;
    }

    public float getEndX(){
        return endX;
    }

    public float getEndY(){
        return endY;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public float getWidthDisplayed(){
        return matrix.getScalingFactorX();
    }

    public float getHeightDisplayed(){
        return matrix.getScalingFactorY();
    }

    public boolean isFigureHorizontal(){
        return matrix.getShearX() == 0;
    }

    public boolean doesFigureRotateToTheRight(){
        return matrix.getShearY() < 0;
    }

    public float getShearX(){
        return matrix.getShearX();
    }

    public float getTranslateY(){
        return matrix.getTranslateY();
    }
}
