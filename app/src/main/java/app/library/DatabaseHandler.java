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
    private static final String KEY_LOCATION = "location";
    private static final String KEY_LANGUAGE = "language";
    private static final String KEY_AGE = "age";
    private static final String KEY_STATE = "state";
    private static final String KEY_API = "api_key";
    private static final String KEY_JOINED = "joined";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN + "("
                + KEY_INTERNAL_ID + " INTEGER PRIMARY KEY,"
                + KEY_NAME + " TEXT,"
                + KEY_MOBILE + " TEXT UNIQUE,"
                + KEY_EMAIL + " TEXT UNIQUE,"
                + KEY_LOCATION +  " TEXT,"
                + KEY_API + " TEXT,"
                + KEY_ID + " TEXT,"
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

    public void addUser(String name, String mobile, String email, String location , String id,
                        String api_key, String joined) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NAME, name); // Name
        values.put(KEY_MOBILE, mobile); // Mobile
        values.put(KEY_EMAIL, email); // Email
        values.put(KEY_LOCATION, location); // Location
        values.put(KEY_ID, id); // id
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

    public HashMap<String, String> getUserDetails(){
        HashMap<String,String> user = new HashMap<String,String>();
        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            user.put("name", cursor.getString(1));
            user.put("mobile", cursor.getString(2));
            user.put("email", cursor.getString(3));
            user.put("location", cursor.getString(4));
            user.put("id", cursor.getString(5));
            user.put("api_key", cursor.getString(6));
            user.put("joined", cursor.getString(7));
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

    public void resetTables(){

        SQLiteDatabase db = this.getWritableDatabase();
        // Delete All Rows
        db.delete(TABLE_LOGIN, null, null);
        db.close();
    }

    public boolean isUserLoggedIn(){
        int count = this.getRowCount();
        if(count > 0){
            return true;
        }
        return false;
    }

    public boolean logoutUser(){
        this.resetTables();
        return true;
    }
}
