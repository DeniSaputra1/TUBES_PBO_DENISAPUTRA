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
import com.example.DataBuku;

public class SecondaryController {

    private SistemPeminjamanBuku mainApp;
    private DataBuku[] dataBuku; // Array untuk menyimpan objek Buku
    private int bookCounter = 0; // Penghitung untuk melacak buku yang telah dimasukkan

    @FXML
    private TextField tfnama;

    @FXML
    private TextField tfid;

    public void setMainApp(SistemPeminjamanBuku mainApp) {
        this.mainApp = mainApp;
    }

    // Inisialisasi array dengan jumlah buku yang dibutuhkan
    public void setNumberOfBooks(int numberOfBooks) {
        dataBuku = new DataBuku[numberOfBooks];
    }

    @FXML
    private void btnPinjamBukuClicked() { addBooksDetails("Pinjam");}

    @FXML
    private void btnBeliBukuClicked() {
        addBooksDetails("Beli");
    }

    // Metode utilitas untuk menambahkan buku ke dalam array
    private void addBooksDetails(String jenisTransaksi) {
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

        dataBuku[bookCounter] = new DataBuku(jenisTransaksi, nama, id, formattedDateTime); // Menyimpan detail
                                                                                               // buku dalam array
        bookCounter++;

        clearFields();

        if (bookCounter == dataBuku.length) {
            displayBookDetailsAndSaveToFile(); // Jika semua detail buku telah dimasukkan, tampilkan detail
                                                   // tersebut
        }
    }

    // Metode utilitas untuk mengosongkan kolom masukan setelah menambahkan detail
    // buku
    private void clearFields() {
        tfnama.clear();
        tfid.clear();
    }

    // Metode utilitas untuk menampilkan detail buku yang dimasukkan dan
    // menyimpannya ke dalam text file
    private void displayBookDetailsAndSaveToFile() {
        if (dataBuku == null || dataBuku.length == 0) {
            showAlert("Error", "Data buku tidak terinisialisasi dengan benar.");
            return;
        }

        StringBuilder details = new StringBuilder();
        for (int i = 0; i < dataBuku.length; i++) {
            DataBuku currentBook = dataBuku[i];
            details.append("==================================== \n")
                    .append("Nama Buku: ").append(currentBook.getNama()).append("\n")
                    .append("Nama Pelanggan: ").append(currentBook.getId()).append("\n")
                    .append("Jenis Transaksi: ").append(currentBook.getJenisTransaksi()).append("\n")
                    .append("Tanggal dan Jam Transaksi: ").append(currentBook.getformattedDateTime()).append("\n");

            // Tambahkan garis pemisah pada entri terakhir
            if (i == dataBuku.length - 1) {
                details.append("====================================");
            }
        }

        saveDataToFile(details.toString()); // Simpan detail buku ke dalam text file

        try {
            mainApp.loadOutputScene(details.toString(), bookCounter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Metode untuk menyimpan detail buku ke dalam text file
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