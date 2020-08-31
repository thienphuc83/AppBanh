package com.example.chonbanh;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.Collections;

public class Main2Activity extends AppCompatActivity {

    TableLayout myTabble;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main2 );

        myTabble = findViewById ( R.id.tabLayout );

        int soDong = 10;
        int soCot  = 3;

        //trộn mảng để ra ngẫu nhiên
        Collections.shuffle ( MainActivity.arrayList );

        // tạo dòng và cột
        for (int i = 1; i <= soDong; i++){
            TableRow tableRow =new TableRow ( this );

            // tạo cột ImageView
            for (int j = 1; j <= soCot; j++){
                ImageView imageView = new ImageView ( this );

                // convert pixel sang dp. tại trong andoid chỉ dùng dp thôi bạn nhó
                int dip = 100;
                Resources r = getResources();
                int px = ( int ) TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        dip,
                        r.getDisplayMetrics()
                );

                TableRow.LayoutParams layoutParams = new TableRow.LayoutParams ( px,px );
                imageView.setLayoutParams ( layoutParams );
                // đỗ ra list
                final int vitri = soCot * ( i - 1 ) + j - 1; // công thức mặc định


                int idHinh = getResources ().getIdentifier ( MainActivity.arrayList.get ( vitri ),"drawable", getPackageName () );
                imageView.setImageResource ( idHinh );
                // add dự liệu cho dòng
                tableRow.addView ( imageView );

                // bắt sự kiện khi click vào imageView
                imageView.setOnClickListener ( new View.OnClickListener ( ) {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent ();
                        intent.putExtra ( "tenhinhchon", MainActivity.arrayList.get ( vitri ) );
                        setResult ( RESULT_OK, intent );
                        finish ();
                    }
                } );


            }
            // add dữ liệu cho bảng
            myTabble.addView ( tableRow );

        }



    }
}
