package com.example.applicationsushi;

public class ClassCategorie {

    private int idCategorie;
    private int image;
    private String description;
    private String nom;

    public ClassCategorie(int image, int idCategorie, String description, String nom) {
        this.image = image;
        this.idCategorie = idCategorie;
        this.description = description;
        this.nom = nom;
    }

    public int getIdCategorie() {
        return idCategorie;
    }

    public void setIdCategorie(int idCategorie) {
        this.idCategorie = idCategorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
