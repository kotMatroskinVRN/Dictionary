package home.fifteen.dictionary.dictionary.word;


public class Word {

    private final String word;
    private final String description;
    private int    weight;

    public Word(String word, String description) {
        this.word = word;
        this.description = description;
        weight = 0;
    }

    public String getWord() {
        return word;
    }

    public String getDescription() {
        return description;
    }

    public void addWeight(){
        weight++;
    }

    public int getWeight(){
        return weight;
    }

    @Override
    public String toString() {
        String format = "%-40s%s\n";

        return String.format(format , word , description);
    }

    public void markMistake() {
        weight = -1;
    }
}
