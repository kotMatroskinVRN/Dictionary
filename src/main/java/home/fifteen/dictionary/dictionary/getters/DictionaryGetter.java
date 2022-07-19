package home.fifteen.dictionary.dictionary.getters;


import home.fifteen.dictionary.Main;
import home.fifteen.dictionary.dictionary.Dictionary;

import java.io.Serializable;
import java.util.logging.Logger;

public interface DictionaryGetter extends Serializable {

    Logger log = Main.getLogger();


    void init();
    String getID();
    Dictionary getDictionary();
    void setDictionaryName();
    Long getLastModified();
    String getMD5Sum();
    boolean isDownloadable();
    void setDownloadable(boolean downloadable);

    default String parseKey(String key){
        return key.replaceAll("\\." , " ");
    }


}
