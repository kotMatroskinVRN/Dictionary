package home.fifteen.dictionary.utils.file;

import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;

public class StringResource {

    private final ColorfulLogger logger = ColorfulLogger.getLogger();


    public String resource2string(String resourceName){
        StringBuilder result = new StringBuilder();

        try(
                InputStream is = ClassLoader.getSystemResourceAsStream(resourceName);
                InputStreamReader isr = new InputStreamReader(Objects.requireNonNull(is));
                BufferedReader reader = new BufferedReader(isr)
                )
        {


            String line = reader.readLine();
            while(line != null){
                result.append(line).append("\n");
                line = reader.readLine();
            }
        }
        catch (IOException e) {
            logger.printError(resourceName , "resource not found" );
            System.exit(1);
            return "";}

        return result.toString() ;
    }

}
