package home.fifteen.dictionary.utils.logger;

import com.google.common.annotations.VisibleForTesting;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class ColorfulLogger {

    private static final String FORMAT    = "%-15s : %s";

    private static ColorfulLogger logger;
    private final  Logger log ;


    private ColorfulLogger(){
        log = Logger.getLogger(ClassLoader.class.getName());


        try {
            LogManager.getLogManager().readConfiguration(
                    ClassLoader.getSystemResourceAsStream("logging.properties"));

        } catch (IOException e) {
            System.err.println("Could not setup logger configuration: " + e);
        }

        log.setLevel(Level.INFO);

    }

    public static synchronized ColorfulLogger getLogger(){
        if(logger == null){
            logger = new ColorfulLogger();
        }
        return logger;
    }

    public void enableVerbose(){
        log.setLevel(Level.FINE);

    }




    public void printWarning(String text){
        log.warning(  text   );
    }

    public void printWarning(String name , String text){
        log.warning( String.format(  FORMAT , name , text ) );
    }

    public void printWarning(String format , String name , String text){
        log.warning( String.format(  format  , name ,  text ) );
    }




    public void printError(String text){
        log.severe( text  );
    }

    public void printError(String name , String text){
        log.severe( String.format( FORMAT , name , text ) );
    }

    public void printError(String format , String name , String text){
        log.severe( String.format( format  , name , text ) );
    }





    public void printInfo(String text){
        log.info( text  );
    }

    public void printInfo(String name , String text){
        log.info( String.format(  FORMAT  , name , text ) );
    }

    public void printInfo(String format , String name , String text){
        log.info( String.format(  format , name , text ) );
    }





    public void printVerbose(String text){
        log.fine(  text  );
    }

    public void printVerbose(String name , String text){
        log.fine( String.format(  FORMAT , name , text ) );
    }

    public void printVerbose(String format , String name , String text){
        log.fine( String.format(  format , name , text ) );
    }

    @VisibleForTesting
    public Logger getLog(){
        return log;
    }



}
