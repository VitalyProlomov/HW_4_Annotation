import ExampleClasses.Building;
import ExampleClasses.Car;
import ExampleClasses.UnAnnotatedClass;
import JsonSerializer.JsonSerializer;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SerializerTests {

    JsonSerializer serializer = new JsonSerializer();
    @Test
    public void shouldThrowExceptionOnUnannotatedClass() throws IllegalAccessException {
        UnAnnotatedClass cl = new UnAnnotatedClass();
        assertThrows(IllegalArgumentException.class, () -> serializer.serialize(cl));
    }

    @Test
    public void shouldIgnoreNullValuesWithAnnotation() throws IllegalAccessException {
        // Class Car has IgnoreNull annotation and is serializable, so it should
        // return the string representation with only 2 fields.

        Car car = new Car(120, "JuhlikCar");
        String strRep = serializer.serialize(car);

        String expectedValue = """
                {
                "speed" : 120
                "modelName" : JuhlikCar
                }
                """;
        assertEquals(expectedValue, strRep);
        // Checks if the amount of lines is actually 4
        // (which means 2 parentheses {} and 2 fields,
        // since each field is written in another line).
        assertEquals(strRep.split("\n").length, 4);
    }

    @Test
    public void shouldChangeFieldNameIfAnnotationIsPresent() throws IllegalAccessException {
        Car car = new Car(150, "Toyota");
        String strRep = serializer.serialize(car);

        // Checking that field name is actually changed to the annotation`s parameter.
        assertEquals("\"speed\"", strRep.split("\n")[1].split(" ")[0]);

        String expectedesult = """
                {
                "speed" : 150
                "modelName" : Toyota
                }
                """;

        assertEquals(strRep, expectedesult);
    }

    @Test
    public void shouldOnlyAddAnnotatedFields() throws IllegalAccessException {
        // Class Building has 3 fields, but only one of them is annotated with
        // JsonElement Annotation.
        Building building = new Building("Samara, Pushkina St, Kolotushkin's House", 1);

        String strRep = serializer.serialize(building);

        // Amount of lines - must be 3 (2 for parentheses and 1 for the field).
        assertEquals(3, strRep.split("\n").length);

        String expectedRep = """
                {
                "address" : Samara, Pushkina St, Kolotushkin's House
                }
                """;
        assertEquals(expectedRep, strRep);

    }
}
