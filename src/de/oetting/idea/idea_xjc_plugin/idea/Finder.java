package de.oetting.idea.idea_xjc_plugin.idea;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class Finder implements FileVisitor<Path> {

    private final Path folder;
    private final String bindingFile;
    private final List<String> matchedFiles = new ArrayList<>();

    public Finder(Path folder, String bindingFile) {
        this.folder = folder;
        this.bindingFile = bindingFile;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path o, BasicFileAttributes basicFileAttributes) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path o, BasicFileAttributes basicFileAttributes) throws IOException {
        String globPattern = "glob:" + bindingFile;
        PathMatcher matcher = FileSystems.getDefault().getPathMatcher(globPattern);
        if (matcher.matches(folder.relativize(o))) {
            matchedFiles.add(o.toAbsolutePath().toString());
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path o, IOException e) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path o, IOException e) throws IOException {
        return FileVisitResult.CONTINUE;
    }

    public List<String> getMatchedFiles() {
        return matchedFiles;
    }

}
