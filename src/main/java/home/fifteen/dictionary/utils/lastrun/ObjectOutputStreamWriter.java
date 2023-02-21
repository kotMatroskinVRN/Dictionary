package home.fifteen.dictionary.utils.lastrun;

import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.utils.Settings;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Set;

public class ObjectOutputStreamWriter implements LastRunWriter{

    private final ColorfulLogger LOGGER = ColorfulLogger.getLogger();
    @Override
    public void write(Set<DictionaryGetter> getters) {
        String FILE_NAME_SERIALIZABLE = Settings.FILE_NAME_SERIALIZABLE.getProperty();

        try(FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME_SERIALIZABLE);
                ObjectOutputStream oos = new ObjectOutputStream( fileOutputStream )
        ){
            oos.writeObject(getters);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
