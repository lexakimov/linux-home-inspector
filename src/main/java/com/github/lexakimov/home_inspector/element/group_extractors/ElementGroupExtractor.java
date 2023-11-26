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

    public void tryExtractElementsToGroup(List<File> sourceFiles, List<ElementGroup> resultList) {
        var group = new ElementGroup(elementGroupType);
        var groupElements = group.getElements();
        var indexesToDelete = new ArrayList<Integer>();

        for (int i = 0; i < sourceFiles.size(); i++) {
            var file = sourceFiles.get(i);
            if (precondition(file)) {
                var element = new Element(file);
                element.setDescription("это кеш приложений");
                groupElements.add(element);
                indexesToDelete.add(i);
            }
        }

        for (int j = indexesToDelete.size() - 1; j >= 0; j--) {
            var indexToRemove = indexesToDelete.get(j);
            sourceFiles.remove((int) indexToRemove);
        }

        if (!groupElements.isEmpty()) {
            groupElements.sort(elementsSortStrategy());
            resultList.add(group);
        }
    }

    Comparator<Element> elementsSortStrategy() {
        return (o1, o2) -> new IsDirectoryFileComparator()
            .thenComparing(new IsHiddenFileComparator())
            .thenComparing(new NameFileComparator())
            .compare(o1.getFile(), o2.getFile());
    }

    protected abstract boolean precondition(File file);

}
