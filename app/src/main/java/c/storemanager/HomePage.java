package c.storemanager;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;




public class HomePage extends AppCompatActivity {

    private ArrayList<String> data = new ArrayList<String>();
    private ArrayList<String> data1 = new ArrayList<String>();
    private ArrayList<String> data2 = new ArrayList<String>();
    private ArrayList<String> data3 = new ArrayList<String>();

    private int STORAGE_PERMISSION_CODE= 1;

    private TableLayout table;
    EditText ed1, ed2, ed3, ed4, ed5, ed6, ed8, edCname, edCphone, edCemail;

    Button b1, b_discard, b_share, b_generate;
    Spinner sp1;
    int myPageWidth = 1200;
    Date dateobj;
    DateFormat dateFormat;
    String file = "receipt";
    private String fileContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        BottomNavigationView btmView = findViewById(R.id.bottom_navigation);
        btmView.setOnNavigationItemSelectedListener(navListener);




        ed1 = findViewById(R.id.ed1);
        ed2 = findViewById(R.id.ed2);
        ed3 = findViewById(R.id.ed3);

        ed4 = findViewById(R.id.txtsub);
        ed6 = findViewById(R.id.txtdiscount);
        ed8 = findViewById(R.id.txttotalcost);
        edCname= findViewById(R.id.txtname);
        edCphone =findViewById(R.id.txtphone);
        edCemail = findViewById(R.id.txtemail);


//        tv1 = findViewById(R.id.add_details);
        b1 = findViewById(R.id.btn1);
        b_discard = findViewById(R.id.btnclear);
        b_generate = findViewById(R.id.btngeneratefile);

        sp1 = findViewById(R.id.spinnerdoctype);

