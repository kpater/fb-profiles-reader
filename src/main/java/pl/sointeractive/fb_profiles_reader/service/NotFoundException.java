package pl.sointeractive.fb_profiles_reader.service;

public class NotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String id) {
        super(String.format("Id %s not found", id));
    }
}
