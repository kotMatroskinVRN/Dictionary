package home.fifteen.dictionary.utils;

import home.fifteen.dictionary.Main;
import home.fifteen.dictionary.dictionary.Sources;
import home.fifteen.dictionary.utils.logger.ColorfulLogger;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

public class EncoderBase64 {

    private final ColorfulLogger LOGGER = ColorfulLogger.getLogger();
    private final String PREFIX = "encoded_";

    private final String fileName;
    private final String outFileName;
    private String codedText;
    private String checkSum;
    private long modifiedTime;

    public EncoderBase64() {
        fileName = "clothes.properties";
        outFileName = Settings.DICTIONARY_DIRECTORY.getProperty() + "/" + PREFIX + fileName;
    }

    public EncoderBase64(String fileName) {
        this.fileName = fileName;
        outFileName = Settings.DICTIONARY_DIRECTORY.getProperty() + "/" + PREFIX + this.fileName;
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

        String string = fileToString(fileName);
        LOGGER.printVerbose(string);
        Base64.Encoder encoder = Base64.getEncoder();
        codedText = encoder.encodeToString(string.getBytes(StandardCharsets.UTF_8));
//        Base64.Decoder decoder = Base64.getDecoder();
        stringToFile(outFileName , getContent());
        LOGGER.printVerbose(codedText);
        updateSourceDictionaryFile();
    }

    private void updateSourceDictionaryFile() {
        File      file = new File(Sources.ENCRYPTED.getFileName());


        try {
            Files.write( file.toPath() ,        "\n".getBytes(), StandardOpenOption.APPEND);
            Files.write( file.toPath() , (PREFIX+fileName).getBytes(), StandardOpenOption.APPEND);
        }
        catch (IOException e) {
            LOGGER.printError(e.getMessage());
        }

        String content = fileToString(Sources.ENCRYPTED.getFileName());

        // Unify lines in EncryptedFiles.txt
        Set<String> lines = new HashSet<>(List.of(content.split("\n")));

        String result = String.join("\n" , lines);
        LOGGER.printVerbose(result);

        stringToFile(Sources.ENCRYPTED.getFileName() , result );

    }


    private String fileToString(String inputFileName) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(inputFileName), StandardCharsets.UTF_8));
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

    private void stringToFile(String output , String content){
        //String        text = getContent();

        try {
            FileWriter     writer = new FileWriter(output, false)	 ;
            (new File(fileName)).deleteOnExit();
            writer.write(content) ; writer.close() ;
        } catch (IOException e) { e.printStackTrace();}

    }

    private String getContent(){
        String result = "Name = " + fileName + "\n" +
                "Modified = " + modifiedTime + "\n" +
                "checkSum = " + checkSum + "\n" +
                "code = " + codedText + "\n";

        return result;
    }

    private void createTextFile(String fileName , String text){
        try {
            FileWriter     writer = new FileWriter(fileName, false)	 ;
            (new File(fileName)).deleteOnExit();
            writer.write(text) ; writer.close() ;
        } catch (IOException e) { e.printStackTrace();}
    }

}
