package home.fifteen.dictionary.task;

import home.fifteen.dictionary.dictionary.Dictionary;
import home.fifteen.dictionary.Main;
import home.fifteen.dictionary.dictionary.Word;

import java.util.*;
import java.util.logging.Logger;

public class Task {

    private final Logger log = Main.getLogger();

    private final List<Word> words ;
    private final ArrayList<String> answers ;
    private Word task ;
    private String  correctAnswer;
    private int correctAnswers , correctAnswersInARow;


    public Task(){
        words = new ArrayList<>();
        answers = new ArrayList<>();
//        words.clear();
        correctAnswers = 0;
        correctAnswersInARow = 0;

    }


    public void addDictionary(Dictionary dictionary){
        for(Word word : dictionary.getWords()){
            if(words.contains(word)){
                log.warning(
                        String.format("word %s exists with meaning %s. Will be skipped" , word.getWord() , word.getDescription() )
                );
            }else {
                words.add(word);
            }
        }

    }

    public void prepareTask(){
        task = getRandomWord();
        makeAnswers();
    }
    public Word getTask(){
        return task;
    }
    public ArrayList<String> getAnswers(){
        return answers;
    }
    public String getCorrectAnswer() {
        return correctAnswer;
    }
    public boolean checkAnswer(String description){
        boolean factor = task.getDescription().equals(description);

        if(factor){
            correctAnswers++;
            correctAnswersInARow++;

        }else{

            correctAnswersInARow = 0;
        }

        return factor;

    }

//    public void clear(){
//        if(!words.isEmpty()){
//            words.clear();
//        }
//    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public int getCorrectAnswersInARow() {
        return correctAnswersInARow;
    }

    private void makeAnswers(){
        if(!answers.isEmpty()){
            answers.clear();
        }

        correctAnswer = task.getDescription();
        answers.add( correctAnswer );

        for(int i=0;i<4;i++){
            String description = getRandomWord().getDescription();
            while( words.size()>4 && answers.contains(description) ){
                description = getRandomWord().getDescription();
            }
            answers.add(description);

        }

        Collections.shuffle(answers);

    }

    private Word getRandomWord(){
        int number = (int)(Math.random()*words.size());
        return words.get(number);

    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder(task.getWord());
        result.append("\n");
        String format = "%-40s%s\n";

        for (String answer : answers) {
            String string  = String.format(format , "" , answer );
            result.append(string);
        }

        return result.toString();
    }


}
