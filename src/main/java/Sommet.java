import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Sommet {

    int numero;
    String nom;
    String ligne;
    boolean terminus;
    int branchement;

    public Sommet(int numero, String nom, String ligne, boolean terminus, int branchement) {
        this.numero = numero;
        this.nom = nom;
        this.ligne = ligne;
        this.terminus = terminus;
        this.branchement = branchement;
    }




    @Override
    public String toString() {
        return numero + " " + nom;
    }



    }


