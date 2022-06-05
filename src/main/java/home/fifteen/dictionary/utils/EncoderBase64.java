package home.fifteen.dictionary.utils;

import home.fifteen.dictionary.Main;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.logging.Logger;

public class EncoderBase64 {

    private Logger log = Main.getLog();

    private final String fileName;
    private String codedText;
    private String checkSum;
    private long modifiedTime;

    public EncoderBase64() {
        fileName = "clothes.properties";
    }

    public EncoderBase64(String fileName) {
        this.fileName = fileName;

    }

    public void initFileInfo(){

        File file = new File(fileName);
        modifiedTime = file.lastModified();

        CheckSum sum = new CheckSum(file);
        sum.generate();
        checkSum = sum.getCheckSum();


    }

    public void setCheckSum(String checkSum) {
        this.checkSum = checkSum;
    }

    public void setModifiedTime(long modifiedTime) {
        this.modifiedTime = modifiedTime;
    }

    public void encode(){

        String string = fileToString();
        log.finest(string);
        Base64.Encoder encoder = Base64.getEncoder();
        codedText = encoder.encodeToString(string.getBytes(StandardCharsets.UTF_8));
//        Base64.Decoder decoder = Base64.getDecoder();
        stringToFile();
        log.finest(codedText);

    }



    private String fileToString() {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8));
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

    private void stringToFile(){
        String outFileName = "encoded_" + fileName;
        String        text = getContent();

        try {
            FileWriter     writer = new FileWriter(outFileName, false)	 ;
            (new File(fileName)).deleteOnExit();
            writer.write(text) ; writer.close() ;
        } catch (IOException e) { e.printStackTrace();}

    }

    private String getContent(){
        StringBuilder result = new StringBuilder();
        result.append("Name = ").append(fileName).append("\n");
        result.append("Modified = ").append(modifiedTime).append("\n");
        result.append("checkSum = ").append(checkSum).append("\n");
        result.append("code = ").append(codedText).append("\n");

        return result.toString();
    }

    private void createTextFile(String fileName , String text){
        try {
            FileWriter     writer = new FileWriter(fileName, false)	 ;
            (new File(fileName)).deleteOnExit();
            writer.write(text) ; writer.close() ;
        } catch (IOException e) { e.printStackTrace();}
    }

}
