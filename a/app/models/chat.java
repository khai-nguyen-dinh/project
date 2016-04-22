package models;

import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by khain on 21/11/2015.
 */
@Entity
public class chat extends Model {
    public String username;


    public String content;
    public Date date;

    @ManyToOne
    public User user;

    public chat(String username, String content, User user) {
        this.username = username;
        this.content = content;
        this.user = user;
        this.date = new Date();
    }
}
