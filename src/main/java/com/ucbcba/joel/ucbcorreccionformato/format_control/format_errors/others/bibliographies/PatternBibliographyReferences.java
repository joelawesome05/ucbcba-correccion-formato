package com.ucbcba.joel.ucbcorreccionformato.format_control.format_errors.others.bibliographies;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternBibliographyReferences {
    private String name;
    private Pattern pattern;
    private String patternString;

    public PatternBibliographyReferences(String name, Pattern pattern,String patternString) {
        this.name = name;
        this.pattern = pattern;
        this.patternString = patternString;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Pattern getPattern() {
        return pattern;
    }

    public void setPattern(Pattern pattern) {
        this.pattern = pattern;
    }

    public Matcher getMatcher(String lineWord){
        return pattern.matcher(lineWord);
    }

    public String getPatternString() {
        return patternString;
    }

    public void setPatternString(String patternString) {
        this.patternString = patternString;
    }
}
