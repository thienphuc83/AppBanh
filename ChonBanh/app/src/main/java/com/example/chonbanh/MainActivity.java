package com.example.chonbanh;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    TextView tvDiem;
    ImageView imgGoc, imgDoi;
    public static ArrayList<String> arrayList;
    int REQUEST_CODE = 123;
    String tenHinhGoc = "";
    int diem=100;

    // lưu điểm
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_main );
        AnhXa ();

        // khởi tạo lưu điểm
        sharedPreferences = getSharedPreferences ( "dataDiem", MODE_PRIVATE );
        // lấy điểm cũ ra
        diem = sharedPreferences.getInt ( "diemluu", 100 ); // nếu ko có dữ liệu để show ra thì lấy mặc định lại 100 đ

        tvDiem.setText ( diem + "");

        String[] mangTen= getResources ().getStringArray ( R.array.list_name );
        arrayList = new ArrayList<> ( Arrays.asList ( mangTen ) );
        // thay vì dùng random để ra ngẫu  nhiên ảnh thì chọn trộn mảng để thay đổi value của phần tử số 5 bằng cách
        Collections.shuffle ( arrayList );

        tenHinhGoc = arrayList.get ( 5 ); //  lấy tên hình để so sánh đúng sai
        // idHinh ghép tên hình lấy trong mảng
        int idHinh= getResources ().getIdentifier ( arrayList.get ( 5 ),"drawable", getPackageName () );
        imgGoc.setImageResource ( idHinh ); // chỉ cần gọi idHinh thay vì R.drawable.hinh1

        imgDoi.setOnClickListener ( new View.OnClickListener ( ) {
            @Override
            public void onClick(View view) {
                startActivityForResult ( new Intent ( MainActivity.this, Main2Activity.class ), REQUEST_CODE );
            }
        } );

    }
    private void AnhXa(){
        imgDoi = findViewById ( R.id.imgDoi );
        imgGoc = findViewById ( R.id.imgGoc );
        tvDiem = findViewById ( R.id.tvDiem );

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null){
            String tenHinhNhan = data.getStringExtra ( "tenhinhchon" );
            int idHinh = getResources ().getIdentifier ( tenHinhNhan,"drawable",getPackageName () );
            imgDoi.setImageResource ( idHinh );

            // so sánh theo tên hình
            if (tenHinhGoc.equals ( tenHinhNhan )){
                Toast.makeText ( this, "Đúng rồi! \n Bạn được cộng 10 điểm!", Toast.LENGTH_SHORT ).show ( );
                // cộng điểm
                diem += 10;
                // lưu điểm
                SharedPreferences.Editor editor = sharedPreferences.edit ();
                editor.putInt ( "diemluu", diem );
                editor.commit ();
                // tự load hình sau 2 giây
                new CountDownTimer ( 3000, 100 ) {
                    @Override
                    public void onTick(long l) {
                    }
                    @Override
                    public void onFinish() {
                        Collections.shuffle ( arrayList );
                        tenHinhGoc = arrayList.get ( 5 ); //  lấy tên hình để so sánh đúng sai
                        // idHinh ghép tên hình lấy trong mảng
                        int idHinh= getResources ().getIdentifier ( arrayList.get ( 5 ),"drawable", getPackageName () );
                        imgGoc.setImageResource ( idHinh ); // chỉ cần gọi idHinh thay vì R.drawable.hinh1
                    }
                }.start ();

            }else {
                Toast.makeText ( this, "Sai rồi! \n Bạn bị trừ 5 điểm!", Toast.LENGTH_SHORT ).show ( );
                diem -= 5;
                // lưu điểm
                SharedPreferences.Editor editor = sharedPreferences.edit ();
                editor.putInt ( "diemluu", diem );
                editor.commit ();
            }
            tvDiem.setText ( diem +"" );
        }
        if (requestCode == REQUEST_CODE && resultCode == RESULT_CANCELED){
            Toast.makeText ( this, "Bạn chưa chọn hình! Muốn xem lại à!\n Vậy là ăn gian rồi, trừ 20 điểm!", Toast.LENGTH_SHORT ).show ( );
            diem -= 20;
            // lưu điểm
            SharedPreferences.Editor editor = sharedPreferences.edit ();
            editor.putInt ( "diemluu", diem );
            editor.commit ();
            tvDiem.setText ( diem + "" );
        }
        super.onActivityResult ( requestCode, resultCode, data );
    }
    // khởi tao menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate ( R.menu.menu_load, menu );
        return super.onCreateOptionsMenu ( menu );
    }
    // bắt sự kiện khi chọn menu
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId () == R.id.reload){
            Collections.shuffle ( arrayList );

            tenHinhGoc = arrayList.get ( 5 ); //  lấy tên hình để so sánh đúng sai
            // idHinh ghép tên hình lấy trong mảng
            int idHinh= getResources ().getIdentifier ( arrayList.get ( 5 ),"drawable", getPackageName () );
            imgGoc.setImageResource ( idHinh ); // chỉ cần gọi idHinh thay vì R.drawable.hinh1
        }

        return super.onOptionsItemSelected ( item );
    }
}
