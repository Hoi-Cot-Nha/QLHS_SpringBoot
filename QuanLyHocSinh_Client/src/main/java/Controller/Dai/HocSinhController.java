package Controller.Dai;

import Api.Đai.HocSinhApi;
import Model.Auth;
import Model.HocSinh;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public class HocSinhController {

    private HocSinhApi api = new HocSinhApi();

    // Load dữ liệu lên bảng
    public void loadTable(DefaultTableModel model) {

        model.setRowCount(0);

        List<HocSinh> list;

        // Kiểm tra phân quyền
        if (Auth.isHocSinh()) {

            HocSinh hs = api.getHocSinh(Auth.maNguoiDung);

            list = new ArrayList<>();

            if (hs != null) {
                list.add(hs);
            }

        } else {

            list = api.getAllHocSinh();

        }

        if (list == null) {
            return;
        }

        for (HocSinh hs : list) {

            model.addRow(new Object[]{
                    hs.getMaHS(),
                    hs.getHoTen(),
                    formatToDDMMYYYY(hs.getNgaySinh()),
                    hs.getGioiTinh(),
                    hs.getDiaChi(),
                    hs.getMaLop(),
                    hs.getMaDT()
            });

        }

    }

    private String formatToDDMMYYYY(String yyyyMMdd) {
        if (yyyyMMdd == null || yyyyMMdd.isEmpty()) return "";
        try {
            java.util.Date d = new java.text.SimpleDateFormat("yyyy-MM-dd").parse(yyyyMMdd);
            return new java.text.SimpleDateFormat("dd/MM/yyyy").format(d);
        } catch (Exception e) {
            return yyyyMMdd;
        }
    }

    public boolean them(HocSinh hs) {
        return api.insertHocSinh(hs);
    }

    // Sửa
    public boolean sua(HocSinh hs) {
        return api.updateHocSinh(hs);
    }

    // Xóa
    public boolean xoa(String maHS) {
        return api.deleteHocSinh(maHS);
    }

    // Load mã lớp
    public void loadComboMaLop(JComboBox<String> cbo) {

        cbo.removeAllItems();

        List<String> list = api.getAllMaLop();

        if (list == null) return;

        for (String ma : list) {
            cbo.addItem(ma);
        }

    }

    // Load mã đối tượng
    public void loadComboMaDT(JComboBox<String> cbo) {

        cbo.removeAllItems();

        List<String> list = api.getAllMaDT();

        if (list == null) return;

        for (String ma : list) {
            cbo.addItem(ma);
        }

    }

    // Tìm kiếm
    public boolean timKiem(String keyword, DefaultTableModel model) {

        model.setRowCount(0);

        List<HocSinh> list = api.search(keyword);

        // Server lỗi hoặc không kết nối được
        if (list == null) {
            throw new RuntimeException("Không thể kết nối tới Server.");
        }

        // Không có kết quả
        if (list.isEmpty()) {
            return false;
        }

        // Có dữ liệu
        for (HocSinh hs : list) {
            model.addRow(new Object[]{
                hs.getMaHS(),
                hs.getHoTen(),
                formatToDDMMYYYY(hs.getNgaySinh()),
                hs.getGioiTinh(),
                hs.getDiaChi(),
                hs.getMaLop(),
                hs.getMaDT()
            });
        }

        return true;
    }

    // Thông tin cá nhân
    public HocSinh getThongTinCaNhan() {

        return api.getHocSinh(Auth.maNguoiDung);

    }

}