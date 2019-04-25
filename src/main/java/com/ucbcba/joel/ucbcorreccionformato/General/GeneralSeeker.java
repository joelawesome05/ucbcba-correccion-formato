package com.ucbcba.joel.ucbcorreccionformato.General;

import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.FigureFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.Format;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.FormatControl.TableFormat;
import com.ucbcba.joel.ucbcorreccionformato.FormatErrors.ImagesOnPdf.PdfImage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.Normalizer;

public class GeneralSeeker {
    private PDDocument pdfdocument;

    public GeneralSeeker(PDDocument pdfdocument){
        this.pdfdocument = pdfdocument;
    }

    public boolean isTheWordInThePageAdvanced(int page, String searchWord) throws IOException {
        boolean resp = isTheWordInThePage(page,searchWord);
        if (!resp){
            String newSearchWord = Normalizer.normalize(searchWord, Normalizer.Form.NFD);
            newSearchWord = newSearchWord.replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
            resp = isTheWordInThePage(page,newSearchWord);
        }
        return resp;
    }

    public boolean isTheWordInThePage(int page, String searchWord) throws IOException {
        final boolean[] resp = {false};
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                WordsProperties word = new WordsProperties(textPositions);
                String string = word.toString();
                int index = 0;
                int indexWordFound;
                while (((indexWordFound = string.indexOf(searchWord, index)) > -1) && !resp[0]) {
                    resp[0] = true;
                    index = indexWordFound + 1;
                }
                super.writeString(text, textPositions);
            }
        };
        stripper.setSortByPosition(true);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(pdfdocument);
        return resp[0];
    }

    public List<WordsProperties> findWordsFromAPage(int page, String searchWord) throws IOException {
        final List<WordsProperties> listWordPositionSequences = new ArrayList<WordsProperties>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                WordsProperties word = new WordsProperties(textPositions);
                String string = word.toString();
                int index = 0;
                int indexWordFound;
                while ((indexWordFound = string.indexOf(searchWord, index)) > -1) {
                    listWordPositionSequences.add(word.subSequence(indexWordFound, indexWordFound + searchWord.length()));
                    index = indexWordFound + 1;
                }
                super.writeString(text, textPositions);
            }
        };
        stripper.setSortByPosition(true);
        stripper.setStartPage(page);
        stripper.setEndPage(page);
        stripper.getText(pdfdocument);
        return listWordPositionSequences;
    }


    public boolean hasTittleTheFigure(PdfImage image, int pageNum) throws IOException {
        boolean resp = false;
        GetterWordLines getterWordLines = new GetterWordLines(pdfdocument);
        List<WordsProperties> wordsLines = getterWordLines.getWordLines(pageNum);

        for(WordsProperties wordLine:wordsLines){
            String arr[] = wordLine.toString().split(" ", 2);
            String firstWordLine = arr[0];
            if (firstWordLine.contains("Figura")){
                if ((image.getEndY() > wordLine.getY()) && (image.getEndY() - image.getHeightDisplayed() - 200 < wordLine.getY())) {
                    return true;
                }
            }
        }
        return resp;
    }


    public WordsProperties findFigureSource(PdfImage image, int pageNum) throws IOException {
        PDFTextStripper pdfStripper = new PDFTextStripper();
        pdfStripper.setStartPage(pageNum);
        pdfStripper.setEndPage(pageNum);
        pdfStripper.setParagraphStart("\n");
        pdfStripper.setSortByPosition(true);

        for (String line : pdfStripper.getText(pdfdocument).split(pdfStripper.getParagraphStart())) {
            String arr[] = line.split(" ", 2);
            if (!arr[0].equals("")) {
                String wordLine = line.trim();
                if (arr[0].contains("Fuente")) {
                    List<WordsProperties> words = findWordsFromAPage(pageNum, wordLine);
                    for (WordsProperties word : words) {
                        if ((image.getEndY() < word.getY()) && (image.getEndY() + 100 > word.getY())) {
                            return word;
                        }
                    }
                }
            }
        }
        return null;
    }

    public List<WordsProperties> findWordsFromPages(int pageStart, int pageEnd, String searchWord) throws IOException {
        final List<WordsProperties> listWordPositionSequences = new ArrayList<WordsProperties>();
        PDFTextStripper stripper = new PDFTextStripper() {
            @Override
            protected void writeString(String text, List<TextPosition> textPositions) throws IOException {
                WordsProperties word = new WordsProperties(textPositions);
                String string = word.toString();
                int index = 0;
                int indexWordFound;
                while ((indexWordFound = string.indexOf(searchWord, index)) > -1) {
                    listWordPositionSequences.add(word.subSequence(indexWordFound, indexWordFound + searchWord.length()));
                    index = indexWordFound + 1;
                }
                super.writeString(text, textPositions);
            }
        };
        stripper.setSortByPosition(true);
        stripper.setStartPage(pageStart);
        stripper.setEndPage(pageEnd);
        stripper.getText(pdfdocument);
        return listWordPositionSequences;
    }
}
