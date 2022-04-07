package home.fifteen.dictionary.dictionary;

import java.io.*;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.PropertyResourceBundle;

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
import home.fifteen.dictionary.dictionary.DictionaryGetter;


public class GoogleDriveDefault implements DictionaryGetter {

    private final String FILE_ID  ;
    private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();



    private final Dictionary dictionary;
    private File file ;

    public GoogleDriveDefault() {
        dictionary = new Dictionary();
        FILE_ID  = "1E36Ch6rdHsEeg4J7d0--X2qiy1tinIcY";
//        FILE_ID  = "1hhLpOfCXx2gNE6sbYMubRo80wtusZrvz";
    }

    public GoogleDriveDefault(String FILE_ID) {
        dictionary = new Dictionary();
        this.FILE_ID = FILE_ID;
    }



    @Override
    public void init() {

        try {
            String URL_NAME = "https://drive.google.com/uc?export=download&confirm=no_antivirus&id=";
            URL url = new URL(URL_NAME + FILE_ID);

        InputStream is = url.openStream();

//        while (is==null){
//            Thread.sleep(50);
//            System.out.println("sleeping...");
//        }

        PropertyResourceBundle prb = new PropertyResourceBundle(is);

        for (String key : prb.keySet()) {
            dictionary.addWord( parseKey(key) , prb.getString(key));
        }

        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
            String APPLICATION_NAME = "learning";
            Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();

        file = service.files().get(FILE_ID).execute();

        printFile();
        setDictionaryName();

//            downloadFileToPRB( service , FILE_ID);


        } catch (IOException  | GeneralSecurityException e) {
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
            dictionary.setName(file.getName());
    }



    private void printFile() {
            System.out.println("Title: " + file.getName());
            System.out.println("Description: " + file.getDescription());
            System.out.println("MIME type: " + file.getMimeType());
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
        Credential credential = new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
        //returns an authorized Credential object.
        return credential;
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



}