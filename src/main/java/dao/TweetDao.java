package dao;

import models.AppUser;
import models.Tweet;

import java.util.List;
import java.util.Optional;

public interface TweetDao {

    void save(Tweet tweet);

    void delete(Long tweetId);

    List<Tweet> getUserTweets(AppUser user);

    Optional<Tweet> getTweet(Long id);
}
