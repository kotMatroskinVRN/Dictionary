package home.fifteen.dictionary.task;

import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.util.HashSet;
import java.util.Set;

public class TaskBuilder {

    private final ColorfulLogger LOGGER = ColorfulLogger.getLogger();

    private final Set<DictionaryGetter> getters ;
    private DictionaryTask task;


    public TaskBuilder(Set<DictionaryGetter> getters) {
        this.getters = new HashSet<>(getters);
        task = new DictionaryTask();
    }

    public void init(){

        for(DictionaryGetter getter : getters){
            LOGGER.printVerbose(getter.getDictionary().getNameForList());
            task.addDictionary(getter.getDictionary());
        }


//        try {
//            LOGGER.printInfo(String.valueOf(Thread.currentThread()));
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }

    }

    public DictionaryTask getTask() {
        return task;
    }

    public void setGetters(Set<DictionaryGetter> getters){
        this.getters.clear();
        this.getters.addAll(getters);
        task = new DictionaryTask();
        init();
    }

    public Set<DictionaryGetter> getGetters() {
        return getters;
    }

    public String getGettersToolTip(){
        StringBuilder result = new StringBuilder();
        for(DictionaryGetter getter : getters){
            result.append(getter.getDictionary().getNameForList()).append("\n");
        }
        return result.toString();
    }

}
