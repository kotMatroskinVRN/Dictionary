package home.fifteen.dictionary.utils;

import home.fifteen.dictionary.utils.file.CheckSum;
import org.junit.jupiter.api.Test;

class CheckSumTest {

    @Test
    void getCheckSum() {

        CheckSum checkSum = new CheckSum();
        checkSum.generate();
        System.out.println(checkSum.getCheckSum());
    }

    @Test
    void generate() {
    }
}