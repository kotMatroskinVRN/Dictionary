package home.fifteen.dictionary.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class CheckSum {

    private String checkSum;
    private final File file;

    public CheckSum(){
        file = new File("DictionarySource/encoded_0104.properties");
    }

    public CheckSum(File file){
        this.file = file;
    }

    public String getCheckSum(){
        return checkSum;
    }

    public void generate()  {


        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            FileInputStream fis = new FileInputStream(file);

            byte[] byteArray = new byte[1024];
            int bytesCount;

            while ((bytesCount = fis.read(byteArray)) != -1)
            {
                digest.update(byteArray, 0, bytesCount);
            };

            fis.close();

            byte[] bytes = digest.digest();



            StringBuilder result = new StringBuilder();

            for (byte aByte : bytes) {
                result.append(Integer.toString((aByte & 0xff) + 0x100, 16).substring(1));
            }

            checkSum =  result.toString();

        } catch (NoSuchAlgorithmException | IOException e) {
            e.printStackTrace();
        }
    }
}
