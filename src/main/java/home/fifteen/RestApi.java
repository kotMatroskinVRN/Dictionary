package home.fifteen;

import java.io.IOException;
import java.net.URL;
import java.util.PropertyResourceBundle;

public class RestApi {

    private final String URL_NAME = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=";
    private final String FILE_ID  = "1lsE_2hRwNH1NGS2dpcC1yATlAyILNN21";

    public static void main(String[] args) {

        RestApi restApi = new RestApi();

        try {
            URL url = new URL(restApi.URL_NAME + restApi.FILE_ID);
            PropertyResourceBundle prb ;
            prb = new PropertyResourceBundle(url.openStream());

            for (String key : prb.keySet()) {
                System.out.printf("%-20s%s\n", key, prb.getString(key));
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
