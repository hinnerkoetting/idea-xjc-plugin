package de.oetting.idea.idea_xjc_plugin.jaxb2mavenplugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MavenXjcSettings {

    private String targetPackage;
    private String targetDir;
    private String bindingDirectory = "src/main/resources";
    private List<String> bindingFiles = new ArrayList<>();
    private List<String> schemaIncludes = new ArrayList<>();
    private String schemaDirectory = "src/main/resources";
    private List<String> args = new ArrayList<>();

    public String getTargetPackage() {
        return targetPackage;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public List<String> getBindingFiles() {
        return Collections.unmodifiableList(bindingFiles);
    }

    public List<String> getSchemaIncludes() {
        return Collections.unmodifiableList(schemaIncludes);
    }

    public String getSchemaDirectory() {
        return schemaDirectory;
    }

    public List<String> getArgs() {
        return args;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public void setSchemaDirectory(String schemaDirectory) {
        this.schemaDirectory = schemaDirectory;
    }

    public void addBindingFiles(List<String> bindingFiles) {
        this.bindingFiles.addAll(bindingFiles);
    }

    public void addSchemaIncludes(List<String> bindingIncludes) {
        this.schemaIncludes.addAll(schemaIncludes);
    }

    public void addArgs(List<String> args) {
        this.args.addAll(args);
    }

    public String getBindingDirectory() {
        return bindingDirectory;
    }

    public void setBindingDirectory(String bindingDirectory) {
        this.bindingDirectory = bindingDirectory;
    }
}

