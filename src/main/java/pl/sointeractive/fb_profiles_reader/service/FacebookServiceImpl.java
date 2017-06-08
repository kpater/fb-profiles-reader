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
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import pl.sointeractive.fb_profiles_reader.data_loader.FbProfilesLoader;
import pl.sointeractive.fb_profiles_reader.data_loader.FbProfilesLoaderImpl;
import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;
import pl.sointeractive.fb_profiles_reader.properties.ApplicationProperties;

public class FacebookServiceImpl implements FacebookService {

    FbProfilesLoader fbProfilesLoader = new FbProfilesLoaderImpl();
    Set<FbProfile> fbProfiles;

    @Override
    public FbProfile findById(String id) throws NotFoundException {
        loadFbProfilesIfNotLoaded();
        return fbProfiles.stream().filter(p -> p.getId() == Long.valueOf(id)).findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Profile with id %s not found", id)));
    }

    @Override
    public Map<String, Long> findMostCommonWords() {
        loadFbProfilesIfNotLoaded();
        return fbProfiles.stream().flatMap(p -> p.getPosts().stream()).map(p -> p.getMessage()).map(m -> m.split(" "))
                .flatMap(Arrays::stream).collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    @Override
    public Set<String> findPostIdsByKeyword(String word) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Set<FbProfile> findAll() {
        fbProfiles = null;
        try {
            fbProfiles = Files.walk(Paths.get(ApplicationProperties.JSON_DIRECTORY)).filter(Files::isRegularFile)
                    .map(Path::toFile).map(getFile2FbProfileFunction()).sorted(getFbProfileComparator())
                    .collect(Collectors.toCollection(LinkedHashSet::new));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fbProfiles;
    }

    private Function<File, FbProfile> getFile2FbProfileFunction() {
        return (File f) -> {
            try {
                return fbProfilesLoader.loadFbProfile(f);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        };
    }

    private Comparator<FbProfile> getFbProfileComparator() {
        Comparator<FbProfile> comparator = Comparator.comparing(fbProfile -> fbProfile.getFirstName());
        comparator = comparator.thenComparing(Comparator.comparing(fbProfile -> fbProfile.getLastName()));
        return comparator;
    }

    private void loadFbProfilesIfNotLoaded() {
        if (fbProfiles == null) {
            fbProfiles = findAll();
        }
    }
}
