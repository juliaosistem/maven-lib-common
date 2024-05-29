package com.juliaosystem.utils.test;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaMethod;
import com.tngtech.archunit.lang.ArchCondition;

import com.tngtech.archunit.lang.ConditionEvents;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Method;

public class MethodSizeAndParametersCondition extends ArchCondition<JavaMethod> {
    private final int maxLines;
    private final int maxParameters;

    protected MethodSizeAndParametersCondition(int maxLines, int maxParameters) {
        super("tener como máximo " + maxLines + " líneas de código y " + maxParameters + " o menos parámetros");
        this.maxLines = maxLines;
        this.maxParameters = maxParameters;
    }

    public static int countMethodLines(String filename, String methodName) {
        int count = 0;
        boolean insideMethod = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains(methodName + "(")) {
                    insideMethod = true;
                }
                if (insideMethod) {
                    line = line.trim();
                    if (!line.isEmpty() && !line.startsWith("//") && !line.startsWith("}")) {
                        count++;
                    }
                    if (line.contains("}")) {
                        insideMethod = false;
                        break;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count;
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
