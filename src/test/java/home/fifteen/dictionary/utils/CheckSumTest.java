package home.fifteen.dictionary.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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