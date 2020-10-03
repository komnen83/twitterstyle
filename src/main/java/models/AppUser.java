package models;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "app_user")
public class AppUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;
    private String email;
    private String name;
    private String password;
    @Column(name = "last_name")
    private String lastName;
    @CreationTimestamp
    private Date registerdSince;

    @ManyToMany(mappedBy = "following", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    private Set<AppUser> followers = new HashSet<>();

    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(name = "follower_followed",
    joinColumns = {@JoinColumn(name = "follower_fk")},
    inverseJoinColumns = {@JoinColumn(name = "followed_fk")})
    private Set<AppUser> following = new HashSet<>();

    public AppUser() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getRegisterdSince() {
        return registerdSince;
    }

    public void setRegisterdSince(Date registerdSince) {
        this.registerdSince = registerdSince;
    }

    public Set<AppUser> getFollowers() {
        return followers;
    }

    public void setFollowers(Set<AppUser> followers) {
        this.followers = followers;
    }

    public Set<AppUser> getFollowing() {
        return following;
    }

    public void setFollowing(Set<AppUser> following) {
        this.following = following;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AppUser appUser = (AppUser) o;
        return Objects.equals(id, appUser.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                ", registerdSince=" + registerdSince +
                ", followers=" + followers +
                ", following=" + following +
                '}';
    }

    public static class UserBuilder {
        private String name;
        private String lastName;
        private String login;
        private String password;
        private String email;

        public static UserBuilder getBuilder() {
            return new UserBuilder();
        }

        public UserBuilder login(String login) {
            this.login = login;
            return this;
        }

        public UserBuilder name(String name) {
            this.name = name;
            return this;
        }

        public UserBuilder lastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder password(String password) {
            this.password = password;
            return this;
        }

        public UserBuilder email(String email) {
            this.email = email;
            return this;
        }

        public AppUser build() {
            AppUser user = new AppUser();
            user.setLogin(this.login);
            user.setPassword(this.password);
            user.setName(this.name);
            user.setLastName(this.lastName);
            user.setEmail(this.email);
            return user;
        }}
}
