package com.github.lexakimov.home_inspector;

import static com.github.lexakimov.home_inspector.util.FileUtil.listFilesOfDirectory;
import static com.github.lexakimov.home_inspector.util.FileUtil.userHome;

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
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Bootstrap {

    public static void main(String[] args) {
        var homeDirectory = userHome();
        var homeDirectoryFiles = listFilesOfDirectory(homeDirectory);

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
            extractor.tryExtractElementsToGroup(homeDirectoryFiles, extractedGroups);
        }

        extractedGroups.sort(Comparator.comparing(ElementGroup::getType));

        printSeparatorLine();
        println("Домашняя директория: " + homeDirectory);
        printSeparatorLine();
        for (ElementGroup extractedGroup : extractedGroups) {
            var groupType = extractedGroup.getType();
            println(groupType.getTitle());
            var description = groupType.getDescription();
            if (description != null) {
                println(description);
            }
            println();
            printElements(extractedGroup.getElements(), 0);
            println();
            printSeparatorLine();
        }

    }

    private static void printElements(List<Element> elements, int indentLevel) {
        for (int i = 0; i < elements.size(); i++) {
            Element element = elements.get(i);
            var file = element.getFile();

            if (indentLevel > 0) {
                println("│");
                if (i + 1 < elements.size()) {
                    print("├");
                } else {
                    print("└");
                }
                for (int j = 0; j < indentLevel; j++) {
                    print("──");
                }
                print(" ");
            }

            if (file.isDirectory()) {
                print("📁");
            } else {
                print("🗒");
            }
            print(" ");
            print(file.getName());
            var elementDescription = element.getDescription();
            if (elementDescription != null) {
                if (indentLevel == 0) {
                    print(" - ");
                    print(elementDescription);
                } else {
                    println();
                    elementDescription = elementDescription.indent(2 * indentLevel);
                    var integer = Integer.valueOf(i);
                    var descriptionStreamIterator = elementDescription.lines().iterator();
                    while (descriptionStreamIterator.hasNext()) {
                        var string = descriptionStreamIterator.next();
                        if (integer + 1 < elements.size()) {
                            print("│");
                        }
                        for (int j = 0; j < indentLevel; j++) {
                            print("  ");
                        }
                        if (descriptionStreamIterator.hasNext()) {
                            println(string);
                        } else {
                            print(string);
                        }
                    }
                }
            }
            println();
            var childElements = element.getChildElements();
            if (!childElements.isEmpty()) {
                printElements(childElements, indentLevel + 1);
            }
        }
    }

    private static void printSeparatorLine() {
        println(
            "━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━");
    }

    private static void println() {
        System.out.println();
    }

    private static void println(String string) {
        System.out.println(string);
    }

    private static void print(String string) {
        System.out.print(string);
    }
}
