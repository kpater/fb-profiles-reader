package pl.sointeractive.fb_profiles_reader.service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import pl.sointeractive.fb_profiles_reader.data_loader.FbProfilesLoader;
import pl.sointeractive.fb_profiles_reader.exception.NotFoundException;
import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;
import pl.sointeractive.fb_profiles_reader.fb_profile.Post;
import pl.sointeractive.fb_profiles_reader.properties.ApplicationProperties;

public class DefaultFacebookService implements FacebookService {

    private static final Logger LOGGER = Logger.getLogger(DefaultFacebookService.class.getName());

    FbProfilesLoader fbProfilesLoader;

    List<Facebook> fbProfilesSortedById;

    public DefaultFacebookService(FbProfilesLoader fbProfilesLoader) throws IOException {
        this.fbProfilesLoader = fbProfilesLoader;
        loadFbProfiles();
    }

    @Override
    public Facebook findById(String id) throws NotFoundException {
        // return fbProfilesSortedById.stream().filter(p -> p.getId().equals(id)).findFirst()
        // .orElseThrow(() -> new NotFoundException(id));
        int i = Collections.binarySearch(fbProfilesSortedById, new Facebook(id),
                Comparator.comparing(Facebook::getId));
        if (i < 0) {
            throw new NotFoundException(id);
        }
        return fbProfilesSortedById.get(i);
    }

    @Override
    public Map<String, Long> findMostCommonWords() {
        return fbProfilesSortedById.stream().map(Facebook::getPosts).flatMap(Collection::stream)
                .map(Post::getMessageAsWords).flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    @Override
    public Set<String> findPostIdsByKeyword(String word) {
        return fbProfilesSortedById.stream().map(Facebook::getPosts).flatMap(Collection::stream)
                .filter(post -> post.hasWord(word)).map(Post::getId).collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Set<Facebook> findAll() {
        return fbProfilesSortedById.stream().sorted(fbProfileFirstNameLastNameComparaotr())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private void loadFbProfiles() throws IOException {
        fbProfilesSortedById = Files.walk(Paths.get(ApplicationProperties.JSON_DIRECTORY)).filter(Files::isRegularFile)
                .map(Path::toFile).map(file2FbProfile()).filter(Objects::nonNull)
                .sorted(Comparator.comparing(Facebook::getId)).collect(Collectors.toCollection(ArrayList::new));
    }

    private Function<File, Facebook> file2FbProfile() {
        return (File file) -> {
            try {
                return fbProfilesLoader.loadFbProfile(file);
            } catch (IOException e) {
                LOGGER.severe(String.format("Cannot load FB profile from file %s", file.getAbsolutePath()));
                e.printStackTrace();
            }
            return null;
        };
    }

    private Comparator<Facebook> fbProfileFirstNameLastNameComparaotr() {
        return Comparator.<Facebook, String>comparing(Facebook::getFirstName)
                .thenComparing(Comparator.comparing(Facebook::getLastName));
    }

}
