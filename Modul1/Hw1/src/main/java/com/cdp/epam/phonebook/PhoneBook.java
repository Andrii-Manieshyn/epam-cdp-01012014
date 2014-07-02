package com.cdp.epam.phonebook;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by Andrii_Manieshyn on 07.02.14.
 */
public class PhoneBook {

    private ConcurrentMap<String, String> phoneBook;

    public PhoneBook(){
        phoneBook = new ConcurrentHashMap<String, String>();
    }

    public String put(String key , String value){
        return phoneBook.put(key, value);
    }

    public String get(String key){
        return phoneBook.get(key);
    }

}
