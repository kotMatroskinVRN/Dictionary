package home.fifteen.dictionary.utils;

import home.fifteen.dictionary.Main;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class DecoderBase64Test {

    private final static DecoderBase64 decoderBase64 = new DecoderBase64();

    @BeforeAll
    static void initTest(){
        decoderBase64.init();
    }

    @Test
    void getFileName() {
        assertEquals("0104.properties" , decoderBase64.getFileName());
    }

    @Test
    void getCheckSum() {
        assertEquals("b0c8de4d70232fbab12a42e7561c6aad" , decoderBase64.getCheckSum());
    }

    @Test
    void getModifiedTime() {
        assertEquals(1650787361444L , decoderBase64.getModifiedTime());
    }

    @Test
    void getDecodedString() {
        decoderBase64.decode();
        String string = decoderBase64.getDecodedString().substring(0,4);
        assertEquals("good" , string);
    }
}