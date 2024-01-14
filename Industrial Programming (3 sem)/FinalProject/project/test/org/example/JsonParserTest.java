package org.example;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonParserTest {

    @Test
    void ParseStringByParser() throws IOException, ParseException {
        String json_str = """
                {
                  "MathematicalEquations": [
                    {
                      "equation": "4+3"
                    },
                    {
                      "equation": "1-1"
                    },
                    {
                      "equation": "4*5"
                    },
                    {
                      "equation": "20/2*5"
                    },
                    {
                      "equation": "20/(2*5)"
                    },
                    {
                      "equation": "(2/2-1+2)*2"
                    }
                  ]
                }""";

        String str = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        JsonParser parser = new JsonParser();
        assertEquals(str, parser.ParseStringByParser(json_str));
    }

    @Test
    void ParseFileByParser() throws IOException, ParseException {
        String file_name = "test.json";

        String str = """
                4+3
                1-1
                4*5
                20/2+5
                20/(2*5)
                (2/2-1+2)*2
                """;

        JsonParser parser = new JsonParser();
        assertEquals(str, parser.ParseFileByParser(file_name));
    }

    @Test
    void makeJson() {
        String str = """
                7.0
                0.0
                20.0
                50.0
                2.0
                4.0
                """;

        String json_str = "{\"Equation answer\":[{\"answer\":\"7.0\"},{\"answer\":\"0.0\"},{\"answer\":\"20.0\"},{\"answer\":\"50.0\"},{\"answer\":\"2.0\"},{\"answer\":\"4.0\"}]}";

        JsonParser parser = new JsonParser();
        assertEquals(json_str, parser.makeJson(str));
    }

    @Test
    void parseFileByReadingLineByLine() throws Exception {
        String file_name = "test.json";

        String str = """
                4+3
                1-1
                4*5
                20/2+5
                20/(2*5)
                (2/2-1+2)*2
                """;

        JsonParser parser = new JsonParser();
        assertEquals(str, parser.ParseFileByReadingLineByLine(file_name));
    }

    @Test
    void parseStringByReadingLineByLine() throws Exception {
        String json_str = """
                {
                  "MathematicalEquations": [
                    {
                      "equation": "4+3"
                    },
                    {
                      "equation": "1-1"
                    },
                    {
                      "equation": "4*5"
                    },
                    {
                      "equation": "20/2*5"
                    },
                    {
                      "equation": "20/(2*5)"
                    },
                    {
                      "equation": "(2/2-1+2)*2"
                    }
                  ]
                }""";

        String str = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        JsonParser parser = new JsonParser();
        assertEquals(str, parser.ParseStringByReadingLineByLine(json_str));
    }
}