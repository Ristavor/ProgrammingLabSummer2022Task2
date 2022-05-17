package du;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;

import java.util.Arrays;
import java.util.List;

public class duLauncher {

        @Option(name = "-h", metaVar = "format", usage = "Turn into more easy-reading format")
        private boolean format;

        @Option(name = "-c", metaVar = "sum", usage = "Find sum of sizes")
        private boolean sum;

        @Option(name = "--si", metaVar = "inBits", usage = "Output in Bits")
        private boolean inBits;

        @Argument(required = true, metaVar = "fileNames", usage = "Files for find size")
        private List<String> fileNamess;

        private void launch(String[] args) {
            CmdLineParser parser = new CmdLineParser(this);
            try {
                parser.parseArgument(args);
            } catch (CmdLineException e) {
                System.err.println(e.getMessage());
                System.err.println("java -jar split.jar [-d] [-l num|-c num|-n num] [-o ofile] file");
                parser.printUsage(System.err);
                return;
            }

            du duer = new du();
            duer.filesSize(fileNamess, format, sum, inBits);
        }
        public static void main(String[] args) {
            new duLauncher().launch(args);
        }
    }
