package org.example;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import org.example.exception.AppException;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void givenValidArg_shouldReturnArgInput() {
        String[] arg = {"filePath"};
        assertEquals("filePath", App.extractFilePathFromArgs(arg));
    }

    @Test
    void givenNoArg_shouldThrowIllegalArgException() {
        String[] arg = null;
        var ex = assertThrows(IllegalArgumentException.class,
            () -> App.extractFilePathFromArgs(arg));
        assertEquals("run argument cannot be empty", ex.getMessage());
    }

    @Test
    void givenMoreThan1Arg_shouldThrowIllegalArgException() {
        String[] arg = {"a", "b"};
        var ex = assertThrows(IllegalArgumentException.class,
            () -> App.extractFilePathFromArgs(arg));
        assertEquals("run argument cannot be more than 1", ex.getMessage());
    }

    @Test
    void givenValidPathAndFile_shouldThrowNoException() throws IOException {
        App.verifyValidPath("src/test/resources/input.txt");
    }

    @Test
    void givenFwdSlashPath_shouldThrowNoException() throws IOException {
        App.verifyValidPath("src\\test\\resources\\input.txt");
    }

    @Test
    void givenEmptyPathAndFile_shouldThrowIOException() {
        var ex = assertThrows(IOException.class, () -> App.verifyValidPath(""));
        assertEquals("File path must not be empty", ex.getMessage());
    }

    @Test
    void givenInvalidPathAndFile_shouldThrowIOException() {
        var ex = assertThrows(IOException.class,
            () -> App.verifyValidPath("/etc/qwerty"));
        assertEquals("File or directory does not exist", ex.getMessage());
    }

    @Test
    void givenValidPathNoFile_shouldThrowIOException() {
        var ex = assertThrows(IOException.class,
            () -> App.verifyValidPath("src/test/resources"));
        assertEquals("File path must point to a file", ex.getMessage());
    }

    @Test
    void givenValidFile_shouldShowExpectedWordCount() {
        var standardOut = System.out;
        var outputStreamCaptor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStreamCaptor));
        var expectedResult = """
            test3: 3
            test2: 2
            好: 2
            test9: 1
            test1: 1""";
        App.countWords("src/test/resources/input.txt");
        assertEquals(expectedResult, outputStreamCaptor.toString()
            .trim());
        System.setOut(standardOut);
    }

    @Test
    void givenInvalidFileType_shouldThrowAppException() {
        var ex = assertThrows(AppException.class,
            () -> App.countWords("src/test/resources/invalidFile.txt"));
        assertEquals("E002", ex.getErrorCode());
    }
}
