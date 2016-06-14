package net.glease.chem.simple.calculation;

/**
 * Marker interface meaning the class's state won't change since it's exposed.
 * Not an annotation because it may be used in lambda. Every immutable implementation
 * of interfaces which lives in {@code net.glease.chem.simple.calculation} should
 * implement this marker interface. Other immutable implementation are also
 * encouraged to implement this marker interface.
 * @author glease
 * @since 0.1
 * @param <T> the type of the mutable version or itself
 */
public interface Immutable<T> {
}
