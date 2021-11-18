package ru.project.tuxarb.kafka.common.util;

import java.util.Map;
import java.util.stream.Collectors;

/**
 * Базовый класс с общими утилитами.
 */
public final class CommonUtil {

    public static final String HEADER_NOT_FOUND_MSG = "Header %s is not found!";

    private CommonUtil() {
    }

    /**
     * Метод для преобразования объекта к строковому типу.
     *
     * @param obj объект для преобразования
     * @return объект {@link String} или {@code null}, если параметр метода отсутствует
     */
    public static String objToString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof byte[]) {
            return new String((byte[]) obj);
        }
        return obj.toString();
    }

    /**
     * Метод для преобразования значений мапы к строковому типу.
     *
     * @param map мапа для преобразования ее значений в строку
     * @return мапа {@link Map} со строковыми значениями или {@code null}, если параметр метода отсутствует
     */
    public static Map<String, String> convertMapValuesToString(Map<String, Object> map) {
        if (map == null) {
            return null;
        }
        return map.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> CommonUtil.objToString(entry.getValue())
                ));
    }
}
