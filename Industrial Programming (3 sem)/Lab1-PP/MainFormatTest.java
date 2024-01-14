package org.example;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
//import java.math.BigInteger;
import static org.junit.Assert.*;

public class MainFormatTest {
    @Test
    public void calculate() {
        MainFormat a = new  MainFormat();
        double result = a.calculate(1.5,0.001);
        assertEquals("2,129277", result);
    }
    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void main() {

    }
}