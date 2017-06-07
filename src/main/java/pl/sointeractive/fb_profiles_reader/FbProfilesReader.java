package pl.sointeractive.fb_profiles_reader;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import pl.sointeractive.fb_profiles_reader.data_loader.FbProfileLoader;
import pl.sointeractive.fb_profiles_reader.data_loader.FbProfileLoaderImpl;
import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;

/**
 * Hello world!
 *
 */
public class FbProfilesReader {
    public static void main(String[] args) {
        FbProfileLoader loader = new FbProfileLoaderImpl();
        try {
            FbProfile fbProfile = loader.loadFbProfile(new File("src/test/resources/f1.json"));
            System.out.println(fbProfile.getId());
        } catch (JsonParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JsonMappingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
