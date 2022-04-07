package home.fifteen.dictionary.task;

import home.fifteen.dictionary.dictionary.Dictionary;
import home.fifteen.dictionary.Main;

import java.util.*;
import java.util.logging.Logger;

public class Task {

    private Logger log = Main.getLog();

    private final Map<String,String> words = new HashMap<>();
    private final List<String> answers = new ArrayList<>();

    private String task ;


    public void addDictionary(Dictionary dictionary){
        for(String key : dictionary.getWords().keySet()){
            if(words.containsKey(key)){
                log.warning(
                        String.format("word %s exists with meaning %s. Will be skipped" , key , words.get(key) )
                );
            }else {
                words.put(key, dictionary.getWords().get(key));
            }
        }

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
        int number = (int)(Math.random()*words.size());
        ArrayList<String> keys = new ArrayList(words.keySet());
        return keys.get(number);

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(task);
        result.append("\n");
        String format = "%-40s%s\n";

        for (String answer : answers) {
            String string  = String.format(format , "" , answer );
            result.append(string);
        }

        return result.toString();
    }


}