package home.fifteen.dictionary.dictionary;


import home.fifteen.dictionary.Main;

import java.io.File;
import java.util.logging.Logger;

public interface DictionaryGetter {

    Logger log = Main.getLog();


    void init();
    Dictionary getDictionary();
    void setDictionaryName();
    Long getLastModified();

    default String parseKey(String key){
        return key.replaceAll("\\." , " ");
    }


}
