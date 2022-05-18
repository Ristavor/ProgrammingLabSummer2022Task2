package duTests;

import du.*;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class duTests {

    static du program = new du();

    static Outer out = new Outer();
    private static String getter(List<String> files, boolean h, boolean c, boolean si) throws IOException {
        program.finder(files, h, c, si, out);
        return out.get();
    }

    @Test
    void oneFileTests() throws IOException {
        String dirInp = "src/test/resources/input/1oneFileTests/";
        String dirExp = "src/test/resources/expected/1oneFileTests/";
        boolean h = false;
        boolean c = false;
        boolean si = false;
        for (byte i = 1; i <= 4; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = List.of(String.format("%s%d", dirInp, i));
            assertEquals(Files.readString(Path.of(exp)), getter(inp, h, c, si));
        }
    }

    @Test
    void oneDirTests() throws IOException {
        String dirInp = "src/test/resources/input/2oneDirTests/";
        String dirExp = "src/test/resources/expected/2oneDirTests/";
        boolean h = false;
        boolean c = false;
        boolean si = false;
        for (byte i = 1; i <= 2; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = List.of(String.format("%s%d", dirInp, i));
            assertEquals(Files.readString(Path.of(exp)), getter(inp, h, c, si));
        }
    }

    @Test
    void someFilesTests() throws IOException {
        String dirInp = "src/test/resources/input/3someFilesTests/";
        String dirExp = "src/test/resources/expected/3someFilesTests/";
        boolean h = false;
        boolean c = false;
        boolean si = false;
        for (byte i = 1; i <= 2; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = new java.util.ArrayList<>(List.of());
            File[] inpFiles = new File(String.format("%s%d", dirInp, i)).listFiles();
            assert inpFiles != null;
            for (File j : inpFiles) inp.add(j.toString());
            assertEquals(Files.readString(Path.of(exp)), getter(inp, h, c, si));
        }
    }

    @Test
    void someDirsTests() throws IOException {
        String dirInp = "src/test/resources/input/4someDirsTests/";
        String dirExp = "src/test/resources/expected/4someDirsTests/";
        boolean h = false;
        boolean c = false;
        boolean si = false;
        for (byte i = 1; i <= 1; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = new java.util.ArrayList<>(List.of());
            File[] inpFiles = new File(String.format("%s%d", dirInp, i)).listFiles();
            assert inpFiles != null;
            for (File j : inpFiles) inp.add(j.toString());
            String s = getter(inp, h, c, si);
            assertEquals(Files.readString(Path.of(exp)), s);
        }
    }

    @Test
    void dirAndFileTests() throws IOException {
        String dirInp = "src/test/resources/input/5dirAndFileTests/";
        String dirExp = "src/test/resources/expected/5dirAndFileTests/";
        boolean h = false;
        boolean c = false;
        boolean si = false;
        for (byte i = 1; i <= 1; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = new java.util.ArrayList<>(List.of());
            File[] inpFiles = new File(String.format("%s%d", dirInp, i)).listFiles();
            assert inpFiles != null;
            for (File j : inpFiles) inp.add(j.toString());
            String s = getter(inp, h, c, si);
            assertEquals(Files.readString(Path.of(exp)), s);
        }
    }

    @Test
    void sumTests() throws IOException {
        String dirInp = "src/test/resources/input/6sumTests/";
        String dirExp = "src/test/resources/expected/6sumTests/";
        boolean h = false;
        boolean c = true;
        boolean si = false;
        for (byte i = 1; i <= 1; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = new java.util.ArrayList<>(List.of());
            File[] inpFiles = new File(String.format("%s%d", dirInp, i)).listFiles();
            assert inpFiles != null;
            for (File j : inpFiles) inp.add(j.toString());
            String s = getter(inp, h, c, si);
            assertEquals(Files.readString(Path.of(exp)), s);
        }
    }

    @Test
    void humanReadableTests() throws IOException {
        String dirInp = "src/test/resources/input/7humanReadableTests/";
        String dirExp = "src/test/resources/expected/7humanReadableTests/";
        boolean h = true;
        boolean c = false;
        boolean si = false;
        for (byte i = 1; i <= 6; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = List.of(String.format("%s%d", dirInp, i));
            assertEquals(Files.readString(Path.of(exp)), getter(inp, h, c, si));
        }
    }

    @Test
    void humanReadableAndSumTests() throws IOException {
        String dirInp = "src/test/resources/input/8humanReadableAndSumTests/";
        String dirExp = "src/test/resources/expected/8humanReadableAndSumTests/";
        boolean h = true;
        boolean c = true;
        boolean si = false;
        for (byte i = 1; i <= 1; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = new java.util.ArrayList<>(List.of());
            File[] inpFiles = new File(String.format("%s", dirInp)).listFiles();
            assert inpFiles != null;
            for (File j : inpFiles) inp.add(j.toString());
            String s = getter(inp, h, c, si);
            assertEquals(Files.readString(Path.of(exp)), s);
        }
    }

    @Test
    void siTests() throws IOException {
        String dirInp = "src/test/resources/input/9siTests/";
        String dirExp = "src/test/resources/expected/9siTests/";
        boolean h = false;
        boolean c = false;
        boolean si = true;
        for (byte i = 1; i <= 4; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = List.of(String.format("%s%d", dirInp, i));
            assertEquals(Files.readString(Path.of(exp)), getter(inp, h, c, si));
        }
    }

    @Test
    void siAndHumanReadableTests() throws IOException {
        String dirInp = "src/test/resources/input/10siAndHumanReadableTests/";
        String dirExp = "src/test/resources/expected/10siAndHumanReadableTests/";
        boolean h = true;
        boolean c = false;
        boolean si = true;
        for (byte i = 1; i <= 4; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = List.of(String.format("%s%d", dirInp, i));
            assertEquals(Files.readString(Path.of(exp)), getter(inp, h, c, si));
        }
    }

    @Test
    void siAndSumTests() throws IOException {
        String dirInp = "src/test/resources/input/11siAndSumTests/";
        String dirExp = "src/test/resources/expected/11siAndSumTests/";
        boolean h = false;
        boolean c = true;
        boolean si = true;
        for (byte i = 1; i <= 1; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = new java.util.ArrayList<>(List.of());
            File[] inpFiles = new File(String.format("%s", dirInp)).listFiles();
            assert inpFiles != null;
            for (File j : inpFiles) inp.add(j.toString());
            String s = getter(inp, h, c, si);
            assertEquals(Files.readString(Path.of(exp)), s);
        }
    }

    @Test
    void siAndSumAndHumanReadableTests() throws IOException {
        String dirInp = "src/test/resources/input/12siAndSumAndHumanReadableTests/";
        String dirExp = "src/test/resources/expected/12siAndSumAndHumanReadableTests/";
        boolean h = true;
        boolean c = true;
        boolean si = true;
        for (byte i = 1; i <= 1; i++) {
            String exp = String.format("%s%d.txt", dirExp, i);
            List<String> inp = new java.util.ArrayList<>(List.of());
            File[] inpFiles = new File(String.format("%s", dirInp)).listFiles();
            assert inpFiles != null;
            for (File j : inpFiles) inp.add(j.toString());
            String s = getter(inp, h, c, si);
            assertEquals(Files.readString(Path.of(exp)), s);
        }
    }
}
