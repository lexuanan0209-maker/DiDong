package com.app.quanlyquy.giao_dien;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.app.quanlyquy.R;
import com.app.quanlyquy.dactinh.TaiKhoan;
import com.app.quanlyquy.dulieu.QuanLyDuLieu;
import com.app.quanlyquy.giao_dien.bophukiens.BoThichNghiTaiKhoan;
import java.util.List;

public class ManHinhQuanTri extends AppCompatActivity {
    private RecyclerView rvTaiKhoan;
    private QuanLyDuLieu quanLyDuLieu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.man_hinh_quan_tri);

        quanLyDuLieu = new QuanLyDuLieu(this);
        rvTaiKhoan = findViewById(R.id.rvDanhSachTaiKhoanAdmin);
        rvTaiKhoan.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onResume() {
        super.onResume();
        capNhatDanhSach();
    }

    private void capNhatDanhSach() {
        List<TaiKhoan> ds = quanLyDuLieu.layDanhSachTaiKhoan();
        ds.removeIf(tk -> "ADMIN".equals(tk.getVaiTro()));

        BoThichNghiTaiKhoan adapter = new BoThichNghiTaiKhoan(ds, new BoThichNghiTaiKhoan.OnTaiKhoanListener() {
            @Override
            public void onChonTaiKhoan(TaiKhoan tk) {
                Intent intent = new Intent(ManHinhQuanTri.this, ManHinhChiTietAdmin.class);
                intent.putExtra("TEN_TAI_KHOAN", tk.getTenDangNhap());
                startActivity(intent);
            }

            @Override
            public void onKhoaTaiKhoan(TaiKhoan tk) {
                tk.setDaKhoa(!tk.isDaKhoa());
                quanLyDuLieu.luuDanhSachTaiKhoan(quanLyDuLieu.layDanhSachTaiKhoan());
                capNhatDanhSach();
            }
        });
        rvTaiKhoan.setAdapter(adapter);
    }
}