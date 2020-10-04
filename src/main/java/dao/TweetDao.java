package dao;

import models.AppUser;
import models.Tweet;

import java.util.List;

public interface TweetDao {
    void save(Tweet tweet);

    void delete(Long tweetId);

    List<Tweet> getUserTweets(AppUser user);

    Tweet getTweet(Long id);

}
