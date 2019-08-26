package cz.nguyenngocanh.aps.model;

public class PrimaryKey {
    String name;
    String type;

    public String getName() {
        return name;
    }

    public PrimaryKey setName(String name) {
        this.name = name;
        return this;
    }

    public String getType() {
        return type;
    }

    public PrimaryKey setType(String type) {
        this.type = type;
        return this;
    }
}
