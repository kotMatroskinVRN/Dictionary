package home.fifteen.dictionary.utils;

import home.fifteen.dictionary.Main;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;
import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

class EncoderBase64Test {

    private ColorfulLogger log = ColorfulLogger.getLogger();


    @Test
    void initFileInfo() {


    }

    @Test
    void setCheckSum() {
    }

    @Test
    void setModifiedTime() {
    }

    @Test
    void encode() {
    }

    public static void main(String[] args) {
        EncoderBase64 encoder = new EncoderBase64("test.properties");
        encoder.initFileInfo();
        encoder.encode();

    }
}