package duTests;

import du.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.util.*;
import java.util.List;

public class duTests {

    @Test
    void firstTests() {
        du x = new du();
        x.filesSize(List.of("C://Games//Slay the Spire//EULA.txt", "C://Games//Slay the Spire", "C://Games//Slay the Spire//mod-uploader.jar"), true, false, false);
        assertTrue(true);
    }

}
