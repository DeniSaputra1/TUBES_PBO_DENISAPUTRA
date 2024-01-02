package com.example;

public class DataBuku {
    private String jenisTransaksi;
    private String nama;
    private String id;
    private String formattedDateTime;

    public DataBuku(String jenisTransaksi, String nama, String id, String formattedDateTime) {
        this.jenisTransaksi = jenisTransaksi;
        this.nama = nama;
        this.id = id;
        this.formattedDateTime = formattedDateTime;
    }

    // Getter untuk detail buku
    public String getJenisTransaksi() {
        return jenisTransaksi;
    }

    public String getNama() {
        return nama;
    }

    public String getId() {
        return id;
    }

    public String getformattedDateTime() {
        return formattedDateTime;
    }
}