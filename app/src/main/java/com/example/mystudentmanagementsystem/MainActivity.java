package com.example.mystudentmanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText name_editText, roll_no_editText, marks_editText;

    private Button insert_button, update_button, delete_button, view_button, view_all_button, show_button;

    SQLiteDatabase sqLiteDatabase;

    @Override

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name_editText = findViewById(R.id.name_Id);
        roll_no_editText = findViewById(R.id.roll_no_Id);
        marks_editText = findViewById(R.id.marks_Id);

        insert_button = findViewById(R.id.insertButton_Id);
        update_button = findViewById(R.id.updateButton_Id);
        delete_button = findViewById(R.id.deleteButton_Id);
        view_button = findViewById(R.id.viewButton_Id);
        view_all_button = findViewById(R.id.view_allButton_Id);
        show_button = findViewById(R.id.showButton_Id);

        insert_button.setOnClickListener(this);
        update_button.setOnClickListener(this);
        delete_button.setOnClickListener(this);
        view_button.setOnClickListener(this);
        view_all_button.setOnClickListener(this);
        show_button.setOnClickListener(this);

        sqLiteDatabase = openOrCreateDatabase("Student_manage", Context.MODE_PRIVATE, null);
        sqLiteDatabase.execSQL(" CREATE TABLE IF NOT EXISTS student(rollno INTEGER, name VARCHAR, marks FLOAT) ");
    }

    @Override

    public void onClick(View v) {

        if (v.getId() == R.id.insertButton_Id) {

            // TODO Auto-generated method stub
            if(roll_no_editText.getText().toString().trim().length()==0||
                    name_editText.getText().toString().trim().length()==0||
                    marks_editText.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter all values");
                return;
            }
            sqLiteDatabase.execSQL("INSERT INTO student VALUES('"+roll_no_editText.getText()+"','"+name_editText.getText()+
                    "','"+marks_editText.getText()+"');");
            showMessage("Success", "Record added successfully");
            clearText();
        }

        if (v.getId() == R.id.updateButton_Id) {

            // TODO Auto-generated method stub
            if(roll_no_editText.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Rollno");
                return;
            }
            Cursor c=sqLiteDatabase.rawQuery("SELECT * FROM student WHERE rollno='"+roll_no_editText.getText()+"'", null);
            if(c.moveToFirst())
            {
                sqLiteDatabase.execSQL("UPDATE student SET name='"+name_editText.getText()+"',marks='"+marks_editText.getText()+
                        "' WHERE rollno='"+roll_no_editText.getText()+"'");
                showMessage("Success", "Record Modified");
            }
            else
            {
                showMessage("Error", "Invalid Roll no.");
            }
            clearText();
        }

        if (v.getId() == R.id.deleteButton_Id) {

            // TODO Auto-generated method stub
            if(roll_no_editText.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Rollno");
                return;
            }
            Cursor c=sqLiteDatabase.rawQuery("SELECT * FROM student WHERE rollno='"+roll_no_editText.getText()+"'", null);
            if(c.moveToFirst())
            {
                sqLiteDatabase.execSQL("DELETE FROM student WHERE rollno='"+roll_no_editText.getText()+"'");
                showMessage("Success", "Record Deleted");
            }
            else
            {
                showMessage("Error", "Invalid Roll no.");
            }
            clearText();
        }

        if (v.getId() == R.id.viewButton_Id) {

            // TODO Auto-generated method stub
            if(roll_no_editText.getText().toString().trim().length()==0)
            {
                showMessage("Error", "Please enter Rollno");
                return;
            }
            Cursor c=sqLiteDatabase.rawQuery("SELECT * FROM student WHERE rollno='"+roll_no_editText.getText()+"'", null);
            if(c.moveToFirst())
            {
                name_editText.setText(c.getString(1));
                marks_editText.setText(c.getString(2));
            }
            else
            {
                showMessage("Error", "Invalid Rollno");
                clearText();
            }
        }

        if (v.getId() == R.id.view_allButton_Id) {

            // TODO Auto-generated method stub
            Cursor c=sqLiteDatabase.rawQuery("SELECT * FROM student", null);
            if(c.getCount()==0)
            {
                showMessage("Error", "No records found");
                return;
            }
            StringBuffer buffer=new StringBuffer();
            while(c.moveToNext())
            {
                buffer.append("Roll no: "+c.getString(0)+"\n");
                buffer.append("Name: "+c.getString(1)+"\n");
                buffer.append("Marks: "+c.getString(2)+"\n\n");
            }
            showMessage("Student Details", buffer.toString());
        }

        if (v.getId() == R.id.showButton_Id) {

            // TODO Auto-generated method stub
            showMessage("Student Management Application", "Developed By Johirul Prokhor.");
        }
    }

    public void showMessage(String title,String message) {

        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
    public void clearText() {

        roll_no_editText.setText("");
        name_editText.setText("");
        marks_editText.setText("");
        roll_no_editText.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.student_menu, menu);
        return true;
    }
}
