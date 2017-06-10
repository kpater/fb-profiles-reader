package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;

public interface FacebookProfilesLoader {
    Facebook loadFacebookProfile(File file) throws IOException;

    List<Facebook> loadProfiles(Path directoryPath) throws IOException;
}
