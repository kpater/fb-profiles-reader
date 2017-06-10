package pl.sointeractive.fb_profiles_reader;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import pl.sointeractive.fb_profiles_reader.data_loader.FacebookProfilesLoaderTest;

public class FbProfilesReaderTest extends TestCase {

    public FbProfilesReaderTest(String testName) {
        super(testName);
    }

    public static Test suite() {
        return new TestSuite(FacebookProfilesLoaderTest.class);
    }

}
