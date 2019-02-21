package test.firebase.mjd.fbpushnot;

public class Users extends UserID{
    String name, image;

    public  Users(){

    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Users(String name, String image) {
        this.name = name;
        this.image = image;
    }
}
