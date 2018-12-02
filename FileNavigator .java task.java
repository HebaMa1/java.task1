import java.io.File;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileNavigator {

    // Method to Navigate Throw the Directory to get List of Files
    public void listfiles(String directoryName, List<File> files) {
        File directory = new File(directoryName);

        // Get all files from a directory.
        File[] fList = directory.listFiles();
        if (fList != null)
            for (File file : fList) {
                if (file.isFile()) {
                    files.add(file);
                } else if (file.isDirectory()) {
                    // Recursion Call if Found Directory Not File
                    listfiles(file.getAbsolutePath(), files);
                }
            }
    }

    public Map<String, String> getFileContent(File filePath) throws IOException {

        // Hash Map Hold The <File Path : File Content>
        Map<String, String> stringStringMap = new HashMap<>();

        //Check if you have Read Access
        if (filePath.canRead()) {
            String data = new String(Files.readAllBytes(Paths.get(filePath.getAbsolutePath())));
            stringStringMap.put(filePath.getAbsolutePath(), data);
        }
        return stringStringMap;
    }

    // Method to get File Path from File Name
    public String getFileExtension(String filename) {


        int dotIndex = -1;


        String extension = null;

        if (filename != null && filename.length() > 0)
            dotIndex = filename.lastIndexOf(".");
        if (dotIndex != -1) {
            extension = filename.substring(dotIndex + 1, filename.length());
        }
        return extension;
    }

    public static void main(String[] args) {
        String extension = null;
        String searchWord = null;
        if (args.length >= 2) {
            extension = args[0];
            searchWord = args[1];
        } else {
            System.out.println("Missing Search Word or Extension");
            System.out.println("USAGE : java -jar appName [EXTENSION} [SEARCH WORD]");
            return;
        }

        String path = new File("").getAbsolutePath();


        FileNavigator fileNavigator = new FileNavigator();
        List<File> files = new ArrayList<>();
        List<String> validPathes = new ArrayList<>();

        fileNavigator.listfiles(path, files);
        try {
            for (File file : files) {
                String fileExtension = fileNavigator.getFileExtension(file.getAbsolutePath());
                if (fileExtension.toLowerCase().equals(extension.toLowerCase())) {
                    Map<String, String> stringStringMap = fileNavigator.getFileContent(file);
                    for (String key : stringStringMap.keySet()) {
                        String content = stringStringMap.get(key);
                        if (content.contains(searchWord)) {
                            validPathes.add(key);
                        }
                    }
                }

            }

            if (validPathes.size() <= 0)
                System.out.println("No file was found");
            else
                for (String validPath : validPathes) {
                    System.out.println(validPath);
                }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
