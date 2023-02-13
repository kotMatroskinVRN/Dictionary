package home.fifteen.dictionary.utils;

import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.io.IOException;
import java.util.Objects;
import java.util.PropertyResourceBundle;

public enum Settings {

    DICTIONARY_DIRECTORY(){},
    FILE_NAME_SERIALIZABLE(){},
    ;

    private final ColorfulLogger log = ColorfulLogger.getLogger();

    private String property ;

    public String getProperty() { return property; }

    Settings(){
        try {
            PropertyResourceBundle prb =
                    new PropertyResourceBundle(
                            Objects.requireNonNull(ClassLoader.getSystemResourceAsStream("settings.properties")));

            property = prb.getString( name() );

            log.printVerbose( this.toString() );

        } catch (IOException e) {
            System.out.println("Check for common.properties " + name() );
            e.printStackTrace();
            System.exit(1);
        }


    }

    @Override
    public String toString() {
        return String.format("%-30s%s\n" ,  this.name() , property );
    }
}
