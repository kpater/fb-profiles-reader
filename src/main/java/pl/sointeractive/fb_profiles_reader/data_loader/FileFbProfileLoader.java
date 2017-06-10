package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;

public class FileFbProfileLoader implements FbProfilesLoader {

    @Override
    public Facebook loadFbProfile(File file) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
        Facebook profile = mapper.readValue(file, Facebook.class);
        return profile;
    }

}
