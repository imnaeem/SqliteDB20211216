package haqnawaz.org.sqlitedb20211216;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.media.session.PlaybackState;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class DbHelper extends SQLiteOpenHelper {
    public static final String STUDENT_NAME = "STUDENTName";
    public static final String STUDENT_AGE = "STUDENTAge";
    public static final String ACTIVE_STUDENT = "ActiveSTUDENT";
    public static final String STUDENT_ID = "STUDENTID";
    public static final String STUDENT_TABLE = "StudentTable";


    public DbHelper(@Nullable Context context) {
        super(context, "studentDB.db", null, 4);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableSTatementOne = "CREATE TABLE CustTable(STUDENTID Integer PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME_FIRST + " Text, STUDENTAge Int, ActiveSTUDENT BOOL) ";
        String createTableSTatement = "CREATE TABLE " + STUDENT_TABLE + "(" + STUDENT_ID + " Integer PRIMARY KEY AUTOINCREMENT, " + STUDENT_NAME + " Text, " + STUDENT_AGE + " Int, " + ACTIVE_STUDENT + " BOOL) ";
        db.execSQL(createTableSTatement);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + STUDENT_TABLE);
        onCreate(db);
    }

    public void  addStudent(StudentModel STUDENTModel){
        SQLiteDatabase db = this.getWritableDatabase();
        //Hash map, as we did in bundles
        ContentValues cv = new ContentValues();

        cv.put(STUDENT_NAME, STUDENTModel.getName());
        cv.put(STUDENT_AGE, STUDENTModel.getAge());
        cv.put(ACTIVE_STUDENT, STUDENTModel.isActive());
        db.insert(STUDENT_TABLE, null, cv);
        db.close();

        //NullCoumnHack
        //long insert =
        //if (insert == -1) { return false; }
        //else{return true;}
    }

    public ArrayList<StudentModel> getAllStudents() {

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursorCourses = db.rawQuery("SELECT * FROM " + STUDENT_TABLE, null);

        ArrayList<StudentModel> studentArrayList = new ArrayList<>();

        // moving our cursor to first position.
        if (cursorCourses.moveToFirst()) {
            do {
                StudentModel st = new StudentModel(cursorCourses.getString(1), cursorCourses.getInt(2), cursorCourses.getInt(3) == 1);
                st.setId(cursorCourses.getInt(0));
                studentArrayList.add(st);
            } while (cursorCourses.moveToNext());

        }

        cursorCourses.close();
        return studentArrayList;
    }

    public boolean deleteStudent (int id)
    {
        String sql = "DELETE FROM StudentTable WHERE STUDENTID = "+ id;
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL(sql);
        db.close();
        return true;
    }

    public void updateStudent(int id, String name, int age, boolean status)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(STUDENT_NAME, name);
        cv.put(STUDENT_AGE, age);
        cv.put(ACTIVE_STUDENT, status);
        String whereClause = "STUDENTID" + " = ?";
        String[] values = new String[]{String.valueOf(id)};

        db.update(STUDENT_TABLE, cv, whereClause, values);
        db.close();
    }

    public boolean deleteAll ()
    {
        String sql = "DELETE FROM StudentTable";
        SQLiteDatabase db = this.getReadableDatabase();

        db.execSQL(sql);
        db.close();
        return true;
    }

}
