package home.fifteen.dictionary.dictionary.word;

import java.util.NoSuchElementException;
import java.util.StringTokenizer;

public class WordMaker {

    public Word make(String line) throws NoSuchElementException {
        Word word = null;
        StringTokenizer tokenizer = new StringTokenizer(line , "=");
        if(tokenizer.hasMoreTokens()){
            String key   = tokenizer.nextToken().trim();
            String value = tokenizer.nextToken().trim();
            word = new Word(key , value);
        }else{
            throw new NoSuchElementException();
        }

        return word;
    }
}
