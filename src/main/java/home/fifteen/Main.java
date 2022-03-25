package home.fifteen;

import home.fifteen.Dictionary.DictionaryGetter;
import home.fifteen.Dictionary.GoogleDriveDefault;

public class Main {


    public static void main(String[] args) {

        DictionaryGetter dictionaryGetter = new GoogleDriveDefault();

        dictionaryGetter.init();

        System.out.println(dictionaryGetter);


    }
}
