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
import de.oetting.idea.idea_xjc_plugin.xjc.XjcExecutor;
import de.oetting.idea.idea_xjc_plugin.xjc.XjcStartOptions;

public class GenerateXsd extends AnAction {

    private static final Logger LOGGER = Logger.getInstance(GenerateXsd.class);

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
        return options;
    }

    private VirtualFile getCurrentlyOpenedFile(AnActionEvent e) {
        Project project = e.getProject();
        Editor editor = FileEditorManager.getInstance(project).getSelectedTextEditor();
        Document document = editor.getDocument();
        return FileDocumentManager.getInstance().getFile(document);
    }
}
