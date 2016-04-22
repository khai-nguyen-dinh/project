package models;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import play.db.jpa.Model;

import javax.persistence.Entity;

/**
 * Created by khain on 18/09/2015.User(
 */
@Entity
public class User extends Model{

    public String username;
    public String password;

    public String fullname;
    public String address;


    public User(String username, String password, String fullname, String address){
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.address = address;
    }

    public static void addUser(String username, String password, String fullname, String address){
        User user = new User(username,password,fullname,address).save();
    }

}
