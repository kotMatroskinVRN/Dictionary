package home.fifteen.dictionary.dictionary.getters;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.util.*;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;

import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import home.fifteen.dictionary.dictionary.Dictionary;
import home.fifteen.dictionary.dictionary.word.Word;
import home.fifteen.dictionary.dictionary.word.WordMaker;


public class GoogleDriveDefault implements DictionaryGetter {

    private final String FILE_ID  ;
    private transient final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();



    private transient final home.fifteen.dictionary.dictionary.Dictionary dictionary;
    private transient Drive service;
    private transient File file ;
    private transient boolean downloadable = false;

    public GoogleDriveDefault() {
        dictionary = new home.fifteen.dictionary.dictionary.Dictionary();
        FILE_ID  = "1E36Ch6rdHsEeg4J7d0--X2qiy1tinIcY";
//        FILE_ID  = "1hhLpOfCXx2gNE6sbYMubRo80wtusZrvz";
    }

    public GoogleDriveDefault(String FILE_ID) {
        dictionary = new home.fifteen.dictionary.dictionary.Dictionary();
        this.FILE_ID = FILE_ID;
    }

    @Override
    public Long getLastModified() {
        return file.getModifiedTime().getValue();
    }

    @Override
    public String getMD5Sum() {
        return file.getMd5Checksum();
    }

    @Override
    public boolean isDownloadable() {
        return downloadable;
    }

    @Override
    public void setDownloadable(boolean downloadable) {
        this.downloadable = downloadable;
    }

    @Override
    public void init() {

        try {


        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            String APPLICATION_NAME = "learning";
            service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();


            file = service.files()
                    .get(FILE_ID)
                    .setFields("*")
                    .execute();



        printFile();
        setDictionaryName();

        download();


        } catch (IOException  | GeneralSecurityException e) {
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
            dictionary.setName(file.getName());
    }


    private void download(){
        try {

            String URL_NAME = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=";
            URL url = new URL(URL_NAME + FILE_ID);

            InputStreamReader is = new InputStreamReader(url.openStream() , StandardCharsets.UTF_8) ;
            BufferedReader reader = new BufferedReader(is );

            String line;
            while( (line = reader.readLine()) != null) {

                WordMaker wordMaker = new WordMaker();
                try {
                    Word word = wordMaker.make(line);
                    dictionary.addWord(word);
                }catch ( NoSuchElementException e ){
                    continue;
                }

            }

            is.close();
            reader.close();

        } catch (IOException   e) {
            e.printStackTrace();
        }
//        httpRequest(service.batch().getBatchUrl());

    }

    private void printFile() {
        System.out.println("Title: " + file.getName());
        System.out.println("Description: " + file.getDescription());
        System.out.println("MIME type: " + file.getMimeType());
        System.out.println("checksum: " + file.getMd5Checksum());
        System.out.println("date: " + new Date(file.getModifiedTime().getValue()) );



//        System.out.println(file.);
//
//        Map<String,String> map = file.ge;
//
//        for(Map.Entry<String,String> entry : map.entrySet()){
//
//            System.out.printf("%-30s%s\n" , entry , map.get(entry));
//        }

    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
        // Load client secrets.
        String CREDENTIALS_FILE_PATH = "client_secret.json";
        InputStream in = ClassLoader.getSystemResourceAsStream(CREDENTIALS_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));


        // Build flow and trigger user authorization request.
        List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
        String TOKENS_DIRECTORY_PATH = "tokens";
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, clientSecrets, SCOPES)
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        //returns an authorized Credential object.
        return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
    }
//
//    private void downloadFileToPRB(Drive service, String fileId) {
//        InputStream inputStream ;
//        try {
//            OutputStream outputStream = new ByteArrayOutputStream();
//            service.files().export(fileId, "text/plain")
//                    .executeMediaAndDownloadTo(outputStream);
//            inputStream = new ByteArrayInputStream(((ByteArrayOutputStream) outputStream).toByteArray());
////            inputStream = service.files()
////                    .export(fileId, "text/plain")
////                    .executeMediaAsInputStream();
//            PropertyResourceBundle prb = new PropertyResourceBundle(inputStream);
//
//            for (String key : prb.keySet()) {
//                System.out.println(key);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//
//
//    }

//    private InputStream downloadFile(Drive service, File file) {
//
//
//        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
//            try {
//                HttpResponse resp =
//                        service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
//                                .execute();
//                return resp.getContent();
//            } catch (IOException e) {
//                // An error occurred.
//                e.printStackTrace();
//                return null;
//            }
//        } else {
//            // The file doesn't have any content stored on Drive.
//            return null;
//        }
//    }

//    private void httpRequest(GenericUrl genericUrl){
//        try {
//            URL url = genericUrl.toURL();
//            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
//            httpURLConnection.setRequestMethod("GET");
//
//            log.info(String.valueOf(url));
//
//            Map<String,String> params = new HashMap<>();
////            params.put("export","download");
//            params.put("fields","md5Checksum");
////            params.put("get","md5Checksum");
//            params.put("confirm","no_antivirus");
//            params.put("id",FILE_ID);
//
//            // Instantiate a requestData object to store our data
//            StringBuilder requestData = new StringBuilder();
//
//            for (Map.Entry<String,String> param : params.entrySet()) {
//                if (requestData.length() != 0) {
//                    requestData.append('&');
//                }
//                // Encode the parameter based on the parameter map we've defined
//                // and append the values from the map to form a single parameter
//                requestData.append(URLEncoder.encode(param.getKey(), StandardCharsets.UTF_8));
//                requestData.append('=');
//                requestData.append(URLEncoder.encode(String.valueOf(param.getValue()), StandardCharsets.UTF_8));
//            }
//
//            // Convert the requestData into bytes
//            byte[] requestDataByes = requestData.toString().getBytes(StandardCharsets.UTF_8);
//
//            // Set the doOutput flag to true
//            httpURLConnection.setDoOutput(true);
//
//            // Get the output stream of the connection instance
//            // and add the parameter to the request
//            try (DataOutputStream writer = new DataOutputStream(httpURLConnection.getOutputStream())) {
//                writer.write(requestDataByes);
//
//                // Always flush and close
//                writer.flush();
//                writer.close();
//            }
//
//            System.out.println(httpURLConnection.getResponseMessage());
//
//            // Returns the value of the content-type header field
//            System.out.println(httpURLConnection.getContentType());
//
//            // Returns an unmodifiable Map of the header fields
//            System.out.println(httpURLConnection.getHeaderFields());
//
//            // Gets the status code from an HTTP response message
//            System.out.println(httpURLConnection.getResponseCode());
//
//            // Gets the HTTP response message returned along with the response code from a server
//            System.out.println(httpURLConnection.getResponseMessage());
//
//            // Returns the error stream if the connection failed but the server sent useful data nonetheless
//            System.out.println(httpURLConnection.getContent());
//
//
//            BufferedReader in = new BufferedReader(
//                    new InputStreamReader(httpURLConnection.getInputStream()));
//            String inputLine;
//            StringBuffer content = new StringBuffer();
//            while ((inputLine = in.readLine()) != null) {
//                content.append(inputLine);
//            }
//            in.close();
//
//            httpURLConnection.disconnect();
//
//            System.out.println(content);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }


}
