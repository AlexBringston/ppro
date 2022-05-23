package lab1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Task1 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String directory = args[0];
        Map<String, Long> map = new TreeMap<>();
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            paths.filter(Files::isRegularFile)
                    .forEach(path -> countElementsInFile(path,map));
        } catch (IOException exception) {
            System.out.println("Could not find anything in directory " + directory);
        }
        System.out.println(map);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms");
    }

    private static void countElementsInFile(Path path, Map<String, Long> map) {
        try (Stream<String> words = Files.lines(path, StandardCharsets.UTF_8)) {
            words.forEach(word -> addWordToMap(word,map));
        } catch (IOException exception) {
            System.out.println("Could not find a file at: " + path + " to read its content");
        }
    }

    private static void addWordToMap(String word, Map<String,Long> map) {
        if (map.containsKey(word)) {
            map.replace(word, map.get(word), map.get(word) + 1);
        } else {
            map.put(word, 1L);
        }
    }

}
