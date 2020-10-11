import dao.impl.MySQLTweetDao;
import dao.impl.MySQLUserDao;
import models.AppUser;
import services.TweetAppService;
import services.impl.TweetAppServiceImpl;

public class TempTest {

    public static void main(String[] args) {
        TweetAppService service = new TweetAppServiceImpl(new MySQLUserDao(), new MySQLTweetDao());
        AppUser h = service.getUser("h");
        service.getNotFollowedUsers(h);


    }
}
