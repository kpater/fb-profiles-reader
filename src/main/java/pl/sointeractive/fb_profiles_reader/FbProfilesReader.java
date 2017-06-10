package pl.sointeractive.fb_profiles_reader;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import pl.sointeractive.fb_profiles_reader.data_loader.FacebookProfilesLoader;
import pl.sointeractive.fb_profiles_reader.data_loader.FileFacebookProfileLoader;
import pl.sointeractive.fb_profiles_reader.exception.NotFoundException;
import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;
import pl.sointeractive.fb_profiles_reader.properties.ApplicationProperties;
import pl.sointeractive.fb_profiles_reader.service.DefaultFacebookService;
import pl.sointeractive.fb_profiles_reader.service.FacebookService;

public class FbProfilesReader {
    private static final Logger LOGGER = Logger.getLogger(FbProfilesReader.class.getName());

    public static void main(String[] args) throws IOException {
        FacebookProfilesLoader facebookProfilesLoader = new FileFacebookProfileLoader();
        List<Facebook> facebookProfiles = facebookProfilesLoader
                .loadProfiles(Paths.get(ApplicationProperties.JSON_DIRECTORY));

        FacebookService facebookService = new DefaultFacebookService(facebookProfiles);

        Set<Facebook> allFacebookProfiles = facebookService.findAll();

        System.out.format("Found %d profiles:\n", allFacebookProfiles.size());
        for (Facebook profile : allFacebookProfiles) {
            System.out.println(" - " + profile.getId() + " " + profile.getFirstName() + " " + profile.getLastName());
        }

        try {
            Facebook profile1 = facebookService.findById("4");
            System.out.format("Profile %s found\n", profile1.getId());
            Facebook profile2 = facebookService.findById("24");
            System.out.format("Profile %s found\n", profile2.getId());
        } catch (NotFoundException e) {
            LOGGER.info(e.getMessage());
        }

        System.out.println("Post words: " + facebookService.findMostCommonWords());

        System.out.println("Word posts: " + facebookService.findPostIdsByKeyword("lighting"));
    }

}
