package c.storemanager;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SalesFragment extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sales);

        BottomNavigationView btmView = findViewById(R.id.bottom_navigation);
        btmView.setOnNavigationItemSelectedListener(navListener);
    }
        BottomNavigationView.OnNavigationItemSelectedListener navListener =
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.home_icon:
                                Intent intent0 =new Intent(SalesFragment.this, MainActivity.class);
                                startActivity(intent0);
                                break;
                            case R.id.sales_icon:
                                break;
                            case R.id.stock_icon:
                                Intent intent2 =new Intent(SalesFragment.this, StockFragment.class);
                                startActivity(intent2);
                                break;
                            case R.id.files_icon:
                                Intent intent3 =new Intent(SalesFragment.this, View_filesFragment.class);
                                startActivity(intent3);

                                break;
                            case R.id.share_icon:
                                Intent intent4 =new Intent(SalesFragment.this, ShareFragment.class);
                                startActivity(intent4);

                                break;
                        }
                        return false;
                    }
                };

}
