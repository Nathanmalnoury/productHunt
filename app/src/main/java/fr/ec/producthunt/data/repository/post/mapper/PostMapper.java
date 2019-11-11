package fr.ec.producthunt.data.repository.post.mapper;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.ec.producthunt.data.api.model.PostResponse;
import fr.ec.producthunt.data.model.Post;

import static android.content.ContentValues.TAG;

public class PostMapper {


    public List<Post> from(List<PostResponse> postResponses) {
        Log.d(TAG, "postResponses.size " + postResponses.size());
        List<Post> posts = new ArrayList<>(postResponses.size());
        for (PostResponse postResponse : postResponses) {
            posts.add(from(postResponse));
        }
        Log.d(TAG, "len postmapper list " + posts.size());
        return posts;
    }

    private Post from(PostResponse postResponse) {
        Post post = new Post();

        post.setId(postResponse.id);
        post.setImageUrl(postResponse.thumbnail.imageUrl);
        post.setSubTitle(postResponse.subTitle);
        post.setPostUrl(postResponse.postUrl);
        post.setCommentsCount(postResponse.commentsCount);
        post.setCreatedAt(postResponse.createdAt);
        post.setTitle(postResponse.title);

        return post;
    }
}
