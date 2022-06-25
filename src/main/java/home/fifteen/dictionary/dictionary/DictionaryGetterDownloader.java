package home.fifteen.dictionary.dictionary;

import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.dictionary.getters.GoogleDriveDefault;

public class DictionaryGetterDownloader {

    private final String SAVE_DIRECTORY = "./DictionarySource";

    private final DictionaryGetter getter;


    DictionaryGetterDownloader(){
        getter = new GoogleDriveDefault();
    }

    public DictionaryGetterDownloader(DictionaryGetter getter) {
        this.getter = getter;
    }

}
