package home.fifteen.dictionary.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SettingsTest {



    @Test
    void getProperty() {
        assertEquals("DictionarySource", Settings.DICTIONARY_DIRECTORY.getProperty());
    }

}