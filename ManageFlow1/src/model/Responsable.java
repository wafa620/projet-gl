/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author wafaj
 */
public class Responsable {

    private String nom;
    private String prenom;
    private String tel;
    private String cin;
    private String adresse;
    private String nomutilisateur;
    private String motpasse;

    public Responsable(String nom, String prenom, String tel, String cin, String adresse, String username, String password) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.cin = cin;
        this.adresse = adresse;
        this.nomutilisateur = username;
        this.motpasse = password;
    }

    // Getters and setters for each field
    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getTel() {
        return tel;
    }

    public String getCin() {
        return cin;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getUsername() {
        return nomutilisateur;
    }

    public String getPassword() {
        return motpasse;
    }
}
