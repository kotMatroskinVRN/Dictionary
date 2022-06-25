package home.fifteen.dictionary.utils;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.regex.Pattern;

public class DecoderBase64 {

    private final String SOURCE;

    private String name;
    private String codedText;
    private String checkSum;
    private long modifiedTime;
    private String decodedString;

    public DecoderBase64() {
        SOURCE = "encoded_0104.properties";
    }

    public DecoderBase64(String SOURCE) {
        this.SOURCE = SOURCE;
    }

    public void init(){
        getFileData();
    }

    public String getName() {
        return name;
    }

    public String getCheckSum() {
        return checkSum;
    }

    public long getModifiedTime() {
        return modifiedTime;
    }

    public String getDecodedString() {
        return decodedString;
    }



    public void decode(){
        Base64.Decoder decoder = Base64.getDecoder();
        byte[] decodedBytes = decoder.decode(codedText);
        decodedString = new String(decodedBytes , StandardCharsets.UTF_8);

    }

    private void getFileData() {

        try {
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader( new FileInputStream(SOURCE), StandardCharsets.UTF_8 )
            );
            String line = reader.readLine();
            while (line != null) {
                parseLine(line);
                line = reader.readLine();
            }
        } catch (IOException e) { e.printStackTrace();}
    }

    private void parseLine(String line ){
        String splitter = "=";

        if(Pattern.compile(splitter).matcher(line).find()){
            String[] array = line.split(splitter);
            String name  = array[0].trim();
            String value = array[1].trim();

            switch(name){
                case "Name":
                    this.name = value;
                    break;
                case "Modified":
                    modifiedTime = Long.parseLong(value);
                    break;
                case "checkSum":
                    checkSum = value;
                    break;
                case "code":
                    codedText = value;
                    break;
            }

        }
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("DecoderBase64{\n");

        String format = "\t%-20s%s\n";

        String line;
        line = String.format(format , "Source" , SOURCE);
        result.append(line);
        line = String.format(format , "File Name" , name);
        result.append(line);
        line = String.format(format , "MD5SUM" , checkSum);
        result.append(line);
        Date date = new Date(modifiedTime);
        line = String.format(format , "Modified Time" , date);
        result.append(line);

        result.append("}\n");
        return result.toString();
    }
}
