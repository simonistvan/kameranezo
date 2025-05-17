package com.example.kameranezo.model;

import com.google.firebase.firestore.PropertyName;

public class Camera {
    private String nev;
    private String cim;

    @PropertyName("link")
    private String kepUrl;

    public Camera() {} // Kell az üres konstruktor Firestore-hoz

    public String getNev() {
        return nev;
    }

    public String getCim() {
        return cim;
    }

    @PropertyName("link")
    public String getKepUrl() {
        return kepUrl;
    }
}