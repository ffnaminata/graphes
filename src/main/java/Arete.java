import java.util.*;

public class Arete {
    Sommet sommet1;
    Sommet sommet2;
    int temps;

    Arete(Sommet sommet1, Sommet sommet2, int temps) {
        this.sommet1 = sommet1;
        this.sommet2 = sommet2;
        this.temps = temps;
    }

    public Sommet getSommet1() {
        return sommet1;
    }

    public Sommet getSommet2() {
        return sommet2;
    }

    @Override
    public String toString() {
        return "Arete de " + sommet1.nom + " à " + sommet2.nom + " avec un temps de " + temps + " secondes";
    }



}


