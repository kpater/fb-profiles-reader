package pl.sointeractive.fb_profiles_reader.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Facebook facebookProfile = facebookService.findById("9");
        assertNotNull(facebookProfile);
        assertEquals("Lewandowski", facebookProfile.getLastName());
    }

    @Test
    public void testFindByIdNotFound() throws NotFoundException {
        try {
            facebookService.findById("-1");
            fail();
        } catch (NotFoundException e) {
            assertEquals("Id -1 not found", e.getMessage());
        }
    }

    @Test
    public void testFindMostCommonWords() {
        assertEquals(3, facebookService.findMostCommonWords().get("gol").longValue());
        assertEquals(2, facebookService.findMostCommonWords().get("brawo").longValue());
    }

    @Test
    public void testFindPostIdsByKeyword() {
        assertTrue(facebookService.findPostIdsByKeyword("rikoszet").contains("5"));
        assertEquals(new HashSet<String>(Arrays.asList("14", "15", "16")), facebookService.findPostIdsByKeyword("Gol"));
    }

    private List<Facebook> getFacebookProfiles() {
        List<Facebook> facebookProfiles = new ArrayList<Facebook>();
        List<Post> posts;
        Facebook facebookProfile;

        facebookProfile = new Facebook("1");
        facebookProfile.setFirstName("Wojciech");
        facebookProfile.setLastName("Szczęsny");
        posts = new ArrayList<Post>();
        posts.add(getPost("1", "Wreszcie pierwsza 11 :)"));
        posts.add(getPost("2", "Nie było co robić"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("3");
        facebookProfile.setFirstName("Artur");
        facebookProfile.setLastName("Jedrzejczyk");
        posts = new ArrayList<Post>();
        posts.add(getPost("4", "Brawo, brawo, brawo"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("2");
        facebookProfile.setFirstName("Michal");
        facebookProfile.setLastName("Pazdan");
        posts = new ArrayList<Post>();
        posts.add(getPost("5", "Jak pech to pech: Nie dość że rikoszet, to jeszcze ręką"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("4");
        facebookProfile.setFirstName("Tiago");
        facebookProfile.setLastName("Cionek");
        posts = new ArrayList<Post>();
        posts.add(getPost("6", "Swoje zrobiłem"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("20");
        facebookProfile.setFirstName("Lukasz");
        facebookProfile.setLastName("Piszczek");
        posts = new ArrayList<Post>();
        posts.add(getPost("7", "Dobrze było znowu pograć z Kubą i Robertem"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("11");
        facebookProfile.setFirstName("Kamil");
        facebookProfile.setLastName("Grosicki");
        posts = new ArrayList<Post>();
        posts.add(getPost("8", "Turbo mi się kilka razy włączyło, szkoda tylko że nic nie wpadło"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("8");
        facebookProfile.setFirstName("Karol");
        facebookProfile.setLastName("Linetty");
        posts = new ArrayList<Post>();
        posts.add(getPost("9", "Doświadczenie zebrane"));
        posts.add(getPost("10", "Kilka drobnych błędów, ale ogólnie na plus"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("5");
        facebookProfile.setFirstName("Krzysztof");
        facebookProfile.setLastName("Maczynski");
        posts = new ArrayList<Post>();
        posts.add(getPost("11", "Wrócę silniejszy"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("16");
        facebookProfile.setFirstName("Jakub");
        facebookProfile.setLastName("Blaszczykowski");
        posts = new ArrayList<Post>();
        posts.add(getPost("12", "Spokojnie, spokojnie, jeszcze 4 mecze przed nami"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("19");
        facebookProfile.setFirstName("Piotr");
        facebookProfile.setLastName("Zielinski");
        posts = new ArrayList<Post>();
        posts.add(getPost("13", "Robertowi brakowało bramki do skompletowania hat-tricka to mu karnego wypracowałem"));
        facebookProfile.setPosts(posts);
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("9");
        facebookProfile.setFirstName("Robert");
        facebookProfile.setLastName("Lewandowski");
        posts = new ArrayList<Post>();
        posts.add(getPost("14", "Gol"));
        posts.add(getPost("15", "Gol"));
        posts.add(getPost("16", "Gol"));
        posts.add(getPost("17", "To piłkę sobie na pamiątkę do domu wziąłem. Brawo Polska!"));
        facebookProfile.setPosts(posts);
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
