package project.estateWatch;

public class item {
    int background ;
    String profileName;
    int profilePhoto;
    String nbCall;

    public item()
    {}

    public item(int background, String profileName, int profilePhoto, String nbCall) {
        this.background = background;
        this.profileName = profileName;
        this.profilePhoto = profilePhoto;
        this.nbCall = nbCall;
    }

    public int getBackground() {
        return background;
    }

    public String getProfileName() {
        return profileName;
    }

    public int getProfilePhoto() {
        return profilePhoto;
    }

    public String getNbCall() {
        return nbCall;
    }

    public void setBackground(int background) {
        this.background = background;
    }

    public void setProfileName(String profileName) {
        this.profileName = profileName;
    }

    public void setProfilePhoto(int profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public void setNbCall (String nbCall) {
        this.nbCall = nbCall;
    }
}
