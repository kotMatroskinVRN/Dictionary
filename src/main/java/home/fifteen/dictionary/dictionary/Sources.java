package home.fifteen.dictionary.dictionary;

import home.fifteen.dictionary.Main;

import java.io.*;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;

public enum Sources {

//    GOOGLE_DRIVE("DictionarySource/GoogleDrive.txt"){
//        @Override
//        DictionaryGetter getGetter(String name) {
//            return new GoogleDriveNoApi(name);
//        }
//    },
//    GOOGLE_DRIVE("DictionarySource/GoogleDrive.txt"){
//        @Override
//        DictionaryGetter getGetter(String name) {
//            return new GoogleDriveDefault(name);
//        }
//    },
    ENCRYPTED("DictionarySource/EncryptedFiles.txt"){
        @Override
        DictionaryGetter getGetter(String name) {
            return new FileGetterEncrypted(name);
        }
    },
    PLAIN("DictionarySource/PlainFiles.txt"){
        @Override
        DictionaryGetter getGetter(String name) {
            return new FileGetter(name);
        }
    },
    ;

    private final Logger log = Main.getLog();

    private static final Set<DictionaryGetter> getters = new HashSet<>();

    private String fileName;

    Sources(String fileName){
        this.fileName = fileName;
    }

    public static Set<DictionaryGetter> getGetters(){
        return getters;
    }

    public static void init(){
//        if(!getters.isEmpty()){
//            getters.clear();
//        }

        getters.clear();
        for( Sources source : Sources.values() ){
            source.readSource();
        }
    }


    public void setFileName(String fileName) {
        this.fileName = fileName;
    }



    abstract DictionaryGetter getGetter(String name);

    private void readSource(){
        for (String line : fileToString().split("\n")){
            DictionaryGetter getter = getGetter(line);
            getter.init();
            File file = new File(line);
            if(file.isFile()) {
                getter.getDictionary().setName(file.getName());
            }
            getters.add(getter);
        }

    }

    private String fileToString() {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                result.append("\n");
                line = reader.readLine();
            }
            return result.toString();
        } catch (IOException e) {
            log.severe("File does not exist : " + fileName);
            e.printStackTrace();
        }
        return "missed";
    }


}
