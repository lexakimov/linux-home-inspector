package com.github.lexakimov.home_inspector.element;

import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ElementGroup {
    private final ElementGroupType type;
    private final List<Element> elements = new ArrayList<>();

}