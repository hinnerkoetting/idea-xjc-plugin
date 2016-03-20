package de.oetting.idea.idea_xjc_plugin.jaxb2mavenplugin;

import de.oetting.idea.idea_xjc_plugin.xjc.XjcStartOptions;
import de.oetting.mavenmodels.Plugin;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class PluginInformationExtractor {

    public void extractInformation(XjcStartOptions options, Plugin plugin) {
        Plugin.Configuration config = plugin.getConfiguration();
        for (org.w3c.dom.Element element: config.getAny()) {
            if (element.getLocalName().equals("packageName")) {
                options.setTargetPackage(element.getTextContent());
            } else if (element.getLocalName().equals("outputDirectory")) {
                options.setTargetDir(element.getTextContent());
            } else if (element.getLocalName().equals("xjbSources")) {
                options.addBindingFiles(extractBindingFiles(element.getChildNodes()));
            }
        }
    }

    private List<String> extractBindingFiles(NodeList childNodes) {
        List<String> bindingFiles = new ArrayList<>();
        for (int i = 0; i < childNodes.getLength(); i++) {
            Node item = childNodes.item(i);
            bindingFiles.add(item.getTextContent());
        }
        return bindingFiles;
    }
}
