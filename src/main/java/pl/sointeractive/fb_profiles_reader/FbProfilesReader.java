package pl.sointeractive.fb_profiles_reader;

import java.io.IOException;
import java.util.Set;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pl.sointeractive.fb_profiles_reader.data_loader.FbProfilesLoader;
import pl.sointeractive.fb_profiles_reader.data_loader.FbProfilesLoaderImpl;
import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;
import pl.sointeractive.fb_profiles_reader.service.FacebookService;
import pl.sointeractive.fb_profiles_reader.service.FacebookServiceImpl;
import pl.sointeractive.fb_profiles_reader.service.NotFoundException;

/**
 * Hello world!
 *
 */
public class FbProfilesReader {

    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        FbProfilesLoader fbProfileLoader = new FbProfilesLoaderImpl();
        FacebookService facebookService = new FacebookServiceImpl();

        Set<FbProfile> fbProfiles = facebookService.findAll();
        System.out.println(fbProfiles.size());
        for (FbProfile profile : fbProfiles) {
            System.out.println(profile.getFirstName() + " " + profile.getLastName());
        }

        FbProfile profile = null;
        try {
            profile = facebookService.findById("4");
        } catch (NotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println(profile.getId());

        System.out.println(facebookService.findMostCommonWords());
    }

}
