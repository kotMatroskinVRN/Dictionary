//package home.fifteen;
//
//import com.google.api.client.http.GenericUrl;
//import com.google.api.client.http.HttpResponse;
//import com.google.api.services.drive.Drive;
//import com.google.api.services.drive.model.File;
//
//import java.io.IOException;
//import java.io.InputStream;
//
//// ...
//
//public class MyClass {
//
//    // ...
//
//    /**
//     * Print a file's metadata.
//     *
//     * @param service Drive API service instance.
//     * @param fileId ID of the file to print metadata for.
//     */
//    private static void printFile(Drive service, String fileId) {
//
//        try {
//            File file = service.files().get(fileId).execute();
//
////            System.out.println("Title: " + file.getTitle());
//            System.out.println("Description: " + file.getDescription());
//            System.out.println("MIME type: " + file.getMimeType());
//        } catch (IOException e) {
//            System.out.println("An error occurred: " + e);
//        }
//    }
//
//    /**
//     * Download a file's content.
//     *
//     * @param service Drive API service instance.
//     * @param file Drive File instance.
//     * @return InputStream containing the file's content if successful,
//     *         {@code null} otherwise.
//     */
//    private static InputStream downloadFile(Drive service, File file) {
////        if (file.getDownloadUrl() != null && file.getDownloadUrl().length() > 0) {
//            try {
//                HttpResponse resp =
//                        service.getRequestFactory().buildGetRequest(new GenericUrl(file.getDownloadUrl()))
//                                .execute();
//                return resp.getContent();
//            } catch (IOException e) {
//                // An error occurred.
//                e.printStackTrace();
//                return null;
//            }
////        } else {
//            // The file doesn't have any content stored on Drive.
////            return null;
////        }
//    }
//
//    // ...
//}