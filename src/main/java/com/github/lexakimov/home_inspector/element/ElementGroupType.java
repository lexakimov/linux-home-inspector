package com.github.lexakimov.home_inspector.element;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ElementGroupType {
    CONFIGS("Конфигурации приложений", null),
    CACHE("Кэш приложений", null),
    LOCAL("Данные приложений", null),
    XDG_DIRS(
        "Папки user dirs",
        """
            Директории пользователя, определенные ~/.config/user-dirs.dirs
            https://wiki.archlinux.org/title/XDG_user_directories_(Русский)"""
    ),

    JAVA("Java", null),
    KUBERNETES("Kubernetes", null),

    OTHER("Остальные", null),
    UNKNOWN("Неизвестно", null);

    private final String title;
    private final String description;
}
