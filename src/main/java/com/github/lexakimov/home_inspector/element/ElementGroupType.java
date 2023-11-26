package com.github.lexakimov.home_inspector.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElementGroupType {
    CACHE("Кэш приложений"),
    CONFIGS("Конфигурации приложений"),
    JAVA("Java"),
    KUBERNETES("Kubernetes"),
    LOCAL("Local"),
    XDG_DIRS("Папки user dirs"),

    OTHER("Остальные"),
    UNKNOWN("Неизвестно");

    private final String title;
}
