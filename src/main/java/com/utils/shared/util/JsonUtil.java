package com.utils.shared.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.Map;

/**
 * Classe utilitaire pour la conversion d'objets en format JSON et vice versa.
 */
public class JsonUtil {

    private static final int INDENTATION_SPACES = 2;

    private JsonUtil() {
        // Constructeur privé pour empêcher l'instanciation de la classe utilitaire
    }

    /**
     * Convertit un objet en format JSON.
     *
     * @param object l'objet à convertir
     * @return la représentation JSON de l'objet
     */
    public static String toJson(Object object) {
        return toJson(object, false);
    }

    /**
     * Convertit un objet en format JSON avec ou sans indentation.
     *
     * @param object l'objet à convertir
     * @param indent true pour l'indentation, false sinon
     * @return la représentation JSON de l'objet
     */
    public static String toJson(Object object, boolean indent) {
        StringBuilder jsonBuilder = new StringBuilder();
        toJson(object, jsonBuilder, indent ? 0 : -1, new IdentityHashMap<>());
        return jsonBuilder.toString();
    }

    private static void toJson(Object object, StringBuilder jsonBuilder, int indentationLevel, Map<Object, Object> processedObjects) {
        if (object == null) {
            jsonBuilder.append("null");
        } else if (object instanceof String) {
            jsonBuilder.append("\"").append(StringUtil.escape((String) object)).append("\"");
        } else if (object instanceof Number || object instanceof Boolean) {
            jsonBuilder.append(object);
        } else if (object instanceof Character) {
            jsonBuilder.append("\"").append(StringUtil.escape(object.toString())).append("\"");
        } else if (object instanceof Collection) {
            toJsonCollection((Collection<?>) object, jsonBuilder, indentationLevel, processedObjects);
        } else if (object.getClass().isArray()) {
            toJsonArray(object, jsonBuilder, indentationLevel, processedObjects);
        } else if (object instanceof Map) {
            toJsonMap((Map<?, ?>) object, jsonBuilder, indentationLevel, processedObjects);
        } else {
            toJsonCustomObject(object, jsonBuilder, indentationLevel, processedObjects);
        }
    }

    private static void toJsonCollection(Collection<?> collection, StringBuilder jsonBuilder, int indentationLevel, Map<Object, Object> processedObjects) {
        jsonBuilder.append("[");
        boolean first = true;
        for (Object item : collection) {
            if (!first) {
                jsonBuilder.append(",");
            }
            if (indentationLevel >= 0) {
                jsonBuilder.append("\n");
                appendIndentation(jsonBuilder, indentationLevel + 1);
            }
            toJson(item, jsonBuilder, indentationLevel >= 0 ? indentationLevel + 1 : -1, processedObjects);
            first = false;
        }
        if (indentationLevel >= 0 && !first) {
            jsonBuilder.append("\n");
            appendIndentation(jsonBuilder, indentationLevel);
        }
        jsonBuilder.append("]");
    }

    private static void toJsonArray(Object array, StringBuilder jsonBuilder, int indentationLevel, Map<Object, Object> processedObjects) {
        jsonBuilder.append("[");
        int length = Array.getLength(array);
        for (int i = 0; i < length; i++) {
            if (i > 0) {
                jsonBuilder.append(",");
            }
            if (indentationLevel >= 0) {
                jsonBuilder.append("\n");
                appendIndentation(jsonBuilder, indentationLevel + 1);
            }
            toJson(Array.get(array, i), jsonBuilder, indentationLevel >= 0 ? indentationLevel + 1 : -1, processedObjects);
        }
        if (indentationLevel >= 0 && length > 0) {
            jsonBuilder.append("\n");
            appendIndentation(jsonBuilder, indentationLevel);
        }
        jsonBuilder.append("]");
    }

    private static void toJsonMap(Map<?, ?> map, StringBuilder jsonBuilder, int indentationLevel, Map<Object, Object> processedObjects) {
        jsonBuilder.append("{");
        boolean first = true;
        for (Map.Entry<?, ?> entry : map.entrySet()) {
            if (!first) {
                jsonBuilder.append(",");
            }
            if (indentationLevel >= 0) {
                jsonBuilder.append("\n");
                appendIndentation(jsonBuilder, indentationLevel + 1);
            }
            toJson(entry.getKey(), jsonBuilder, indentationLevel >= 0 ? indentationLevel + 1 : -1, processedObjects);
            jsonBuilder.append(":");
            if (indentationLevel >= 0) {
                jsonBuilder.append(" ");
            }
            toJson(entry.getValue(), jsonBuilder, indentationLevel >= 0 ? indentationLevel + 1 : -1, processedObjects);
            first = false;
        }
        if (indentationLevel >= 0 && !first) {
            jsonBuilder.append("\n");
            appendIndentation(jsonBuilder, indentationLevel);
        }
        jsonBuilder.append("}");
    }

    private static void toJsonCustomObject(Object object, StringBuilder jsonBuilder, int indentationLevel, Map<Object, Object> processedObjects) {
        if (processedObjects.containsKey(object)) {
            jsonBuilder.append("{}");
            return;
        }
        processedObjects.put(object, null);
        jsonBuilder.append("{");
        boolean first = true;
        for (Field field : object.getClass().getDeclaredFields()) {
            if (!Modifier.isStatic(field.getModifiers())) {
                field.setAccessible(true);
                try {
                    Object value = field.get(object);
                    if (value != null) {
                        if (!first) {
                            jsonBuilder.append(",");
                        }
                        if (indentationLevel >= 0) {
                            jsonBuilder.append("\n");
                            appendIndentation(jsonBuilder, indentationLevel + 1);
                        }
                        jsonBuilder.append("\"").append(StringUtil.escape(field.getName())).append("\":");
                        if (indentationLevel >= 0) {
                            jsonBuilder.append(" ");
                        }
                        toJson(value, jsonBuilder, indentationLevel >= 0 ? indentationLevel + 1 : -1, processedObjects);
                        first = false;
                    }
                } catch (IllegalAccessException e) {
                    // Ignore inaccessible fields
                }
            }
        }
        if (indentationLevel >= 0 && !first) {
            jsonBuilder.append("\n");
            appendIndentation(jsonBuilder, indentationLevel);
        }
        jsonBuilder.append("}");
        processedObjects.remove(object);
    }

    private static void appendIndentation(StringBuilder jsonBuilder, int indentationLevel) {
        for (int i = 0; i < indentationLevel * INDENTATION_SPACES; i++) {
            jsonBuilder.append(" ");
        }
    }

    /**
     * Classe interne représentant une exception spécifique à la conversion JSON.
     */
    private static class JsonParsingException extends Exception {
        public JsonParsingException(String message) {
            super(message);
        }

        public JsonParsingException(String message, Throwable cause) {
            super(message, cause);
        }
    }

}
