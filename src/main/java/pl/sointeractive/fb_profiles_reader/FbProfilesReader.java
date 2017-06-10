package pl.sointeractive.fb_profiles_reader;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import pl.sointeractive.fb_profiles_reader.data_loader.FileFbProfileLoader;
import pl.sointeractive.fb_profiles_reader.exception.NotFoundException;
import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;
import pl.sointeractive.fb_profiles_reader.service.DefaultFacebookService;
import pl.sointeractive.fb_profiles_reader.service.FacebookService;

public class FbProfilesReader {
    private static final Logger LOGGER = Logger.getLogger(FbProfilesReader.class.getName());

    public static void main(String[] args) throws IOException {
        FacebookService facebookService = new DefaultFacebookService(new FileFbProfileLoader());

        Set<Facebook> fbProfiles = facebookService.findAll();

        System.out.format("Found %d profiles:\n", fbProfiles.size());
        for (Facebook profile : fbProfiles) {
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
