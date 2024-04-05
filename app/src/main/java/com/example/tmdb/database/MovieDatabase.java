    package com.example.tmdb.database;

    import android.content.Context;
    import androidx.room.Room;
    import androidx.room.RoomDatabase;
    import androidx.room.Database;

    import com.example.tmdb.domain.Collection;
    import com.example.tmdb.domain.ListMovies;
    import com.example.tmdb.domain.Movie;


@Database(entities = {Movie.class, Collection.class, ListMovies.class}, version = 6)
public abstract class MovieDatabase extends RoomDatabase {
    public abstract DAO getDAO();


    public void saveListLocally(Collection collection) {
        new Repository.insertCollectionAsyncTask(getDAO()).execute(collection);
    }

    private static MovieDatabase INSTANCE;

        public static MovieDatabase getDatabase(final Context context) {
            if (INSTANCE == null) {
                synchronized (Database.class) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                        MovieDatabase.class, "tmdb_database")
                                .fallbackToDestructiveMigration()
                                .build();
                    }
                }
            }
            return INSTANCE;
        }

    }

