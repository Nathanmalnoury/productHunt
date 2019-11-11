package fr.ec.producthunt.data.model;

import android.content.ContentValues;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import fr.ec.producthunt.data.database.DataBaseContract;


@Entity(tableName = "posts")
public class Post {

    @PrimaryKey
    private long id;

    private String title;
    private String subTitle;
    private String imageUrl;
    private String postUrl;
    private String createdAt;
    private int commentsCount;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setCommentsCount(int commentsCount) {
        this.commentsCount = commentsCount;
    }

    public int getCommentsCount() {
        return commentsCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @NonNull
    @Override
    public String toString() {
        return title + "; " + id + "; " + commentsCount + "; " + createdAt.toString();
    }

    public ContentValues toContentValues() {

        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBaseContract.PostTable.ID_COLUMN, id);
        contentValues.put(DataBaseContract.PostTable.TITLE_COLUMN, title);
        contentValues.put(DataBaseContract.PostTable.SUBTITLE_COLUMN, subTitle);
        contentValues.put(DataBaseContract.PostTable.IMAGE_URL_COLUMN, imageUrl);
        contentValues.put(DataBaseContract.PostTable.POST_URL_COLUMN, postUrl);
        return contentValues;
    }


}
