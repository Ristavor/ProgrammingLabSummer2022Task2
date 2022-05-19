package duTests;

import du.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DuTests {

    static Du program = new Du();

    static Outer out = new Outer();

    private static String getter(List<String> files, boolean h, boolean c, boolean si) throws IOException {
        program.find(files, h, c, si, out);
        return out.get();
    }

    static void testFiles(String folder, int cnt, boolean h, boolean c, boolean si)
            throws IOException {
        for (byte i = 1; i <= cnt; i++) {
            List<String> inp = new ArrayList<>();
            String exp = String.format("src/test/resources/expected/" + folder + "/" + i +".txt");
            File oneInp = new File("src/test/resources/input/" + folder + "/" + i);
            File[] inpFiles = oneInp.listFiles();
            assert inpFiles != null;
            for (File j : inpFiles) inp.add(j.toString());
            inp = inp.stream().sorted().collect(Collectors.toList());
            assertEquals(getter(inp, h, c, si), Files.readString(Path.of(exp)));
        }
    }

    @Test
    void oneFileTests() throws IOException {
        testFiles("1oneFileTests", 4, false, false, false);
    }

    @Test
    void oneDirTests() throws IOException {
        testFiles("2oneDirTests", 2, false, false, false);
    }

    @Test
    void someFilesTests() throws IOException {
        testFiles("3someFilesTests", 2, false, false, false);
    }

    @Test
    void someDirsTests() throws IOException {
        testFiles("4someDirsTests", 2, false, false, false);
    }

    @Test
    void dirAndFileTests() throws IOException {
        testFiles("5dirAndFileTests", 1, false, false, false);
    }

    @Test
    void sumTests() throws IOException {
        testFiles("6sumTests", 1, false, true, false);
    }

    @Test
    void humanReadableTests() throws IOException {
        testFiles("7humanReadableTests", 6, true, false, false);
    }

    @Test
    void humanReadableAndSumTests() throws IOException {
        testFiles("8humanReadableAndSumTests", 1, true, true, false);
    }

    @Test
    void siTests() throws IOException {
        testFiles("9siTests", 1, false, false, true);
    }

    @Test
    void siAndHumanReadableTests() throws IOException {
        testFiles("10siAndHumanReadableTests", 4, true, false, true);
    }

    @Test
    void siAndSumTests() throws IOException {
        testFiles("11siAndSumTests", 1, false, true, true);
    }

    @Test
    void siAndSumAndHumanReadableTests() throws IOException {
        testFiles("12siAndSumAndHumanReadableTests", 1, true, true, true);
    }

    @Test
    void errorTests() {
        assertThrows(FileNotFoundException.class, () -> program.
                find(List.of("xxx"), false, false, false, new Outer(true)));
    }

    @Test
    void launcherTests() {
    }


}
