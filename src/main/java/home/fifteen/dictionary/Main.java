package home.fifteen.dictionary;

import home.fifteen.dictionary.dictionary.*;
import home.fifteen.dictionary.task.Sources;
import home.fifteen.dictionary.task.Task;

import java.io.File;
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


        if(args.length>1 && args[0].equals("-encode")){
            encodeDictionary(args[1]);
        }

        Task task = new Task();

        Sources.init();
        for(DictionaryGetter getter : Sources.getGetters()){
            task.addDictionary(getter.getDictionary());
            log.fine(getter.getDictionary().toString());
        }

        for(int i=0;i<10;i++) {
            task.prepareTask();
            log.info(task.toString());
        }
//



    }

    private static void encodeDictionary(String fileName) {

        File file = new File(fileName);

        boolean factor = file.isFile() && file.getName().equals(fileName);

        if(factor) {
            EncoderBase64 encoder = new EncoderBase64(fileName);
            encoder.encode();

        }else{
            String string = String.format(
                    "\nWrong File Name : %s\nPlease specify file name from working directory." ,
                    fileName
            );
            log.severe( string );
        }

        System.exit(0);
    }

    public static Logger getLog(){
        return log;
    }

}
