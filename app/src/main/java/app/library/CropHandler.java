package app.library;

 import android.content.ContentValues;
 import android.content.Context;
 import android.database.Cursor;
 import android.database.sqlite.SQLiteDatabase;
 import android.database.sqlite.SQLiteOpenHelper;
 import com.android.volley.toolbox.StringRequest;

 import java.io.StringReader;
 import java.util.HashMap;

/**
 * Created by admin on 29-04-2015.
 */
public class CropHandler extends SQLiteOpenHelper{
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "program";  // Database Name
    private static final String TABLE_CROP = "crop ";


    private static final String KEY_ID = "id";
    private static final String KEY_STARTDAY= "sday";
    private static final String KEY_STARTMONTH = "smonth";
    private static final String KEY_STARTYEAR = "syear";
    private static final String KEY_ENDDAY = "eday";
    private static final String KEY_ENDMONTH = "emonth";
    private static final String KEY_ENDYEAR = "eyear";
    private static final String KEY_EVENTNAME = "ename";
    private static final String KEY_EVENTDESC = "edesc";



    public CropHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String CREATE_CROP_TABLE = "CREATE TABLE" + TABLE_CROP + "("
                + KEY_ID + " INTEGER PRIMARY KEY,"
                + KEY_STARTDAY + " INTEGER,"
                +KEY_STARTMONTH + " INTEGER,"
                +KEY_STARTYEAR + " INTEGER,"
                + KEY_ENDDAY+ "INTEGER,"
                + KEY_ENDMONTH+ " INTEGER,"
                + KEY_ENDYEAR+ " INTEGER,"
                + KEY_EVENTNAME + " TEXT,"
                + KEY_EVENTDESC + " TEXT" + ")";

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_CROP);
        onCreate(sqLiteDatabase);

    }
    public void addCrop (String sday, String smonth , String syear ,String eday,String emonth ,String eyear, String evname, String evdesc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put( KEY_STARTDAY ,sday);
        values.put( KEY_STARTMONTH ,smonth);
        values.put( KEY_STARTYEAR ,syear);
        values.put(KEY_ENDDAY, eday);
        values.put(KEY_ENDMONTH, emonth);
        values.put(KEY_ENDYEAR, eyear);
        values.put(KEY_EVENTNAME,evname);
        values.put(KEY_EVENTDESC,evdesc);

        db.insert(TABLE_CROP, null, values);
        db.close();

    }
    public HashMap<String, String> getCropDetails() {
        HashMap<String, String>  crop = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_CROP;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        cursor.moveToFirst();
        if (cursor.getCount() > 0)
        {
            crop.put(KEY_ID, cursor.getString(1));
            crop.put(KEY_STARTDAY, cursor.getString(2));
            crop.put(KEY_STARTMONTH, cursor.getString(3));
            crop.put(KEY_STARTYEAR, cursor.getString(4));
            crop.put(KEY_ENDDAY, cursor.getString(5));
            crop.put(KEY_ENDMONTH, cursor.getString(6));
            crop.put(KEY_ENDYEAR, cursor.getString(7));
            crop.put(KEY_EVENTNAME,cursor.getString(8));
            crop.put(KEY_EVENTDESC,cursor.getString(9));
        }
        cursor.close();
        db.close();
        return crop;


    }
    public int getRowCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CROP;
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
        db.delete(TABLE_CROP, null, null); // Delete All Rows
        db.close();
    }

    public void getEventDescription (int day,int month, int year) {
        String eventDescribe = "SELECT * FROM" + TABLE_CROP + "WHERE day>=startday AND month>=startmonth AND year>=startyear AND day<=endday AND month<=endmonth AND year<=endyear";
        SQLiteDatabase cp = this.getReadableDatabase();
        Cursor cursor = cp.rawQuery(eventDescribe ,null);
    }

}