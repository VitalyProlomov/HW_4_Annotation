package JsonSerializer;

import Annotations.IgnoreNull;
import Annotations.JsonElement;
import Annotations.JsonSerializable;

import java.lang.reflect.Field;
import java.util.Objects;

public class JsonSerializer {
    boolean isSerializable(Object object) {
        return !(object == null) && object.getClass().isAnnotationPresent(JsonSerializable.class);
    }

    /**
     * returns String representation of serialized object in Json Serialization.
     * Throws IllegalArgumentException if object is not serializable (iut is either null or
     * not marked by JsonSerializable annotation).
     * @param object the object that needs to be serialized
     * @return serialized string representation.
     */
    public String serialize(Object object) throws IllegalAccessException {
        if (!isSerializable(object)) {
            throw new IllegalArgumentException("Object is not serializable - it is either null or" +
                    " not marked with proper annotation.");
        }

        StringBuilder stringRepresentation = new StringBuilder("{\n");
        Field[] allFields = object.getClass().getDeclaredFields();
        boolean wasAccessChanged;
        for (Field field : allFields) {
            wasAccessChanged = false;
            if (!field.canAccess(object)) {
                field.setAccessible(true);
                wasAccessChanged = true;
            }

            if (!field.isAnnotationPresent(JsonElement.class)) {
                if (wasAccessChanged) {
                    field.setAccessible(false);
                }
                continue;
            }

            if (field.get(object) == null && field.isAnnotationPresent(IgnoreNull.class)) {
                if (wasAccessChanged) {
                    field.setAccessible(false);
                }
                continue;
            }

            stringRepresentation.append("\"");
            String name = Objects.equals(field.getAnnotation(JsonElement.class).fieldName(), "")
                    ? field.getName()
                    : field.getAnnotation(JsonElement.class).fieldName();
            stringRepresentation.append(name);
            stringRepresentation.append("\" : ");

            String value = (field.get(object) == null)
                    ? "null"
                    : field.get(object).toString();
            stringRepresentation.append(value);
            stringRepresentation.append("\n");

            if (wasAccessChanged) {
                field.setAccessible(false);
            }
        }
        stringRepresentation.append("}\n");
        return stringRepresentation.toString();
    }

}
