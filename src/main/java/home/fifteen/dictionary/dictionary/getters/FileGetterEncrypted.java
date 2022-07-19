package home.fifteen.dictionary.dictionary.getters;

import home.fifteen.dictionary.dictionary.Dictionary;
import home.fifteen.dictionary.utils.DecoderBase64;
import home.fifteen.dictionary.utils.Settings;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;

public class FileGetterEncrypted implements DictionaryGetter {

    private transient final String DICTIONARY_DIRECTORY =
            Settings.DICTIONARY_DIRECTORY.getProperty();

    private final String FILE_ID  ;
    private transient final Dictionary dictionary;
    private transient final DecoderBase64 decoderBase64;
//    private final File file;

    public FileGetterEncrypted() {
        dictionary = new Dictionary();
//        FILE_ID = "DictionarySource/encoded_clothes.properties";
        FILE_ID = DICTIONARY_DIRECTORY + "/encoded_0104.properties";
//        file = new File(FILE_ID);
        decoderBase64  = new DecoderBase64(this.FILE_ID);

    }

    public FileGetterEncrypted(String FILE_ID) {
        this.FILE_ID = DICTIONARY_DIRECTORY + "/" + FILE_ID;
        dictionary = new Dictionary();
        decoderBase64  = new DecoderBase64(this.FILE_ID);
//        file = new File(FILE_ID);
    }

    @Override
    public void init() {

        decoderBase64.init();
        decoderBase64.decode();

        String decodedString = decoderBase64.getDecodedString();

        setDictionaryName();

        try {
            InputStream is = new ByteArrayInputStream(decodedString.getBytes(StandardCharsets.UTF_8));
            PropertyResourceBundle prb = new PropertyResourceBundle(is);

            for (String key : prb.keySet()) {
                dictionary.addWord( parseKey(key) , prb.getString(key));
            }

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
        dictionary.setName(decoderBase64.getName());
        log.info("Setting name for Dictionary " + dictionary.getName());
        log.info("Setting dictionary name fo GUI usage " + dictionary.getNameForList());
    }

    @Override
    public Long getLastModified() {
        return decoderBase64.getModifiedTime();
    }

    @Override
    public String getMD5Sum() {
        return decoderBase64.getCheckSum();
    }

    @Override
    public boolean isDownloadable() {
        return false;
    }

    @Override
    public void setDownloadable(boolean downloadable) {

    }


    private String fileToString() {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_ID), StandardCharsets.UTF_8));
            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                result.append("\n");
                line = reader.readLine();
            }
            return result.toString();
        } catch (IOException e) { e.printStackTrace();}
        return "missed";
    }

}
