package dao.impl;

import dao.AbstractMySQLDao;
import dao.TweetDao;
import models.AppUser;
import models.Tweet;

import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class MySQLTweetDao extends AbstractMySQLDao implements TweetDao {
    @Override
    public void save(Tweet tweet) {
        hibernateUtil.save(tweet);
    }

    @Override
    public void delete(Long tweetId) {
        hibernateUtil.delete(Tweet.class, tweetId);
    }

    @Override
    public List<Tweet> getUserTweets(AppUser user) {
        TypedQuery<Tweet> query = em.createQuery("select t from Tweet t where t.author= :login",  Tweet.class);
        query.setParameter("login", user.getLogin());
        return query.getResultList();
    }

    @Override
    public Optional<Tweet> getTweet(Long id) {
        return Optional.ofNullable(em.find(Tweet.class, id));
    }
}
