package com.utils.shared.annotation.processor;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@SupportedAnnotationTypes("engineering.util.annotation.Binding")
@SupportedSourceVersion(SourceVersion.RELEASE_8)
public class BindingAnnotationProcessor extends AbstractProcessor {
    private final Logger LOG = Logger.getAnonymousLogger();
    private final List<String> imports = new ArrayList<>();

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        LOG.info("@Binding Processor processing");
        for (TypeElement annotation : annotations) {
            Set<? extends Element> annotatedElements = roundEnv.getElementsAnnotatedWith(annotation);
            for (Element annotatedElement : annotatedElements) {
                if (annotatedElement.getKind() == ElementKind.CLASS) {
                    TypeElement classElement = (TypeElement) annotatedElement;
                    generateBinderClass(classElement);
                }
            }
        }
        LOG.info("processing Done");
        return true;
    }

    private void generateBinderClass(TypeElement classElement) {
        String packageName = processingEnv.getElementUtils().getPackageOf(classElement).toString();
        String className = classElement.getSimpleName().toString();
        String binderClassName = className + "Binder";

        StringBuilder sb = new StringBuilder();
        sb.append("package ").append(packageName).append(";\n\n");

        collectImports(classElement);

        sb.append("public class ").append(binderClassName).append(" {\n");
        sb.append("\tpublic static void bind(").append(className).append(" entity) {\n");

        generateBindingCode(classElement, sb);

        sb.append("\t}\n");
        sb.append("}");

        writeSourceFile(packageName, binderClassName, sb.toString());
    }

    private void generateBindingCode(TypeElement classElement, StringBuilder sb) {
        List<? extends Element> enclosedElements = classElement.getEnclosedElements();
        for (Element enclosedElement : enclosedElements) {
            if (enclosedElement.getKind() == ElementKind.FIELD) {
                VariableElement fieldElement = (VariableElement) enclosedElement;
                String fieldName = fieldElement.getSimpleName().toString();

                if (fieldElement.getAnnotation(OneToMany.class) != null) {
                    generateOneToManyBindingCode(fieldElement, fieldName, sb);
                } else if (fieldElement.getAnnotation(OneToOne.class) != null) {
                    generateOneToOneBindingCode(fieldElement, fieldName, sb);
                } else if (fieldElement.getAnnotation(ManyToMany.class) != null) {
                    generateManyToManyBindingCode(fieldElement, fieldName, sb);
                }
            }
        }
    }

    private void generateOneToManyBindingCode(VariableElement fieldElement, String fieldName, StringBuilder sb) {
        OneToMany oneToManyAnnotation = fieldElement.getAnnotation(OneToMany.class);
        String mappedBy = oneToManyAnnotation.mappedBy();

        addImport(fieldElement.asType().toString());

        sb.append("\t\t// Binding for field: ").append(fieldName).append(" (OneToMany)\n");
        sb.append("\t\tCollection<").append(fieldElement.asType()).append("> ").append(fieldName).append("List = entity.get")
                .append(capitalize(fieldName)).append("List();\n");
        sb.append("\t\tfor (").append(fieldElement.asType()).append(" child : ").append(fieldName).append("List) {\n");
        sb.append("\t\t\tif (").append(getBinderClassName(fieldElement)).append(".class != null) {\n");
        sb.append("\t\t\t\t").append(getBinderClassName(fieldElement)).append(".bind(child);\n");
        sb.append("\t\t\t}\n");
        sb.append("\t\t\tchild.set").append(capitalize(mappedBy)).append("(entity);\n");
        sb.append("\t\t}\n");
    }

    private void generateOneToOneBindingCode(VariableElement fieldElement, String fieldName, StringBuilder sb) {
        JoinColumn joinColumnAnnotation = fieldElement.getAnnotation(JoinColumn.class);

        if (joinColumnAnnotation != null) {
            addImport(fieldElement.asType().toString());

            sb.append("\t\t// Binding for field: ").append(fieldName).append(" (OneToOne)\n");
            sb.append("\t\t").append(fieldElement.asType()).append(" ").append(fieldName).append(" = entity.get")
                    .append(capitalize(fieldName)).append("();\n");
            sb.append("\t\tif (").append(fieldName).append(" != null) {\n");
            sb.append("\t\t\t").append(getBinderClassName(fieldElement)).append(".bind(").append(fieldName).append(");\n");
            sb.append("\t\t\t").append(fieldName).append(".set").append(capitalize(joinColumnAnnotation.referencedColumnName()))
                    .append("(entity);\n");
            sb.append("\t\t}\n");
        }
    }

    private void generateManyToManyBindingCode(VariableElement fieldElement, String fieldName, StringBuilder sb) {
        ManyToMany manyToManyAnnotation = fieldElement.getAnnotation(ManyToMany.class);
        String mappedBy = manyToManyAnnotation.mappedBy();

        addImport(fieldElement.asType().toString());

        sb.append("\t\t// Binding for field: ").append(fieldName).append(" (ManyToMany)\n");
        sb.append("\t\tCollection<").append(fieldElement.asType()).append("> ").append(fieldName).append("List = entity.get")
                .append(capitalize(fieldName)).append("List();\n");
        sb.append("\t\tfor (").append(fieldElement.asType()).append(" child : ").append(fieldName).append("List) {\n");
        sb.append("\t\t\tif (").append(getBinderClassName(fieldElement)).append(".class != null) {\n");
        sb.append("\t\t\t\t").append(getBinderClassName(fieldElement)).append(".bind(child);\n");
        sb.append("\t\t\t}\n");
        sb.append("\t\t\tchild.add").append(capitalize(mappedBy)).append("(entity);\n");
        sb.append("\t\t}\n");
    }

    private String capitalize(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }
        return Character.toUpperCase(input.charAt(0)) + input.substring(1);
    }

    private String getBinderClassName(VariableElement fieldElement) {
        String fieldType = fieldElement.asType().toString();
        String[] fieldTypeParts = fieldType.split("\\.");
        String className = fieldTypeParts[fieldTypeParts.length - 1];

        addImport(fieldType);

        return className + "Binder";
    }

    private void addImport(String importClass) {
        if (!imports.contains(importClass)) {
            imports.add(importClass);
        }
    }

    private void collectImports(TypeElement classElement) {
        for (Element element : classElement.getEnclosedElements()) {
            if (element.getKind() == ElementKind.FIELD) {
                VariableElement fieldElement = (VariableElement) element;
                if (fieldElement.getAnnotation(OneToMany.class) != null ||
                        fieldElement.getAnnotation(OneToOne.class) != null ||
                        fieldElement.getAnnotation(ManyToMany.class) != null) {
                    addImport(fieldElement.asType().toString());
                }
            }
        }
    }

    private void writeSourceFile(String packageName, String className, String sourceCode) {
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(packageName + "." + className);
            try (Writer writer = sourceFile.openWriter()) {
                writeImports(writer);
                writer.write(sourceCode);
            }
        } catch (IOException e) {
            throw new BindingProcessorException("Erreur durant le processus de création des classes de binding", e);
        }
    }

    private void writeImports(Writer writer) throws IOException {
        for (String importClass : imports) {
            writer.write("import " + importClass + ";\n");
        }
        writer.write("\n");
    }

    /**
     * Classe interne représentant une exception spécifique à la création des classes.
     */
    private static class BindingProcessorException extends RuntimeException {
        public BindingProcessorException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}
