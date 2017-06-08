package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;

public interface FbProfileLoader {
    FbProfile loadFbProfile(File file) throws JsonParseException, JsonMappingException, IOException;
}
