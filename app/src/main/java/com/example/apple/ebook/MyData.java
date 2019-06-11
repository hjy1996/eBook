package com.example.apple.ebook;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by apple on 2018/5/16.
 */

public class MyData extends SQLiteOpenHelper {

    private final static String database = "eBook_db";   //设置数据库名称
    private final static int version = 1;
    private final static String usertable = "User_table";
    private final static String booktable = "PVT_Booktable";
    private final static String leasetable = "Lease_table";

    public final static String User_ID = "_id";
    public final static String User_PassWord = "password";
    public final static String User_Name = "name";
    public final static String User_Realname = "realname";
    public final static String User_Contact = "contact";
    public final static String User_Address = "address";

    public final static String PvtBookID = "pvt_bookid";
    public final static String PvtID = "pvtid";
    public final static String PvtBookName = "pvt_bookname";
    public final static String PvtBookAuth = "pvt_bookauth";
    public final static String PvtBookInfo = "pvt_bookinfo";
    public final static String BookQueue = "bookqueue";

    public final static String LeaseNum = "lease_num";
    public final static String LeaseID = "lease_id";
    public final static String HostID = "host_id";
    public final static String LeaseBookID = "leasebook_id";
    public final static String LeaserID = "leaser_id";
    public final static String LeaserName = "leaser_name";
    public final static String LeaserContact = "leaser_contact";
    public final static String LeaserAddress = "leaser_address";
    public final static String LeaseBook = "lease_book";

    public int userid;

    String User = "create table User_table (_id integer primary key autoincrement,"   +
            "name text," +
            "password text, " +
            "realname text," +
            "contact text," +
            "address text)";

    String Book = "create table PVT_Booktable (pvt_bookid integer primary key autoincrement," +
            "pvtid int," +
            "pvt_bookname text," +
            "pvt_bookauth text," +
            "pvt_bookinfo text," +
            "bookqueue int)";

    String Lease = "create table Lease_table (" + LeaseID + " integer primary key autoincrement," +
            LeaseNum + " int," +
            LeaseBookID + " int," +
            HostID + " int," +
            LeaserID + " int," +
            LeaserName + " text," +
            LeaserContact + " text," +
            LeaserAddress + " text," +
            LeaseBook + " text)";

    public MyData(Context context) {
        super(context, database, null, version);
        //重写构造方法并设置工厂为空
    }






    @Override
    public void onCreate(SQLiteDatabase db) {

                db.execSQL(User);   //创建用户表
                db.execSQL(Book);   //创建书籍表
                db.execSQL(Lease);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        System.out.println("---更新---" + oldVersion + "-->" + newVersion);
    }


    public int insertUser(String name, String password, String realname, String contact,
                           String address ){

        SQLiteDatabase db = this.getWritableDatabase();
        //实例化一个SQLiteDatabase对象

        ContentValues cv = new ContentValues();
        //将修改的数据存入ContentValues对象中

        cv.put(User_Name,name);
        cv.put(User_PassWord,password);
        cv.put(User_Realname,realname);
        cv.put(User_Contact,contact);
        cv.put(User_Address,address);
        //存入数据

        int result = (int) db.insert(usertable,null,cv);
        //通过isert方法返回插入数据的结果
        //若插入成功，返回行数；插入失败，返回-1

        cv.put(User_ID,result);
        db.close();
        return result;

    }

    public int insertBook(String bookname, String bookauth, String bookifo, int pvtid){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(PvtBookName,bookname);
        cv.put(PvtBookAuth,bookauth);
        cv.put(PvtBookInfo,bookifo);
        cv.put(PvtID,pvtid);
        cv.put(BookQueue, 0);

        int bookresult = (int) db.insert(booktable, null, cv);
        
        cv.put(PvtBookID, bookresult);
        return bookresult;
    }

    public int insertLease(int id1, int id2, int id3, int id4,String name, String contact, String address,
                            String bookname){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LeaserName, name);
        cv.put(LeaserContact, contact);
        cv.put(LeaserAddress, address);
        cv.put(HostID, id2);
        cv.put(LeaserID, id1);
        cv.put(LeaseBookID, id4);
        cv.put(LeaseNum, id3 + 1);
        cv.put(LeaseBook, bookname);

