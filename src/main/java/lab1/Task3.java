package lab1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Stream;

public class Task3{
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        String directory = args[0];
        Map<String, Long> map = new TreeMap<>();
        try (Stream<Path> paths = Files.walk(Paths.get(directory))) {
            ThreadPoolExecutor executor =
                    (ThreadPoolExecutor) Executors.newFixedThreadPool(2);
            map = executor.submit(() -> paths.filter(Files::isRegularFile)
                    .map(TaskManager::countElementsInFile)).get().reduce(TaskManager::mergeTwoMaps).orElse(new HashMap<>());
            executor.shutdown();
        } catch (IOException exception) {
            System.out.println("Could not find anything in directory " + directory);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(map);
        System.out.println("Time elapsed: " + (System.currentTimeMillis() - startTime) + "ms");
    }


}
