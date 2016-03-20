package de.oetting.idea.idea_xjc_plugin.xjc;

import com.intellij.openapi.diagnostic.Logger;
import de.oetting.idea.idea_xjc_plugin.util.Guard;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import static com.intellij.openapi.diagnostic.Logger.*;

public class XjcExecutor {

    private static final Logger LOGGER = getInstance(XjcExecutor.class);

    public void start(XjcStartOptions options) {
        validate(options);
        String command = buildCommand(options);
        execute(options, command);
    }

    private void validate(XjcStartOptions options) {
        Guard.againstNull("xjc command name is not defined", options.getXjcCommandName());
        Guard.againstNull("Source file is not defined", options.getSrcFile());
        Guard.againstNull("Source directory is undefined", options.getSrcDirectory());
//        Guard.againstNull("xjc folder is not defined", options.getXjcCommandDir());
    }

    private String buildCommand(XjcStartOptions options) {
        String command = options.getXjcCommandName() + " ";
        command += createTargetDirParameter(options);
        command += createTargetPackage(options);
        command += options.getSrcFile() + " ";
        command += createBindingFilesParameters(options);
        return command;
    }

    private String createBindingFilesParameters(XjcStartOptions options) {
        String parameter = "";
        for (String bindingFile: options.getBindingFiles()) {
            parameter += "-b " + bindingFile;
        }
        return parameter + " ";
    }

    private String createTargetDirParameter(XjcStartOptions options) {
        if (options.getTargetDir() != null) {
            return "-d " + options.getTargetDir() + " ";
        }
        return "";
    }

    private String createTargetPackage(XjcStartOptions options) {
        if (options.getTargetPackage() != null) {
            return "-p " + options.getTargetPackage() + " ";
        }
        return "";
    }

    private void execute(XjcStartOptions options, String command) {
        LOGGER.info("Executing xjc with options: " + options);
        LOGGER.info("Using command " + command);
        try {
            File sourcedir = options.getSrcDirectory() == null ? null : new File(options.getSrcDirectory());
            Process exec = Runtime.getRuntime().exec(command, new String[0], sourcedir);
            int exitValue = exec.waitFor();
            logProgramOutput(exec);
            if (exitValue != 0)
                throw new RuntimeException("xjc failed. RC is " + exitValue);
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }
    }

    private void logProgramOutput(Process exec) throws IOException {
        String line;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(exec.getInputStream()) );
        while ((line = in.readLine()) != null)
            LOGGER.error(line);
        in.close();
    }

}
