package ru.inno.stc.entity;

import org.bson.types.ObjectId;

import java.util.List;
import java.util.Objects;

public class Post {

    private ObjectId     id;
    private String       title;
    private List<String> comments;

    public Post() {
        id = new ObjectId();
    }

    public Post(String title, List<String> comments) {
        this();
        this.comments = comments;
        this.title    = title;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public List<String> getComments() {
        return comments;
    }

    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Post{" +
               "id=" + id +
               ", title='" + title + '\'' +
               ", comments=" + comments +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final Post post = (Post) o;
        return Objects.equals(id, post.id) &&
               Objects.equals(title, post.title) &&
               Objects.equals(comments, post.comments);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, comments);
    }
}
