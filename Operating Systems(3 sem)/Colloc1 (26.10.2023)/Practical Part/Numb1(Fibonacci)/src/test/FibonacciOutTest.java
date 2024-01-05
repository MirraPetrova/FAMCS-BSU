import org.example.FibonacciOut;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class FibonacciOutTest {

    @Test
    public void testFibonacciCalc1() {
        List<Integer> FibonacciNumbs = FibonacciOut.FibonacciCalc(5);
        assertEquals(List.of(0, 1, 1, 2, 3), FibonacciNumbs);
    }

    @Test
    public void testFibonacciCalc2() {
        assertThrows(IllegalArgumentException.class, () -> FibonacciOut.FibonacciCalc(0));
    }

    @Test
    public void testFibonacciCalc3() {
        assertThrows(IllegalArgumentException.class, () -> FibonacciOut.FibonacciCalc(-5));
    }
}

