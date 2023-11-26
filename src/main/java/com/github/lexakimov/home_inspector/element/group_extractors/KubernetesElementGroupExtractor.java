package com.github.lexakimov.home_inspector.element.group_extractors;

import com.github.lexakimov.home_inspector.element.ElementGroupType;
import java.io.File;
import java.util.Set;

public class KubernetesElementGroupExtractor extends ElementGroupExtractor {

    public KubernetesElementGroupExtractor() {
        super(ElementGroupType.KUBERNETES);
    }

    @Override
    protected boolean precondition(File file) {
        return Set.of(".helm", ".kube", ".k8slens", ".minikube").contains(file.getName());
    }
}
