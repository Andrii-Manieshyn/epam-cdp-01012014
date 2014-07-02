package com.cdp.epam.dictionary.impl;
import com.cdp.epam.dictionary.Dictionary;
import com.google.common.collect.Multiset;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Created by Andrii_Manieshyn on 20.02.14.
 */
public class DictionaryImpl implements Dictionary {

    private String fileName;
    private Multiset<String> occurrenceOfWord;

    private static final Pattern separateWordPattern = Pattern.compile("[\\w']+");

    public DictionaryImpl(Multiset map) {
        occurrenceOfWord = map;
    }

    public Long initializeDictionary() throws FileNotFoundException {
        long result = 0;
        Scanner scanner = new Scanner(new BufferedInputStream(com.cdp.epam.dictionary.Dictionary.class.getClassLoader()
                .getResourceAsStream(fileName)));
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Matcher matcher = separateWordPattern.matcher(line);
            while (matcher.find()) {
                String separateWord = line.substring(matcher.start(), matcher.end());
                Long start = System.nanoTime();
                occurrenceOfWord.add(separateWord);
                Long finish = System.nanoTime();
                result += finish - start;
            }
        }
        scanner.close();
        return result;
    }

    public Integer getWordOccurrence(String word) {
        return occurrenceOfWord.count(word);
    }

    public Integer getNumberOfUniqueWords() {
        return occurrenceOfWord.elementSet().size();
    }

    public List<String> getWordsWithUniqueOccurrenceInText() {
        List<String> uniqueWords = new ArrayList<String>();
        for (String entry : occurrenceOfWord.elementSet()) {
            if (occurrenceOfWord.count(entry) == 1) {
                uniqueWords.add(entry);
            }
        }
        return uniqueWords;
    }

    public void setFileName(String fileName){
        this.fileName = fileName;
    }
}
