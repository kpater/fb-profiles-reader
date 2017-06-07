package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import junit.framework.TestCase;
import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;

public class FbProfileLoaderTest extends TestCase {

    FbProfileLoader fbProfileLoader = new FbProfileLoaderImpl();

    @Test
    public void testAdd() throws JsonParseException, JsonMappingException, IOException {
        File file = new File("src/test/resources/f1.json");
        FbProfile fbProfile = fbProfileLoader.loadFbProfile(file);
        assertEquals(1, fbProfile.getId());
    }
}
