package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ParserDirectorTest {

    @Test
    void getAnswerByStack() throws Exception {
        Settings InputFileSettings = new Settings();
        Settings OutputFileSettings = new Settings();

        InputFileSettings.txt_info = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        ParserDirector matchParserDirector = new ParserDirector();
        matchParserDirector.SetBuilder(new ParserByStack());

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);

        String result = """
                7.0
                0.0
                20.0
                50.0
                2.0
                4.0
                """;

        assertEquals(result, OutputFileSettings.txt_info);
    }


    @Test
    void getAnswerByLib() throws Exception {
        Settings InputFileSettings = new Settings();
        Settings OutputFileSettings = new Settings();

        InputFileSettings.txt_info = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        ParserDirector matchParserDirector = new ParserDirector();
        matchParserDirector.SetBuilder(new ParserByLib());

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);

        String result = """
                7.0
                0.0
                20.0
                50.0
                2.0
                4.0
                """;

        assertEquals(result, OutputFileSettings.txt_info);
    }


    @Test
    void getAnswerByRegular() throws Exception {
        Settings InputFileSettings = new Settings();
        Settings OutputFileSettings = new Settings();

        InputFileSettings.txt_info = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        ParserDirector matchParserDirector = new ParserDirector();
        matchParserDirector.SetBuilder(new Regular());

        matchParserDirector.GetAnswer(InputFileSettings, OutputFileSettings);

        String result = """
                7.0
                0.0
                20.0
                50.0
                2.0
                4.0
                """;

        assertEquals(result, OutputFileSettings.txt_info);
    }
}