package home.fifteen.dictionary.utils.file;

import java.io.*;

public class FileCopier {
    private final int BUFFER_SIZE = 1024;

    public void copyFileUsingStream(File source, File dest) throws IOException {
        try (
                InputStream is = new FileInputStream(source);
                OutputStream os = new FileOutputStream(dest);
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

    public void copyFileUsingStream(String sourceName, String destName) {
        File source = new File(sourceName);
        File dest   = new File(destName);
        try (
                InputStream is = new FileInputStream(source);
                OutputStream os = new FileOutputStream(dest);
        ) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }catch (IOException ioException){
            System.out.println("Can't get access to File:");
            System.out.println(ioException.getMessage());
        }
    }

}
