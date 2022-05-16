package duTests;

import du.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.*;
import java.util.List;

public class duTests {

    @Test
    void firstTests() {
        du x = new du();
        x.size(List.of("C://Games//Slay the Spire//EULA.txt", "C://Games//Slay the Spire//config.json"), false, true, false);
        assertEquals(1, 2);
    }

}
