package dev.boiarshinov.tests4tests.t4t;

import io.github.classgraph.AnnotationInfo;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ScanResult;
import org.junit.jupiter.api.Test;
import org.opentest4j.AssertionFailedError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class AuthorisationTested {

    public static final String BASE_PACKAGE = "dev.boiarshinov";
    @Autowired
    WebApplicationContext context;

    @Test
    void searchForEndpoints() {
        Set<String> endpoints = new HashSet<>(getEndpoints());
        Set<String> testedEndpoints = new HashSet<>(findAnnotatedTests());

        endpoints.removeAll(testedEndpoints);

        if (!endpoints.isEmpty()) {
            throw new AssertionFailedError("Authorisation for endpoints not tested: " + endpoints);
        }
    }

    private Set<String> getEndpoints() {
        RequestMappingHandlerMapping mapping = context.getBean(RequestMappingHandlerMapping.class);

        return mapping.getHandlerMethods().entrySet().stream()
                .filter(e -> e.getValue().getBeanType().getPackage().getName().startsWith(BASE_PACKAGE))
                .map(e -> e.getKey())
                .flatMap(endpoint -> {
                    Set<RequestMethod> methods = endpoint.getMethodsCondition().getMethods();
                    Set<String> paths = endpoint.getPathPatternsCondition().getPatternValues();
                    return methods.stream()
                            .flatMap(method -> paths.stream().map(path -> method + " " + path));
                })
                .collect(Collectors.toSet());
    }

    public static Set<String> findAnnotatedTests() {
        try (ScanResult scanResult = new ClassGraph()
                .enableAllInfo()
                .acceptPackages(BASE_PACKAGE)
                .scan()) {

            return scanResult.getClassesWithMethodAnnotation(AuthorisationTest.class.getName())
                    .stream()
                    .flatMap(classInfo -> classInfo.getMethodInfo()
                            .stream()
                            .filter(methodInfo -> methodInfo.hasAnnotation(AuthorisationTest.class.getName()))
                            .map(methodInfo -> {
                                AnnotationInfo annotationInfo = methodInfo.getAnnotationInfo(AuthorisationTest.class.getName());
                                return (String) annotationInfo.getParameterValues().getValue("endpoint");
                            }))
                    .collect(Collectors.toSet());
        }
    }
}
