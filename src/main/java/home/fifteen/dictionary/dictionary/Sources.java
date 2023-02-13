package home.fifteen.dictionary.dictionary;

import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.dictionary.getters.FileGetter;
import home.fifteen.dictionary.dictionary.getters.FileGetterEncrypted;
import home.fifteen.dictionary.utils.Settings;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

public enum Sources {

//    GOOGLE_DRIVE_NOAPI("GoogleDrive.txt" , false){
//        @Override
//        DictionaryGetter getGetter(String name) {
//            return new GoogleDriveNoApi(name);
//        }
//    },
//    GOOGLE_DRIVE("GoogleDrive.txt", false){
//        @Override
//        DictionaryGetter getGetter(String name) {
//            return new GoogleDriveDefault(name);
//        }
//    },
    ENCRYPTED("EncryptedFiles.txt" , true){
        @Override
        DictionaryGetter getGetter(String name) {
            return new FileGetterEncrypted(name);
        }
    },
    PLAIN("PlainFiles.txt" , true){
        @Override
        DictionaryGetter getGetter(String name) {
            return new FileGetter(name);
        }
    },
    ;

    private final ColorfulLogger log = ColorfulLogger.getLogger();
    private static final Set<DictionaryGetter> getters = new HashSet<>();
    private static final Set<DictionaryGetter> onlineGetters = new HashSet<>();
    private final String fileName;
    private final boolean isOffline;

    Sources(String fileName , boolean isOffline){
        this.fileName = Settings.DICTIONARY_DIRECTORY.getProperty() + "/" + fileName;
        this.isOffline = isOffline;
    }

    public static Set<DictionaryGetter> getGetters(){
        return getters;
    }
    public static Set<DictionaryGetter> getOnlineGetters(){
        return onlineGetters;
    }
    public String getFileName(){
        return fileName;
    }

    public static void init(){
//        if(!getters.isEmpty()){
//            getters.clear();
//        }

        getters.clear();
        for( Sources source : Sources.values() ){
            if(source.isOffline) {
                source.readSource();
            }
        }
    }

    public static void initOnline(){

        onlineGetters.clear();
        for( Sources source : Sources.values() ){
            if(!source.isOffline) {
                source.readOnlineSource();
            }
        }
    }


//    public void setFileName(String fileName) {
//        this.fileName = fileName;
//    }



    abstract DictionaryGetter getGetter(String name);

    private void readSource(){
        for (String line : fileToString().split("\n")){
            DictionaryGetter getter = getGetter(line);
            getter.init();

//            File file = new File(line);
//            if(file.isFile()) {
//                getter.getDictionary().setName(file.getName());
//            }
            getters.add(getter);
        }

    }

    private void readOnlineSource(){
        for (String line : fileToString().split("\n")){
            DictionaryGetter getter = getGetter(line);
            getter.init();
            File file = new File(line);
            if(file.isFile()) {
                getter.getDictionary().setName(file.getName());
            }
            onlineGetters.add(getter);
        }


    }

    private String fileToString() {
        StringBuilder result = new StringBuilder();

        try(
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(new FileInputStream(fileName), "UTF-8")
                );
                ) {

            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                result.append("\n");
                line = reader.readLine();
            }
            return result.toString();
        } catch (IOException e) {
            log.printError("File does not exist : " + fileName);
            e.printStackTrace();
        }
        return "missed";
    }


}
