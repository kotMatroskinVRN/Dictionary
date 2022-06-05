package home.fifteen.dictionary.dictionary;

import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;

public class DictionaryGetterComparator {

    private final DictionaryGetter first;
    private final DictionaryGetter second;

    public DictionaryGetterComparator(DictionaryGetter first, DictionaryGetter second) {
        this.first = first;
        this.second = second;
    }

    public boolean isEqualCheckSum(){
        return first.getMD5Sum().equals(second.getMD5Sum());
    }

    public boolean isEqualFileName(){
        return first.getDictionary().getName().equals(second.getDictionary().getName());
    }

    public boolean isEqual(){
        return isEqualCheckSum()&&isEqualFileName();
    }

}
