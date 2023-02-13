package home.fifteen.dictionary.task;

import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.utils.Settings;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashSet;
import java.util.Set;

public class TaskBuilder {

    private final ColorfulLogger LOGGER = ColorfulLogger.getLogger();

    private final Set<DictionaryGetter> getters ;
    private Task task;


    public TaskBuilder(Set<DictionaryGetter> getters) {
        this.getters = new HashSet<>(getters);
//        this.getters = getters;
        task = new Task();

    }

    public void init(){

        try {
            final String FILE_NAME_SERIALIZABLE = Settings.FILE_NAME_SERIALIZABLE.getProperty();
            FileOutputStream fileOutputStream = new FileOutputStream(FILE_NAME_SERIALIZABLE);
            ObjectOutputStream oos = new ObjectOutputStream( fileOutputStream );


            for(DictionaryGetter getter : getters){
                LOGGER.printVerbose(getter.getDictionary().getNameForList());
                task.addDictionary(getter.getDictionary());
            }
            oos.writeObject(getters);

            oos.close();




        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public Task getTask() {
        return task;
    }

    public void setGetters(Set<DictionaryGetter> getters){
        this.getters.clear();
        this.getters.addAll(getters);
        task = new Task();
        init();
    }

    public String getGettersToolTip(){
        StringBuilder result = new StringBuilder();
        for(DictionaryGetter getter : getters){
            result.append(getter.getDictionary().getNameForList()).append("\n");
        }
        return result.toString();
    }

}
