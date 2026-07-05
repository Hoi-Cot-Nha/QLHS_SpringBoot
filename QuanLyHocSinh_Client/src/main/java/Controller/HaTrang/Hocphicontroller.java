package Controller.HaTrang;

import Api.HocPhiApiClient;
import Model.Auth;
import Model.Hocphi;
import View.HaTrang.QuanLyHocPhiPanel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JOptionPane;
import java.util.List;

public class Hocphicontroller {
    private QuanLyHocPhiPanel view;
    private HocPhiApiClient dao;
    private int selectedMaHP = 0;

    public Hocphicontroller(QuanLyHocPhiPanel view) {
        this.view = view;
        this.dao = new HocPhiApiClient();

        System.out.println("DEBUG Controller: Khởi tạo controller...");
        initEvents();
        loadTatCaDuLieu();
    }

    private void initEvents() {
        boolean[] updateMode = {false};

        // Định nghĩa lại các trạng thái chuẩn
        Runnable setIdleState = () -> {
            view.setCrudButtonState(true, false, false, false, false); // Chỉ sáng nút Thêm
            view.setInputEditable(false); // Khóa các ô nhập
        };
        Runnable setAddState = () -> {
            view.setCrudButtonState(false, false, false, true, true); // Sáng nút Lưu, Hủy
            view.setInputEditable(true);  // Mở khóa các ô để nhập mới
        };
        Runnable setSelectedState = () -> {
            view.setCrudButtonState(false, true, true, false, true); // Sáng nút Sửa, Xóa, Hủy (Nút Lưu bị ẩn/khóa)
            view.setInputEditable(false); // VẪN KHÓA CÁC Ô NHẬP LIỆU (CHỈ CHO XEM)
        };
        Runnable setEditState = () -> {
            view.setCrudButtonState(false, false, false, true, true); // Sáng nút Lưu, Hủy để chuẩn bị lưu dữ liệu mới
            view.setInputEditable(true);  // MỞ KHÓA CÁC Ô ĐỂ ADMIN BẮT ĐẦU SỬA
            view.getTxtMaHS().setEditable(false); // Khóa riêng ô Mã Học Sinh vì không được sửa Mã
        };

        setIdleState.run();

        // 1. Nút Lọc dữ liệu
        view.getBtnLoc().addActionListener(e -> locDuLieu());

        // 2. Nút Thêm
        view.getBtnThem().addActionListener(e -> {
            updateMode[0] = false;
            selectedMaHP = 0;
            view.refreshForm();
            setAddState.run();
        });

        // 3. Nút Sửa
        view.getBtnSua().addActionListener(e -> {
            int selectedRow = view.getTableHocPhi().getSelectedRow();
            if (selectedRow == -1) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng cần sửa!");
                return;
            }
            updateMode[0] = true;
            setEditState.run();
        });

        // 4. Nút Lưu
        view.getBtnLuu().addActionListener(e -> {
            if (xuLyLuu(updateMode[0])) {
                updateMode[0] = false;
                setIdleState.run();
            }
        });

        // 5. Nút Xóa
        view.getBtnXoa().addActionListener(e -> {
            if (xoaHocPhi()) {
                updateMode[0] = false;
                setIdleState.run();
            }
        });

        // 6. Nút Hủy
        view.getBtnHuy().addActionListener(e -> {
            view.refreshForm();
            updateMode[0] = false;
            setIdleState.run();
        });

