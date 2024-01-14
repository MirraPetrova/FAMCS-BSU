package org.example;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EncryptionTest {

    private static final String KEY = "1234password4321";

    @Test
    void decryptTxtFileToString() throws Exception {
        String result = Encryption.DecryptFileToString(KEY, "test_encrypted.txt");
        String str = """
                4+3
                1-1
                4*5
                20/2*5
                20/(2*5)
                (2/2-1+2)*2""";

        assertEquals(str, result);
    }

    @Test
    void decryptJsonFileToString() throws Exception {
        String result = Encryption.DecryptFileToString(KEY, "test_encrypted.json");
        String str = """
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

        assertEquals(str, result);
    }

    @Test
    void decryptXmlFileToString() throws Exception {
        String result = Encryption.DecryptFileToString(KEY, "test_encrypted.xml");
        String str = """
                <?xml version="1.0" encoding="UTF-8" standalone="no"?>
                <MathematicalEquations>
                    <equation>4+3</equation>
                    <equation>1-1</equation>
                    <equation>4*5</equation>
                    <equation>20/2*5</equation>
                    <equation>20/(2*5)</equation>
                    <equation>(2/2-1+2)*2</equation>
                </MathematicalEquations>""";

        assertEquals(str, result);
    }
}