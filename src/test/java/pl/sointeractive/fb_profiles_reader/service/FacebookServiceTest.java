package pl.sointeractive.fb_profiles_reader.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import pl.sointeractive.fb_profiles_reader.exception.NotFoundException;
import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;
import pl.sointeractive.fb_profiles_reader.fb_profile.Post;

public class FacebookServiceTest extends TestCase {

    FacebookService facebookService;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        facebookService = new DefaultFacebookService(getFacebookProfiles());
    }

    @Test
    public void testFindByIdFound() throws NotFoundException {
        Facebook facebookProfile = facebookService.findById("1001");
        assertNotNull(facebookProfile);
        assertEquals("1001", facebookProfile.getId());
    }

    @Test
    public void testFindByIdNotFound() throws NotFoundException {
        try {
            Facebook facebookProfile = facebookService.findById("-1");
            fail();
        } catch (NotFoundException e) {
            assertEquals("Id -1 not found", e.getMessage());
        }
    }

    @Test
    public void testFindMostCommonWords() {
        assertEquals(6, facebookService.findMostCommonWords().get("test").longValue());
        assertEquals(4, facebookService.findMostCommonWords().get("message").longValue());
    }

    private List<Facebook> getFacebookProfiles() {
        List<Facebook> facebookProfiles = new ArrayList<Facebook>();
        List<Post> posts;
        Facebook facebookProfile;

        facebookProfile = new Facebook("100");
        posts = new ArrayList<Post>();
        posts.add(getPost("1", "First message"));
        posts.add(getPost("2", "Second message"));
        posts.add(getPost("3", "Thrid message"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1");
        posts = new ArrayList<Post>();
        posts.add(getPost("1", "My message"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1001");
        posts = new ArrayList<Post>();
        posts.add(getPost("1", "Used word test"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("500");
        posts = new ArrayList<Post>();
        posts.add(getPost("1", "test word to be counted"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("300");
        posts = new ArrayList<Post>();
        posts.add(getPost("1", "check test word"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1300");
        posts = new ArrayList<Post>();
        posts.add(getPost("1", "here there is also test word"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("10");
        posts = new ArrayList<Post>();
        posts.add(getPost("1", "Another test word here"));
        posts.add(getPost("1", "But not here"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("140");
        posts = new ArrayList<Post>();
        posts.add(getPost("1", "Another test word here"));
        posts.add(getPost("1", "But not here"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1004");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1050");
        facebookProfiles.add(facebookProfile);

        return facebookProfiles;
    }

    private Post getPost(String id, String message) {
        Post post = new Post();
        post.setId(id);
        post.setMessage(message);
        return post;
    }
}
