package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;

public class FileFbProfileLoader implements FbProfilesLoader {

    @Override
    public FbProfile loadFbProfile(File file) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
        FbProfile profile = mapper.readValue(file, FbProfile.class);
        return profile;
    }

}
