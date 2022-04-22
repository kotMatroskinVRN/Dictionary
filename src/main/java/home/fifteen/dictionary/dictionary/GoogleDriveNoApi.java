package home.fifteen.dictionary.dictionary;


import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GoogleDriveNoApi implements DictionaryGetter {

    private final String FILE_ID  ;

    private final Dictionary dictionary;
    private URL url;


    public GoogleDriveNoApi() {
        dictionary = new Dictionary();
        FILE_ID  = "1E36Ch6rdHsEeg4J7d0--X2qiy1tinIcY";
//        FILE_ID  = "1hhLpOfCXx2gNE6sbYMubRo80wtusZrvz";
    }

    public GoogleDriveNoApi(String FILE_ID) {
        dictionary = new Dictionary();
        this.FILE_ID = FILE_ID;
    }



    @Override
    public void init() {

        try {
            String URL_NAME = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=";
            url = new URL(URL_NAME + FILE_ID);

        InputStreamReader is = new InputStreamReader(url.openStream() , StandardCharsets.UTF_8) ;

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
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public void setDictionaryName(){

        try {
            URLConnection connection = url.openConnection();
            connection.connect();
            String requestResult = connection.getHeaderField("Content-Disposition");

            Pattern pattern = Pattern.compile("filename=\"(.*)\"");
            Matcher matcher = pattern.matcher(requestResult);
            if(matcher.find()) {
                log.info("Google Drive File Name has been found ");
                String fileName = matcher.group(1);
                System.out.println(fileName);
                dictionary.setName(fileName);
            }


        } catch (IOException e) {
            e.printStackTrace();
            dictionary.setName("Not Found");
        }


    }


}
