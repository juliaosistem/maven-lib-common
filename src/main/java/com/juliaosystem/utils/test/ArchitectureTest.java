package com.juliaosystem.utils.test;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;
import com.tngtech.archunit.library.Architectures;



public class ArchitectureTest {

    private static   JavaClasses allClases(String packageName){
        return new ClassFileImporter()
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
                .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_ARCHIVES)
                .importPackages(packageName);
    }

    public static void checkHexagonalArchitecture(String packageName) {
        
        JavaClasses   allClasses = allClases(packageName);

        ArchRule controllersShouldBeInApiPackage =
                ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Controller")
                        .should()
                        .resideInAPackage("..api.controller..")
                        .andShould()
                        .onlyHaveDependentClassesThat()
                        .haveSimpleNameContaining("Service");

        ArchRule servicesAdapterImplShouldBeInServicesPackage =
                ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Impl")
                        .should()
                        .resideInAPackage("..infrastructure.adapters.primary..");
                       // .andShould()
//                        .onlyHaveDependentClassesThat()
//                        .resideInAPackage("..infrastructure.adapters.secondary..");

        ArchRule servicesAdapterSecondaryShouldBeInServicesPackage =
                ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Adapter")
                        .should().resideInAPackage("..infrastructure.adapters.secondary");


        ArchRule servicesShouldBeInServicesPackage =
                ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Service")
                        .should().resideInAPackage("..infrastructure.services.primary");

        ArchRule servicesInterShouldBeInServicesPackage =
                ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Inter")
                        .should().resideInAPackage("..infrastructure.services.secondary");


        ArchRule repositoriesShouldBeInRepositoryPackage =
                ArchRuleDefinition.classes().that().haveSimpleNameEndingWith("Repository")
                        .should().resideInAPackage("..infrastructure.repository");

        servicesAdapterImplShouldBeInServicesPackage.check(allClasses);
        servicesAdapterSecondaryShouldBeInServicesPackage.check(allClasses);
        servicesInterShouldBeInServicesPackage.check(allClasses);
        controllersShouldBeInApiPackage.check(allClasses);
        servicesShouldBeInServicesPackage.check(allClasses);
        repositoriesShouldBeInRepositoryPackage.check(allClasses);
    }

    public static void checkMethodSizeAndParameters(String packageName) {
        JavaClasses   allClasses = allClases(packageName);

        ArchCondition<JavaMethod> condition = new ArchCondition<JavaMethod>("no exceder el límite de líneas") {
            @Override
            public void check(JavaMethod method, ConditionEvents events) {
                int maxLines = 12;
                int maxParameters = 3;

                int linesOfCode = MethodSizeAndParametersCondition.verificarTamanoMetodos(method.getOwner().reflect());
                if (linesOfCode > maxLines) {
                    events.add(SimpleConditionEvent.violated(method, "El método excede las 12 líneas de código" + "archivo :" + method.getSourceCodeLocation().getSourceClass() + "linea:" + method.getSourceCodeLocation().getLineNumber()));
                }
                if (!MethodSizeAndParametersCondition.haveAtMost(maxParameters).test(method)) {
                    events.add(SimpleConditionEvent.violated(method, "El método excede los 3 parámetros" +"archivo :" + method.getSourceCodeLocation().getSourceClass() + "linea:" + method.getSourceCodeLocation().getLineNumber()));
                }
            }

        };

        ArchRule rule = ArchRuleDefinition.methods()
                .that().areDeclaredInClassesThat().resideInAPackage(packageName + "..")
                .should(condition);

        rule.check(allClasses);

    }

}
