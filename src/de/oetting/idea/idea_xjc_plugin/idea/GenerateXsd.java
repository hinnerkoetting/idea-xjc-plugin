package de.oetting.idea.idea_xjc_plugin.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.module.Module;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import de.oetting.idea.idea_xjc_plugin.jaxb2mavenplugin.MavenXjcSettings;
import de.oetting.idea.idea_xjc_plugin.jaxb2mavenplugin.PluginInformationExtraction;
import de.oetting.idea.idea_xjc_plugin.xjc.XjcExecution;
import de.oetting.idea.idea_xjc_plugin.xjc.XjcStartOptions;
import de.oetting.mavenmodels.Model;
import de.oetting.mavenmodels.Plugin;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.intellij.openapi.diagnostic.Logger.getInstance;

public class GenerateXsd extends AnAction {

    private static final Logger LOGGER = getInstance(XjcExecution.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (!isValidState(e)) {
            LOGGER.info("Cannot generate java classes");
            return;
        }
        XjcStartOptions options = collectOptions(e);
        new XjcExecution().start(options);
    }

    private boolean isValidState(AnActionEvent e) {
        final Project project = e.getProject();
        if (project == null) {
            return false;
        }
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        if (editor == null) {
            return false;
        }
        final Document document = editor.getDocument();
        VirtualFile virtualFile = FileDocumentManager.getInstance().getFile(document);
        return virtualFile != null;
    }

    private XjcStartOptions collectOptions(AnActionEvent e) {
        XjcStartOptions options = new XjcStartOptions();
        options.setXjcCommandName("xjc");
        options.setSrcFile(getCurrentlyOpenedFile(e).getName());
        options.setSrcDirectory(getCurrentlyOpenedFile(e).getParent().getPath());
        options.setExecutionDirectory(e.getProject().getBaseDir().getPath());
        collectOptionsFromPom(options, e);
        return options;
    }

    private void collectOptionsFromPom(XjcStartOptions options, AnActionEvent e) {
        Module currentModule = ProjectRootManager.getInstance(e.getProject()).getFileIndex().getModuleForFile(getCurrentlyOpenedFile(e));
        VirtualFile baseDir = currentModule.getModuleFile().getParent();
        File pom = new File(baseDir.getPath() + "/pom.xml");
        if (!pom.exists()) {
            LOGGER.info("Cannot read Properties from pom.xml. Values from pom.xml are used to set useful default values");
            return;
        } else {
            LOGGER.info("Analysing " + pom.getAbsolutePath() + " ...");
        }
        analysePom(options, pom, e);
    }

    private void analysePom(XjcStartOptions options, File pom, AnActionEvent e) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
            JAXBElement element = (JAXBElement) jaxbContext.createUnmarshaller().unmarshal(pom);
            analysePomXml(options, (Model) element.getValue(), e);
        } catch (JAXBException e1) {
            throw new RuntimeException(e1);
        }
    }

    private void analysePomXml(XjcStartOptions options, Model model, AnActionEvent e) {
        if (model.getBuild() != null && model.getBuild().getPlugins() != null) {
            for (Plugin plugin : model.getBuild().getPlugins().getPlugin()) {
                if (isXjcPlugin(plugin)) {
                    MavenXjcSettings mavenSettings = analysePlugin(plugin);
                    applyMavenSettings(options, mavenSettings, e);
                }
            }
        }
    }

    private void applyMavenSettings(XjcStartOptions options, MavenXjcSettings mavenSettings, AnActionEvent e) {
        options.setTargetDir(e.getProject().getBasePath() + "/" + mavenSettings.getTargetDir());
        options.setTargetPackage(mavenSettings.getTargetPackage());
        options.addBindingFiles(findBindingFiles(mavenSettings, e));
        options.addArgs(mavenSettings.getArgs());
    }

    private List<String> findBindingFiles(MavenXjcSettings mavenSettings, AnActionEvent event) {
        List<String> result = new ArrayList<>();
        Path folder = Paths.get(event.getProject().getBasePath() + "/" + mavenSettings.getBindingDirectory());

        for (String bindingFile: mavenSettings.getBindingFiles()) {
            Finder finder = new Finder(folder, bindingFile);
            try {
                Files.walkFileTree(folder, finder);
                result.addAll(finder.getMatchedFiles());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        LOGGER.info("Found the following binding-files" + result.toString());
        return result;
    }

    private MavenXjcSettings analysePlugin(Plugin plugin) {
        return new PluginInformationExtraction().extractInformation(plugin);
    }

    private boolean isXjcPlugin(Plugin plugin) {
        return plugin.getArtifactId().equals("maven-jaxb2-plugin");
    }

    private VirtualFile getCurrentlyOpenedFile(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        Document document = editor.getDocument();
        return FileDocumentManager.getInstance().getFile(document);
    }
}
