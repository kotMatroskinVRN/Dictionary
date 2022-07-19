package home.fifteen.dictionary;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.PropertyResourceBundle;

public class RestApi {

    private final String URL_NAME = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=";
    private final String FILE_ID  = "1lsE_2hRwNH1NGS2dpcC1yATlAyILNN21";

    public static void main(String[] args) {

        RestApi restApi = new RestApi();

        try {
            URL url = new URL(restApi.URL_NAME + restApi.FILE_ID);

            InputStreamReader is = new InputStreamReader(url.openStream() , StandardCharsets.UTF_8) ;
            PropertyResourceBundle prb = new PropertyResourceBundle(is  );

            for (String key : prb.keySet()) {
                System.out.printf("%-20s%s\n", key, prb.getString(key));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
