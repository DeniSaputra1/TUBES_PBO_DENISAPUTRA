package com.example;

public class Employee {
    private String jenisKaryawan;
    private String nama;
    private String id;
    private String formattedDateTime;

    public Employee(String jenisKaryawan, String nama, String id, String formattedDateTime) {
        this.jenisKaryawan = jenisKaryawan;
        this.nama = nama;
        this.id = id;
        this.formattedDateTime = formattedDateTime;
    }

    // Getter untuk detail employee
    public String getJenisKaryawan() {
        return jenisKaryawan;
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