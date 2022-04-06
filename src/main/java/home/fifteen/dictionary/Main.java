package home.fifteen.dictionary;

import home.fifteen.dictionary.dictionary.*;
import home.fifteen.dictionary.task.Task;

import java.io.IOException;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main {

    private final static Logger log = Logger.getLogger(ClassLoader.class.getName());

    public static void main(String[] args) {

        try {
            LogManager.getLogManager().readConfiguration(
                    ClassLoader.getSystemResourceAsStream("logging.properties"));

        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e);
        }

        DictionaryGetter dictionaryGetter;
        Task task = new Task();

        dictionaryGetter = new GoogleDriveDefault();
        dictionaryGetter.init();
        task.addDictionary(dictionaryGetter.getDictionary() );
//        task.addDictionary(dictionary);

        dictionaryGetter = new FileGetter();
        dictionaryGetter.init();
        task.addDictionary(dictionaryGetter.getDictionary());

        dictionaryGetter = new FileGetterEncrypted();
        dictionaryGetter.init();
        task.addDictionary(dictionaryGetter.getDictionary());

        for(int i=0;i<10;i++) {
            task.prepareTask();
            log.info(task.toString());
        }
//
//        EncoderBase64 encoder = new EncoderBase64();
//        encoder.encode();


    }

    public static Logger getLog(){
        return log;
    }

}
