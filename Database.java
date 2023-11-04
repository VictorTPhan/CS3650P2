public class Database {
    private UserGroup root;
    private static Database instance;

    public static Database getInstance() {
        return instance;
    }

    private Database() {
        root = new UserGroup("Root");
    }

    public UserGroup getRoot() {
        return root;
    }
}
