package du;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class duLauncher {

        @Option(name = "-h", metaVar = "human-readable", usage = "print sizes in human readable format (e.g., 1K 234M 2G)")
        private boolean humanReadable;

        @Option(name = "-c", metaVar = "total", usage = "produce a grand total")
        private boolean total;

        @Option(name = "--si", usage = "use powers of 1000 not 1024")
        private boolean si;

        @Argument(required = true, metaVar = "files[]", usage = "files you want to find size")
        private List<String> files;

        private void launch(String[] args) {
            CmdLineParser parser = new CmdLineParser(this);
            try {
                parser.parseArgument(args);
                for (String i : files) if (!new File(i).exists())
                    throw new FileNotFoundException("There is no such file/files or directory/directories");
            } catch (CmdLineException | FileNotFoundException e) {
                System.err.println(e.getMessage());
                System.err.println("java -jar du.jar [-h] [-c] [--si] file1 file2 file3 ...");
                parser.printUsage(System.err);
                return;
            }

            du program = new du();
            program.filesSize(files, humanReadable, total, si);
        }

        public static void main(String[] args) {
            new duLauncher().launch(args);
        }
    }
