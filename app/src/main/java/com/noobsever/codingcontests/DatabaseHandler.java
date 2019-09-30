package com.noobsever.codingcontests;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.noobsever.codingcontests.Models.Notification;
import com.noobsever.codingcontests.Models.SingleObj;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;


public class DatabaseHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "EventManager.db";
    private static final String DURATION = "Duration";
    private static final String END = "End";
    private static final String EVENT = "Event";
    private static final String HREF = "href";
    private static final String ID = "Id";
    private static final String NAME = "Name";
    private static final String START = "Start";
    private static final String KEY_ID = "Key_Id";
    Constants c= new Constants();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    SimpleDateFormat sdf1 = new SimpleDateFormat("dd-MM-yyyy | HH:mm");
    Context cc;



    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.cc=context;

    }

    // Creating Tables  
    @Override
    public void onCreate(SQLiteDatabase db) {

        Constants c= new Constants();
        String CREATE_CODEFORCES_TABLE = "CREATE TABLE " + c.TABLE_CODEFORCES + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_CODECHEF_TABLE = "CREATE TABLE " + c.TABLE_CODECHEF + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_HACKERRANK_TABLE = "CREATE TABLE " + c.TABLE_HACKERRANK + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_HACKEREARTH_TABLE = "CREATE TABLE " + c.TABLE_HACKEREARTH + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_TOPCODER_TABLE = "CREATE TABLE " + c.TABLE_TOPCODER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_SPOJ_TABLE = "CREATE TABLE " + c.TABLE_SPOJ + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_ATCODER_TABLE = "CREATE TABLE " + c.TABLE_ATCODER + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_LIVE_TABLE = "CREATE TABLE " + c.LIVE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_LONG_TABLE = "CREATE TABLE " + c.LONG + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_SHORT_TABLE = "CREATE TABLE " + c.SHORT + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + DURATION + " INTEGER," +END + " TEXT,"
                + EVENT + " TEXT," + HREF + " TEXT," + ID + " INTEGER," +  NAME + " TEXT," + START + " TEXT" + ")";

        String CREATE_NOTIFICATION_TABLE = "CREATE TABLE " + c.NOTIFICATION + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + ID + " INTEGER," + EVENT + " TEXT," + START + " TEXT" + ")";


        db.execSQL(CREATE_CODEFORCES_TABLE);
        db.execSQL(CREATE_CODECHEF_TABLE);
        db.execSQL(CREATE_HACKERRANK_TABLE);
        db.execSQL(CREATE_HACKEREARTH_TABLE);
        db.execSQL(CREATE_TOPCODER_TABLE);
        db.execSQL(CREATE_SPOJ_TABLE);
        db.execSQL(CREATE_ATCODER_TABLE);
        db.execSQL(CREATE_LIVE_TABLE);
        db.execSQL(CREATE_LONG_TABLE);
        db.execSQL(CREATE_SHORT_TABLE);
        db.execSQL(CREATE_NOTIFICATION_TABLE);

    }

    // Upgrading database  
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        Constants c= new Constants();
        db.execSQL("DROP TABLE IF EXISTS " + c.TABLE_CODEFORCES);
        db.execSQL("DROP TABLE IF EXISTS " + c.TABLE_CODECHEF);
        db.execSQL("DROP TABLE IF EXISTS " + c.TABLE_HACKERRANK);
        db.execSQL("DROP TABLE IF EXISTS " + c.TABLE_HACKEREARTH);
        db.execSQL("DROP TABLE IF EXISTS " + c.TABLE_TOPCODER);
        db.execSQL("DROP TABLE IF EXISTS " + c.TABLE_SPOJ);
        db.execSQL("DROP TABLE IF EXISTS " + c.TABLE_ATCODER);

        db.execSQL("DROP TABLE IF EXISTS " + c.LIVE);
        db.execSQL("DROP TABLE IF EXISTS " + c.LONG);
        db.execSQL("DROP TABLE IF EXISTS " + c.SHORT);

        db.execSQL("DROP TABLE IF EXISTS " + c.NOTIFICATION);

        // Create tables again  
        onCreate(db);
    }

    // code to add the new contact  
    public void addEvent (SingleObj singleObj, String TABLE_HERE) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(DURATION,singleObj.getDuration()); // Name
        values.put(END, singleObj.getEnd());
        values.put(EVENT, singleObj.getEvent());
        values.put(HREF, singleObj.getHref());
        values.put(ID,singleObj.getResource().getId());
        values.put(NAME, singleObj.getResource().getName());
        values.put(START, singleObj.getStart());


        db.insert(TABLE_HERE, null, values);
        db.close();
    }



    void DeleteAll(String TABLE_HERE){

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("delete from "+ TABLE_HERE);
        db.close();

    }



    public List<SingleObj> getAllEvents(String TABLE_HERE) {
        List<SingleObj> eventlist = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_HERE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
               SingleObj singleObj  = new SingleObj();


                //Integer duration, String end, String event, String href, Integer id, SingleObj.Res resource, String start) {4

               singleObj.setKey_Id(Integer.parseInt(cursor.getString(0)));
                singleObj.setDuration(Integer.parseInt(cursor.getString(1)));
                singleObj.setEnd(cursor.getString(2));
                singleObj.setEvent(cursor.getString(3));
                singleObj.setHref(cursor.getString(4));

                SingleObj.Res res = new SingleObj.Res();
                res.setId(Integer.parseInt(cursor.getString(5)));
                res.setName(cursor.getString(6));
                singleObj.setResource(res);

                singleObj.setStart(cursor.getString(7));
                eventlist.add(singleObj);

            } while (cursor.moveToNext());
        }

        return eventlist;
    }



    public void AddNotification (Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(ID,notification.getNotiId());
        values.put(EVENT,notification.getEvent());
        values.put(START,notification.getTime());
        db.insert(c.NOTIFICATION, null, values);
        //Toast.makeText(cc,notification.getEvent(),Toast.LENGTH_LONG).show();
        db.close();
    }


    public void DeleteNotification(Notification notification) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(c.NOTIFICATION, ID + " = ?",
                new String[] { String.valueOf(notification.getNotiId())});
        db.close();
    }


    public List<Notification> GetAllNotifications() {
        List<Notification> eventlist = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + c.NOTIFICATION;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {


                Notification notification = new Notification();


                //Integer duration, String end, String event, String href, Integer id, SingleObj.Res resource, String start) {4

                notification.setId(Integer.parseInt(cursor.getString(0)));
                notification.setNotiId(Integer.parseInt(cursor.getString(1)));
                notification.setEvent(cursor.getString(2));
                notification.setTime(cursor.getString(3));


                Date date,d2;
                sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
                try {
                    date = sdf.parse(notification.getTime());
                    sdf.setTimeZone(TimeZone.getDefault());
                    String s= sdf.format(date);
                    d2= sdf.parse(s);

                    if(d2.before(new Date()))
                    {
                            DeleteNotification(notification);
                    }
                    else
                    {
                            String p= sdf1.format(date);
                            notification.setTime(p);
                            eventlist.add(notification);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }

            } while (cursor.moveToNext());
        }

        return eventlist;
    }




   /* Student getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CONTACTS, new String[] { KEY_ID,
                        KEY_NAME, KEY_PH_NO }, KEY_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Student contact = new Student(cursor.getString(1), cursor.getString(2));
        // return contact
        return contact;
    }*/



   /* public void delete(Purchase person) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_RESTAURENT, KEY_ID + " = ?",
                new String[] { String.valueOf(person.getId())});
        db.close();
    }

    public void updateContact(Purchase purchase) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        Gson gson= new Gson();
        String json = gson.toJson(purchase.getList());

        values.put(KEY_RESTONAME,purchase.getRestaurent()); // Name
        values.put(KEY_LIST, json); // LIST as String
        values.put(KEY_DATE, purchase.getDate()); //  date

        // updating row
        db.update(TABLE_RESTAURENT, values, KEY_ID + " = ?",
                new String[] { String.valueOf(purchase.getId()) });

    }*/
}  