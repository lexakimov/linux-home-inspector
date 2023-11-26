package com.github.lexakimov.home_inspector;

import com.github.lexakimov.home_inspector.element.Element;
import com.github.lexakimov.home_inspector.element.ElementGroup;
import com.github.lexakimov.home_inspector.element.group_extractors.CacheElementGroupExtractor;
import com.github.lexakimov.home_inspector.element.group_extractors.ConfigsElementGroupExtractor;
import com.github.lexakimov.home_inspector.element.group_extractors.ElementGroupExtractor;
import com.github.lexakimov.home_inspector.element.group_extractors.JavaElementGroupExtractor;
import com.github.lexakimov.home_inspector.element.group_extractors.KubernetesElementGroupExtractor;
import com.github.lexakimov.home_inspector.element.group_extractors.LocalElementGroupExtractor;
import com.github.lexakimov.home_inspector.element.group_extractors.UnknownElementGroupExtractor;
import com.github.lexakimov.home_inspector.element.group_extractors.XdgDirsElementGroupExtractor;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Objects;

public class Bootstrap {

    public static void main(String[] args) {
        var homeDirectoryPath = System.getProperty("user.home");
        var homeDirectoryFile = new File(homeDirectoryPath);
        var listFiles = homeDirectoryFile.listFiles();
        Objects.requireNonNull(listFiles);

        var files = new LinkedList<File>();
        Collections.addAll(files, listFiles);

        var extractors = new ArrayList<ElementGroupExtractor>();
        extractors.add(new CacheElementGroupExtractor());
        extractors.add(new ConfigsElementGroupExtractor());
        extractors.add(new JavaElementGroupExtractor());
        extractors.add(new KubernetesElementGroupExtractor());
        extractors.add(new LocalElementGroupExtractor());
        extractors.add(new XdgDirsElementGroupExtractor());
        extractors.add(new UnknownElementGroupExtractor());

        var extractedGroups = new ArrayList<ElementGroup>();

        for (ElementGroupExtractor extractor : extractors) {
            extractor.tryExtractElementsToGroup(files, extractedGroups);
        }

        for (ElementGroup extractedGroup : extractedGroups) {
            System.out.println(extractedGroup.getType());
            for (Element element : extractedGroup.getElements()) {
                System.out.println(element.getFile().getName());
            }
            System.out.println("------------------------------------------------------------------------------------");
        }

    }


}
