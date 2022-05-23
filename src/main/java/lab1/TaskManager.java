package lab1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TaskManager {

     static Map<String, Long> countElementsInFile(Path path) {
        Map<String, Long> map = new HashMap<>();
        try (Stream<String> words = Files.lines(path, StandardCharsets.UTF_8)) {
            words.forEach(word -> addWordToMap(word,map));
        } catch (IOException exception) {

        }
        return map;
    }

    static void addWordToMap(String word, Map<String,Long> map) {
        if (map.containsKey(word)) {
            map.replace(word, map.get(word), map.get(word) + 1);
        } else {
            map.put(word, 1L);
        }
    }

    static Map<String, Long> mergeTwoMaps(Map<String, Long> map1, Map<String, Long> map2) {
        Map<String, Long> map3 = new HashMap<>();
        map1.forEach(
                (key, value) -> map3.merge(key, value, Long::sum));
        map2.forEach(
                (key, value) -> map3.merge(key, value, Long::sum));
        return map3;
    }
}
