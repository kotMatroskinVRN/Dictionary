package home.fifteen.dictionary.utils;

import java.util.logging.Formatter;
import java.util.logging.LogRecord;

public class LevelAndMessageFormatter extends Formatter {
    @Override
    public String format(LogRecord record) {
        return String.format("%s : %s\n" , record.getLevel() , record.getMessage());
    }
}
