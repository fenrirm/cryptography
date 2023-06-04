import org.example.Main;
import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

public class ElGamalTest {
    static final String[] arr = {"4", "6", "8", "9", "10", "12", "14", "15", "16", "18", "20", "21", "22",
            "24", "25", "26", "27", "28","30", "32", "33", "34", "35", "36", "38",
            "39", "40", "42", "44", "45", "46", "48", "49", "50", "51", "52", "54"};

    public static final int NUMBER_OF_TESTS = arr.length;

    @Test
    public void run() {
        for(int i = 0; i < NUMBER_OF_TESTS; i++){
            BigInteger num = new BigInteger(arr[i]);
            Assert.assertEquals(num, new Main().run(num));
        }
    }
}
