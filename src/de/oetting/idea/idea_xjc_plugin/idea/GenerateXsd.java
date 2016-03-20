package de.oetting.idea.idea_xjc_plugin.idea;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import de.oetting.idea.idea_xjc_plugin.jaxb2mavenplugin.PluginInformationExtractor;
import de.oetting.idea.idea_xjc_plugin.xjc.XjcExecutor;
import de.oetting.idea.idea_xjc_plugin.xjc.XjcStartOptions;
import de.oetting.mavenmodels.Model;
import de.oetting.mavenmodels.Plugin;

import javax.security.auth.login.Configuration;
import javax.xml.bind.Element;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import java.io.File;

import static com.intellij.openapi.diagnostic.Logger.getInstance;

public class GenerateXsd extends AnAction {

    private static final Logger LOGGER = getInstance(XjcExecutor.class);

    @Override
    public void actionPerformed(AnActionEvent e) {
        if (!isValidState(e)) {
            LOGGER.info("Cannot generate java classes");
            return;
        }
        XjcStartOptions options = collectOptions(e);
        new XjcExecutor().start(options);
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
        collectOptionsFromPom(options, e);
        return options;
    }

    private void collectOptionsFromPom(XjcStartOptions options, AnActionEvent e) {
        VirtualFile baseDir = e.getProject().getBaseDir();
        File pom = new File(baseDir.getPath() + "/pom.xml");
        if (!pom.exists()) {
            LOGGER.info("Cannot read Properties from pom.xml. Values from pom.xml are used to set useful default values");
            return;
        }
        analysePom(options, pom);

    }

    private void analysePom(XjcStartOptions options, File pom) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Model.class);
            JAXBElement element = (JAXBElement) jaxbContext.createUnmarshaller().unmarshal(pom);
            analysePomXml(options, (Model) element.getValue());
        } catch (JAXBException e1) {
            throw new RuntimeException(e1);
        }
    }

    private void analysePomXml(XjcStartOptions options,Model model) {
        for (Plugin plugin: model.getBuild().getPlugins().getPlugin()) {
            if (isXjcPlugin(plugin)) {
                analysePlugin(options, plugin);
            }
        }
    }

    private void analysePlugin(XjcStartOptions options, Plugin plugin) {
        new PluginInformationExtractor().extractInformation(options, plugin);
    }

    private boolean isXjcPlugin(Plugin plugin) {
        return plugin.getArtifactId().equals("jaxb2-maven-plugin");
    }

    private VirtualFile getCurrentlyOpenedFile(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        Document document = editor.getDocument();
        return FileDocumentManager.getInstance().getFile(document);
    }
}
