package home.fifteen.dictionary.task;

import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;

import java.util.HashSet;
import java.util.Set;

public class TaskBuilder {

    private final Set<DictionaryGetter> getters ;
    private Task task;


    public TaskBuilder(Set<DictionaryGetter> getters) {
        this.getters = new HashSet<>(getters);
//        this.getters = getters;
        task = new Task();
    }

    public void init(){

        for(DictionaryGetter getter : getters){
//            getter.init();
            task.addDictionary(getter.getDictionary());
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

}
