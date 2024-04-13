package me.alexand.spacebattle.service.exceptions;

public class DependencyResolveException extends RuntimeException {

    private final String dependencyName;

    public DependencyResolveException(String dependencyName) {
        this.dependencyName = dependencyName;
    }

    @Override
    public String toString() {
        return String.format("dependency '%s' is not found", dependencyName);
    }
}
