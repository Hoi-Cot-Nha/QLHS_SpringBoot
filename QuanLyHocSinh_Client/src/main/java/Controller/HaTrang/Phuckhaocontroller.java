package Controller.HaTrang;

import Api.PhucKhaoApiClient;
import Model.Auth;
import Model.Phuckhao;
import View.HaTrang.QuanLyPhucKhaoPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.swing.JOptionPane;

public class Phuckhaocontroller {
    private final QuanLyPhucKhaoPanel view;
    private final PhucKhaoApiClient dao;
    private List<Phuckhao> listCurrent;
    private boolean isSystemRefreshing = false;

    public Phuckhaocontroller(QuanLyPhucKhaoPanel view) {
        this.view = view;
        this.dao = new PhucKhaoApiClient();
        initEvents();
        safeRefreshForm();
        loadData();
    }

    private void safeRefreshForm() {
        isSystemRefreshing = true;
        try {
            view.getTable().clearSelection();
            view.refresh();
            if (Auth.isHocSinh() && Auth.maNguoiDung != null) {
                view.getTxtMaHS().setText(Auth.maNguoiDung.trim().toUpperCase());
            }
        } finally {
            isSystemRefreshing = false;
        }
    }

    private void initEvents() {
        boolean[] editMode = {false};

        Runnable setIdleState = () -> {
            view.setCrudButtonState(Auth.isHocSinh(), false, false, false, false);
            view.setInputEditable(false);
        };
        Runnable setAddState = () -> {
            view.setCrudButtonState(false, false, false, true, true);
            view.setInputEditable(true);
        };
        Runnable setSelectedState = () -> {
            view.setCrudButtonState(false, true, Auth.isHocSinh(), false, true);
            view.setInputEditable(false);
        };

        setIdleState.run();

        // Table Click
        view.getTable().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = view.getTable().getSelectedRow();
                if (!isSystemRefreshing && row >= 0 && view.getTable().getRowCount() > 0) {
                    editMode[0] = true;
                    view.fillForm(row);
                    setSelectedState.run();
                }
            }
        });

        // Các nút bấm chức năng (Dùng Lambda giúp code siêu ngắn)
        view.getBtnThem().addActionListener(e -> {
            editMode[0] = false;
            safeRefreshForm();
            setAddState.run();
        });

        view.getBtnSua().addActionListener(e -> {
            if (view.getTable().getSelectedRow() != -1) {
                editMode[0] = true;
                view.setCrudButtonState(false, false, false, true, true);
                view.setInputEditable(true);
            } else {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần sửa trên danh sách trước!");
            }
        });

        view.getBtnLuu().addActionListener(e -> {
            if (xuLyLuuPhucKhao(editMode[0])) {
                editMode[0] = false;
                setIdleState.run();
            }
        });

        view.getBtnXoa().addActionListener(e -> {
            int row = view.getTable().getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần xóa!");
                return;
            }
            if (JOptionPane.showConfirmDialog(view, "Bạn có chắc chắn muốn xóa?", "Xác nhận", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                int id = listCurrent.get(view.getTable().convertRowIndexToModel(row)).getMaPK();
                if (dao.delete(id)) {
                    JOptionPane.showMessageDialog(view, "Xóa đơn thành công!");
                    loadData();
                    safeRefreshForm();
                    editMode[0] = false;
                    setIdleState.run();
                } else {
                    JOptionPane.showMessageDialog(view, "Xóa đơn thất bại!");
                }
            }
        });

        view.getBtnLoc().addActionListener(e -> {
            String tuKhoa = view.getLocKeyword().trim().toLowerCase();
            if (tuKhoa.isEmpty()) { loadData(); return; }

            List<Phuckhao> gocList = Auth.isHocSinh() ? dao.getByMaHS(Auth.maNguoiDung != null ? Auth.maNguoiDung.toUpperCase() : "") : dao.getAll();
            listCurrent = (gocList != null) ? gocList.stream().filter(pk ->
                                                                      (pk.getMaMH() != null && pk.getMaMH().toLowerCase().contains(tuKhoa)) ||
                                                                      (pk.getTrangThai() != null && pk.getTrangThai().toLowerCase().contains(tuKhoa)) ||
                                                                      (pk.getLyDo() != null && pk.getLyDo().toLowerCase().contains(tuKhoa)) ||
                                                                      (pk.getMaHS() != null && pk.getMaHS().toLowerCase().contains(tuKhoa))
            ).collect(Collectors.toList()) : new ArrayList<>();

            view.loadTable(listCurrent);
            if (listCurrent.isEmpty()) JOptionPane.showMessageDialog(view, "Không tìm thấy kết quả!");
        });

        view.getBtnHuy().addActionListener(e -> {
            safeRefreshForm();
            loadData();
            editMode[0] = false;
            setIdleState.run();
        });
    }

    private void loadData() {
        try {
            String maHSChuan = (Auth.maNguoiDung != null) ? Auth.maNguoiDung.toUpperCase() : "";
            listCurrent = Auth.isHocSinh() ? dao.getByMaHS(maHSChuan) : dao.getAll();
            view.loadTable(listCurrent);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private boolean validateForm() {
        if (isSystemRefreshing) return false;
        if (view.getMaHS().trim().isEmpty() || view.getMaMH().trim().isEmpty()) {
            JOptionPane.showMessageDialog(view, "Mã học sinh và Mã môn học không được để trống!");
            return false;
        }
        return true;
    }

    private boolean xuLyLuuPhucKhao(boolean isUpdate) {
        if (!validateForm()) return false;
        try {
            Phuckhao pk = new Phuckhao();
            pk.setMaHS(view.getMaHS().trim().toUpperCase());
            pk.setMaMH(view.getMaMH().trim().toUpperCase());
            pk.setTrangThai(view.getTrangThai().trim());
            pk.setLyDo(view.getLyDo().trim());
            pk.setNgayGui(new Date());

            if (isUpdate) {
                int row = view.getTable().getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần sửa!");
                    return false;
                }
                pk.setMaPK(listCurrent.get(view.getTable().convertRowIndexToModel(row)).getMaPK());
                if (!dao.update(pk)) {
                    JOptionPane.showMessageDialog(view, "Cập nhật thất bại!");
                    return false;
                }
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            } else {
                if (!dao.insert(pk)) {
                    JOptionPane.showMessageDialog(view, "Thêm thất bại! Kiểm tra lại Mã Môn Học.");
                    return false;
                }
                JOptionPane.showMessageDialog(view, "Gửi yêu cầu phúc khảo thành công!");
            }
            loadData();
            safeRefreshForm();
            return true;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Lỗi: " + ex.getMessage());
            return false;
        }
    }
}