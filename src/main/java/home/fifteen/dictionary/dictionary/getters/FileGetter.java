package home.fifteen.dictionary.dictionary.getters;


import home.fifteen.dictionary.dictionary.Dictionary;
import home.fifteen.dictionary.utils.CheckSum;
import home.fifteen.dictionary.utils.Settings;

import java.io.*;
import java.util.PropertyResourceBundle;

public class FileGetter implements DictionaryGetter {

    private transient final String DICTIONARY_DIRECTORY =
            Settings.DICTIONARY_DIRECTORY.getProperty();

    private final String FILE_ID  ;
    private transient final Dictionary dictionary;

    private transient final File file;
    private transient String checkSum;

    public FileGetter() {
        dictionary = new Dictionary();
        FILE_ID = "test.properties";
        file = new File(DICTIONARY_DIRECTORY + "/" + FILE_ID);
    }

    public FileGetter(String FILE_ID) {
        this.FILE_ID = FILE_ID;
        dictionary = new Dictionary();
        file = new File(DICTIONARY_DIRECTORY + "/" +FILE_ID);
    }

    @Override
    public void init() {

        CheckSum sum = new CheckSum(file);
        sum.generate();
        checkSum = sum.getCheckSum();

        try {
            InputStream is = new FileInputStream(file);
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
    public String getID() {
        return FILE_ID;
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
        return checkSum;
    }

    @Override
    public boolean isDownloadable() {
        return false;
    }

    @Override
    public void setDownloadable(boolean downloadable) {

    }


}
