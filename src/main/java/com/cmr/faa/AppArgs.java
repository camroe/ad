package com.cmr.faa;

public class AppArgs {

    private String[] arguments;

    private boolean valid = true;
    private boolean loadADs = false;
    private boolean loadMakes = false;
    private boolean loadModels = false;
    private boolean loadMapping = false;

    public AppArgs(String[] args) {
        this.arguments = args;
        for (String arg :
                args) {
            if (!validArg(arg))
                valid = false;
        }
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
                loadMapping=true;
                break;
            case "-all":
                loadADs = loadMakes = loadModels = loadMapping = true;
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
                .append("\t Loads the Airworthiness Directives 'AD' excel spreadsheet").append(lf)
                .append("-makes").append(lf)
                .append("\t Loads the 'MAKES' excel spreadsheet").append(lf)
                .append("-models").append(lf)
                .append("\t Loads the 'MODELS' excel spreadsheet").append(lf)
                .append("-mapping").append(lf)
                .append("\t Loads the Mapping of Models to ADs from the Access Database file (.accdb)").append(lf)
                .append("-all").append(lf)
                .append("\t Loads ALL the above spreadsheets").append(lf)
                .append("Arguments Given")
                .append(lf)
                .append("---------------")
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

    public void setLoadMapping(boolean loadMapping) {
        this.loadMapping = loadMapping;
    }
}
