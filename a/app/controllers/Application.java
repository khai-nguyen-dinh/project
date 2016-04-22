package controllers;

import play.cache.Cache;
import play.mvc.*;


import models.*;
import java.util.List;

//---------------------------------------------INDEX---------------------------------------------------------


public class Application extends Controller {

    public static User users;

    public static void index() {
        if (Cache.get(session.getId() + "-userID") == null) {
            render();
        } else {
            temp(true);
        }
    }


    //-----------------------------------------CODE-----------------------------------------------------------

    public static void dk() {
        render();
    }


//------------------------sigUp-----------------------


    public static void signUp(String username, String password, String fullname, String adress) {
        User user = User.find("username=? and password=?", username, password).first();
        User temp = User.find("username=?", username).first();
        if (user != null || temp != null) {
            flash.error("Oop!There is already an account associated with this user address");
            loi();
        }
        if (username.equals("") | password.equals("")) {
            flash.error("Oop!nhap thieu du lieu");
            loi();
        } else {
            if (check_length(password) == false) {
                flash.error("Oop!password khong du do dai!");
                loi();
            } else {
                if (check(password)) {
                    flash.error("Oop!password phai chua so!");
                    loi();
                } else {
                    User u = new User(username,password,fullname,adress);
                    u.save();
                    flash.success("thank you signUp %s", username);
                    render();
                }
            }
        }

    }

    public static void loi(){
        render();
    }

    public static void login() {
        render();
    }

//---------------------signIn----------------------------


    public static void signIn() {
        render();
    }

    public static void signed(String username, String password, boolean remember) {
        users = User.find("username=? and password=?", username, password).first();
        if (users != null) {
            if (remember == true) {
                Cache.set(session.getId() + "-userID", users.id);
            }
            temp(true);

        } else {
            temp(false);
        }
    }


//-----------------------logOut----------------------


    public static void logOut() {
        Cache.clear();
        signIn();
    }


//--------------------Comment------------------------


    public static void Comment() {

        List<Post> post = Post.find("author.id=? order by postedAt desc", users.getId()).fetch();
        int size = post.size();

        render(post,size);
    }


    public static void postComment(String content) {

        if (content.isEmpty()) {
            flash.error("Loi!ban chua nhap du lieu");
            Comment();
        } else {
            int size = content.length();
            if (size <= 255) {
                Post posts = new Post(users, content);
                posts.addComment(users, content);
            } else {
                int count = 0;
                int i = 255;
                int j = 1;
                while (size > 255) {
                    String temp = "(" + j + ") "+content.substring(count, i) ;
                    Post posts = new Post(users, temp);
                    posts.addComment(users, temp);
                    count += i;
                    i += 255;
                    size -= 255;
                    j++;
                }
                System.out.println(content.substring(count));
                Post posts = new Post(users,"(" + j + ") " + content.substring(count) );
                posts.addComment(users,"(" + j + ") " +content.substring(count));
            }
        }
        Comment();
    }

    public static void menu(Post post) {
        render(post);
    }

    public static void editNote(Post post) {
        render(post);
    }


    public static void deleteNote(Post post) {

        Post p = Post.find("content = ?", post.content).first();
        p.delete();
        flash.success("Delete success!");
        render();
    }

    public static void edited(Post post, String content) {
        Post p = Post.find("content = ? ", post.content).first();
        p.content = content;
        p.save();

        flash.success("Edit success!");
        render();
    }




    public static void chatroom(){
        List<chat> c = chat.find("order by date asc").fetch();
        render(c);

    }
    public static void chats(String content){

        chat c = new chat(users.username,content,users);
        c.save();
        chatroom();
    }



    public static void share(Post post){
        render(post);

    }


    public static void shared(Post post,String username){
        User user = User.find("username = ?",username).first();
        if(user==null){
            flash.error("loi!khong tim thay thong tin nguoi dung");
            render(post);
        }else{
            Post p = new Post(user,post.content);
            p.save();
            flash.success("share thanh cong.");
            render();
        }
    }
    //----------------------------------------------CHECK-TEMP-----------------------------------------------------


    public static void temp(boolean a) {
        if (a == true) {
            flash.success("You are sign in");
            login();
        } else
            flash.error("invalid username.please try again");
        signIn();

    }


    public static boolean check_length(String password) {
        if (password.length() < 4 || password.length() > 11) {
            return false;
        } else {
            return true;
        }
    }

    public static boolean check(String password) {

        if (password.matches(".*(\\d+).*") == true) {
            return false;
        } else {
            return true;
        }
    }


}