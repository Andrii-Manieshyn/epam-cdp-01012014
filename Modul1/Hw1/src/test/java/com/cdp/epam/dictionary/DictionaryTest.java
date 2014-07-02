package com.cdp.epam.dictionary;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;

/**
 * Created by Andrii_Manieshyn on 07.02.14.
 */
public class DictionaryTest {

    public File warAndPeaceFile = new File("WarAndPeace.txt");
    public static final ApplicationContext context =
            new ClassPathXmlApplicationContext("spring-beans.xml");

    @Test
    public void whenHashMap_thanReturnTimeOfDictionaryInitialization() throws FileNotFoundException {
        Dictionary dictionary = (Dictionary)context.getBean("dictionaryHashSet");
        System.out.println("HashMap initialization time : " + dictionary.initializeDictionary());
    }

    @Test
    public void whenLinkedHashMap_thanReturnTimeOfDictionaryInitialization() throws FileNotFoundException {
        Dictionary dictionary = (Dictionary)context.getBean("dictionaryLinkedHashSet");
        System.out.println("LinkedHashMap initialization time : " +  dictionary.initializeDictionary());
    }

    @Test
    public void whenTreeMap_thanReturnTimeOfDictionaryInitialization() throws FileNotFoundException {
        Dictionary dictionary = (Dictionary)context.getBean("dictionaryTreeSet");
        System.out.println("TreeMap initialization time : " +  dictionary.initializeDictionary());
    }

    @Test
    public void whenHashMap_thanReturnTimeGettingWordFromDictionary() throws FileNotFoundException {
        Dictionary dictionary = (Dictionary)context.getBean("dictionaryHashSet");
        dictionary.initializeDictionary();
        Long startTime = System.currentTimeMillis();
        dictionary.getWordOccurrence("look");
        Long finishTime = System.currentTimeMillis();
        System.out.println("HashMap number of occurrence of the word 'look' time : " +  (finishTime - startTime));

        startTime = System.currentTimeMillis();
        dictionary.getWordOccurrence("the");
        finishTime = System.currentTimeMillis();
        System.out.println("HashMap number of occurrence of the word 'look' time : " +  (finishTime - startTime));
    }

    @Test
    public void whenLinkedHashMap_thanReturnTimeGettingWordFromDictionary() throws FileNotFoundException {
        Dictionary dictionary = (Dictionary)context.getBean("dictionaryLinkedHashSet");
        dictionary.initializeDictionary();
        Long startTime = System.currentTimeMillis();
        dictionary.getWordOccurrence("look");
        Long finishTime = System.currentTimeMillis();
        System.out.println("LinkedHashMap number of occurrence of the word 'look' time : " +  (finishTime - startTime));

        startTime = System.currentTimeMillis();
        dictionary.getWordOccurrence("the");
        finishTime = System.currentTimeMillis();
        System.out.println("LinkedHashMap number of occurrence of the word 'the' time : " +  (finishTime - startTime));
    }

    @Test
    public void whenTreeMap_thanReturnTimeGettingWordFromDictionary() throws FileNotFoundException {
        Dictionary dictionary = (Dictionary)context.getBean("dictionaryTreeSet");
        dictionary.initializeDictionary();
        Long startTime = System.currentTimeMillis();
        dictionary.getWordOccurrence("look");
        Long finishTime = System.currentTimeMillis();
        System.out.println("TreeMap number of occurrence of the word 'look' time : " +  (finishTime - startTime));

        startTime = System.currentTimeMillis();
        dictionary.getWordOccurrence("the");
        finishTime = System.currentTimeMillis();
        System.out.println("TreeMap number of occurrence of the word 'the' time : " +  (finishTime - startTime));
    }

    @Test
    public void whenHashMap_thanReturnTimeGettingWordWithUniqueOccurrenceFromDictionary() throws FileNotFoundException {
        Dictionary dictionary = (Dictionary)context.getBean("dictionaryHashSet");
        dictionary.initializeDictionary();
        Long startTime = System.currentTimeMillis();
        dictionary.getWordsWithUniqueOccurrenceInText();
        Long finishTime = System.currentTimeMillis();
        System.out.println("HashMap time of getting words with unique occurrence: " +  (finishTime - startTime));

    }

    @Test
    public void whenLinkedHashMap_thanReturnTimeGettingWordWithUniqueOccurrenceFromDictionary() throws FileNotFoundException {
        Dictionary dictionary = (Dictionary)context.getBean("dictionaryLinkedHashSet");
        dictionary.initializeDictionary();
        Long startTime = System.currentTimeMillis();
        dictionary.getWordsWithUniqueOccurrenceInText();
        Long finishTime = System.currentTimeMillis();
        System.out.println("LinkedHashMap time of getting words with unique occurrence: " +  (finishTime - startTime));
    }

    @Test
    public void whenTreeMap_thanReturnTimeGetingWordWithUniqueOccurrenceFromDictionary() throws FileNotFoundException {
        Dictionary dictionary = (Dictionary)context.getBean("dictionaryTreeSet");
        dictionary.initializeDictionary();
        Long startTime = System.currentTimeMillis();
        dictionary.getWordsWithUniqueOccurrenceInText();
        Long finishTime = System.currentTimeMillis();
        System.out.println("TreeMap time of getting words with unique occurrence: " +  (finishTime - startTime));

    }




}
