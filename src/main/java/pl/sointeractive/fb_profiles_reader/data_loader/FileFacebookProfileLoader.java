package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;

import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;
import pl.sointeractive.fb_profiles_reader.service.DefaultFacebookService;

public class FileFacebookProfileLoader implements FacebookProfilesLoader {

    private static final Logger LOGGER = Logger.getLogger(DefaultFacebookService.class.getName());

    @Override
    public Facebook loadFacebookProfile(File file) throws JsonParseException, JsonMappingException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setPropertyNamingStrategy(PropertyNamingStrategy.LOWER_CASE);
        mapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING, true);
        mapper.configure(DeserializationFeature.USE_LONG_FOR_INTS, true);
        Facebook profile = mapper.readValue(file, Facebook.class);
        return profile;
    }

    @Override
    public List<Facebook> loadProfiles(Path directoryPath) throws IOException {
        return Files.walk(directoryPath).filter(Files::isRegularFile).map(Path::toFile).map(file2FacebookProfile())
                .filter(Objects::nonNull).collect(Collectors.toCollection(ArrayList::new));
    }

    private Function<File, Facebook> file2FacebookProfile() {
        return (File file) -> {
            try {
                return loadFacebookProfile(file);
            } catch (IOException e) {
                LOGGER.severe(String.format("Cannot load FB profile from file %s", file.getAbsolutePath()));
                e.printStackTrace();
            }
            return null;
        };
    }

}
