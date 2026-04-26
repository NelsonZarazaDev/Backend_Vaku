package com.Vaku.Vaku.utils;

import java.text.Normalizer;
import java.util.Locale;

public final class RoleUtils {
    private RoleUtils() {
    }

    public static String normalizeRole(String role) {
        if (role == null) {
            return "";
        }

        return Normalizer.normalize(role, Normalizer.Form.NFD)
                .replaceAll("\\p{M}", "")
                .toLowerCase(Locale.ROOT)
                .trim();
    }
}
