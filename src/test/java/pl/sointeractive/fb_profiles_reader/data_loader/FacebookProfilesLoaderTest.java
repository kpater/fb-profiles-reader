package pl.sointeractive.fb_profiles_reader.data_loader;

import java.io.File;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import pl.sointeractive.fb_profiles_reader.fb_profile.City;
import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;
import pl.sointeractive.fb_profiles_reader.fb_profile.Gender;
import pl.sointeractive.fb_profiles_reader.fb_profile.Post;
import pl.sointeractive.fb_profiles_reader.fb_profile.Relationship;

public class FacebookProfilesLoaderTest extends TestCase {

    File file;
    FacebookProfilesLoader facebookProfileLoader;
    Facebook facebookProfile;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        file = new File("src/test/resources/f1.json");
        facebookProfileLoader = new FileFacebookProfileLoader();
        facebookProfile = facebookProfileLoader.loadFacebookProfile(file);
    }

    @Test
    public void testProfileId() {
        assertEquals("1", facebookProfile.getId());
    }

    @Test
    public void testProfileBirthday() {
        LocalDateTime expectedDate = Instant.ofEpochMilli(401280850089L).atZone(ZoneId.systemDefault())
                .toLocalDateTime();
        assertEquals(expectedDate, facebookProfile.getBirthday());
    }

    @Test
    public void testFirstName() {
        assertEquals("Luna", facebookProfile.getFirstName());
    }

    @Test
    public void testLastName() {
        assertEquals("Kling", facebookProfile.getLastName());
    }

    @Test
    public void testOccupation() {
        assertEquals("Direct Applications Administrator", facebookProfile.getOccupation());
    }

    @Test
    public void testGender() {
        assertEquals(Gender.FEMALE, facebookProfile.getGender());
    }

    @Test
    public void testWork() {
        assertEquals("Lebsack - Rippin", facebookProfile.getWork());
    }

    @Test
    public void testFrieds() {
        List<Long> friendIds = facebookProfile.getFriendIds();
        assertNotNull(friendIds);
        assertEquals(14, friendIds.size());
        assertEquals(38L, friendIds.get(1).longValue());
    }

    @Test
    public void testSchool() {
        assertEquals("Walter, Cartwright and Jerde", facebookProfile.getSchool());
    }

    @Test
    public void testCity() {
        City city = facebookProfile.getCity();
        assertEquals("United Kingdom", city.getCountry());
        assertEquals("London", city.getCity());
        assertEquals("England", city.getState());
        assertEquals(-0.12574, city.getCoordinates().getLongitude());
        assertEquals(51.50853, city.getCoordinates().getLatitude());
    }

    @Test
    public void testLocation() {
        assertEquals("London", facebookProfile.getLocationCity());
    }

    @Test
    public void testRelationship() {
        assertEquals(Relationship.MARRIED, facebookProfile.getRelationship());
    }

    @Test
    public void testPosts() {
        List<Post> posts = facebookProfile.getPosts();
        assertNotNull(posts);
        assertEquals(3, posts.size());

        Post post = posts.get(2);
        assertEquals("3", post.getId());
        assertNotNull(post.getMessage());
    }
}
