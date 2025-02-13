package com.lance.spring.springinitstage.importlearn;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportSelect implements ImportSelector {
    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        return new String[]{ImportSelectEntity1.class.getName(), ImportSelectEntity2.class.getName()};
    }
}
