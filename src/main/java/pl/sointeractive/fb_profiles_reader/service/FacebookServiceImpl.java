package pl.sointeractive.fb_profiles_reader.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import pl.sointeractive.fb_profiles_reader.data_loader.FbProfilesLoader;
import pl.sointeractive.fb_profiles_reader.data_loader.FbProfilesLoaderImpl;
import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;
import pl.sointeractive.fb_profiles_reader.properties.ApplicationProperties;

public class FacebookServiceImpl implements FacebookService {

    private static final Logger LOGGER = Logger.getLogger(FacebookServiceImpl.class.getName());

    FbProfilesLoader fbProfilesLoader = new FbProfilesLoaderImpl();
    Set<FbProfile> fbProfilesSortedById;

    public FacebookServiceImpl() throws IOException {
        loadFbProfiles();
    }

    @Override
    public FbProfile findById(String id) throws NotFoundException {
        return fbProfilesSortedById.stream().filter(p -> p.getId() == Long.valueOf(id)).findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Profile with id %s not found", id)));
    }

    @Override
    public Map<String, Long> findMostCommonWords() {
        return fbProfilesSortedById.stream().flatMap(p -> p.getPosts().stream()).map(p -> p.getMessage().split(" "))
                .flatMap(Arrays::stream).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    @Override
    public Set<String> findPostIdsByKeyword(String word) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<FbProfile> findAll() {

        return fbProfilesSortedById.stream().sorted(getFbProfileComparator())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void loadFbProfiles() throws IOException {
        fbProfilesSortedById = Files.walk(Paths.get(ApplicationProperties.JSON_DIRECTORY)).filter(Files::isRegularFile)
                .map(Path::toFile).map(getFile2FbProfileFunction()).filter(Objects::nonNull)
                .sorted(Comparator.comparing(FbProfile::getId)).collect(Collectors.toCollection(LinkedHashSet::new));

    }

    private Function<File, FbProfile> getFile2FbProfileFunction() {
        return (File file) -> {
            try {
                return fbProfilesLoader.loadFbProfile(file);
            } catch (IOException e) {
                LOGGER.severe(String.format("Cannot load FB profile from file %s", file.getAbsolutePath()));
            }
            return null;
        };
    }

    private Comparator<FbProfile> getFbProfileComparator() {
        Comparator<FbProfile> comparator = Comparator.comparing(FbProfile::getFirstName);
        comparator = comparator.thenComparing(Comparator.comparing(FbProfile::getLastName));
        return comparator;
    }

}
