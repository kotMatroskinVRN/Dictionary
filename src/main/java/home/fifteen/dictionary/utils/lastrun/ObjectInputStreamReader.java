package home.fifteen.dictionary.utils.lastrun;

import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.gui.MainWindow;
import home.fifteen.dictionary.task.TaskBuilder;
import home.fifteen.dictionary.utils.Settings;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.HashSet;
import java.util.Set;

public class ObjectInputStreamReader implements LastRunReader{

    private final ColorfulLogger LOGGER = ColorfulLogger.getLogger();
    private final String FILE_NAME = Settings.FILE_NAME_SERIALIZABLE.getProperty();
    @Override
    public boolean checkSource() {
        File file = new File(FILE_NAME);
        return file.exists();
    }

    @Override
    public Set<DictionaryGetter> read() {
        Set<DictionaryGetter> getters = new HashSet<>();
        try(
                FileInputStream fileInputStream = new FileInputStream(FILE_NAME);
                ObjectInputStream ois = new ObjectInputStream(fileInputStream)
                    )
            {
                Object object = ois.readObject();
                Set<DictionaryGetter> serializedGetters = new HashSet<>();
                if(object instanceof HashSet ){
                    serializedGetters.addAll( (HashSet<DictionaryGetter>) object);
                }

                LOGGER.printInfo("have been read");

                for (DictionaryGetter serializedGetter : serializedGetters) {
                    for (DictionaryGetter sourceGetter : Sources.getGetters()) {
                        if (serializedGetter.getID().equals(sourceGetter.getID())) {
                            getters.add(sourceGetter);
                        }
                    }
                }

                LOGGER.printVerbose( String.valueOf(getters.size()) );

                TaskBuilder taskBuilder = MainWindow.getInstance().getController().getTaskBuilder();
                taskBuilder.setGetters(getters);
                MainWindow.getInstance().getController().setTaskBuilder(taskBuilder);


            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        return getters;
    }
}
