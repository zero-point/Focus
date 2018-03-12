package uk.ac.gla.focuswatch.model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class TaskOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "TaskDatabase";

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "tasks";

    // --------------------- TASK TABLE ---------------------------------
    static final String TASK_TABLE = "task";
    static final String FIELD_TASK_ID = "_id";  // PK, _ is convention
    static final String FIELD_TASK_LABEL = "label";  // text
    static final String FIELD_TASK_COMPLETED = "completed";  // boolean

    private static final String TASK_TABLE_CREATE =
            "CREATE TABLE " + TASK_TABLE + " (" +
                    FIELD_TASK_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_TASK_LABEL + " TEXT, " +
                    FIELD_TASK_COMPLETED + " INTEGER DEFAULT 0" +
            ");";

    // --------------------- TASK SESSION TABLE --------------------------
    static final String TASK_SESSION_TABLE = "task_session";
    static final String FIELD_TASK_SESSION_ID = "_id";  // PK, _ is convention
    static final String FIELD_TASK_SESSION_START_TIME = "start_time";  // datetime
    static final String FIELD_TASK_SESSION_TIME_SPENT = "time_spent";  // int seconds
    static final String FIELD_TASK_FK_ID = "task_id";  // foreign key to task table

    private static final String TASK_SESSION_TABLE_CREATE =
            "CREATE TABLE " + TASK_SESSION_TABLE + " (" +
                    FIELD_TASK_SESSION_ID + " INTEGER  PRIMARY KEY AUTOINCREMENT, " +
                    FIELD_TASK_FK_ID + " INTEGER, " +
                    FIELD_TASK_SESSION_START_TIME + " TEXT, " +
                    FIELD_TASK_SESSION_TIME_SPENT + " INTEGER, " +
                    "FOREIGN KEY(" + FIELD_TASK_FK_ID + ") " +
                        "REFERENCES " + TASK_TABLE + "(" + FIELD_TASK_ID + ")" +
            ");";



    TaskOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TASK_TABLE_CREATE);
        db.execSQL(TASK_SESSION_TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(TAG, "Upgrading database from version " + oldVersion + " to "
                + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }
}