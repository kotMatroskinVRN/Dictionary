package home.fifteen.dictionary.dictionary;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GoogleDriveNoApiTest {

    @Test
    void init() {

        DictionaryGetter getter = new GoogleDriveNoApi();
        getter.init();
    }

    @Test
    void getDictionary() {
    }

    @Test
    void testToString() {
    }

    @Test
    void setDictionaryName() {
    }
}