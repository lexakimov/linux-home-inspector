package com.github.lexakimov.home_inspector.comparators;

import java.io.File;
import java.util.Comparator;

public class NameFileComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        var n1 = o1.getName();
        var n2 = o2.getName();

        return n1.compareToIgnoreCase(n2);
    }
}
