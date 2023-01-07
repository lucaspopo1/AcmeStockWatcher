package org.acme;

import java.util.function.Predicate;

public class FilterComboItem<T> {
    private final String label;
    private final Predicate<T> compareMethod;
    public FilterComboItem(String label, Predicate<T> compareMethod) {
        this.label = label;
        this.compareMethod = compareMethod;
    }

    @Override
    public String toString() {
        return label;
    }

    public Predicate<T> getCompareMethod() {
        return compareMethod;
    }
}
