package pl.sointeractive.fb_profiles_reader.service;

import java.io.IOException;
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
import java.util.stream.Collectors;
import java.util.stream.Stream;

import pl.sointeractive.fb_profiles_reader.exception.NotFoundException;
import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;
import pl.sointeractive.fb_profiles_reader.fb_profile.Post;

public class DefaultFacebookService implements FacebookService {

    List<Facebook> facebookProfilesSortedById;

    public DefaultFacebookService(List<Facebook> facebookProfiles) throws IOException {
        this.facebookProfilesSortedById = facebookProfiles;
        this.facebookProfilesSortedById.sort(Comparator.comparing(Facebook::getId));
    }

    @Override
    public Facebook findById(String id) throws NotFoundException {
        // return facebookProfilesSortedById.stream().filter(p -> p.getId().equals(id)).findFirst()
        // .orElseThrow(() -> new NotFoundException(id));
        int i = Collections.binarySearch(facebookProfilesSortedById, new Facebook(id),
                Comparator.comparing(Facebook::getId));
        if (i < 0) {
            throw new NotFoundException(id);
        }
        return facebookProfilesSortedById.get(i);
    }

    @Override
    public Map<String, Long> findMostCommonWords() {
        return getPostStream().map(Post::getMessageAsWords).filter(Objects:nonNull).flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));
    }

    @Override
    public Set<String> findPostIdsByKeyword(String word) {
        return getPostStream().filter(post -> post.hasWord(word)).map(Post::getId)
                .collect(Collectors.toCollection(HashSet::new));
    }

    @Override
    public Set<Facebook> findAll() {
        return facebookProfilesSortedById.stream().sorted(facebookProfileFirstNameLastNameComparator())
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private Comparator<Facebook> facebookProfileFirstNameLastNameComparator() {
        return Comparator.<Facebook, String>comparing(Facebook::getFirstName)
                .thenComparing(Comparator.comparing(Facebook::getLastName));
    }

    private Stream<Post> getPostStream() {
        return facebookProfilesSortedById.stream().map(Facebook::getPosts).filter(Objects::nonNull)
                .flatMap(Collection::stream);
    }

}
