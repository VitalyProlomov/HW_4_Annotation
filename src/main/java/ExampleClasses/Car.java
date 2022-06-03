package ExampleClasses;

import Annotations.IgnoreNull;
import Annotations.JsonElement;
import Annotations.JsonSerializable;

@JsonSerializable
public class Car {
    @JsonElement(fieldName = "speed")
    private int maxSpeed;

    @JsonElement
    private String modelName;


    public Car(int speed, String name) {
        maxSpeed = speed;
        modelName = name;
    }

    public int getMaxSpeed() {
        return maxSpeed;
    }

    public void setMaxSpeed(int maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

}
