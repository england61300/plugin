package com.example.core.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

public final class CoreUtil {
    private static final LegacyComponentSerializer LEGACY = LegacyComponentSerializer.legacyAmpersand();

    private CoreUtil() {
    }

    public static String color(String input) {
        if (input == null) {
            return "";
        }
        Component component = LEGACY.deserialize(input);
        return LEGACY.serialize(component);
    }
}
