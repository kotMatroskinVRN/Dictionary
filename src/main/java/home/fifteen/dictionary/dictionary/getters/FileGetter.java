package home.fifteen.dictionary.dictionary.getters;


import home.fifteen.dictionary.dictionary.Dictionary;
import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;

import java.io.*;
import java.util.PropertyResourceBundle;

public class FileGetter implements DictionaryGetter {


    private final String FILE_ID  ;
    private final Dictionary dictionary;

    private final File file;

    public FileGetter() {
        dictionary = new Dictionary();
        FILE_ID = "DictionarySource/2018.properties";
        file = new File(FILE_ID);
    }

    public FileGetter(String FILE_ID) {
        this.FILE_ID = FILE_ID;
        dictionary = new Dictionary();
        file = new File(FILE_ID);
    }

    @Override
    public void init() {
        try {
            InputStream is = new FileInputStream(FILE_ID);
            PropertyResourceBundle prb = new PropertyResourceBundle(is);

            for (String key : prb.keySet()) {
                dictionary.addWord( parseKey(key) , prb.getString(key));
            }



            setDictionaryName();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Dictionary getDictionary() {
        return dictionary;
    }

    @Override
    public void setDictionaryName() {
        dictionary.setName(FILE_ID);
    }

    @Override
    public Long getLastModified() {
        return file.lastModified();
    }

    @Override
    public String getMD5Sum() {
        return null;
    }


}
