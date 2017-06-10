package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.io.IOException;

import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;

public interface FbProfilesLoader {
    Facebook loadFbProfile(File file) throws IOException;
}
