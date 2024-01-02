package com.example;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class SecondaryController {

    private SistemPeminjamanBuku mainApp;
    private Employee[] employees; // Array untuk menyimpan objek Employee
    private int employeeCounter = 0; // Penghitung untuk melacak employee yang telah dimasukkan

    @FXML
    private TextField tfnama;

    @FXML
    private TextField tfid;

    public void setMainApp(SistemPeminjamanBuku mainApp) {
        this.mainApp = mainApp;
    }

    // Inisialisasi array dengan jumlah employee yang dibutuhkan
    public void setNumberOfEmployees(int numberOfEmployees) {
        employees = new Employee[numberOfEmployees];
    }

    @FXML
    private void btnPinjamBukuClicked() {
        addEmployeeDetails("Pinjam");
    }

    @FXML
    private void btnBeliBukuClicked() {
        addEmployeeDetails("Beli");
    }

    // Metode utilitas untuk menambahkan detail employee ke dalam array
    private void addEmployeeDetails(String jenisKaryawan) {
        // Mendapatkan tanggal dan waktu saat ini
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String formattedDateTime = currentDateTime.format(formatter);
        String nama = tfnama.getText();
        String id = tfid.getText();

        if (nama.isEmpty() || id.isEmpty()) {
            showAlert("Error", "Harap isi semua kolom.");
            return;
        }

        employees[employeeCounter] = new Employee(jenisKaryawan, nama, id, formattedDateTime); // Menyimpan detail
                                                                                               // employee dalam array
        employeeCounter++;

        clearFields();

        if (employeeCounter == employees.length) {
            displayEmployeeDetailsAndSaveToFile(); // Jika semua detail employee telah dimasukkan, tampilkan detail
                                                   // tersebut
        }
    }

    // Metode utilitas untuk mengosongkan kolom masukan setelah menambahkan detail
    // employee
    private void clearFields() {
        tfnama.clear();
        tfid.clear();
    }

    // Metode utilitas untuk menampilkan detail employee yang dimasukkan dan
    // menyimpannya ke dalam text file
    private void displayEmployeeDetailsAndSaveToFile() {
        StringBuilder details = new StringBuilder();
        for (int i = 0; i < employees.length; i++) {
            Employee employee = employees[i];
            details.append("==================================== \n")
                    .append("Nama Buku: ").append(employee.getNama()).append("\n")
                    .append("Nama Pelanggan: ").append(employee.getId()).append("\n")
                    .append("Jenis Transaksi: ").append(employee.getJenisKaryawan()).append("\n")
                    .append("Tanggal dan Jam Transaksi: ").append(employee.getformattedDateTime()).append("\n");
    
            // Tambahkan garis pemisah pada entri terakhir
            if (i == employees.length - 1) {
                details.append("====================================");
            } 
        }

        saveDataToFile(details.toString()); // Simpan detail employee ke dalam text file

        try {
            mainApp.loadOutputScene(details.toString(), employeeCounter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metode untuk menyimpan detail employee ke dalam text file
    private void saveDataToFile(String data) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("data_transaksi.txt", true))) {
            writer.write(data);
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Metode utilitas untuk menampilkan peringatan
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        // Terapkan stylesheet ke alert
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.getStylesheets().add(getClass().getResource("Styling.css").toExternalForm());

        alert.show();
    }
}