        int result = (int) db.insert(leasetable, null, cv);
        cv.put(LeaseID, result);

        return id3 + 1;

    }

    public int SearchRename(String name){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = User_Name + " = ? ";
        String[] whereValues = {name};
        Cursor cursor = db.query(usertable,null, where, whereValues,null,
                null,null);
        if (cursor.moveToNext()){
            return 0;
        }else
            return 1;
    }

    public int login(String name, String password){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = User_Name + " = ? " + " and " + User_PassWord + " = ? ";
        String[] whereValues = {name, password};
        Cursor cursor = db.query(usertable,null, where, whereValues,null,
                null,null);
        if (cursor.moveToNext()){
            int index = cursor.getColumnIndex(User_ID);
            userid = cursor.getInt(index);
            return userid;
        }else
            return 0;
        //cursor.moveToNext为假时将跳出query循环，表示输入的的用户名与密码匹配不到
    }

    public String getUsername(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        String where = User_ID + "=?";
        String[] wherevalues = {String.valueOf(id)};
        Cursor cursor = db.query(usertable, null, where, wherevalues,null,
                null,null);
        if (cursor.moveToNext()) {
            int index = cursor.getColumnIndex("name");
            String username = cursor.getString(index);
            return username;
        }
        return "errorname";
    }

    public String getUserrealname(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = User_ID + "=?";
        String[] wherevalues = {String.valueOf(id)};
        Cursor cursor = db.query(usertable, null, where, wherevalues,null,
                null,null);
        if (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(User_Realname);
            String userrealname = cursor.getString(index);
            return userrealname;
        }
        return "errorname";

    }

    public String getUsercontact(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = User_ID + "=?";
        String[] wherevalues = {String.valueOf(id)};
        Cursor cursor = db.query(usertable, null, where, wherevalues,null,
                null,null);
        if (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(User_Contact);
            String usercontact = cursor.getString(index);
            return usercontact;
        }
        return "errorcontact";

    }

    public String getUseraddress(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String where = User_ID + "=?";
        String[] wherevalues = {String.valueOf(id)};
        Cursor cursor = db.query(usertable, null, where, wherevalues,null,
                null,null);
        if (cursor.moveToNext()) {
            int index = cursor.getColumnIndex(User_Address);
            String useraddress = cursor.getString(index);
            return useraddress;
        }
        return "erroraddress";

    }


    public List<Map<String, Object>> SearchByID(int id){

        SQLiteDatabase db = this.getWritableDatabase();
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        String where = PvtID + "=?";
        String[] wherevalues = {String.valueOf(id)};
        Cursor cursor = db.query(booktable, null, where, wherevalues, null, null,null);

            while (cursor.moveToNext()) {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put(PvtBookID, cursor.getInt(cursor.getColumnIndex(PvtBookID)));
                map.put(PvtBookName, cursor.getString(cursor.getColumnIndex(PvtBookName)));
                map.put(PvtBookAuth, cursor.getString(cursor.getColumnIndex(PvtBookAuth)));
                map.put(PvtBookInfo, cursor.getString(cursor.getColumnIndex(PvtBookInfo)));
                map.put(PvtID,cursor.getInt(cursor.getColumnIndex(PvtID)));
                map.put(BookQueue,cursor.getInt(cursor.getColumnIndex(BookQueue)));
                data.add(map);

            }
            return data;


    }

    public List<Map<String, Object>> ShowBook(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(booktable, null, null, null, null, null,null);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        while(cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String,Object>();
            map.put(PvtBookID,cursor.getInt(cursor.getColumnIndex(PvtBookID)));
            map.put(PvtBookName,cursor.getString(cursor.getColumnIndex(PvtBookName)));
            map.put(PvtBookAuth,cursor.getString(cursor.getColumnIndex(PvtBookAuth)));
            map.put(PvtBookInfo,cursor.getString(cursor.getColumnIndex(PvtBookInfo)));
            map.put(PvtID,cursor.getInt(cursor.getColumnIndex(PvtID)));
            map.put(BookQueue,cursor.getInt(cursor.getColumnIndex(BookQueue)));

            data.add(map);

        }
        return data;

    }

    public List<Map<String, Object>> ShowLeaserByID(int id1){

        SQLiteDatabase db = this.getWritableDatabase();

        String where = HostID + "=?" + " and " + LeaseNum + "=?";
        String[] wherevalues = {String.valueOf(id1), String.valueOf(1)};

        Cursor cursor = db.query(leasetable, null, where, wherevalues,
                null, null, null);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        while(cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String,Object>();
            map.put(LeaseID, cursor.getInt(cursor.getColumnIndex(LeaseID)));
            map.put(LeaserID, cursor.getInt(cursor.getColumnIndex(LeaserID)));
            map.put(LeaseBook,cursor.getString(cursor.getColumnIndex(LeaseBook)));
            map.put(LeaserName,cursor.getString(cursor.getColumnIndex(LeaserName)));
            map.put(LeaserContact,cursor.getString(cursor.getColumnIndex(LeaserContact)));
            map.put(LeaserAddress,cursor.getString(cursor.getColumnIndex(LeaserAddress)));
            map.put(LeaseNum,cursor.getInt(cursor.getColumnIndex(LeaseNum)));
            map.put(HostID, cursor.getInt(cursor.getColumnIndex(HostID)));
            map.put(LeaseBookID,cursor.getInt(cursor.getColumnIndex(LeaseBookID)));
            data.add(map);

        }
        return data;

    }

    public List<Map<String, Object>> ShowBorrowerByID(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String where = LeaserID + " =? "+ " and " + LeaseNum + " =? " ;
        String[] wherevalues = {String.valueOf(id),String.valueOf(1)};

        Cursor cursor = db.query(leasetable, null, where, wherevalues,
                null, null, null);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();

        while(cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String,Object>();
            map.put(LeaseBook,cursor.getString(cursor.getColumnIndex(LeaseBook)));

            data.add(map);

        }
        return data;

    }

    public List<Map<String, Object>> ShowUser(){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.query(usertable,null,null,null,null,
                        null,null);
        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        while (cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put(User_ID, cursor.getInt(cursor.getColumnIndex(User_ID)));
            map.put(User_Name, cursor.getString(cursor.getColumnIndex(User_Name)));

            data.add(map);
        }
        return data;
    }

    public List<Map<String, Object>> SearchBykey(String key){

        SQLiteDatabase db = this.getWritableDatabase();

        String where = PvtBookName + " like '%" + key + "%' or " +
                       PvtBookAuth + " like '%" + key + "%' or " +
                       PvtBookInfo + " like '%" + key + "%'";
        Cursor cursor = db.query(booktable, null , where, null,
                null, null,null);

        List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
        while (cursor.moveToNext()){
            Map<String, Object> map = new HashMap<String,Object>();
            map.put(PvtBookID,cursor.getInt(cursor.getColumnIndex(PvtBookID)));
            map.put(PvtBookName,cursor.getString(cursor.getColumnIndex(PvtBookName)));
            map.put(PvtBookAuth,cursor.getString(cursor.getColumnIndex(PvtBookAuth)));
            map.put(PvtBookInfo,cursor.getString(cursor.getColumnIndex(PvtBookInfo)));
            map.put(PvtID,cursor.getInt(cursor.getColumnIndex(PvtID)));
            map.put(BookQueue,cursor.getInt(cursor.getColumnIndex(BookQueue)));

            data.add(map);

        }
    return data;
    }

    public String Showname(int id){
        String name = null ;
        SQLiteDatabase db = this.getWritableDatabase();
        String where = User_ID + "=?";
        String[] wherevalues = {String.valueOf(id)};

        Cursor cursor = db.query(usertable, null, where, wherevalues,
                null,null, null);
        if (cursor.moveToNext()){
            name = cursor.getString(cursor.getColumnIndex(User_Name));
        }
        return name;
    }

    public boolean DeleteUser(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String where1 = User_ID + "=?";
        String where4 = PvtID + "=?";
        String where2 = HostID + "=?";
        String where3 = LeaserID + "=?";
        String[] wherevalues = {String.valueOf(id)};

        boolean result1 = (db.delete(usertable,where1,wherevalues) > 0);
        boolean result2 = (db.delete(leasetable,where2,wherevalues) > 0);
        boolean result3 = (db.delete(booktable, where4,wherevalues) > 0);

        Cursor cursor = db.query(leasetable,null, where3, wherevalues,null,
                null,null);
        while (cursor.moveToNext()){
            cv.put(LeaserID, 0);
            db.update(leasetable, cv, where3, wherevalues);
        }
        return result1 && result2 && result3;
    }

    public boolean DeleteBook(int id){

        SQLiteDatabase db = this.getWritableDatabase();

        String where1 = PvtBookID + "=?" ;
        String[] wherevalues = {String.valueOf(id)};
        String where2 = LeaseBookID + "=?";
        boolean result1 = ( db.delete(booktable, where1, wherevalues) > 0 );
        boolean result2 = ( db.delete(leasetable, where2, wherevalues) > 0);

        return result1 && result2;
    }

    public boolean DeleteLease(int id){
        SQLiteDatabase db = this.getWritableDatabase();

        String where = LeaseID + "=?" ;
        String[] wherevalues = {String.valueOf(id)};
        boolean result = ( db.delete(leasetable, where, wherevalues) > 0 );

        return result;

    }

    public boolean Delete(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BookQueue, 0);

        boolean result1 = ( db.delete(leasetable, null, null) > 0 );
        boolean result2 = (db.update(booktable, cv,null,null) > 0);

        return result1 && result2;

    }

    public boolean UpdataLeaseNum(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String where = HostID + "=?" ;
        String[] wherevalues = {String.valueOf(id)};
        Cursor cursor = db.query(leasetable,null, where,wherevalues,null,
                null,null);
        while (cursor.moveToNext()){
            int leaseid = cursor.getInt(cursor.getColumnIndex(LeaseID));
            int leasenum = cursor.getInt(cursor.getColumnIndex(LeaseNum));
            updataNum(leaseid,leasenum);
        }
        return true;
    }

    public void updataNum(int id1, int id2){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(LeaseNum, id2-1);

        String where = LeaseID + "=?";
        String[] wherevalues = {String.valueOf(id1)};
        db.update(leasetable, cv, where, wherevalues);
    }

    public boolean UpdataBook(int bookid, String name, String bookauthor, String bookinfo){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(PvtBookName, name);
        cv.put(PvtBookAuth, bookauthor);
        cv.put(PvtBookInfo, bookinfo);

        String where = PvtBookID + "=?" ;
        String[] wherevalues = {String.valueOf(bookid)};

        boolean row = (db.update(booktable, cv, where, wherevalues) > 0);
        return row;
    }

    public boolean UpdataBookNum(int id, int number){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(BookQueue, number );

        String where = PvtBookID + "=?";
        String[] wherevalues = {String.valueOf(id)};

        boolean result = (db.update(booktable, cv, where, wherevalues) > 0);
        return result;
    }

    public boolean UpdataBookqueue(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String where = PvtBookID + "=?" ;
        String[] wherevalues = {String.valueOf(id)};
        Cursor cursor = db.query(booktable,null, where,wherevalues,null,
                null,null);
        if (cursor.moveToNext()) {
            int bookqueue = cursor.getInt(cursor.getColumnIndex(BookQueue));
            cv.put(BookQueue, bookqueue - 1);
        }

        boolean result = (db.update(booktable, cv, where,wherevalues) > 0);
        return result;
    }

    public boolean UpdataUser(int id, String contact, String address){

        SQLiteDatabase db =this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(User_Contact, contact);
        cv.put(User_Address, address);

        String where = User_ID + "=?";
        String[] wherevalues = {String.valueOf(id)};

        boolean result = ( db.update(usertable, cv, where, wherevalues) > 0 );

        return result;

    }

}
