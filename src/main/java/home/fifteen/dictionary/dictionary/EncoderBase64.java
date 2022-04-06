package home.fifteen.dictionary.dictionary;

import home.fifteen.dictionary.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

public class EncoderBase64 {

    private Logger log = Main.getLog();

    private final String fileName;
    private String codedText;

    public EncoderBase64() {
        fileName = "clothes.properties";
    }

    public EncoderBase64(String fileName) {
        this.fileName = fileName;

    }


    public void encode(){

        String string = fileToString();
        log.finest(string);
        Base64.Encoder encoder = Base64.getEncoder();
        codedText = encoder.encodeToString(string.getBytes(StandardCharsets.UTF_8));
//        Base64.Decoder decoder = Base64.getDecoder();
        stringTofile();
        log.finest(codedText);

    }



    private String fileToString() {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), "UTF-8"));
            String line = reader.readLine();
            while (line != null) {
                result.append(line);
                result.append("\n");
                line = reader.readLine();
            }
            return result.toString();
        } catch (IOException e) { e.printStackTrace();}
        return "missed";
    }

    private void stringTofile(){
        String outFileName = "encoded_" + fileName;

        try (PrintStream out = new PrintStream(new FileOutputStream(outFileName))) {
            out.print(codedText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
