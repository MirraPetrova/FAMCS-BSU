package org.example;

import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class XmlTest {

    @org.junit.jupiter.api.Test
    void ParseStringByDOM() throws IOException, ParseException, ParserConfigurationException, SAXException {
        String str = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        String xml_str = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <MathematicalEquations>
                    <equation>4+3</equation>
                    <equation>1-1</equation>
                    <equation>4*5</equation>
                    <equation>20/2*5</equation>
                    <equation>20/(2*5)</equation>
                    <equation>(2/2-1+2)*2</equation>
                </MathematicalEquations>""";

        Xml parser = new Xml();
        assertEquals(parser.ParseStringByDOM(xml_str), str);
    }

    @Test
    void ParseFileByDOM() throws IOException, ParseException, ParserConfigurationException, SAXException {
        String file_name = "test.xml";
        String str = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        Xml parser = new Xml();
        assertEquals(str, parser.ParseFileByDOM(file_name));
    }

    @Test
    void makeXml() throws ParserConfigurationException, TransformerException {
        String str = """
                7.0
                0.0
                20.0
                50.0
                2.0
                4.0
                """;

        String xml_str = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><EquationAnswer><answer>7.0</answer><answer>0.0</answer><answer>20.0</answer><answer>50.0</answer><answer>2.0</answer><answer>4.0</answer></EquationAnswer>";

        Xml parser = new Xml();
        assertEquals(parser.makeXml(str), xml_str);
    }

    @Test
    void parseFileByReadingLineByLine() throws IOException {
        String file_name = "test.xml";
        String str = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        Xml parser = new Xml();
        assertEquals(str, parser.ParseFileByReadingLineByLine(file_name));
    }

    @Test
    void parseStringByReadingLineByLine() throws Exception {
        String str = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2
                """;

        String xml_str = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <MathematicalEquations>
                    <equation>4+3</equation>
                    <equation>1-1</equation>
                    <equation>4*5</equation>
                    <equation>20/2*5</equation>
                    <equation>20/(2*5)</equation>
                    <equation>(2/2-1+2)*2</equation>
                </MathematicalEquations>""";

        Xml parser = new Xml();
        assertEquals(parser.ParseStringByReadingLineByLine(xml_str), str);
    }
}