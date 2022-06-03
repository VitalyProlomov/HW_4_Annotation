package ExampleClasses;

import Annotations.IgnoreNull;
import Annotations.JsonElement;

public class UnAnnotatedClass {
    @JsonElement
    String field1;
    @JsonElement(fieldName = "payroll")
    double field2;
    Object field3;
}
