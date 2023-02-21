package home.fifteen.dictionary.utils.lastrun;

import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;

import java.util.Set;

public interface LastRunWriter {
    void write(Set<DictionaryGetter> getters);

}
