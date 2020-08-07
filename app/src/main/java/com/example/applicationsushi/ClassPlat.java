package com.example.applicationsushi;

public class ClassPlat {

    private int idPlat;
    private int idCategorie;
    private String description;
    private String nom;
    private Double prix;
    private Double note;

    public ClassPlat(int idPlat, int idCategorie, String description, String nom, Double prix, Double note) {
        this.idPlat = idPlat;
        this.idCategorie = idCategorie;
        this.description = description;
        this.nom = nom;
        this.prix = prix;
        this.note = note;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getIdPlat() {
        return idPlat;
    }

    public void setIdPlat(int idPlat) {
        this.idPlat = idPlat;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Double getPrix() {
        return prix;
    }

    public void setPrix(Double prix) {
        this.prix = prix;
    }

    public Double getNote() {
        return note;
    }

    public void setNote(Double note) {
        this.note = note;
    }
}
