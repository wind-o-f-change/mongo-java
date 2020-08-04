package ru.inno.stc;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.pojo.PojoCodecProvider;
import ru.inno.stc.entity.Post;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MongoDbProducer {
    private static final String        COLLECTION_NAME = "MyCollection";
    private              MongoClient   client;
    private              MongoDatabase database;

    public void init() {
        client   = MongoClients.create();
        database = client.getDatabase("db")
                .withCodecRegistry(CodecRegistries.fromProviders(MongoClientSettings.getDefaultCodecRegistry(),
                                                                 PojoCodecProvider
                                                                         .builder()
                                                                         .register(Post.class)
                                                                         .build()));


    }

    public void close() {
        client.close();
    }

    public void insert(String field1Name, String field1Value, String field2Name, String field2Value) {
        MongoCollection<BasicDBObject> mycollection = database.getCollection(COLLECTION_NAME, BasicDBObject.class);
        BasicDBObject                  document     = new BasicDBObject();
        document.put(field1Name, field1Value);
        document.put(field2Name, field2Value);
        mycollection.insertOne(document);
    }

    public List<String> getAll() {
        MongoCollection<Document> mycollection = database.getCollection(COLLECTION_NAME);
        List<String>              lps          = new ArrayList<>();
        for (Document doc : mycollection.find()) {
            lps.add(doc.toJson());

        }
        return lps;
    }

    public List<Post> getAllNew() {
        MongoCollection<Document> mycollection = database.getCollection(COLLECTION_NAME);
        List<Post>                lps          = new ArrayList<>();
        for (Document doc : mycollection.find()) {
            lps.add(new Gson().fromJson(doc.toJson(), Post.class));

        }
        return lps;
    }

    public void insert(Post post1) {
        MongoCollection<Document> mycollection = database.getCollection(COLLECTION_NAME);
        mycollection.insertOne(Document.parse(new Gson().toJson(post1)));
    }

    public void insertNew(Post post1) {
        MongoCollection<Post> mycollection = database.getCollection(COLLECTION_NAME, Post.class);
        mycollection.insertOne(post1);
    }

    public List<Post> findByComment(String s) {
        MongoCollection<Post> collection = database.getCollection(COLLECTION_NAME, Post.class);
        BasicDBObject         search     = new BasicDBObject();
        search.put("comments", new BasicDBObject("$in", Collections.singletonList(s)));
        List<Post> lps = new ArrayList<>();
        for (Post doc : collection.find(search)) {
            lps.add(doc);

        }
        return lps;
    }
}
