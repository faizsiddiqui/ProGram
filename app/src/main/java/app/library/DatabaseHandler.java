package app.library;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.HashMap;

/**
 * Not for public use
 * Created by FAIZ on 19-02-2015.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "program";  // Database Name
    private static final String TABLE_LOGIN = "login";  // Login table name

    // Login Table Columns names
    private static final String KEY_INTERNAL_ID = "internalId";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_MOBILE = "mobile";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_STATE = "state";
    private static final String KEY_CATEGORY = "category";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_AGE = "age";
    private static final String KEY_API = "api_key";
    private static final String KEY_JOINED = "joined";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_INTERNAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_ID + " INTEGER UNIQUE,"
                + KEY_NAME + " TEXT,"
                + KEY_MOBILE + " TEXT UNIQUE,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_STATE + " TEXT,"
                + KEY_CATEGORY + " TEXT,"
                + KEY_GENDER + " TEXT,"
                + KEY_AGE + " TEXT,"
                + KEY_API + " TEXT,"
                + KEY_JOINED + " TEXT" + ")";
        sqLiteDatabase.execSQL(CREATE_LOGIN_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // Drop older table if existed
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN);

        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void addUser(String id, String name, String mobile, String email, String state, String category,
                        String gender, String age, String api_key, String joined) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_ID, id); // id
        values.put(KEY_NAME, name); // Name
        values.put(KEY_MOBILE, mobile); // Mobile
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_STATE, state); // Location
        values.put(KEY_CATEGORY, category); //category
        values.put(KEY_GENDER, gender); //gender
        values.put(KEY_AGE, age); //age
        values.put(KEY_API, api_key); // api_key
        values.put(KEY_JOINED, joined); // joined At

        // Inserting Row
        db.insert(TABLE_LOGIN, null, values);
        db.close(); // Closing database connection
    }

    public void putDetail(String field, String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put(field, value);
        db.update(TABLE_LOGIN, content, null, null);
        db.close();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> user = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            user.put(KEY_ID, cursor.getString(1));
            user.put(KEY_NAME, cursor.getString(2));
            user.put(KEY_MOBILE, cursor.getString(3));
            user.put(KEY_EMAIL, cursor.getString(4));
            user.put(KEY_STATE, cursor.getString(5));
            user.put(KEY_CATEGORY, cursor.getString(6));
            user.put(KEY_GENDER, cursor.getString(7));
            user.put(KEY_AGE, cursor.getString(8));
            user.put(KEY_API, cursor.getString(9));
            user.put(KEY_JOINED, cursor.getString(10));
        }
        cursor.close();
        db.close();
        // return user
        return user;
    }

    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LOGIN;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int rowCount = cursor.getCount();
        db.close();
        cursor.close();
        // return row count
        return rowCount;
    }

    public void resetTables() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LOGIN, null, null); // Delete All Rows
        db.close();
    }

    public boolean isUserLoggedIn() {
        int count = this.getRowCount();
        if (count > 0) {
            return true;
        }
        return false;
    }

    public boolean logoutUser() {
        this.resetTables();
        return true;
    }
}
