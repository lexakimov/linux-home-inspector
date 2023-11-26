package com.github.lexakimov.home_inspector.comparators;

import java.io.File;
import java.util.Comparator;

public class IsDirectoryFileComparator implements Comparator<File> {
    @Override
    public int compare(File o1, File o2) {
        int i1 = o1.isDirectory() ? 0 : 1;
        int i2 = o2.isDirectory() ? 0 : 1;
        return Integer.compare(i1, i2);
    }
}
