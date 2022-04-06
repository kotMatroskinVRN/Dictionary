package home.fifteen.dictionary.dictionary;

import java.util.*;

public class Dictionary {

    private final Map<String,String> words = new HashMap<>();

    private String name ;

    public void addWord(String word , String description){
        words.put( word,description );
    }

    public Map<String, String> getWords() {
        return words;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name + " :\n");
        String format = "%-40s%s\n";

        for (String key : words.keySet()) {
            String string  = String.format(format , key , words.get(key));
            result.append(string);
        }

        return result.toString();
    }

}
