package com.cdp.epam;

import com.cdp.epam.dictionary.Dictionary;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author Andrii_Manieshyn
 */
public class Main {

    public static final ApplicationContext context =
            new ClassPathXmlApplicationContext("spring-beans.xml");

    public static void main(String[] args) throws FileNotFoundException {

        Dictionary dictionary = (Dictionary) context.getBean("dictionaryHashSet");
        dictionary.initializeDictionary();
        System.out.println("Number of unique words is: " + dictionary.getNumberOfUniqueWords());
        System.out.println("Enter the word, which occurrence you want to know:");
        Scanner systemScanner = new Scanner(System.in);
        String word = systemScanner.nextLine();
        while (!word.equals("$exit")) {
            Integer occurence = dictionary.getWordOccurrence(word);
            System.out.println("This word occur " + occurence + " times!");
            System.out.println("Enter another word please:");
            word = systemScanner.nextLine();
        }

    }
}
