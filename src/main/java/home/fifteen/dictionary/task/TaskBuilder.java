package home.fifteen.dictionary.task;

import home.fifteen.dictionary.dictionary.DictionaryGetter;

import java.util.Set;

public class TaskBuilder {

    private final Set<DictionaryGetter> getters ;
    private final Task task;


    public TaskBuilder(Set<DictionaryGetter> getters) {
        this.getters = getters;
        task = new Task();
    }

    public void init(){

        for(DictionaryGetter getter : getters){
            getter.init();
            task.addDictionary(getter.getDictionary());
        }

    }

    public Task getTask() {
        return task;
    }


}
