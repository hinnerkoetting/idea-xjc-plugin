package de.oetting.idea.idea_xjc_plugin.util;

public class Guard {

    public static void againstNull(String message, Object value) {
        if (value == null)
            throw new IllegalArgumentException(message);
    }
}
