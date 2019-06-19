package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.others.images_pdf;

import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GetterPdfImages {

    public List<PdfImage> getPdfImages(int pageNum, float pageHeight, PDPage page) throws IOException {
        final List<PdfImage> pdfImages = new ArrayList<>();
        ImageLocations imageLocations = new ImageLocations(){
            @Override
            protected void processOperator(Operator operator, List<COSBase> operands) throws IOException
            {
                String operation = operator.getName();
                if( "Do".equals(operation) )
                {
                    COSName objectName = (COSName) operands.get( 0 );
                    PDXObject xobject = getResources().getXObject( objectName );
                    if( xobject instanceof PDImageXObject)
                    {
                        Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
                        pdfImages.add(new PdfImage(ctmNew,pageNum,pageHeight));
                    }
                    else if(xobject instanceof PDFormXObject)
                    {
                        PDFormXObject form = (PDFormXObject)xobject;
                        showForm(form);
                    }
                }
                else
                {
                    super.processOperator( operator, operands);
                }
            }
        };
        imageLocations.processPage(page);
        List<PdfImage> pdfFigures = new ArrayList<>();
        for (PdfImage image : pdfImages) {
            if (image.getWidthDisplayed() > 100 && image.getHeightDisplayed() > 100) {
                pdfFigures.add(image);
            }
        }
        Collections.sort(pdfFigures, (pdfImage1, pdfImage2) -> (int) (pdfImage1.getY() - pdfImage2.getY()));
        return pdfFigures;
    }
}
