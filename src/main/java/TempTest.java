import dao.AppUserDao;
import dao.impl.MySQLUserDao;
import models.AppUser;

public class TempTest {

    public static void main(String[] args) {
        AppUserDao dao = new MySQLUserDao();
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

        dao.deleteUser(user2);
        dao.getAll().forEach(System.out::println);

        dao.getUserByEmail("k@op.pl");



    }
}
