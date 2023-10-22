import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Sommet> sommets;
    private List<Arete> aretes;

    Graph() {
        sommets = new ArrayList<>();
        aretes = new ArrayList<>();
    }

    public void ajouterSommet(Sommet sommet) {
        sommets.add(sommet);
    }

    public void ajouterArete(Arete arete) {
        aretes.add(arete);
    }

    public static void afficherSommets(List<Sommet> sommets) {
        for (Sommet sommet : sommets) {
            System.out.println("Numéro : " + sommet.numero);
            System.out.println("Nom : " + sommet.nom);
            System.out.println("Ligne : " + sommet.ligne);
            System.out.println("Terminus : " + sommet.terminus);
            System.out.println("Branchement : " + sommet.branchement);
            System.out.println();
        }
    }

    public static void afficherAretes(List<Arete> aretes){
        for (Arete arete : aretes){
            System.out.println("Sommet de départ : " + arete.sommet1.nom);
            System.out.println("Sommet d'arrivée : " + arete.sommet2.nom);
            System.out.println("Temps (en secondes) : " + arete.temps);
            System.out.println();
        }
    }

    /**
     * La méthode permet de créer une liste sommet depuis un fichier
     * @param nomFichier
     */
    public List<Sommet> lireSommetsDepuisFichier(String nomFichier) {
        List<Sommet> sommets = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichier))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] tokens = ligne.split(";");

                if (tokens.length >= 5) {
                    int numero = Integer.parseInt(tokens[0].trim());
                    String nom = tokens[1].trim();
                    String ligne2 = tokens[2].trim();
                    boolean terminus = Boolean.parseBoolean(tokens[3].trim());
                    int branchement = Integer.parseInt(tokens[4].trim());

                    Sommet sommet = new Sommet(numero, nom, ligne2, terminus, branchement);
                    sommets.add(sommet);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sommets;
    }

    /**
     * La méthode permet de créer une liste sommet depuis un fichier
     * @param nomFichier
     */
    public List<Arete> lireAretesDepuisFichier(String nomFichierSommets, String nomFichierAretes) {
        List<Arete> aretes = new ArrayList<>();
        List<Sommet> sommets = lireSommetsDepuisFichier(nomFichierSommets);

        try (BufferedReader br = new BufferedReader(new FileReader(nomFichierAretes))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] tokens = ligne.split(" ");

                if (tokens.length >= 3) {
                    int numsommet1 = Integer.parseInt(tokens[0].trim());
                    int numsommet2 = Integer.parseInt(tokens[1].trim());
                    int temps = Integer.parseInt(tokens[2].trim());

                    Sommet sommet1 = null;
                    Sommet sommet2 = null;

                    for (int i = 0; i < sommets.size(); i++) {
                        if (sommets.get(i).numero == numsommet1) {
                            sommet1 = sommets.get(i);
                        } else if (sommets.get(i).numero == numsommet2) {
                            sommet2 = sommets.get(i);
                        }
                        if (sommet1 != null && sommet2 != null) {
                            break;
                        }
                    }

                    if (sommet1 != null && sommet2 != null) {
                        Arete arete = new Arete(sommet1, sommet2, temps);
                        aretes.add(arete);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aretes;
    }
}
