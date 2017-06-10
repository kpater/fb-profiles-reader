package pl.sointeractive.fb_profiles_reader.service;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import junit.framework.TestCase;
import pl.sointeractive.fb_profiles_reader.exception.NotFoundException;
import pl.sointeractive.fb_profiles_reader.fb_profile.Facebook;

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

    private List<Facebook> getFacebookProfiles() {
        List<Facebook> facebookProfiles = new ArrayList<Facebook>();
        Facebook facebookProfile;

        facebookProfile = new Facebook("100");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1001");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("500");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("300");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1300");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("10");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("140");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1004");
        facebookProfiles.add(facebookProfile);

        facebookProfile = new Facebook("1050");
        facebookProfiles.add(facebookProfile);

        return facebookProfiles;
    }
}
