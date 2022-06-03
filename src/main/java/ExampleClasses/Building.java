package ExampleClasses;

import Annotations.IgnoreNull;
import Annotations.JsonElement;
import Annotations.JsonSerializable;

@JsonSerializable
@IgnoreNull
public class Building {
    @JsonElement
    String address;

    int floorsAmount;

    Object nullObject = null;

    public Building(String ad, int fl) {
        address = ad;
        floorsAmount = fl;
    }
}
