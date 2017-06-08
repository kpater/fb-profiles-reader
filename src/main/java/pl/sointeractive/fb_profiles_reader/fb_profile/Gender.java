package pl.sointeractive.fb_profiles_reader.fb_profile;

public enum Gender {
    MALE("male"), FEMALE("female");

    // private static Map<String, Gender> FORMAT_MAP =
    // Stream.of(Gender.values())
    // .collect(Collectors.toMap(s -> s.formatted, Function.identity()));

    private final String jsonFormat;

    Gender(String jsonFormat) {
        this.jsonFormat = jsonFormat;
    }

    // @JsonCreator
    // public static Gender fromString(String string) {
    // return Optional.ofNullable(FORMAT_MAP.get(string)).orElseThrow(() -> new
    // IllegalArgumentException(string));
    // }

    @Override
    public String toString() {
        return jsonFormat;
    }
}
