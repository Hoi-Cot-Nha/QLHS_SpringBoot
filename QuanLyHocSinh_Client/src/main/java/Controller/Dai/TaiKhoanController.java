package Controller.Dai;

import Api.Đai.TaiKhoanApi;
import Model.TaiKhoan;

import javax.swing.table.DefaultTableModel;
import java.util.List;

public class TaiKhoanController {

    private final TaiKhoanApi api = new TaiKhoanApi();

    /**
     * Hiển thị danh sách tài khoản
     */
    public void loadTable(DefaultTableModel model) {

        model.setRowCount(0);

        List<TaiKhoan> list = api.getAll();

        for (TaiKhoan tk : list) {

            model.addRow(new Object[]{
                    tk.getTenDangNhap(),
                    tk.getMatKhau(),
                    tk.getQuyen(),
                    tk.getMaNguoiDung()
            });

        }

    }

    /**
     * Thêm tài khoản
     */
    public boolean them(TaiKhoan tk) {
        return api.insert(tk);
    }

    /**
     * Cập nhật tài khoản
     */
    public boolean sua(TaiKhoan tk) {
        return api.update(tk);
    }

    /**
     * Xóa tài khoản
     */
    public boolean xoa(String tenDangNhap) {
        return api.delete(tenDangNhap);
    }

    /**
     * Tìm kiếm tài khoản
     */
    public boolean timKiem(String keyword, DefaultTableModel model) {

        model.setRowCount(0);

        List<TaiKhoan> list = api.search(keyword);

        for (TaiKhoan tk : list) {
            model.addRow(new Object[]{
                    tk.getTenDangNhap(),
                    tk.getMatKhau(),
                    tk.getQuyen(),
                    tk.getMaNguoiDung()
            });
        }

        return !list.isEmpty();
    }

}