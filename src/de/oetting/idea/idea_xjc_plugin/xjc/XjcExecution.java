package de.oetting.idea.idea_xjc_plugin.xjc;

import com.intellij.openapi.diagnostic.Logger;
import de.oetting.idea.idea_xjc_plugin.util.Guard;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.intellij.openapi.diagnostic.Logger.*;

public class XjcExecution {

    private static final Logger LOGGER = getInstance(XjcExecution.class);

    public void start(XjcStartOptions options) {
        validate(options);
        createMissingFolders(options);
        List<String> command = buildCommand(options);
        execute(options, command);
    }

    private void createMissingFolders(XjcStartOptions options) {
        if (options.getTargetDir() != null) {
            File targetDirectory = new File(options.getTargetDir());
            LOGGER.info("Creating target directory " + targetDirectory.getAbsolutePath());
            if (!targetDirectory.exists()) {
                boolean created = targetDirectory.mkdirs();
                if (!created) {
                    LOGGER.warn("Could not create target directory " + options.getTargetDir());
                }
            }
        }
    }

    private void validate(XjcStartOptions options) {
        Guard.againstNull("xjc command name is not defined", options.getXjcCommandName());
        Guard.againstNull("Source file is not defined", options.getSrcFile());
        Guard.againstNull("Source directory is undefined", options.getSrcDirectory());
//        Guard.againstNull("xjc folder is not defined", options.getXjcCommandDir());
    }

    private List<String> buildCommand(XjcStartOptions options) {
        List<String> command = new ArrayList<>(Arrays.asList(options.getXjcCommandName()));
        command.addAll(createTargetDirParameter(options));
        command.addAll(createTargetPackage(options));
        command.addAll(createArgs(options));
        command.addAll(createSourceFileArgument(options));
        command.addAll(createBindingFilesParameters(options));
        return command;
    }

    private List<String> createTargetDirParameter(XjcStartOptions options) {
        if (options.getTargetDir() != null) {
            return Arrays.asList("-d", options.getTargetDir());
        }
        return Collections.emptyList();
    }

    private List<String> createTargetPackage(XjcStartOptions options) {
        if (options.getTargetPackage() != null) {
            return Arrays.asList("-p", options.getTargetPackage());
        }
        return Collections.emptyList();
    }

    private List<String> createArgs(XjcStartOptions options) {
        return options.getArgs();
    }

    private List<String> createSourceFileArgument(XjcStartOptions options) {
        String result = "";
        result += options.getSrcDirectory() != null ? options.getSrcDirectory() + "/" : "";
        return  Collections.singletonList(result + options.getSrcFile());
    }

    private List<String> createBindingFilesParameters(XjcStartOptions options) {
        List<String> bindingFiles = new ArrayList<>();
        for (String bindingFile: options.getBindingFiles()) {
            bindingFiles.add("-b");
            bindingFiles.add(bindingFile);
        }
        return bindingFiles;
    }

    private void execute(XjcStartOptions options, List<String> command) {
        LOGGER.info("Executing xjc with options: " + options);
        try {
            File executionDir = new File(options.getExecutionDirectory());
            execute(command, executionDir);
        } catch (Exception e1) {
            throw new RuntimeException(e1);
        }
    }

    private void execute(List<String> command, File sourcedir) throws IOException, InterruptedException {
        LOGGER.info("Using command " + command.toString());
        Process exec = Runtime.getRuntime().exec(command.toArray(new String[command.size()]), new String[0], sourcedir);
        logProgramOutput(exec);
        int exitValue = exec.waitFor();
        if (exitValue != 0)
            throw new RuntimeException("xjc failed. RC is " + exitValue);
    }

    private void logProgramOutput(Process exec) throws IOException {
        String line;
        BufferedReader in = new BufferedReader(
                new InputStreamReader(exec.getInputStream()) );
        while ((line = in.readLine()) != null)
            LOGGER.info(line);
        in.close();
    }

}
