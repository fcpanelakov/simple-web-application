package cz.nguyenngocanh.aps.model;

/**
 * Column
 * Primary key data type
 */
public class PrimaryKey {
    private String name;
    private String type;

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
