package pl.sointeractive.fb_profiles_reader.fb_profile;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {
    @JsonProperty("countryName")
    String country;
    @JsonProperty("cityName")
    String city;
    @JsonProperty("stateName")
    String state;
    @JsonProperty("coords")
    Coordinates coordinates;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

}
