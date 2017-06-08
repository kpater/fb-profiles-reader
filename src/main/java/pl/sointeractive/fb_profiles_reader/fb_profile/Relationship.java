package pl.sointeractive.fb_profiles_reader.fb_profile;

public enum Relationship {
    MARRIED("Married");

    String jsonFormat;

    Relationship(String jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    @Override
    public String toString() {
        return jsonFormat;
    }
}
