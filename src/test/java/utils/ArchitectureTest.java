package utils;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.lang.syntax.ArchRuleDefinition;



public class ArchitectureTest {

    public static void checkMethodSizeAndParameters(String packageName) {
        JavaClasses allClasses = new ClassFileImporter().importPackages(packageName);


        ArchCondition<JavaMethod> condition = new ArchCondition<JavaMethod>("no exceder el límite de líneas") {
            @Override
            public void check(JavaMethod method, ConditionEvents events) {
                int maxLines = 12;
                int maxParameters = 3;

                int linesOfCode = MethodSizeAndParametersCondition.verificarTamanoMetodos(method.getOwner().reflect());
                if (linesOfCode > maxLines) {
                    events.add(SimpleConditionEvent.violated(method, "El método excede las 12 líneas de código"));
                }
                if (MethodSizeAndParametersCondition.haveAtMost(maxParameters).test(method)) {
                    events.add(SimpleConditionEvent.violated(method, "El método excede los 3 parámetros"));
                }
            }

        };

        ArchRule rule = ArchRuleDefinition.methods()
                .that().areDeclaredInClassesThat().resideInAPackage(packageName + "..")
                .should(condition);

        rule.check(allClasses);

    }

}