        // 7. Sự kiện Click vào bảng
        view.getTableHocPhi().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedRow = view.getTableHocPhi().getSelectedRow();
                if (selectedRow >= 0) {
                    selectedMaHP = Integer.parseInt(view.getTableHocPhi().getValueAt(selectedRow, 0).toString());
                    updateMode[0] = true;
                    setSelectedState.run();
                }
            }
        });

        // 8. TỰ ĐỘNG ĐIỀN MÃ LỚP KHI GÕ MÃ HỌC SINH
        view.getTxtMaHS().addFocusListener(new FocusAdapter() {
            @Override
            public void focusLost(FocusEvent e) {
                String maHS = view.getTxtMaHS().getText().trim();
                if (!maHS.isEmpty() && !updateMode[0]) {
                    String maLopTuDong = layMaLopTheoMaHS(maHS);
                    view.getTxtMaLopCT().setText(maLopTuDong);
                }
            }
        });

        // 9. BỔ SUNG: TỰ ĐỘNG TÍNH TOÁN SỐ TIỀN PHẢI ĐÓNG TRÊN UI KHI ĐANG NHẬP SỐ
        KeyAdapter tinhTienTuDong = new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                try {
                    String tongTienStr = view.getTxtTongTien().getText().trim();
                    String mienGiamStr = view.getTxtMienGiam().getText().trim();

                    long tongTien = tongTienStr.isEmpty() ? 0 : Long.parseLong(tongTienStr);
                    long mienGiam = mienGiamStr.isEmpty() ? 0 : Long.parseLong(mienGiamStr);

                    long phaiDong = tongTien - mienGiam;
                    view.getTxtPhaiDong().setText(String.valueOf(phaiDong));
                } catch (NumberFormatException ex) {
                    // Ký tự không hợp lệ, bỏ qua để tránh đỏ lòm màn hình console
                }
            }
        };
        view.getTxtTongTien().addKeyListener(tinhTienTuDong);
        view.getTxtMienGiam().addKeyListener(tinhTienTuDong);

        System.out.println("DEBUG Controller: Controller khởi tạo xong!");
    }

    private String layMaLopTheoMaHS(String maHS) {
        if (maHS.toUpperCase().startsWith("HS")) {
            return "10A1";
        }
        return "CHƯA XÁC ĐỊNH";
    }

    private void loadTatCaDuLieu() {
        try {
            List<Hocphi> listAll;
            if (Auth.isHocSinh()) {
                listAll = dao.getByMaHS(Auth.maNguoiDung);
            } else {
                listAll = dao.getAllHocPhi();
            }
            view.loadTable(listAll);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void locDuLieu() {
        try {
            Object cboHocKySelected = view.getCboHocKy().getSelectedItem();
            String maLop = view.getTxtMaLop().getText().trim().toUpperCase();
            String hocKyStr = (cboHocKySelected != null) ? cboHocKySelected.toString().trim() : "";
            String namHoc = view.getTxtNamHoc().getText().trim();

            if (maLop.isEmpty() && hocKyStr.isEmpty() && namHoc.isEmpty()) {
                loadTatCaDuLieu();
                return;
            }

            int hocKy = hocKyStr.isEmpty() ? 0 : Integer.parseInt(hocKyStr);
            List<Hocphi> list = dao.getHocPhiByLop(maLop, hocKy, namHoc);
            view.loadTable(list);

            if (list.isEmpty()) {
                JOptionPane.showMessageDialog(view, "Không tìm thấy dữ liệu!");
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Lỗi định dạng số!");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean xuLyLuu(boolean isUpdate) {
        try {
            String maHS = view.getTxtMaHS().getText().trim();
            String maLop = view.getTxtMaLopCT().getText().trim();
            String tongTienStr = view.getTxtTongTien().getText().trim();
            String mienGiamStr = view.getTxtMienGiam().getText().trim();
            Object hocKyObj = view.getCboHocKyCT().getSelectedItem();
            Object trangThaiObj = view.getCboTrangThai().getSelectedItem();

            String hocKyStr = hocKyObj == null ? "" : hocKyObj.toString().trim();
            String namHoc = view.getTxtNamHocCT().getText().trim();
            String trangThaiStr = trangThaiObj == null ? "" : trangThaiObj.toString().trim();

            if (maHS.isEmpty() || tongTienStr.isEmpty() || hocKyStr.isEmpty() || namHoc.isEmpty() || maLop.equalsIgnoreCase("CHƯA XÁC ĐỊNH")) {
                JOptionPane.showMessageDialog(view, "Vui lòng nhập đầy đủ thông tin hợp lệ (Kiểm tra lại Mã HS để nhận Mã Lớp)!");
                return false;
            }

            Hocphi hp = new Hocphi();
            if (isUpdate) {
                hp.setMaHP(selectedMaHP);
            }
            hp.setMaHS(maHS);
            hp.setMaLop(maLop);
            hp.setHocKy(Integer.parseInt(hocKyStr));
            hp.setNamHoc(namHoc);

            long tongTien = Long.parseLong(tongTienStr);
            long mienGiam = mienGiamStr.isEmpty() ? 0 : Long.parseLong(mienGiamStr);

            // CHẶN BÁO LỖI KHI MIỄN GIẢM LỚN HƠN TỔNG TIỀN (SỐ TIỀN PHẢI ĐÓNG BỊ ÂM)
            if (mienGiam > tongTien) {
                JOptionPane.showMessageDialog(view, "Số tiền miễn giảm không được lớn hơn tổng tiền học phí!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return false; // Ngăn không cho chạy tiếp xuống lệnh gọi API lưu database
            }

            long phaiDong = tongTien - mienGiam;

            hp.setTongTien(tongTien);
            hp.setMienGiam(mienGiam);
            hp.setPhaiDong(phaiDong);
            hp.setTrangThai(trangThaiStr.isEmpty() ? "Chưa đóng" : trangThaiStr);

            if (dao.saveHocPhi(hp)) {
                String thongBao = isUpdate ? "Cập nhật học phí thành công!" : "Thêm mới học phí thành công!";
                JOptionPane.showMessageDialog(view, thongBao);
                loadTatCaDuLieu();
                view.refreshForm();
                return true;
            } else {
                JOptionPane.showMessageDialog(view, "Lưu thất bại! Kiểm tra kết nối API/DB.");
                return false;
            }

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(view, "Số tiền học phí hoặc miễn giảm phải nhập định dạng số!");
            return false;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi hệ thống: " + ex.getMessage());
            return false;
        }
    }

    private boolean xoaHocPhi() {
        int selectedRow = view.getTableHocPhi().getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng trên bảng để xóa!");
            return false;
        }

        int xacNhan = JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa học phí này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (xacNhan == JOptionPane.YES_OPTION) {
            int maHP = (int) view.getTableHocPhi().getValueAt(selectedRow, 0);
            if (dao.deleteHocPhi(maHP)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadTatCaDuLieu();
                view.refreshForm();
                return true;
            } else {
                JOptionPane.showMessageDialog(view, "Xóa thất bại!");
                return false;
            }
        }
        return false;
    }
}