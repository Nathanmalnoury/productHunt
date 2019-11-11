package fr.ec.producthunt.data.database;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import fr.ec.producthunt.data.model.Post;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PostDao_Impl implements PostDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Post> __insertionAdapterOfPost;

  public PostDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPost = new EntityInsertionAdapter<Post>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `posts` (`id`,`title`,`subTitle`,`imageUrl`,`postUrl`,`createdAt`,`commentsCount`) VALUES (?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Post value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getSubTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSubTitle());
        }
        if (value.getImageUrl() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getImageUrl());
        }
        if (value.getPostUrl() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getPostUrl());
        }
        if (value.getCreatedAt() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getCreatedAt());
        }
        stmt.bindLong(7, value.getCommentsCount());
      }
    };
  }

  @Override
  public void insertAllPosts(final List<Post> posts) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPost.insert(posts);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void insertPost(final Post post) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfPost.insert(post);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public List<Post> getPosts() {
    final String _sql = "SELECT * FROM posts ORDER BY createdAt DESC";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfSubTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "subTitle");
      final int _cursorIndexOfImageUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "imageUrl");
      final int _cursorIndexOfPostUrl = CursorUtil.getColumnIndexOrThrow(_cursor, "postUrl");
      final int _cursorIndexOfCreatedAt = CursorUtil.getColumnIndexOrThrow(_cursor, "createdAt");
      final int _cursorIndexOfCommentsCount = CursorUtil.getColumnIndexOrThrow(_cursor, "commentsCount");
      final List<Post> _result = new ArrayList<Post>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Post _item;
        _item = new Post();
        final long _tmpId;
        _tmpId = _cursor.getLong(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpSubTitle;
        _tmpSubTitle = _cursor.getString(_cursorIndexOfSubTitle);
        _item.setSubTitle(_tmpSubTitle);
        final String _tmpImageUrl;
        _tmpImageUrl = _cursor.getString(_cursorIndexOfImageUrl);
        _item.setImageUrl(_tmpImageUrl);
        final String _tmpPostUrl;
        _tmpPostUrl = _cursor.getString(_cursorIndexOfPostUrl);
        _item.setPostUrl(_tmpPostUrl);
        final String _tmpCreatedAt;
        _tmpCreatedAt = _cursor.getString(_cursorIndexOfCreatedAt);
        _item.setCreatedAt(_tmpCreatedAt);
        final int _tmpCommentsCount;
        _tmpCommentsCount = _cursor.getInt(_cursorIndexOfCommentsCount);
        _item.setCommentsCount(_tmpCommentsCount);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getLength() {
    final String _sql = "SELECT COUNT(id) FROM posts";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getLastPostId() {
    final String _sql = "SELECT id FROM posts ORDER BY id DESC LIMIT 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}
