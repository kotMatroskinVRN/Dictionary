package home.fifteen.Dictionary;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.PropertyResourceBundle;

public class GoogleDriveDefault implements DictionaryGetter{

    private final String URL_NAME = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=";
    private final String FILE_ID  = "1hhLpOfCXx2gNE6sbYMubRo80wtusZrvz";

    private final Map<String,String> dictionary = new HashMap<>();

    @Override
    public void init() {

        try {
        URL url = new URL(URL_NAME + FILE_ID);

        InputStream is = url.openStream();

        while (is==null){
            Thread.sleep(50);
            System.out.println("sleeping...");
        }

        PropertyResourceBundle prb = new PropertyResourceBundle(is);

            for (String key : prb.keySet()) {
                dictionary.put( parseKey(key) , prb.getString(key));
            }


        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String,String> getDictionary() {
        return dictionary;
    }

    private String parseKey(String key){
        return key.replaceAll("\\." , " ");
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        String format = "%-40s%s\n";

        for (String key : dictionary.keySet()) {
            String string  = String.format(format , key , dictionary.get(key));
            result.append(string);
        }

        return result.toString();
    }
}
