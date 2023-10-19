import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Graph {

    private List<Sommet> sommets;
    private List<Arete> arêtes;

    Graph() {
        sommets = new ArrayList<>();
        arêtes = new ArrayList<>();
    }

    public void ajouterSommet(Sommet sommet) {
        sommets.add(sommet);
    }

    public void ajouterArête(Arete arete) {
        arêtes.add(arete);
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


}
