package com.github.lexakimov.home_inspector.element.group_extractors;

import static java.util.Map.entry;

import com.github.lexakimov.home_inspector.element.Element;
import com.github.lexakimov.home_inspector.element.ElementGroup;
import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;
import java.util.Comparator;
import java.util.Map;

public class LocalElementGroupExtractor extends ElementGroupExtractor {

    public LocalElementGroupExtractor() {
        super(ElementGroupType.LOCAL);
    }

    @Override
    protected boolean precondition(File file) {
        return file.getName().equals(".local");
    }

    @Override
    protected void processChildElements(ElementGroup group) {
        group.getElements().stream()
            .filter(e -> e.getFile().getName().equals(".local"))
            .findFirst().ifPresent(local -> {
                var childElements = local.getChildElements();
                var files = local.getFile().listFiles();
                if (files == null) {
                    return;
                }
                for (File file : files) {
                    if (file.isDirectory() && CHILD_DIRS.containsKey(file.getName())) {
                        var description = CHILD_DIRS.get(file.getName());
                        var childElement = new Element(file);
                        if (description != null && !description.isBlank()) {
                            childElement.setDescription(description);
                        }
                        childElements.add(childElement);
                    }

                    childElements.sort(Comparator.comparing(Element::getFile));
                }
            });
    }

    private static final Map<String, String> CHILD_DIRS = Map.ofEntries(
        entry("bin", """
            Существует единственный базовый каталог, относительно которого могут быть записаны исполняемые файлы, специфичные для пользователя.
            Исполняемые файлы, специфичные для пользователя, могут храниться в $HOME/.local/bin. Дистрибутивы должны гарантировать, что этот каталог отображается в $PATH переменной среды UNIX в соответствующем месте.
            Поскольку $HOME они могут использоваться системами с разной архитектурой, установка скомпилированных двоичных файлов в $HOME/.local/bin может вызвать проблемы при использовании в системах с разными архитектурами.
            Часто это не является проблемой, но $HOME следует иметь в виду тот факт, что это частично зависит от архитектуры, если в нее помещаются скомпилированные двоичные файлы."""),
        entry("lib", ""),
        entry("share", """
            содержит файлы данных, специфичные для пользователя
            является частью спецификации базового каталога XDG https://specifications.freedesktop.org/basedir-spec/basedir-spec-latest.html
            определяется значением переменной $XDG_DATA_HOME, либо значением по умолчанию $HOME/.local/share"""),
        entry("state", """
            содержит данные о состоянии, которые должны сохраняться между перезапусками (приложения), специфичные для пользователя.
            но они не настолько важны и переносимы для пользователя, что их следует хранить в файлах $XDG_DATA_HOME.
            Он может содержать:
             - история действий (журналы, история, недавно использованные файлы,…)
             - текущее состояние приложения, которое можно повторно использовать при перезапуске (просмотр, макет, открытые файлы, история отмены и т. д.)
            является частью спецификации базового каталога XDG https://specifications.freedesktop.org/basedir-spec/basedir-spec-latest.html
            определяется значением переменной $XDG_STATE_HOME, либо значением по умолчанию $HOME/.local/state""")
    );
}
