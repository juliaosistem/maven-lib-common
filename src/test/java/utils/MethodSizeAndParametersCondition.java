package utils;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;

import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;

import java.lang.reflect.Method;

public class MethodSizeAndParametersCondition extends ArchCondition<JavaMethod> {
    private final int maxLines;
    private final int maxParameters;

    protected MethodSizeAndParametersCondition(int maxLines, int maxParameters) {
        super("tener como máximo " + maxLines + " líneas de código y " + maxParameters + " o menos parámetros");
        this.maxLines = maxLines;
        this.maxParameters = maxParameters;
    }

    public static int verificarTamanoMetodos(Class<?> clase) {
        Method[] metodos = clase.getDeclaredMethods();
        for (Method metodo : metodos) {
            int lineasCodigo = contarLineasCodigo(metodo);
            if (lineasCodigo > 12) {
                return lineasCodigo;
            }
        }
        return 1;
    }
    private static int contarLineasCodigo(Method metodo) {
        String codigo = metodo.toString();
        int lineas = codigo.split("\n").length;
        return lineas;
    }

    public static DescribedPredicate<JavaMethod> haveAtMost( int maxParameters) {
        return new DescribedPredicate<JavaMethod>("tener como máximo  :" + maxParameters + " o menos parámetros") {
            @Override
            public boolean test(JavaMethod input) {
                return   input.getParameters().size() <= maxParameters;
            }
        };
    }

    @Override
    public void check(JavaMethod javaMethod, ConditionEvents conditionEvents) {

    }
}
