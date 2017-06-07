package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.util.Date;

import org.junit.Test;

import junit.framework.TestCase;
import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;
import pl.sointeractive.fb_profiles_reader.fb_profile.Gender;

public class FbProfileLoaderTest extends TestCase {

    File file;
    FbProfileLoader fbProfileLoader;
    FbProfile fbProfile;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        file = new File("src/test/resources/f1.json");
        fbProfileLoader = new FbProfileLoaderImpl();
        fbProfile = fbProfileLoader.loadFbProfile(file);
    }

    @Test
    public void testProfileId() {
        assertEquals(1, fbProfile.getId());
    }

    @Test
    public void testProfileBirthday() {
        // LocalDate expectedDate =
        // Instant.ofEpochMilli(401280850089L).atZone(ZoneId.systemDefault()).toLocalDate();
        Date expectedDate = new Date(401280850089L);
        assertEquals(expectedDate, fbProfile.getBirthday());
    }

    @Test
    public void testFirstName() {
        assertEquals("Luna", fbProfile.getFirstName());
    }

    @Test
    public void testLastName() {
        assertEquals("Kling", fbProfile.getLastName());
    }

    @Test
    public void testOccupation() {
        assertEquals("Direct Applications Administrator", fbProfile.getOccupation());
    }

    @Test
    public void testGender() {
        assertEquals(Gender.FEMALE, fbProfile.getGender());
    }

    @Test
    public void testWork() {
        assertEquals("Lebsack - Rippin", fbProfile.getWork());
    }

    @Test
    public void testSchool() {
        assertEquals("Walter, Cartwright and Jerde", fbProfile.getSchool());
    }
}
