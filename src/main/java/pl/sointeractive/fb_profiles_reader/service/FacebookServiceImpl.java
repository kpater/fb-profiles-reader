package pl.sointeractive.fb_profiles_reader.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashSet;
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
import pl.sointeractive.fb_profiles_reader.fb_profile.Post;
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
        return fbProfilesSortedById.stream().filter(p -> p.getId().equals(id)).findFirst()
                .orElseThrow(() -> new NotFoundException(id));
    }

    @Override
    public Map<String, Long> findMostCommonWords() {
        return fbProfilesSortedById.stream().map(FbProfile::getPosts).flatMap(Collection::stream)
                .map(Post::getMessageAsWords).flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    @Override
    public Set<String> findPostIdsByKeyword(String word) {
        return fbProfilesSortedById.stream().map(FbProfile::getPosts).flatMap(Collection::stream)
                .filter(post -> post.hasWord(word)).map(Post::getId).collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Set<FbProfile> findAll() {
        return fbProfilesSortedById.stream().sorted(fbProfileFirstNameLastNameComparaotr())
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

    private Comparator<FbProfile> fbProfileFirstNameLastNameComparaotr() {
        Comparator<FbProfile> comparator = Comparator.comparing(FbProfile::getFirstName);
        comparator = comparator.thenComparing(Comparator.comparing(FbProfile::getLastName));
        return comparator;
    }

}
