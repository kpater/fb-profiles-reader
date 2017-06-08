package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.io.IOException;

import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;

public interface FbProfilesLoader {
    FbProfile loadFbProfile(File file) throws IOException;
}
