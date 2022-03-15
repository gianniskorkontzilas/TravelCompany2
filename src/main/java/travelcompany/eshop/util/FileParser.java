package travelcompany.eshop.util;


import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Parses a file with a collection of objects (of any type)
 */
public class FileParser {
     public static List<String> load(String filePath){
         // method could also not be static, but it's OK for now
         List<String> lines = new ArrayList<>();
         try (BufferedReader reader = Files.newBufferedReader(Path.of(filePath))){
             String line;
             while ((line = reader.readLine()) != null){
                 lines.add(line);
             }
         } catch (IOException ioException) {
             ioException.printStackTrace();
         }
         return lines;
     }
}
