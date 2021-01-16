package project.estateWatch;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.support.annotation.Nullable;

public class myDatabaseHelper extends SQLiteOpenHelper {

    public myDatabaseHelper( @Nullable Context context) {
        super(context, "EstateWatchman.db-wal", null  , 3);
        //SQLiteDatabase db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table User(ID INTEGER PRIMARY KEY AUTOINCREMENT, Name TEXT, password TEXT, phoneNumber TEXT, Email TEXT, Address TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS User");
        onCreate(db);
    }

    //inserting into database
    public boolean insert(String name, String password, String email,  String phoneNumber, String address)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name",name);
        contentValues.put("password",password);
        contentValues.put("email",email);
        contentValues.put("PhoneNumber",phoneNumber);
        contentValues.put("address",address);

        long ins =   db.insert("User",null,contentValues);
        if (ins ==-1) return false;
        else return true;
    }

    //checking if email exists
    public Boolean checkEmail(String email){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("Select * from User where email=?", new String[] {email});
        if (cursor.getCount()>0) return false;
        else return true;
    }

    //checking email and password
    public Boolean checkEmailPassword(String email, String password) {
        SQLiteDatabase myDb = this.getReadableDatabase();
        Cursor cursor = myDb.rawQuery("select * from User where email=? and password=?", new String[]{email,password});
        if (cursor.getCount()>0) return false;
        else return true;
    }
}
