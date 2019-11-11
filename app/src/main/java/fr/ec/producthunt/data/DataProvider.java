package fr.ec.producthunt.data;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import java.util.List;

import fr.ec.producthunt.data.database.PostDao;
import fr.ec.producthunt.data.database.RoomProductHuntDb;
import fr.ec.producthunt.data.model.Post;
import fr.ec.producthunt.data.repository.post.remote.PostRemoteRepository;
import fr.ec.producthunt.data.repository.post.remote.PostRemoteRepositoryException;

import static android.content.ContentValues.TAG;

public class DataProvider {

    public static final String POST_API_END_POINT =
            "https://api.producthunt.com/v1/posts?access_token=46a03e1c32ea881c8afb39e59aa17c936ff4205a8ed418f525294b2b45b56abb";

    private JsonPostParser jsonPostParser = new JsonPostParser();

    private final PostDao postDao;
    private final PostRemoteRepository postRemoteRepository;

    private static DataProvider dataProvider;

    public static DataProvider getInstance(Application application) {

        if (dataProvider == null) {
            dataProvider = new DataProvider(application);
        }
        return dataProvider;
    }

    public DataProvider(Context context) {
        postDao = RoomProductHuntDb.getDatabase(context).postDao();
        postRemoteRepository = new PostRemoteRepository();
    }

    public List<Post> getPostsFromDatabase() {
        List<Post> retrievedPosts = postDao.getPosts();
        Log.d(TAG, "getPostsFromDatabase len: " + retrievedPosts.size());
        return retrievedPosts;
    }

    public Boolean syncPost() {

        int nb = 0;

        try {
            if (postDao.getLength() == 0) {
            // We have to try getting all the posts
            List<Post> postList = postRemoteRepository.getPosts();
            postDao.insertAllPosts(postList);
            nb = postList.size();
            } else {
                int lastId = postDao.getLastPostId();
                List<Post> postList = postRemoteRepository.getNewPosts(lastId);
                postDao.insertAllPosts(postList);
                nb = postList.size();
            }
            Log.d(TAG, "syncPost: in database : " + postDao.getLength() +
                    "; tried to insert " + nb);
        } catch (PostRemoteRepositoryException e) {
            Log.e(TAG, "syncPost: Fails", e);
        }
        Log.d(TAG, "syncPost: Success");
        return nb > 0;
    }
}

