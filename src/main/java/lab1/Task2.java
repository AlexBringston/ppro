package lab1;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Stream;

public class Task2 {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String directory = args[0];
        Map<String, Long> map = new TreeMap<>();
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            map = paths.filter(Files::isRegularFile)
                    .map(TaskManager::countElementsInFile).reduce(TaskManager::mergeTwoMaps).orElse(new HashMap<>());
        } catch (IOException exception) {
            System.out.println("Could not find anything in directory " + directory);
        }
        System.out.println(map);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms");
    }
}
