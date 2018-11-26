package br.ufpe.cin.if688.minijava.Exceptions;

import br.ufpe.cin.if688.minijava.ast.Type;

public class Exception {
    public static Type error(Type expected, Type actual) {
        throw new RuntimeException(String.format("Couldn't match expected type '" + expected + "' with actual type '" + actual + "'"));
    }
}
