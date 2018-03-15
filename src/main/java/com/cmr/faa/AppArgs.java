package com.cmr.faa;

public class AppArgs {

    private String[] arguments;

    private boolean valid = true;
    private boolean loadADs = false;
    private boolean loadMakes = false;
    private boolean loadModels = false;
    private boolean loadMapping = false;
    private boolean describe = false;
    private boolean spreadSheet = false;
    private boolean database = true;
    private boolean testUrls = false;

    public AppArgs(String[] args) {
        this.arguments = args;
        for (String arg :
                args) {
            if (!validArg(arg))
                valid = false;
        }
        // If both or neither of database or spreadsheet are defined in the arguments.
        if ((database && spreadSheet) || (!database && !spreadSheet))
            valid = false;
    }

    private boolean validArg(String arg) {
        boolean returnValue = true;
        switch (arg.toLowerCase()) {
            case "-ads":
                loadADs = true;
                break;
            case "-makes":
                loadMakes = true;
                break;
            case "-models":
                loadModels = true;
                break;
            case "-mapping":
                loadMapping = true;
                break;
            case "-all":
                loadADs = loadMakes = loadModels = loadMapping = true;
                break;
            case "-describe":
                describe = true;
                break;
            case "-ss":
                spreadSheet = true;
                break;
            case "-db":
                database = true;
                break;
            case "-testurls":
                testUrls = true;
                break;
            default:
                returnValue = false;
        }
        return returnValue;
    }


    public boolean isValid() {
        return valid;
    }


    public String usageMessage() {
        StringBuilder sb = new StringBuilder();
        String lf = "\n";
        sb.append(lf)
                .append("Usage")
                .append(lf)
                .append("_____")
                .append(lf)
                .append("The following are valid valid flags:")
                .append(lf)
                .append("-ads").append(lf)
                .append("\t Loads the Airworthiness Directives 'ADs' ").append(lf)
                .append("-makes").append(lf)
                .append("\t Loads the 'MAKES' ").append(lf)
                .append("-models").append(lf)
                .append("\t Loads the 'MODELS' ").append(lf)
                .append("-mapping").append(lf)
                .append("\t Loads the Mapping of Models to ADs ").append(lf)
                .append("-all").append(lf)
                .append("\t Loads ALL the above ").append(lf)
                .append(lf)
                .append("---------------")
                .append(lf)
                .append("-describe").append(lf)
                .append("\t Describes the access database").append(lf)
                .append(lf)
                .append("-db").append(lf)
                .append(("\t Loads from the Access Database file (.accdb) file defined in program properties")).append(lf)
                .append("-ss").append(lf)
                .append("\t Loads from spreadsheets defined in program properties")
                .append(lf)
                .append("NOTE: Only one of -db or -ss is allowed.")
                .append(lf)
                .append("-testURLS").append(lf)
                .append("\t Tests each attachment URl")
                .append(lf)
                .append(lf)
                .append("Arguments Given on the commandline")
                .append(lf)
                .append("-----------------------------------------")
                .append(lf)
                .append(argsString(arguments))
                .append(lf);
        sb.trimToSize();
        return sb.toString();
    }

    private String argsString(String[] args) {
        StringBuilder sb = new StringBuilder();
        String space = " ";
        for (String arg :
                args) {
            sb.append(arg).append(space);
        }
        sb.trimToSize();
        return (sb.toString());
    }

    public boolean isLoadADs() {
        return loadADs;
    }

    public boolean isLoadMakes() {
        return loadMakes;
    }

    public boolean isLoadModels() {
        return loadModels;
    }

    public boolean isLoadMapping() {
        return loadMapping;
    }

    public boolean isDescribe() {
        return describe;
    }

    public boolean isSpreadSheet() {
        return spreadSheet;
    }

    public boolean isDatabase() {
        return database;
    }

    public boolean isTestUrls() {
        return testUrls;
    }
}
