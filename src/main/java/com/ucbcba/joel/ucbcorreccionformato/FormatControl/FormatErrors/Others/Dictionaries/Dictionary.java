package com.ucbcba.joel.ucbcorreccionformato.FormatControl.FormatErrors.Others.Dictionaries;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.Set;
import java.util.Collections;


public class Dictionary
{
    private Set<String> wordsSet;

    public Dictionary() throws IOException
    {
        Path path = Paths.get("words.txt");
        byte[] readBytes = Files.readAllBytes(path);
        String wordListContents = new String(readBytes, "UTF-8");
        String[] words = wordListContents.split("\n");
        wordsSet = new HashSet<>();
        Collections.addAll(wordsSet, words);
    }

    public boolean contains(String word)
    {
        return wordsSet.contains(word);
    }
}
