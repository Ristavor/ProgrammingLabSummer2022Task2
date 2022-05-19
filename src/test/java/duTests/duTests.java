package duTests;

import du.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class duTests {

    static du program = new du();

    static Outer out = new Outer();

    private static String getter(List<String> files, boolean h, boolean c, boolean si) throws IOException {
        program.finder(files, h, c, si, out);
        return out.get();
    }

    static void testerOneFile(String fold, int cnt, boolean h, boolean c, boolean si)
            throws IOException {
        String dirInp = "src/test/resources/input/" + fold + "/";
        String dirExp = "src/test/resources/expected/" + fold + "/";
        for (byte i = 1; i <= cnt; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = List.of(String.format("%s%d", dirInp, i));
            assertEquals(Files.readString(Path.of(exp)), getter(inp, h, c, si));
        }
    }

    static void testerSomeFiles(String fold, int cnt, boolean h, boolean c, boolean si)
            throws IOException {
        String dirInp = "src/test/resources/input/" + fold + "/";
        String dirExp = "src/test/resources/expected/" + fold + "/";
        for (byte i = 1; i <= cnt; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = new java.util.ArrayList<>(List.of());
            File[] inpFiles = new File(String.format("%s%d", dirInp, i)).listFiles();
            assert inpFiles != null;
            for (File j : inpFiles) inp.add(j.toString());
            inp = inp.stream().sorted().collect(Collectors.toList());
            assertEquals(Files.readString(Path.of(exp)), getter(inp, h, c, si));
        }
    }

    @Test
    void oneFileTests() throws IOException {
        testerOneFile("1oneFileTests", 4, false, false, false);
    }

    @Test
    void oneDirTests() throws IOException {
        testerOneFile("2oneDirTests", 2, false, false, false);
    }

    @Test
    void someFilesTests() throws IOException {
        testerSomeFiles("3someFilesTests", 2, false, false, false);
    }

    @Test
    void someDirsTests() throws IOException {
        testerSomeFiles("4someDirsTests", 2, false, false, false);
    }

    @Test
    void dirAndFileTests() throws IOException {
        testerSomeFiles("5dirAndFileTests", 1, false, false, false);
    }

    @Test
    void sumTests() throws IOException {
        testerSomeFiles("6sumTests", 1, false, true, false);
    }

    @Test
    void humanReadableTests() throws IOException {
        testerOneFile("7humanReadableTests", 6, true, false, false);
    }

    @Test
    void humanReadableAndSumTests() throws IOException {
        testerSomeFiles("8humanReadableAndSumTests", 1, true, true, false);
    }

    @Test
    void siTests() throws IOException {
        testerOneFile("9siTests", 1, false, false, true);
    }

    @Test
    void siAndHumanReadableTests() throws IOException {
        testerOneFile("10siAndHumanReadableTests", 4, true, false, true);
    }

    @Test
    void siAndSumTests() throws IOException {
        testerSomeFiles("11siAndSumTests", 1, false, true, true);
    }

    @Test
    void siAndSumAndHumanReadableTests() throws IOException {
        testerSomeFiles("12siAndSumAndHumanReadableTests", 1, true, true, true);
    }

    @Test
    void errorTests() {
        assertThrows(FileNotFoundException.class, () -> program.
                finder(List.of("xxx"), false, false, false, new Outer(true)));
    }

    @Test
    void launcherTests() {
    }


}
