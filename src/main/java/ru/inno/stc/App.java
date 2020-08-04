package ru.inno.stc;

import ru.inno.stc.entity.Post;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        MongoDbProducer producer = new MongoDbProducer();
        producer.init();

        String value1 = "filed1 value";
        String value2 = "filed2 value";
        producer.insert("Filed1", value1, "Field2", value2);

        Post post1 = new Post("Post1", Arrays.asList("Comment 1 (post1)", "Comment 2 (post1)"));
        Post post2 = new Post("Post2", Arrays.asList("Comment 1 (post2)", "Comment 2 (post2)"));
        producer.insertNew(post1);
        producer.insertNew(post2);

        System.out.println("FindByComment");
        producer.findByComment("Comment 1 (post1)").forEach(System.out::println);
        System.out.println("FindAll");
        producer.getAllNew().forEach(System.out::println);

        producer.close();
    }
}
