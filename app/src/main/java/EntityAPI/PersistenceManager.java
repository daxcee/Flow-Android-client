package EntityAPI;

import Utils.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class PersistenceManager extends SQLiteOpenHelper {

    public PersistenceManager(Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
    }

    /**
     * Creating Tables
     *
     * @param db instance
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "Create table " + Constants.EventEntityName + "("
                + Constants.EventEntityAttributes.id.toString() + " String primary key," +
                Constants.EventEntityAttributes.title.toString() + " TEXT," +
                Constants.EventEntityAttributes.date.toString() + " DATE" +
                Constants.EventEntityAttributes.city.toString() + " TEXT" +
                Constants.EventEntityAttributes.venue.toString() + " TEXT" +
                Constants.EventEntityAttributes.country.toString() + " TEXT" +
                Constants.EventEntityAttributes.city + " TEXT" + ")";

        db.execSQL(createTableQuery);
    }

    /**
     * Upgrading database
     *
     * @param db instance
     * @param oldVersion curr
     * @param newVersion new
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + Constants.EventEntityName);

        // Create tables again
        onCreate(db);
    }

}
