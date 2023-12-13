package com.github.lexakimov.home_inspector.element.group_extractors;

import com.github.lexakimov.home_inspector.comparators.IsDirectoryFileComparator;
import com.github.lexakimov.home_inspector.comparators.IsHiddenFileComparator;
import com.github.lexakimov.home_inspector.comparators.NameFileComparator;
import com.github.lexakimov.home_inspector.element.Element;
import com.github.lexakimov.home_inspector.element.ElementGroup;
import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public abstract class ElementGroupExtractor {

    final ElementGroupType elementGroupType;

    public void tryExtractElementsToGroup(List<File> homeDirectoryFiles, List<ElementGroup> resultList) {
        var group = new ElementGroup(elementGroupType);
        var groupElements = group.getElements();
        var indexesToDelete = new ArrayList<Integer>();

        for (int i = 0; i < homeDirectoryFiles.size(); i++) {
            var homeDirectoryFile = homeDirectoryFiles.get(i);
            if (precondition(homeDirectoryFile)) {
                var element = new Element(homeDirectoryFile);
                element.setDescription(description(homeDirectoryFile));
                groupElements.add(element);
                indexesToDelete.add(i);
            }
        }

        for (int j = indexesToDelete.size() - 1; j >= 0; j--) {
            var indexToRemove = indexesToDelete.get(j);
            homeDirectoryFiles.remove((int) indexToRemove);
        }

        if (!groupElements.isEmpty()) {
            groupElements.sort(elementsSortStrategy());
            resultList.add(group);
            processChildElements(group);
        }
    }

    Comparator<Element> elementsSortStrategy() {
        return (o1, o2) -> new IsDirectoryFileComparator()
            .thenComparing(new IsHiddenFileComparator())
            .thenComparing(new NameFileComparator())
            .compare(o1.getFile(), o2.getFile());
    }

    /**
     * Условие обработки файла из домашней директории пользователя данным объектом.
     *
     * @param homeDirectoryFile файл или папка из домашней директории пользователя.
     * @return true если нужно обработать файл или папка из пользовательской директории home.
     */
    protected abstract boolean precondition(File homeDirectoryFile);

    /**
     * @param homeDirectoryFile обрабатываемый файл домашней директории
     * @return описание обрабатываемого файла домашней директории (элемента группы).
     */
    protected String description(File homeDirectoryFile) {
        return null;
    }

    protected void processChildElements(ElementGroup group) {

    }

}
