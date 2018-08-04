package facebook.example.com.gosport.SqlLite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

import facebook.example.com.gosport.Class.EventInformation;
import facebook.example.com.gosport.Class.RegisterUser;

public class DBHandler extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 6;

    // Database Name
    private static final String DATABASE_NAME = "goSport";

    // Contacts table name
    private static final String TABLE_USERS = "users";
    private static final String TABLE_EVENTS = "events";
    private static final String TABLE_USERPEREVENT = "userperevent";

    // Users Table Columns names
    private static final String KEY_USERS_ID = "id";
    private static final String KEY_USERS_FNAME = "first_name";
    private static final String KEY_USERS_LNAME = "last_name";
    private static final String KEY_USERS_EMAIL = "email";
    private static final String KEY_USERS_PASSWORD = "password";

    // Events Table Columns names
    private static final String KEY_EVENTS_ID = "id";
    private static final String KEY_EVENTS_NAME = "name";
    private static final String KEY_EVENTS_LOCATION = "location";
    private static final String KEY_EVENTS_INFO = "info";
    private static final String KEY_EVENTS_YEAR = "year";
    private static final String KEY_EVENTS_MONTH = "month";
    private static final String KEY_EVENTS_DAY = "day";
    private static final String KEY_EVENTS_HOUR = "hour";
    private static final String KEY_EVENTS_MINUTE = "minute";

    // UserPerEvent Table Columns names
    private static final String KEY_USERPEREVENT_ID = "id";
    private static final String KEY_USERPEREVENT_EVENT = "eventid";
    private static final String KEY_USERPEREVENT_USER = "userid";

    public DBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_USERS_ID + " INTEGER PRIMARY KEY," + KEY_USERS_FNAME + " TEXT,"
                + KEY_USERS_LNAME + " TEXT," + KEY_USERS_EMAIL + " TEXT," + KEY_USERS_PASSWORD + " TEXT" + ")";
        db.execSQL(CREATE_USERS_TABLE);
        String CREATE_EVENTS_TABLE = "CREATE TABLE " + TABLE_EVENTS + "("
                + KEY_EVENTS_ID + " INTEGER PRIMARY KEY," + KEY_EVENTS_NAME + " TEXT,"
                + KEY_EVENTS_LOCATION + " TEXT," + KEY_EVENTS_INFO + " TEXT,"
                + KEY_EVENTS_YEAR + " INTEGER,"+ KEY_EVENTS_MONTH + " INTEGER,"+ KEY_EVENTS_DAY + " INTEGER,"
                + KEY_EVENTS_HOUR + " INTEGER,"+ KEY_EVENTS_MINUTE + " INTEGER" + ")";
        db.execSQL(CREATE_EVENTS_TABLE);
        String CREATE_USEREVENT_TABLE = "CREATE TABLE " + TABLE_USERPEREVENT + "("
                + KEY_USERPEREVENT_ID + " INTEGER PRIMARY KEY," + KEY_USERPEREVENT_EVENT + " INTEGER,"
                + KEY_USERPEREVENT_USER + " INTEGER" + ")";
        db.execSQL(CREATE_USEREVENT_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EVENTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERPEREVENT);
        // Creating tables again
        onCreate(db);
    }

    // Adding new user
    public int addUser(RegisterUser user) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_USERS_FNAME, user.getFirstName());
            values.put(KEY_USERS_LNAME, user.getLastName());
            values.put(KEY_USERS_EMAIL, user.getEmail());
            values.put(KEY_USERS_PASSWORD, user.getpassword());

            // Inserting Row
            long id = db.insert(TABLE_USERS, null, values);
            db.close(); // Closing database connection
            return (int) id;
        }catch (Exception e){
            return 0;
        }
    }

    // Adding new event
    public void addEvent(EventInformation event) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_EVENTS_NAME, event.getEventName());
            values.put(KEY_EVENTS_LOCATION, event.getLocation());
            values.put(KEY_EVENTS_INFO, event.getExtraInfo());
            values.put(KEY_EVENTS_YEAR, event.getYear());
            values.put(KEY_EVENTS_MONTH, event.getMonth());
            values.put(KEY_EVENTS_DAY, event.getDay());
            values.put(KEY_EVENTS_HOUR, event.getHour());
            values.put(KEY_EVENTS_MINUTE, event.getMinute());

            // Inserting Row
            db.insert(TABLE_EVENTS, null, values);
            db.close(); // Closing database connection
        }catch (Exception e){

        }
    }

    public void addUserForEvent(int userID, int eventID){
        try{
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(KEY_USERPEREVENT_EVENT, eventID);
            values.put(KEY_USERPEREVENT_USER, userID);

            // Inserting Row
            db.insert(TABLE_USERPEREVENT, null, values);
            db.close(); // Closing database connection
        }catch (Exception e){

        }
    }

    public RegisterUser getUserRegister(int id) {
        String selectQuery = "SELECT  * FROM " + TABLE_USERS + " Where id = '"+id+"'" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        RegisterUser foundUser = new RegisterUser();
        if (cursor.moveToFirst()) {
            foundUser.setId(Integer.parseInt(cursor.getString(0)));
            foundUser.setFirstName(cursor.getString(1));
            foundUser.setLastName(cursor.getString(2));
            foundUser.setEmail(cursor.getString(3));
            foundUser.setPassword(cursor.getString(4));
        }
        // return user
        return foundUser;
    }

    public EventInformation getEvent (int id){
        String selectQuery = "SELECT  * FROM " + TABLE_EVENTS + " Where id = '"+id+"'" ;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        EventInformation foundEvent = new EventInformation();
        if (cursor.moveToFirst()) {
            foundEvent.setId(Integer.parseInt(cursor.getString(0)));
            foundEvent.setEventName(cursor.getString(1));
            foundEvent.setLocation(cursor.getString(2));
            foundEvent.setExtraInfo(cursor.getString(3));
            foundEvent.setYear(Integer.parseInt(cursor.getString(4)));
            foundEvent.setMonth(Integer.parseInt(cursor.getString(5)));
            foundEvent.setDay(Integer.parseInt(cursor.getString(6)));
            foundEvent.setHour(Integer.parseInt(cursor.getString(7)));
            foundEvent.setMinute(Integer.parseInt(cursor.getString(8)));
        }
        // return event
        return foundEvent;
    }

    public List<RegisterUser> getAllUsers() {
        try {
            List<RegisterUser> userList = new ArrayList<RegisterUser>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_USERS;

            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    RegisterUser user = new RegisterUser();
                    user.setId(Integer.parseInt(cursor.getString(0)));
                    user.setFirstName(cursor.getString(1));
                    user.setLastName(cursor.getString(2));
                    user.setEmail(cursor.getString(3));
                    user.setPassword(cursor.getString(4));
                    // Adding user to list
                    userList.add(user);
                } while (cursor.moveToNext());
            }

            // return userList
            return userList;
        }catch (Exception e){
            return null;
        }
    }

    public ArrayList<EventInformation> getAllEvents() {
        try{
            ArrayList<EventInformation> eventsList = new ArrayList<EventInformation>();
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_EVENTS;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    EventInformation event = new EventInformation();
                    event.setId(Integer.parseInt(cursor.getString(0)));
                    event.setEventName(cursor.getString(1));
                    event.setLocation(cursor.getString(2));
                    event.setExtraInfo(cursor.getString(3));
                    event.setYear(Integer.parseInt(cursor.getString(4)));
                    event.setMonth(Integer.parseInt(cursor.getString(5)));
                    event.setDay(Integer.parseInt(cursor.getString(6)));
                    event.setHour(Integer.parseInt(cursor.getString(7)));
                    event.setMinute(Integer.parseInt(cursor.getString(8)));
                    // Adding user to list
                    eventsList.add(event);
                } while (cursor.moveToNext());
            }

            // return userList
            return eventsList;
        }
        catch (Exception e){
            return null;
        }
    }

    public ArrayList<EventInformation> getAllEventsOfUserOfMonthYear(int userID, int month, int year) {
        try{
            ArrayList<EventInformation> eventsList = new ArrayList<EventInformation>();
            // Select All Query
            String selectQuery = "SELECT * FROM " +  TABLE_USERPEREVENT + " a inner join " + TABLE_EVENTS + " b ON a.eventid = b.id  where a.userid = " + userID + " and b.year = " + year + " AND b.month = " + month;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    EventInformation event = new EventInformation();
                    event.setId(Integer.parseInt(cursor.getString(3)));
                    event.setEventName(cursor.getString(4));
                    event.setLocation(cursor.getString(5));
                    event.setExtraInfo(cursor.getString(6));
                    event.setYear(Integer.parseInt(cursor.getString(7)));
                    event.setMonth(Integer.parseInt(cursor.getString(8)));
                    event.setDay(Integer.parseInt(cursor.getString(9)));
                    event.setHour(Integer.parseInt(cursor.getString(10)));
                    event.setMinute(Integer.parseInt(cursor.getString(11)));
                    // Adding user to list
                    eventsList.add(event);
                } while (cursor.moveToNext());
            }

            // return userList
            return eventsList;
        }
        catch (Exception e){
            return null;
        }
    }

    public ArrayList<EventInformation> getAllEventsOfUser(int userID) {
        try{
            ArrayList<EventInformation> eventsList = new ArrayList<EventInformation>();
            // Select All Query
            String selectQuery = "SELECT * FROM " +  TABLE_USERPEREVENT + " a inner join " + TABLE_EVENTS + " b ON a.eventid = b.id  where a.userid = " + userID;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    EventInformation event = new EventInformation();
                    event.setId(Integer.parseInt(cursor.getString(3)));
                    event.setEventName(cursor.getString(4));
                    event.setLocation(cursor.getString(5));
                    event.setExtraInfo(cursor.getString(6));
                    event.setYear(Integer.parseInt(cursor.getString(7)));
                    event.setMonth(Integer.parseInt(cursor.getString(8)));
                    event.setDay(Integer.parseInt(cursor.getString(9)));
                    event.setHour(Integer.parseInt(cursor.getString(10)));
                    event.setMinute(Integer.parseInt(cursor.getString(11)));
                    // Adding user to list
                    eventsList.add(event);
                } while (cursor.moveToNext());
            }

            // return userList
            return eventsList;
        }
        catch (Exception e){
            return null;
        }
    }

    public ArrayList<EventInformation> getAllEventsOfUserIDandEventID(int userID, int eventID) {
        try{
            ArrayList<EventInformation> eventsList = new ArrayList<EventInformation>();
            // Select All Query
            String selectQuery = "SELECT * FROM " +  TABLE_USERPEREVENT + " a inner join " + TABLE_EVENTS + " b ON a.eventid = b.id  where a.userid = " + userID + " and a.eventid = " + eventID;

            SQLiteDatabase db = this.getWritableDatabase();
            Cursor cursor = db.rawQuery(selectQuery, null);

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    EventInformation event = new EventInformation();
                    event.setId(Integer.parseInt(cursor.getString(3)));
                    event.setEventName(cursor.getString(4));
                    event.setLocation(cursor.getString(5));
                    event.setExtraInfo(cursor.getString(6));
                    event.setYear(Integer.parseInt(cursor.getString(7)));
                    event.setMonth(Integer.parseInt(cursor.getString(8)));
                    event.setDay(Integer.parseInt(cursor.getString(9)));
                    event.setHour(Integer.parseInt(cursor.getString(10)));
                    event.setMinute(Integer.parseInt(cursor.getString(11)));
                    // Adding user to list
                    eventsList.add(event);
                } while (cursor.moveToNext());
            }

            // return userList
            return eventsList;
        }
        catch (Exception e){
            return null;
        }
    }

    public boolean updateUser(int id,String firstName,String lastName,String email, String password) {
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_USERS_ID,id);
            contentValues.put(KEY_USERS_FNAME,firstName);
            contentValues.put(KEY_USERS_LNAME,lastName);
            contentValues.put(KEY_USERS_EMAIL,email);
            contentValues.put(KEY_USERS_PASSWORD,password);
            db.update(TABLE_USERS, contentValues, "ID = ?",new String[] { Integer.toString(id) });
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void delete_byID(int userID, int eventID){
        try{
            SQLiteDatabase db = this.getReadableDatabase();
            db.execSQL("delete from "+ TABLE_USERPEREVENT + " WHERE " + KEY_USERPEREVENT_EVENT + " = " + eventID + " and " + KEY_USERPEREVENT_USER + " = " + userID);
        }catch (Exception e){
        }
    }
}
