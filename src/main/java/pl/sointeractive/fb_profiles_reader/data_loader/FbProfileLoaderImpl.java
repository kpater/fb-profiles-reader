package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;

public class FbProfileLoaderImpl implements FbProfileLoader {

    public FbProfile loadFbProfile(File file) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        FbProfile profile = mapper.readValue(file, FbProfile.class);
        return profile;
    }

}
