public class Tweet implements ITweet {
    private String content;
    private UID poster;

    public Tweet(String content, UID poster) {
        this.content = content;
        this.poster = poster;
    }

    @Override
    public String getTweet() {
        return content;
    }

    @Override
    public User getPoster() {
        return poster.getUser();
    }
}
