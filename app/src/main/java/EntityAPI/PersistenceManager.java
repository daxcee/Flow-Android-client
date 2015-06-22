package EntityAPI;

import Utils.Constants;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import static Utils.Constants.*;
import static Utils.Constants.EventEntityAttributes.*;

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

        String attrTxtType = " TEXT";
        String createTableQuery =
                String.format("Create table %s (%s primary key, %s, %s, %s, %s, %s)",
                    EventEntityName,
                        id.toString() + attrTxtType,
                        title.toString() + attrTxtType,
                        date.toString()+ attrTxtType,
                        city.toString()+ attrTxtType,
                        venue.toString()+ attrTxtType,
                        country.toString() + attrTxtType);

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
