package de.oetting.idea.idea_xjc_plugin.xjc;

import java.util.ArrayList;
import java.util.List;

public class XjcStartOptions {

    private String xjcCommandName;
    private String xjcCommandDir;
    private List<String> bindingFiles = new ArrayList<>();
    private String targetDir;
    private String targetPackage;
    private String srcFile;
    private String srcDirectory;

    public String getXjcCommandName() {
        return xjcCommandName;
    }

    public void setXjcCommandName(String xjcCommandName) {
        this.xjcCommandName = xjcCommandName;
    }

    public String getXjcCommandDir() {
        return xjcCommandDir;
    }

    public void setXjcCommandDir(String xjcCommandDir) {
        this.xjcCommandDir = xjcCommandDir;
    }

    public List<String> getBindingFiles() {
        return bindingFiles;
    }

    public String getTargetDir() {
        return targetDir;
    }

    public void setTargetDir(String targetDir) {
        this.targetDir = targetDir;
    }

    public String getTargetPackage() {
        return targetPackage;
    }

    public void setTargetPackage(String targetPackage) {
        this.targetPackage = targetPackage;
    }

    public String getSrcFile() {
        return srcFile;
    }

    public void setSrcFile(String srcFile) {
        this.srcFile = srcFile;
    }

    public String getSrcDirectory() {
        return srcDirectory;
    }

    public void setSrcDirectory(String srcDirectory) {
        this.srcDirectory = srcDirectory;
    }

    @Override
    public String toString() {
        return "XjcStartOptions{" +
                "xjcCommandName='" + xjcCommandName + '\'' +
                ", xjcCommandDir='" + xjcCommandDir + '\'' +
                ", bindingFiles=" + bindingFiles +
                ", targetDir='" + targetDir + '\'' +
                ", targetPackage='" + targetPackage + '\'' +
                ", srcFile='" + srcFile + '\'' +
                ", srcDirectory='" + srcDirectory + '\'' +
                '}';
    }

}
