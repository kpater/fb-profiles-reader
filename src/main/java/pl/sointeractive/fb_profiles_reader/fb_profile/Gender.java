package pl.sointeractive.fb_profiles_reader.fb_profile;

public enum Gender {
    MALE("male"), FEMALE("female");

    private final String jsonFormat;

    Gender(String jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    @Override
    public String toString() {
        return jsonFormat;
    }
}
