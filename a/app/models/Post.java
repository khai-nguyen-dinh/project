package models;

import jdk.nashorn.api.scripting.JSObject;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by khain on 25/09/2015.
 */
@Entity
public class Post extends Model{
    public Date postedAt;

    @Lob
    public String content;

    @ManyToOne
    public User author;

    public Post(User author,String content){

        this.author = author;
        this.content = content;
        this.postedAt = new Date();
    }

    public static void addComment(User author,String content){
        Post post = new Post(author,content).save();

    }

}
