package com.nbastat.player.utils;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class FileReaderTests {

    @Test
    public void readFileToStringHappyPath() {

        String fileName = "testString.txt";
        String expectedReturnedString = "testing123456\n";

        FileReader fileReader = new FileReader();

        String returnedFile = fileReader.getStringFromFile(fileName);

        assertThat(returnedFile).isEqualTo(expectedReturnedString);

    }

    @Test
    public void readFileToStringGrumpyPathFileNotFound() {

        String fileName = "testStringa.txt";

        FileReader fileReader = new FileReader();

        try {
            fileReader.getStringFromFile(fileName);
            throw new RuntimeException("Test Should Not Reach This Point");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("File Not Found At : " + fileName);
        }


    }


}
