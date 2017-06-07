package pl.sointeractive.fb_profiles_reader.fb_profile;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FbProfile {
    long id;
    @JsonIgnore
    Date birthDate;
    @JsonIgnore
    String firstName;
    @JsonIgnore
    String lastName;
    @JsonIgnore
    String occupation;
    @JsonIgnore
    Gender gender;
    @JsonIgnore
    City city;
    @JsonIgnore
    String work;
    @JsonIgnore
    List<FbProfile> friends;
    @JsonIgnore
    String school;
    @JsonIgnore
    City location;
    @JsonIgnore
    Relationship relationship;
    @JsonIgnore
    List<Post> posts;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

}