        table = findViewById(R.id.tb1);


//        b_generate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//
//            }

//            private void createTxt() {
//
//                fileContent = table.toString();
//                try {
//                    FileOutputStream fileOutputStream = openFileOutput(file, MODE_PRIVATE);
//                    fileOutputStream.write(fileContent.getBytes());
//                    fileOutputStream.close();
//
//                    File fileDirectory = new File(getFilesDir(), file);
//                    Toast.makeText(getBaseContext(), "FILE SAVED"+fileDirectory, Toast.LENGTH_LONG).show();
//                } catch (FileNotFoundException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        ed6.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(TextUtils.isEmpty(ed4.getText().toString().trim()) ){
                    Toast errorToast = Toast.makeText(HomePage.this, "Make at least one entry in the form above", Toast.LENGTH_SHORT);
                    errorToast.show();
                }
                else {
                    int subtotal = Integer.parseInt(ed4.getText().toString());
                    int discountable = 0;
                    discountable= Integer.parseInt(ed6.getText().toString());
                    int finalcost = (subtotal-discountable);
                    ed8.setText(String.valueOf(finalcost));
                }

            }
        });


        b_discard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDiscardPressed();
            }
        });



        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(HomePage.this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED){

                    if(TextUtils.isEmpty(ed1.getText().toString().trim()) ||
                            TextUtils.isEmpty(ed2.getText().toString().trim())  ||
                            TextUtils.isEmpty(ed3.getText().toString().trim()) ){
                        Toast errorToast = Toast.makeText(HomePage.this, "Name , price and quantity can't be empty!", Toast.LENGTH_LONG);
                        errorToast.show();
                    }
                    else{
                        add();
                    }
                }
                else{
                    requestStoragePermission();
                }

            }
        });
    }

    BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                    switch (menuItem.getItemId()){
                        case R.id.home_icon:
                            Intent intent0 =new Intent(HomePage.this, MainActivity.class);
                            startActivity(intent0);
                            break;
                        case R.id.sales_icon:
                            Intent intent4 =new Intent(HomePage.this, SalesFragment.class);
                            startActivity(intent4);
                            break;
                        case R.id.stock_icon:
                            Intent intent3 =new Intent(HomePage.this, StockFragment.class);
                            startActivity(intent3);
                            break;
                        case R.id.files_icon:
                            Intent intent1 =new Intent(HomePage.this, View_filesFragment.class);
                            startActivity(intent1);

                            break;
                        case R.id.share_icon:
                            Intent intent2 =new Intent(HomePage.this, ShareFragment.class);
                            startActivity(intent2);

                            break;
                    }
                    return false;
                }
            };

    private void requestStoragePermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)){

            new AlertDialog.Builder(this)
                    .setTitle("Permission Needed")
                    .setMessage("Permission to storage is needed to allow you save this data")
                    .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.requestPermissions(HomePage.this, new  String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                }
            }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            })
            .create().show();
        }
        else{
            ActivityCompat.requestPermissions(this, new  String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length> 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(this, "permission granted", Toast.LENGTH_SHORT).show();
            }
            else{

                Toast.makeText(this, "permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
//Takes screenshot of the data in the table
    public void generateFiles(View view) {

        if(TextUtils.isEmpty(edCname.getText().toString().trim())){
            Toast errorToast = Toast.makeText(HomePage.this, "Please Enter customers name!", Toast.LENGTH_LONG);
            errorToast.show();
        }
        else {

            File root = new File(Environment.getExternalStorageDirectory(), "Sto manager");
            if (!root.exists()) {
                root.mkdir();
            }
            View view1 = findViewById(R.id.tb1);
            view1.setDrawingCacheEnabled(true);
            Bitmap bitmap = Bitmap.createBitmap(view1.getDrawingCache());
            view1.setWillNotDraw(false);
            String docName = edCname.getText().toString().trim();
            String filepath = Environment.getExternalStorageDirectory() + "/Sto manager/"+docName+".jpg";
//            String filepath1 = "/Sto manager/" +edCname+ ".jpg";
            File fileScreenshot = new File(filepath);
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(fileScreenshot);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                fileOutputStream.flush();
                fileOutputStream.close();

                Toast.makeText(this, "img generated successfully", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Failed! try again", Toast.LENGTH_SHORT).show();
            }

//            Bitmap bitmap1 = BitmapFactory.decodeFile(filepath1);
//
//            //pdf
//            PdfDocument pdfDocument = new PdfDocument();
//            PdfDocument.PageInfo pi = new PdfDocument.PageInfo.Builder(bitmap1.getWidth(), bitmap1.getHeight(), 1).create();
//            PdfDocument.Page page = pdfDocument.startPage(pi);
//            Canvas canvas = page.getCanvas();
//            Paint paint = new Paint();
//            paint.setColor(Color.parseColor("#ffffff"));
//            canvas.drawPaint(paint);
//
//            bitmap = Bitmap.createScaledBitmap(bitmap1, bitmap1.getWidth(), bitmap1.getHeight(), true);
//            paint.setColor(Color.BLUE);
//            canvas.drawBitmap(bitmap1, 0, 0, null);
//            pdfDocument.finishPage(page);

//
//            File filePdf = new File(root, "try.pdf");
//            try {
//                FileOutputStream fileOutputStream1 = new FileOutputStream(file);
//                pdfDocument.writeTo(fileOutputStream1);
//                Toast.makeText(this, "File generated successfully", Toast.LENGTH_SHORT).show();
//            } catch (Exception e) {
//                e.printStackTrace();
//                Toast.makeText(this, "Error", Toast.LENGTH_LONG).show();
//            }
//            pdfDocument.close();
        }


//        generatepdf();
    }





//    public void generatepdf() {
//
//        if(TextUtils.isEmpty(edCname.getText().toString().trim())){
//            Toast errorToast = Toast.makeText(HomePage.this, "Please Enter customers name!", Toast.LENGTH_LONG);
//            errorToast.show();
//        }
//        else{
//
//            PdfDocument myPdf = new PdfDocument();
//            Paint myPaint = new Paint();
//
//            PdfDocument.PageInfo myPageInfo1 = new PdfDocument.PageInfo.Builder(1200, 2010, 1).create();
//            PdfDocument.Page mypage1 = myPdf.startPage(myPageInfo1);
//            Canvas canvas = mypage1.getCanvas();
//
//
//            myPdf.finishPage(mypage1);
//
//            File filepdf = new File(Environment.getExternalStorageDirectory(),"/hi.pdf");
//
//            try{
//                myPdf.writeTo(new FileOutputStream(filepdf));
//            }
//            catch (Exception e){
//                e.printStackTrace();
//            }
//
//            myPdf.close();
//        }
//    }



    //Creating method to add date to table layout
    public void add() {

        int tot;
        String name = ed1.getText().toString();
        int price = 0;
        try {
            price = Integer.parseInt(ed2.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast errorToast = Toast.makeText(HomePage.this, "Price can only take numeric values only!", Toast.LENGTH_SHORT);
            errorToast.show();
        }
        int qtt = 0;
        try {
            qtt = Integer.parseInt(ed3.getText().toString().trim());
        } catch (NumberFormatException e) {
            Toast errorToast = Toast.makeText(HomePage.this, "Quantity can only take numeric values only!", Toast.LENGTH_SHORT);
            errorToast.show();
        }

        tot = price * qtt;
        data.add(name);
        data1.add(String.valueOf(price));
        data2.add(String.valueOf(qtt));
        data3.add(String.valueOf(tot));

        TableLayout table = (TableLayout) findViewById(R.id.tb1);
        TableRow row = new TableRow(this);
        TextView t1 = new TextView(this);
        TextView t2 = new TextView(this);
        TextView t3 = new TextView(this);
        TextView t4 = new TextView(this);

        String total;
        int sum = 0;
        for (int i = 0; i < data.size(); i++) {
            String name1 = data.get(i);
            String prc = data1.get(i);
            String qt = data2.get(i);
            total = data3.get(i);
            t1.setText(name1);
            t2.setText(prc);
            t3.setText(qt);
            t4.setText(total);

            sum = sum + Integer.parseInt(data3.get(i).toString());
        }
//   adding data to table rows.
        row.addView(t1);
        row.addView(t2);
        row.addView(t3);
        row.addView(t4);




        table.addView(row);


        ed4.setText(String.valueOf(sum));

//  clearing the textviews.
        ed1.setText("");
        ed2.setText("");
        ed3.setText("");


        ed1.requestFocus();

    }



//    method for alert message box when discard button is pressed

    public void onDiscardPressed(){
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Discard all your entries");
        alertDialogBuilder.setMessage("All your entries in this form will be deleted!\nDo you want to continue?");
        alertDialogBuilder.setCancelable(false);
        alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
                startActivity(getIntent());
            }
        });

        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog = alertDialogBuilder. create();
        alertDialog.show();
    }

}