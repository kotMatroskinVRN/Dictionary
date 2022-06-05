package home.fifteen.dictionary.dictionary.getters;


import home.fifteen.dictionary.Main;
import home.fifteen.dictionary.dictionary.Dictionary;

import java.io.File;
import java.util.logging.Logger;

public interface DictionaryGetter {

    Logger log = Main.getLog();


    void init();
    Dictionary getDictionary();
    void setDictionaryName();
    Long getLastModified();
    String getMD5Sum();

    default String parseKey(String key){
        return key.replaceAll("\\." , " ");
    }


}
