package c.storemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private LinearLayout lL_receipt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        lL_receipt = findViewById(R.id.lL_receipt);
        lL_receipt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openHomePage();
            }
        });

//        BottomNavigationView btmView = findViewById(R.id.bottom_navigation);
//        btmView.setOnNavigationItemSelectedListener(navListener);
    }


//    BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
//    BottomNavigationViewHelper.diablebleShiftMode(bottomNavigationView);
//
//    BottomNavigationView.OnNavigationItemSelectedListener()

//    BottomNavigationView.OnNavigationItemSelectedListener navListener =
//            new BottomNavigationView.OnNavigationItemSelectedListener() {
//                @Override
//                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
//                    switch (menuItem.getItemId()){
//                        case R.id.home_icon:
//                            break;
//                        case R.id.sales_icon:
//                            Intent intent1 =new Intent(MainActivity.this, SalesFragment.class);
//                            startActivity(intent1);
//                            break;
//                        case R.id.stock_icon:
//                            Intent intent2 =new Intent(MainActivity.this, StockFragment.class);
//                            startActivity(intent2);
//                            break;
//                        case R.id.files_icon:
//                            Intent intent3 =new Intent(MainActivity.this, View_filesFragment.class);
//                            startActivity(intent3);
//
//                            break;
//                        case R.id.share_icon:
//                            Intent intent4 =new Intent(MainActivity.this, ShareFragment.class);
//                            startActivity(intent4);
//
//                            break;
//                    }
//                    return false;
//                }
//            };

    public  void openHomePage(){
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
    }
}