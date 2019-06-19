package com.ucbcba.joel.ucbcorreccionformato.format_control.general_format.page_format_rules.bibliography;


public class BibliographyFactory {
    public Bibliography getBibliography(String bibliographyType){
        if(bibliographyType == null){
            return null;
        }
        if(bibliographyType.equalsIgnoreCase("UCB")){
            return new UCBBibliography();

        } else if(bibliographyType.equalsIgnoreCase("APA")){
            return new APABibliography();
        } else if(bibliographyType.equalsIgnoreCase("IEEE")){
            return new IEEEBibliography();
        }
        return null;
    }
}
