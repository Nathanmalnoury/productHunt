package fr.ec.producthunt.data.repository.post.remote;

import android.util.Log;

import java.util.List;

import fr.ec.producthunt.data.api.ProductHuntService;
import fr.ec.producthunt.data.api.ServiceApiFactory;
import fr.ec.producthunt.data.api.model.PostResponse;
import fr.ec.producthunt.data.model.Post;
import fr.ec.producthunt.data.repository.post.mapper.PostMapper;

import static android.content.ContentValues.TAG;

public class PostRemoteRepository {

  private final ProductHuntService service;
  private final PostMapper postMapper;

  public PostRemoteRepository() {
    service = ServiceApiFactory.create(ProductHuntService.class);
    postMapper = new PostMapper();

  }

  public List<Post> getPosts() throws PostRemoteRepositoryException {
    try {
      List<PostResponse> postResponseList = service.getPosts().execute().body().posts;
      Log.d(TAG, "getPosts: len :" + postResponseList.size());
      return postMapper.from(postResponseList);

    } catch (Exception e) {
      Log.e(TAG, "getPosts: Fails", e);
      throw new PostRemoteRepositoryException(e.getCause());

    }
  }
  public List<Post> getNewPosts(int newId) throws PostRemoteRepositoryException {
    try {
      List<PostResponse> postResponseList = service.getNewPosts(newId).execute().body().posts;
      Log.d(TAG, "getNewPosts: len :" + postResponseList.size());
      return postMapper.from(postResponseList);
    }catch (Exception e) {
      Log.e(TAG, "getNewPosts: Failed", e);
      throw new PostRemoteRepositoryException(e.getCause());
    }
  }
}
