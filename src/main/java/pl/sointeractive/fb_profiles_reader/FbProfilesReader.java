package pl.sointeractive.fb_profiles_reader;

import java.io.IOException;
import java.util.Set;
import java.util.logging.Logger;

import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;
import pl.sointeractive.fb_profiles_reader.service.FacebookService;
import pl.sointeractive.fb_profiles_reader.service.FacebookServiceImpl;
import pl.sointeractive.fb_profiles_reader.service.NotFoundException;

/**
 * Hello world!
 *
 */
public class FbProfilesReader {
    private static final Logger LOGGER = Logger.getLogger(FbProfilesReader.class.getName());

    public static void main(String[] args) throws IOException {
        FacebookService facebookService = new FacebookServiceImpl();

        Set<FbProfile> fbProfiles = facebookService.findAll();
        System.out.println(fbProfiles.size());
        for (FbProfile profile : fbProfiles) {
            System.out.println(profile.getFirstName() + " " + profile.getLastName());
        }

        FbProfile profile1 = null;
        FbProfile profile2 = null;
        try {
            profile1 = facebookService.findById("4");
            profile2 = facebookService.findById("24");
        } catch (NotFoundException e) {
            LOGGER.info(e.getMessage());
        }
        System.out.println(profile1.getId());

        System.out.println(facebookService.findMostCommonWords());

        System.out.println(facebookService.findPostIdsByKeyword("the"));
    }

}
