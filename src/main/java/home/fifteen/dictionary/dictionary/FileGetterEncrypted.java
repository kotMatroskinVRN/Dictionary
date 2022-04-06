package home.fifteen.dictionary.dictionary;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;
import java.util.PropertyResourceBundle;

public class FileGetterEncrypted implements DictionaryGetter {


    private final String FILE_ID  ;
    private final Dictionary dictionary;

    public FileGetterEncrypted() {
        dictionary = new Dictionary();
        FILE_ID = "encoded_clothes.properties";
    }

    public FileGetterEncrypted(String FILE_ID) {
        this.FILE_ID = FILE_ID;
        dictionary = new Dictionary();
    }

    @Override
    public void init() {

        String string = fileToString().replace("\n" , "");
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(string);
        String decodedString = new String(decodedBytes);


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
    public Dictionary getDictionary() {
        return dictionary;
    }

    @Override
    public void setDictionaryName() {
        dictionary.setName(FILE_ID);
    }



    private String fileToString() {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(FILE_ID), "UTF-8"));
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
