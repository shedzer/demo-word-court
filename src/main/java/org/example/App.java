package org.example;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.extern.slf4j.Slf4j;
import org.example.exception.AppErrorMapping;
import org.example.exception.AppException;

@Slf4j
public class App {

    public static void main(String[] args) throws Exception {

        var filePath = extractFilePathFromArgs(args);
        verifyValidPath(filePath);
        countWords(filePath);

    }

    public static String extractFilePathFromArgs(String[] args) {

        if (null == args || args.length == 0) {
            throw new IllegalArgumentException("run argument cannot be empty");
        } else if (args.length > 1) {
            throw new IllegalArgumentException("run argument cannot be more than 1");
        }

        return args[0];
    }

    public static void verifyValidPath(String filePath) throws IOException {

        if (null == filePath || filePath.trim().isEmpty()) {
            throw new IOException("File path must not be empty");
        }

        // convert slash to OS independent
        filePath = filePath.replace("\\", "/");
        var file = new File(filePath);
        if (!file.exists()) {
            throw new IOException("File or directory does not exist");
        }

        if (file.isDirectory()) {
            throw new IOException("File path must point to a file");
        }

    }

    public static void countWords(String filePath) {
        try (Stream<String> lines = Files.lines(Paths.get(filePath))) {
            Map<String, Long> wordCountMap = lines
                //.parallel() // enable only if file size is large
                .flatMap(line -> Arrays.stream(line.split("\\s+")))
                .map(String::toLowerCase)
                // use below only if using parallel
                //   .collect(Collectors.groupingByConcurrent(word -> word, Collectors.counting()));
                .collect(Collectors.groupingBy(w -> w, Collectors.counting()));

            wordCountMap.entrySet()
                .stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .forEach(e -> log.info(e.getKey() + ": " + e.getValue()));
        } catch (UncheckedIOException u) {
            throw new AppException(AppErrorMapping.E002.name(),
                AppErrorMapping.E002.getErrorMessage());
        } catch (IOException io) {
            io.printStackTrace();
            throw new AppException(AppErrorMapping.E001.name(),
                AppErrorMapping.E001.getErrorMessage());
        }
    }
}
