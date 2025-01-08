package model;

import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleStringProperty;

public class Player {
    private final StringProperty name;

    public Player(String name) {
        this.name = new SimpleStringProperty(name);
    }

    // Getter for name
    public String getName() {
        return name.get();
    }

    // Setter for name
    public void setName(String name) {
        this.name.set(name);
    }

    // JavaFX property getter (important for TableView binding)
    public StringProperty nameProperty() {
        return name;
    }
}
