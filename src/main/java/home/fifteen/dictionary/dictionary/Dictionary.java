package home.fifteen.dictionary.dictionary;

import home.fifteen.dictionary.dictionary.word.Word;

import java.util.*;

public class Dictionary {

    private final List<Word> words = new ArrayList<>();

    private String name ;

    public void addWord(Word word){
        words.add( word );
    }

    public List<Word> getWords() {
        return words;
    }

    public String getName() {
        return name;
    }
    public String getNameForList() {
        return name.replaceAll("_" , " ")
                .replaceAll(".dct" , "");
    }


    public void setName(String name) {
        this.name = name;

    }



    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(name + " :\n");
        String format = "%-40s%s\n";

        for (Word word : words) {
            String string  = String.format(format , word.getWord() , word.getDescription());
            result.append(string);
        }

        return result.toString();
    }

}
