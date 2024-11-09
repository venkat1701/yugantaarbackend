package io.github.venkat1701.yugantaarbackend.utilities.permissions;

import org.springframework.javapoet.AnnotationSpec;
import org.springframework.javapoet.JavaFile;
import org.springframework.javapoet.TypeSpec;
import org.springframework.security.access.prepost.PreAuthorize;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;

public class AnnotationGenerator {
    public static void main(String[] args) throws IOException {
        var permUtility = new PermissionsUtility();
        Arrays.stream(PermissionsEnum.values())
                .forEach(permission -> {
                   var annotationName = "Requires"+getCamelCaseName(permission.name())+"Permission";
                   String[] roles = permUtility.getRolesForPermission(permission);
                   String rolesSpEL = String.join(", ", roles);
                   var preAuthValue = "hasAnyRole('"+rolesSpEL.replace(",", "', '")+"')";
                   AnnotationSpec annotation = AnnotationSpec.builder(PreAuthorize.class)
                           .addMember("value", "$S", preAuthValue)
                           .build();

                   TypeSpec annotationType = TypeSpec.annotationBuilder(annotationName)
                            .addModifiers(Modifier.PUBLIC)
                            .addAnnotation(annotation)
                            .build();

                   JavaFile javaFile = JavaFile.builder("io.github.venkat1701.yugantaarbackend.utilities.permissions.authannotations", annotationType)
                            .build();
                    try {
                        javaFile.writeTo(Paths.get("src/main/java"));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    private static String getCamelCaseName(String className){
        return Arrays.stream(className.split("_"))
                .map(s -> s.substring(0, 1).toUpperCase() + s.substring(1).toLowerCase())
                .reduce("", String::concat);
    }
}
