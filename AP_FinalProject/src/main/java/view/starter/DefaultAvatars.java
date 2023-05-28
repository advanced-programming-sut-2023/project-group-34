package view.starter;

public enum DefaultAvatars {

    A1(DefaultAvatars.class.getResource("/images/registerAvatar/1.png").toString(), 1),
    A2(DefaultAvatars.class.getResource("/images/registerAvatar/2.png").toString(), 2),
    A3(DefaultAvatars.class.getResource("/images/registerAvatar/3.png").toString(), 3),
    A4(DefaultAvatars.class.getResource("/images/registerAvatar/4.png").toString(), 4);

    private int number;
    private String link;

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    DefaultAvatars(String link, int number){
        this.link = link;
        this.number = number;
    }
}
