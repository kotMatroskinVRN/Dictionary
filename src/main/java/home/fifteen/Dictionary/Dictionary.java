package home.fifteen.Dictionary;

import java.util.*;

public class Dictionary {

    private final Map<String,String> words = new HashMap<>();
    private final List<String> answers = new ArrayList<>();

    private String task ;
    private String name ;


    public void addWord(String word , String description){
        words.put( word,description );
    }

    public Map<String, String> getWords() {
        return words;
    }

    public void prepareTask(){
        task = getRandomWord();
        makeAnswers();
    }

    public String getTask(){
        return task;
    }

    public List<String> getAnswers(){
        return answers;
    }

    public boolean checkAnswer(String description){
        return words.get(task).equals(description);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void makeAnswers(){
        if(!answers.isEmpty()){
            answers.clear();
        }

        answers.add(words.get(task));

        for(int i=0;i<4;i++){
            String description = words.get(getRandomWord());
            while( words.size()>4 &&
                    answers.contains(description) ){
                description = words.get(getRandomWord());
            }
            answers.add(description);

        }

        Collections.shuffle(answers);

    }

    private String getRandomWord(){
        int number = (int)(Math.random()*words.size())+1;
        ArrayList<String> keys = new ArrayList(words.keySet());
        return keys.get(number);

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
