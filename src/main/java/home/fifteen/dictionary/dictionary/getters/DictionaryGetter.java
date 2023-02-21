package home.fifteen.dictionary.dictionary.getters;


import home.fifteen.dictionary.dictionary.Dictionary;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.io.Serializable;

public interface DictionaryGetter extends Serializable {

    ColorfulLogger log = ColorfulLogger.getLogger();


    void init();
    String getID();
    Dictionary getDictionary();
    void setDictionaryName();
    Long getLastModified();
    String getMD5Sum();
    boolean isDownloadable();
    void setDownloadable(boolean downloadable);


}
