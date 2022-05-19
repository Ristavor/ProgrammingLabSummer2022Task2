package du;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.IOException;
import java.util.List;

public class DuLauncher {

        @Option(name = "-h", aliases = "--human-readable" ,metaVar = "human-readable", usage = "print sizes in human readable format (e.g., 1K 234M 2G)")
        private boolean humanReadable;

        @Option(name = "-c", aliases = "--total" ,metaVar = "total", usage = "produce a grand total")
        private boolean total;

        @Option(name = "--si", usage = "use powers of 1000 not 1024")
        private boolean si;

        @Argument(required = true, metaVar = "files[]", usage = "files you want to find size")
        private List<String> files;

        private void launch(String[] args) throws IOException {
            CmdLineParser parser = new CmdLineParser(this);
            try {
                parser.parseArgument(args);
            } catch (CmdLineException e) {
                System.err.println(e.getMessage());
                System.err.println("java -jar du.jar [-h] [-c] [--si] file1 file2 file3 ...");
                parser.printUsage(System.err);
                return;
            }

            Du program = new Du();
            program.find(files, humanReadable, total, si, new Outer(true));
        }

        public static void main(String[] args) throws IOException {
            new DuLauncher().launch(args);
        }
    }
