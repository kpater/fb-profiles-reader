package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import pl.sointeractive.fb_profiles_reader.fb_profile.City;
import pl.sointeractive.fb_profiles_reader.fb_profile.FbProfile;
import pl.sointeractive.fb_profiles_reader.fb_profile.Gender;
import pl.sointeractive.fb_profiles_reader.fb_profile.Post;
import pl.sointeractive.fb_profiles_reader.fb_profile.Relationship;

public class FbProfilesLoaderTest extends TestCase {

    File file;
    FbProfilesLoader fbProfileLoader;
    FbProfile fbProfile;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        file = new File("src/test/resources/f1.json");
        fbProfileLoader = new FileFbProfileLoader();
        fbProfile = fbProfileLoader.loadFbProfile(file);
    }

    @Test
    public void testProfileId() {
        assertEquals(1, fbProfile.getId());
    }

    @Test
    public void testProfileBirthday() {
        LocalDateTime expectedDate = Instant.ofEpochMilli(401280850089L).atZone(ZoneId.systemDefault())
                .toLocalDateTime();
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
    public void testFrieds() {
        List<Long> friendIds = fbProfile.getFriendIds();
        assertNotNull(friendIds);
        assertEquals(14, friendIds.size());
        assertEquals(38L, friendIds.get(1).longValue());
    }

    @Test
    public void testSchool() {
        assertEquals("Walter, Cartwright and Jerde", fbProfile.getSchool());
    }

    @Test
    public void testCity() {
        City city = fbProfile.getCity();
        assertEquals("United Kingdom", city.getCountry());
        assertEquals("London", city.getCity());
        assertEquals("England", city.getState());
        assertEquals(-0.12574, city.getCoordinates().getLongitude());
        assertEquals(51.50853, city.getCoordinates().getLatitude());
    }

    @Test
    public void testLocation() {
        assertEquals("London", fbProfile.getLocationCity());
    }

    @Test
    public void testRelationship() {
        assertEquals(Relationship.MARRIED, fbProfile.getRelationship());
    }

    @Test
    public void testPosts() {
        List<Post> posts = fbProfile.getPosts();
        assertNotNull(posts);
        assertEquals(3, posts.size());

        Post post = posts.get(2);
        assertEquals(3, post.getId());
        assertNotNull(post.getMessage());
    }
}
