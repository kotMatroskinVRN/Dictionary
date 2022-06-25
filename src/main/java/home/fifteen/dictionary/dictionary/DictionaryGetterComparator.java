package home.fifteen.dictionary.dictionary;

import home.fifteen.dictionary.dictionary.getters.DictionaryGetter;

public class DictionaryGetterComparator {

    private final DictionaryGetter first;
    private final DictionaryGetter second;

    private final boolean sameName ;

    public DictionaryGetterComparator(DictionaryGetter first, DictionaryGetter second) {
        this.first = first;
        this.second = second;
        sameName = first.getDictionary().getName().equals(second.getDictionary().getName());
    }

    public void compare(){
        if( sameName ) {
            if( isFirstNewer() ) {
                first.setDownloadable(true);
            }
        }
    }

    public boolean isEqualCheckSum(){
        return sameName && first.getMD5Sum().equals(second.getMD5Sum());
    }

    public boolean isEqualFileName(){
        return sameName && first.getDictionary().getName().equals(second.getDictionary().getName());
    }

    public boolean isEqual(){
        return sameName && isEqualCheckSum()&&isEqualFileName();
    }

    public boolean isSameName() {
        return sameName;
    }

    public boolean isFirstNewer(){
        return first.getLastModified()>second.getLastModified();
    }



}
