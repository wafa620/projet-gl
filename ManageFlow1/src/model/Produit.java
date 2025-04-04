/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

public class Produit {
    private int id;
    private String nom;
    private String categorie;
    private int quantite;
    private double prix;
    private String description;

    
    public Produit() {
        
    }

    // Constructor
    public Produit(int id, String nom, String categorie, int quantite, double prix, String description) {
        this.id = id;
        this.nom = nom;
        this.categorie = categorie;
        this.quantite = quantite;
        this.prix = prix;
        this.description = description;
    }

  

    

   

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    
    public String getCategorie() { return categorie; }
    public void setCategorie(String categorie) { this.categorie = categorie; }
    
    public int getQuantite() { return quantite; }
    public void setQuantite(int quantite) { this.quantite = quantite; }
    
    public double getPrix() { return prix; }
    public void setPrix(double prix) { this.prix = prix; }
    
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
}
