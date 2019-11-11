package fr.ec.producthunt.data.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import fr.ec.producthunt.data.model.Post;

@Database(entities = {Post.class}, version = 9, exportSchema = false)
public abstract class RoomProductHuntDb extends RoomDatabase {
    public abstract PostDao postDao();

    private static RoomProductHuntDb INSTANCE;

    public static RoomProductHuntDb getDatabase(final Context context) {
        // gets an instance of the database
        if (INSTANCE == null) {
            synchronized (RoomProductHuntDb.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            RoomProductHuntDb.class, "roomproducthuntdb")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
