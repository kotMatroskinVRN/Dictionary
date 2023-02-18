package home.fifteen.dictionary.gui;

import home.fifteen.dictionary.utils.logger.ColorfulLogger;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class FontForQuestion {

    private final ColorfulLogger log = ColorfulLogger.getLogger();
    private Font font;

    public FontForQuestion(Font font) {
        this.font = font;
    }

    public void adjustSize(int length){
        String family = font.getFamily();
        FontWeight weight = FontWeight.findByName(font.getStyle());

        log.printVerbose("Font Size" ,  String.valueOf(font.getSize()));
        log.printVerbose(font.getStyle());

        if(length>19){
            font = Font.font( family , weight , 20);
        } else{
            font = Font.font( family , weight ,36);
        }
        if(length>30){
            font = Font.font( family , weight ,16);
        }

        log.printVerbose(String.valueOf(length));
    }

    public Font getFont() {
        return font;
    }
}
