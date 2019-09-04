package alexander.ivanov.util;

import org.apache.commons.cli.*;

public class CliHelper {
    private static final int TIME_DEFAULT = Constants.TIME_DEFAULT.getIntValue();
    private static final float ACCESSIBILITY_DEFAULT = Constants.ACCESSIBILITY_DEFAULT.getFloatValue();
    private static final int LINES_DEFAULT = Constants.LINES_DEFAULT.getIntValue();

    private static final Options options = new Options();
    private static final HelpFormatter helpFormatter = new HelpFormatter();
    private static StringBuilder usage;
    private static CommandLine cli;

    static {
        buildOptions();
        initHelp();
    }

    private static void buildOptions() {
        options.addOption(buildOption("t", "time", Integer.class,
                "Acceptable response time, by default " + TIME_DEFAULT))
                .addOption(buildOption("a", "accessibility", Float.class,
                        "Minimum allowed accessibility level, by default " + ACCESSIBILITY_DEFAULT))
                .addOption(buildOption("nl", "lines", Integer.class,
                        "The number of analyzed lines, by default " + LINES_DEFAULT))
                .addOption(buildOption("f", "file", Integer.class,
                        "The name of the analyzed file"))
                .addOption(buildHelpOption("h", "help", String.class,
                        "Display this help and exit"));
    }

    private static Option buildOption(String shortName, String longName, Class<?> type, String desc) {
        return Option.builder(shortName)
                .argName(shortName)
                .longOpt(longName)
                .hasArg(true)
                .numberOfArgs(1)
                .type(type)
                //.required(true)
                .desc(desc)
                .build();
    }

    private static Option buildHelpOption(String shortName, String longName, Class<?> type, String desc) {
        return Option.builder(shortName).longOpt(longName).type(type).desc(desc).build();
    }

    private static void initHelp() {
        usage = new StringBuilder();
        usage.append("\n");
        usage.append("java -jar access-log-analyzer.jar [OPTIONS]").append("\n");
        usage.append("cat <file-name> | java -jar access-log-analyzer.jar [OPTIONS]>").append("\n");
        usage.append("\n");
    }

    private static String getValueAsString(String name) {
        return cli.getOptionValue(name, "");
    }

    private static int parseInt(String value, Number defaultValue) {
        int numValue = defaultValue.intValue();
        try {
            if (!value.isEmpty()) {
                numValue = Integer.parseInt(value);
            }
        } catch (Exception e) {
            ErrorHandler.printStackTrace(e.getStackTrace());
            throw new RuntimeException(e.getMessage());
        }
        return numValue;
    }

    private static float parseFloat(String value, Number defaultValue) {
        float numValue = defaultValue.floatValue();
        try {
            if (!value.isEmpty()) {
                numValue = Float.parseFloat(value);
            }
        } catch (Exception e) {
            ErrorHandler.printStackTrace(e.getStackTrace());
            throw new RuntimeException(e.getMessage());
        }
        return numValue;
    }

    public static CommandLine parseArgs(String[] args) {
        try {
            cli = new DefaultParser().parse(options, args);
        } catch (ParseException e) {
            ErrorHandler.printStackTrace(e.getStackTrace());
            throw new RuntimeException(e.getMessage());
        }
        return cli;
    }

    public static int getTimeValue() {
        String valueAsString = getValueAsString("t");
        return parseInt(valueAsString, TIME_DEFAULT);
    }

    public static float getAccessibilityValue() {
        String valueAsString = getValueAsString("a");
        return parseFloat(valueAsString, ACCESSIBILITY_DEFAULT);
    }

    public static int getLinesValue() {
        String valueAsString = getValueAsString("nl");
        return parseInt(valueAsString, LINES_DEFAULT);
    }

    public static String getFileNameValue() {
        return getValueAsString("f");
    }

    public static boolean isHelpExists() {
        return cli.hasOption("h");
    }

    public static void printHelp() {
        helpFormatter.printHelp(
                usage.toString(),
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ OPTIONS ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~",
                options,
                "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
        );
    }
}
