package de.oetting.idea.idea_xjc_plugin.jaxb2mavenplugin;

import de.oetting.mavenmodels.Plugin;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class PluginInformationExtraction {

    public MavenXjcSettings extractInformation(Plugin plugin) {
        MavenXjcSettings settings = new MavenXjcSettings();
        Plugin.Configuration config = plugin.getConfiguration();
        for (org.w3c.dom.Element element: config.getAny()) {
            if (element.getLocalName().equals("generatePackage")) {
                settings.setTargetPackage(element.getTextContent());
            } else if (element.getLocalName().equals("generateDirectory")) {
                settings.setTargetDir(element.getTextContent());
            } else if (element.getLocalName().equals("schemaDirectory")) {
                settings.setSchemaDirectory(element.getTextContent());
            } else if (element.getLocalName().equals("xjbSources")) {
                settings.addBindingFiles(extractChildNodes(element.getChildNodes()));
            } else if (element.getLocalName().equals("schemaIncludes")) {
                settings.addSchemaIncludes(extractChildNodes(element.getChildNodes()));
            } else if (element.getLocalName().equals("bindingIncludes")) {
                settings.addBindingFiles(extractChildNodes(element.getChildNodes()));
            } else if (element.getLocalName().equals("args")) {
                settings.addArgs(extractChildNodes(element.getChildNodes()));
            } else if (element.getLocalName().equals("bindingDirectory")) {
                settings.setBindingDirectory(element.getTextContent());
            }
        }
        return settings;
    }

    private List<String> extractChildNodes(NodeList childNodes) {
        List<String> bindingFiles = new ArrayList<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            if (item.getLocalName() != null)
                bindingFiles.add(item.getTextContent());
        }
        return bindingFiles;
    }
}
