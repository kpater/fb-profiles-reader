package pl.sointeractive.fb_profiles_reader.fb_profile;

import java.util.Arrays;

import pl.sointeractive.fb_profiles_reader.util.StringUtils;

public class Post {
    String id;
    String message;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean hasWord(String word) {
        return Arrays.asList(message.split(StringUtils.WORD_REGEX)).contains(word);
    }

    public String[] getMessageAsWords() {
        return message.split(StringUtils.WORD_REGEX);
    }
}
