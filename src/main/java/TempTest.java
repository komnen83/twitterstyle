import dao.AppUserDao;
import dao.TweetDao;
import dao.impl.MySQLTweetDao;
import dao.impl.MySQLUserDao;
import models.AppUser;
import models.Tweet;

import java.util.List;

public class TempTest {

    public static void main(String[] args) {
        AppUserDao dao = new MySQLUserDao();
        TweetDao tDao = new MySQLTweetDao();

        AppUser user1 = AppUser.UserBuilder.getBuilder()
                .name("Jacob")
                .lastName("1")
                .email("komnen@op.pl")
                .login("kom")
                .password("kom")
                .build();

        AppUser user2 = AppUser.UserBuilder.getBuilder()
                .name("Marck")
                .lastName("2")
                .email("k@op.pl")
                .login("komnen")
                .password("komnen")
                .build();

        AppUser user3 = AppUser.UserBuilder.getBuilder()
                .name("Terry")
                .lastName("3")
                .email("nen@op.pl")
                .login("nen")
                .password("nen")
                .build();

        dao.saveUser(user1);
        dao.saveUser(user2);
        dao.saveUser(user3);

        dao.follow(user1, user2);
        dao.follow(user2, user3);
        dao.follow(user1, user3);
        System.out.println("unfollowing user ------------");
        dao.unfollow(user1, user3);

        System.out.printf("Followed of user1 ");
        dao.getFollowedUsers(user1).forEach(System.out::println);
        System.out.printf("Followers of user2 ");
        dao.getFollowers(user2).forEach(System.out::println);

        dao.getAll().forEach(System.out::println);

        dao.getUserByEmail("k@op.pl");

        Tweet tweet1 = new Tweet(user1.getLogin(), "adsfa");
        Tweet tweet2 = new Tweet(user2.getLogin(), "qertet");
        Tweet tweet3 = new Tweet(user3.getLogin(), "rtyttyu");

        tDao.save(tweet1);
        tDao.save(tweet2);
        tDao.save(tweet3);

        System.out.println("---------------------------------");
        List<Tweet> tweets = tDao.getUserTweets(user1);
        tweets.forEach(System.out::println);
        System.out.println("---------------------------------");
        dao.deleteUser(user2);


    }
}
