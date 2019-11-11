package fr.ec.producthunt.data.api.model;

import com.google.gson.annotations.SerializedName;

public class PostResponse {
    @SerializedName("id")
    public long id;

    @SerializedName("name")
    public String title;

    @SerializedName("tagline")
    public String subTitle;

    @SerializedName("redirect_url")
    public String postUrl;

    @SerializedName("thumbnail")
    public ThumbnailResponse thumbnail;

    @SerializedName("comments_count")
    public int commentsCount;

    @SerializedName("created_at")
    public String createdAt;
}
