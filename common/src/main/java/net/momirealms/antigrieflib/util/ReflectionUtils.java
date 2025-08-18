package net.momirealms.antigrieflib.util;

import org.jetbrains.annotations.NotNull;

public final class ReflectionUtils {

    public static boolean classExists(@NotNull final String clazz) {
        try {
            Class.forName(clazz);
            return true;
        } catch (Throwable e) {
            return false;
        }
    }
}
