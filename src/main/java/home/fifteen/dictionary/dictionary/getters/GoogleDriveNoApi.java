package home.fifteen.dictionary.dictionary.getters;




import home.fifteen.dictionary.dictionary.Dictionary;
import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;

import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpHeaders;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GoogleDriveNoApi implements DictionaryGetter {

    private final String FILE_ID  ;
    private final String SERVER = "https://drive.google.com/uc";

    private final home.fifteen.dictionary.dictionary.Dictionary dictionary;
    private URL url;
    private String fileName;
    private Long lastModified;
    private String checkSum;


    public GoogleDriveNoApi() {
        dictionary = new home.fifteen.dictionary.dictionary.Dictionary();
//        FILE_ID  = "1E36Ch6rdHsEeg4J7d0--X2qiy1tinIcY";
        FILE_ID  = "1hhLpOfCXx2gNE6sbYMubRo80wtusZrvz";
    }

    public GoogleDriveNoApi(String FILE_ID) {
        dictionary = new home.fifteen.dictionary.dictionary.Dictionary();
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

            System.out.println(new Date(2592000));

//        getFileInfo();
            httpRequest();
            httpClient();
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
    public String toString() {
        return dictionary.toString();
    }

    @Override
    public void setDictionaryName(){

        dictionary.setName(fileName);


    }

    @Override
    public Long getLastModified(){
        return lastModified;
    }

    @Override
    public String getMD5Sum() {
        return null;
    }

    @Override
    public boolean isDownloadable() {
        return false;
    }

    @Override
    public void setDownloadable(boolean downloadable) {

    }

    private void getFileInfo(){

        try {
            URLConnection connection = url.openConnection();
            connection.connect();

            String requestResult = connection.getHeaderField("Content-Disposition");

            Map<String, List<String>> headers =  connection.getHeaderFields();

            Pattern pattern = Pattern.compile("filename=\"(.*)\"");
            Matcher matcher = pattern.matcher(requestResult);
            if(matcher.find()) {
                log.info("Google Drive File Name has been found ");
                fileName = matcher.group(1);
                log.info(fileName);

            }


//            for(String header : headers.keySet()){
//                System.out.println(header);
//                System.out.println(headers.get(header));
//            }

//            log.info(connection.getHeaderField("Access-Control-Allow-Headers"));

//            for (Map.Entry<String,List<String>> entry : headers.entrySet())
//            {
//                String key = entry.getKey();
//                for (String value : entry.getValue())
//                    System.out.println(key + ": " + value);
//            }

//            HttpRequestFactory request = Request.Builder().url(url)
//                    .header("User-Agent", "Mozilla/5.0 (Linux; Android 7.0) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Focus/2.2 Chrome/61.0.3163.98 Mobile Safari/537.36")
//                    .header("Origin", "*")
//                    .header("Access-Control-Request-Method", "POST")
//                    .header("Access-Control-Request-Headers", "Accept, Content-Length")
//                    .build()

            lastModified = connection.getLastModified();
            log.info("Modified : " + lastModified);



//            connection.disconnect();



        } catch (IOException e) {
            e.printStackTrace();
            fileName ="Not Found";
        }

    }

    private void httpRequest(){
        try {
            URL url = new URL(SERVER);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");

            log.info(String.valueOf(url));

            Map<String,String> params = new HashMap<>();
//            params.put("export","download");
            params.put("fields","md5Checksum");
//            params.put("get","md5Checksum");
            params.put("confirm","no_antivirus");
            params.put("id",FILE_ID);

            // Instantiate a requestData object to store our data
            StringBuilder requestData = new StringBuilder();

            for (Map.Entry<String,String> param : params.entrySet()) {
                if (requestData.length() != 0) {
                    requestData.append('&');
                }
                // Encode the parameter based on the parameter map we've defined
                // and append the values from the map to form a single parameter
                requestData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
                requestData.append('=');
                requestData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));
            }

            // Convert the requestData into bytes
            byte[] requestDataByes = requestData.toString().getBytes(StandardCharsets.UTF_8);

            // Set the doOutput flag to true
            httpURLConnection.setDoOutput(true);

            // Get the output stream of the connection instance
            // and add the parameter to the request
            try (DataOutputStream writer = new DataOutputStream(httpURLConnection.getOutputStream())) {
                writer.write(requestDataByes);

                // Always flush and close
                writer.flush();
                writer.close();
            }

            System.out.println(httpURLConnection.getResponseMessage());

            // Returns the value of the content-type header field
            System.out.println(httpURLConnection.getContentType());

            // Returns an unmodifiable Map of the header fields
            System.out.println(httpURLConnection.getHeaderFields());

            // Gets the status code from an HTTP response message
            System.out.println(httpURLConnection.getResponseCode());

            // Gets the HTTP response message returned along with the response code from a server
            System.out.println(httpURLConnection.getResponseMessage());

            // Returns the error stream if the connection failed but the server sent useful data nonetheless
            System.out.println(httpURLConnection.getContent());


            BufferedReader in = new BufferedReader(
                    new InputStreamReader(httpURLConnection.getInputStream()));
            String inputLine;
            StringBuffer content = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                content.append(inputLine);
            }
            in.close();

            httpURLConnection.disconnect();

            System.out.println(content);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void httpClient(){
        try {
            String base = "https://docs.google.com/uc";

            String field = "title";
            String address = base+ "?id=" + FILE_ID;
            System.out.println(address);

            HttpClient client = HttpClient.newBuilder()
                    .followRedirects(HttpClient.Redirect.ALWAYS)
                    .build();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(new URI(address))
                    .GET()
                    .build();

            HttpResponse<String> response = client
                    .send(request , HttpResponse.BodyHandlers.ofString());
            log.info(String.valueOf(response.statusCode()));
//            log.info(response.body());


            HttpHeaders responseHeaders = response.headers();
            Map<String,List<String>> headers = responseHeaders.map();

            for (Map.Entry<String,List<String>> entry : headers.entrySet()){
                String key = entry.getKey();
                for (String value : entry.getValue())
                    System.out.println(key + ": " + value);
            }

            log.info(response.body());

            log.info(String.valueOf(responseHeaders.firstValue("content-length")));



        } catch (URISyntaxException | IOException | InterruptedException e) {
            e.printStackTrace();
        }

    }




}
