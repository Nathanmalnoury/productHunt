package fr.ec.producthunt.data.api;

import fr.ec.producthunt.data.api.model.PostResponseList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ProductHuntService {
    String TOKEN = "46a03e1c32ea881c8afb39e59aa17c936ff4205a8ed418f525294b2b45b56abb";

    @GET("posts?access_token=" + TOKEN)
    Call<PostResponseList> getPosts();

    @GET("posts/all?access_token=" + TOKEN)
    Call<PostResponseList> getNewPosts(@Query("newer") int idPost);
}
