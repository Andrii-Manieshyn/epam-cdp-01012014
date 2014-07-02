package com.cdp.epam.dictionary;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Andrii_Manieshyn on 07.02.14.
 */
public interface Dictionary {

    public Long initializeDictionary() throws FileNotFoundException;

    public Integer getWordOccurrence(String word);

    public Integer getNumberOfUniqueWords();

    public List<String> getWordsWithUniqueOccurrenceInText();

}
