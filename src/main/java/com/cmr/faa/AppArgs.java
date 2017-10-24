package com.cmr.faa;

public class AppArgs {


    private boolean valid = false;

    private String[] arguments;

    public AppArgs(String[] args) {
        this.arguments = args;
        if (args.length == 0)
            valid = true;
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
                .append("There is no set usage for arguments yet so any arguments provided are invalid.")
                .append(lf)
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
}
