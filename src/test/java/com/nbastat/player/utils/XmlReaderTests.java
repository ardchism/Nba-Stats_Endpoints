package com.nbastat.player.utils;

import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.assertThat;

public class XmlReaderTests {

    @Test
    public void readFileToStringHappyPath() throws URISyntaxException, IOException {

        String fileName = "testString.txt";
        String expectedReturnedString = "testing123456\n";

        XmlReader xmlReader = new XmlReader();

        String returnedFile = xmlReader.readXmlFromFile(fileName);

        assertThat(returnedFile).isEqualTo(expectedReturnedString);

    }

    @Test
    public void readFileToStringGrumpyPathFileNotFound() {

        String fileName = "testStringa.txt";

        XmlReader xmlReader = new XmlReader();

        try {
            xmlReader.readXmlFromFile(fileName);
            throw new RuntimeException("Test Should Not Reach This Point");
        } catch(Exception e) {
            assertThat(e.getMessage()).isEqualTo("File Not Found At : " + fileName);
        }


    }


}
