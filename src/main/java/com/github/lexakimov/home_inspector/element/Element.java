package com.github.lexakimov.home_inspector.element;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class Element {
    private final File file;
    private String description;
    private final List<Element> childElements = new ArrayList<>();
}
