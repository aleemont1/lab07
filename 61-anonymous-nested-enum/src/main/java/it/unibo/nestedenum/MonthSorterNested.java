package it.unibo.nestedenum;

import java.util.Comparator;
import java.util.Locale;
import java.util.Objects;

/**
 * Implementation of {@link MonthSorter}.
 */
public final class MonthSorterNested implements MonthSorter {
    private static final Comparator<String> BY_DAYS = new SortByDays();
    private static final Comparator<String> BY_ORDER = new SortByMonthOrder();

    @Override
    public Comparator<String> sortByDays() {
        return BY_DAYS;
    }

    @Override
    public Comparator<String> sortByOrder() {
        return BY_ORDER;
    }

    private enum Month {
        JANUARY(31),
        FEBRUARY(28),
        MARCH(31),
        APRIL(30),
        MAY(31),
        JUNE(30),
        JULY(31),
        AUGUST(31),
        SEPTEMBER(30),
        OCTOBER(31),
        NOVEMBER(30),
        DECEMBER(31);
    
        private final int days;

        Month(final int days) {
            this.days = days;
        }

        public static Month fromString(final String name) {
            Objects.requireNonNull(name);
            try {
                return valueOf(name);
            } catch (IllegalArgumentException e) {
                Month match = null;
                for (final Month month : values()) {
                    if(month.toString().toLowerCase(Locale.ROOT).startsWith(name.toLowerCase(Locale.ROOT))) {
                        if (match != null) {
                            throw new IllegalArgumentException(
                                name + " is ambiguous ("+match+") and ("+month+") matched"
                            );

                        }
                        match = month;
                    }
                }
                if (match == null) {
                    throw new IllegalArgumentException("No matching months for " + name, e);
                }
                return match;
            }

        }
    }

    private static class SortByDays implements Comparator<String> {
        @Override
        public int compare(final String s1, final String s2) {
            final var month1 = Month.fromString(s1);
            final var month2 = Month.fromString(s2);
            return Integer.compare(month1.days, month2.days);
        }
    }

    private static class SortByMonthOrder implements Comparator<String> {
        @Override
        public int compare(final String s1, final String s2) {
            return Month.fromString(s1).compareTo(Month.fromString(s2));
        }
    }
}
