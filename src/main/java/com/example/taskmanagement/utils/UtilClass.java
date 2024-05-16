package com.example.taskmanagement.utils;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;

@Slf4j
public class UtilClass {
    private UtilClass() {

    }

    public static boolean isNull(Object obj) {
        return obj == null;
    }

    public static boolean isNull(Collection<?> collection) {
        return isNull((Object) collection) || collection.isEmpty();
    }

    public static boolean isNull(Map<?, ?> map) {
        return isNull((Object) map) || map.isEmpty();
    }

    public static boolean isNull(String key) {
        return key == null || key.isEmpty();
    }

}
