package fr.ec.producthunt.data.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import fr.ec.producthunt.data.model.Post;

@Dao
public interface PostDao {
    @Query("SELECT * FROM posts ORDER BY createdAt DESC")
    List<Post> getPosts();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAllPosts(List<Post> posts);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPost(Post post);

    @Query("SELECT COUNT(id) FROM posts")
    int getLength();

    @Query("SELECT id FROM posts ORDER BY id DESC LIMIT 1")
    int getLastPostId();
}
