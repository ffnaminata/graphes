import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Graph {

    private List<Sommet> sommets;
    private List<Arete> aretes;

    public List<Sommet> getSommets() {
        return sommets;
    }

    public List<Arete> getAretes(){
        return aretes;
    }
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



    Graph(String nomFichierSommets, String nomFichierAretes ){
        sommets = lireSommetsDepuisFichier(nomFichierSommets);
        aretes = lireAretesDepuisFichier(nomFichierSommets, nomFichierAretes);
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
     * @param
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

                    for (Sommet sommet : sommets) {
                        if (sommet.numero == numsommet1) {
                            sommet1 = sommet;
                        } else if (sommet.numero == numsommet2) {
                            sommet2 = sommet;
                        }
                    }

                    if (sommet1 != null && sommet2 != null) {
                        Arete arete = new Arete(sommet1, sommet2, temps);
                        //aretes.add(arete);
                        //ajouterArete(sommet1,sommet2,temps);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return aretes;
    }



    public boolean estConnexe() {
        // Sélectionnez un sommet arbitraire (par exemple, le premier sommet)
        Sommet sommetDeDepart = sommets.get(0);

        // Utilisez BFS pour parcourir le graphe à partir du sommet de départ
        Set<Sommet> sommetsVisites = new HashSet<>();
        Queue<Sommet> fileAttente = new LinkedList<>();

        fileAttente.add(sommetDeDepart);
        sommetsVisites.add(sommetDeDepart);

        while (!fileAttente.isEmpty()) {
            Sommet sommetCourant = fileAttente.poll();

            // Pour chaque sommet adjacent non visité, ajoutez-le à la file d'attente
            for (Sommet voisin : obtenirVoisinsNonVisites(sommetCourant, sommetsVisites)) {
                fileAttente.add(voisin);
                sommetsVisites.add(voisin);
            }
        }

        // Si tous les sommets ont été visités, le graphe est connexe
        return sommetsVisites.size() == sommets.size();
    }

    private List<Sommet> obtenirVoisinsNonVisites(Sommet sommet, Set<Sommet> sommetsVisites) {
        List<Sommet> voisins = new ArrayList<>();

        // Remplacez ceci par la logique pour obtenir les voisins du sommet depuis vos arêtes
        for (Arete arete : aretes) {
            if (arete.sommet1.equals(sommet) && !sommetsVisites.contains(arete.sommet2)) {
                voisins.add(arete.sommet2);
                System.out.println(aretes.get(1).toString());
            } else if (arete.sommet2.equals(sommet) && !sommetsVisites.contains(arete.sommet1)) {
                voisins.add(arete.sommet1);
                System.out.println(aretes.get(1).toString());
            }
        }

        return voisins;


    }

    Sommet recherche(int id){
        for (Sommet sommet: getSommets()){
            if (sommet.numero==id){
                return sommet;
            }
        }
        return null;
    }
    public void bellmanFord(Sommet source, Sommet cible) {
        // Assurez-vous que les sommets source et cible existent dans la liste des sommets
        if (!sommets.contains(source) || !sommets.contains(cible)) {
            System.out.println("Le sommet source ou le sommet cible n'existe pas.");
            return;
        }

        // Initialisation des distances
        Map<Sommet, Integer> distances = new HashMap<>();
        Map<Sommet, Sommet> predecesseurs = new HashMap<>();

        for (Sommet sommet : sommets) {
            distances.put(sommet, Integer.MAX_VALUE);
            predecesseurs.put(sommet, null);
        }
        distances.put(source, 0);

        // Relaxation des arêtes
        for (int i = 1; i < sommets.size(); i++) {
            for (Arete arete : aretes) {
                Sommet sommet1 = arete.sommet1;
                Sommet sommet2 = arete.sommet2;
                int poids = arete.temps;

                if (distances.get(sommet1) != null && distances.get(sommet1) + poids < distances.get(sommet2)) {
                    distances.put(sommet2, distances.get(sommet1) + poids);
                    predecesseurs.put(sommet2, sommet1);
                }
            }
        }

        // Vérification des cycles négatifs
        for (Arete arete : aretes) {
            Sommet sommet1 = arete.sommet1;
            Sommet sommet2 = arete.sommet2;
            int poids = arete.temps;

            if (distances.get(sommet1) != null && distances.get(sommet1) + poids < distances.get(sommet2)) {
                System.out.println("Le graphe contient un cycle de poids négatif.");
                return;
            }
        }

        // Affichage du plus court chemin
        System.out.println("Chemin le plus court depuis le sommet source " + source.nom + " vers " + cible.nom + ":");
        Integer distanceCible = distances.get(cible);
        if (distanceCible == null || distanceCible == Integer.MAX_VALUE) {
            System.out.println("Aucun chemin trouvé.");
        } else {
            List<Sommet> chemin = construireChemin(source, cible, predecesseurs);
            System.out.println("Distance = " + distanceCible);
            System.out.println("Chemin = " );
            for (Sommet sommet : chemin){
                System.out.println(sommet.nom + " Ligne "+ sommet.ligne + "->");
            }
        }
    }


    public void trouverPlusCourtsChemins(Sommet source, Sommet cible) {
        // Assurez-vous que les sommets source et cible existent dans la liste des sommets
        if (!sommets.contains(source) || !sommets.contains(cible)) {
            System.out.println("Le sommet source ou le sommet cible n'existe pas.");
            return;
        }

        // Initialisation des distances
        Map<Sommet, Integer> distances = new HashMap<>();
        Map<Sommet, Sommet> predecesseurs = new HashMap<>();

        for (Sommet sommet : sommets) {
            distances.put(sommet, Integer.MAX_VALUE);
            predecesseurs.put(sommet, null);
        }
        distances.put(source, 0);

        // Relaxation des arêtes
        for (int i = 1; i < sommets.size(); i++) {
            for (Arete arete : aretes) {
                Sommet sommet1 = arete.sommet1;
                Sommet sommet2 = arete.sommet2;
                int poids = arete.temps;

                if (distances.get(sommet1) != null && distances.get(sommet1) + poids < distances.get(sommet2)) {
                    distances.put(sommet2, distances.get(sommet1) + poids);
                    predecesseurs.put(sommet2, sommet1);
                }
            }
        }

        // Vérification des cycles négatifs
        for (Arete arete : aretes) {
            Sommet sommet1 = arete.sommet1;
            Sommet sommet2 = arete.sommet2;
            int poids = arete.temps;

            if (distances.get(sommet1) != null && distances.get(sommet1) + poids < distances.get(sommet2)) {
                System.out.println("Le graphe contient un cycle de poids négatif.");
                return;
            }
        }

        // Affichage du plus court chemin
        System.out.println("Chemin le plus court depuis le sommet source " + source.nom + " vers " + cible.nom + ":");
        Integer distanceCible = distances.get(cible);
        if (distanceCible == null || distanceCible == Integer.MAX_VALUE) {
            System.out.println("Aucun chemin trouvé.");
        } else {
            List<Sommet> chemin = construireChemin(source, cible, predecesseurs);
            System.out.println("Distance = " + distanceCible);
            System.out.println("Chemin = " + chemin);
        }
    }


    private List<Sommet> construireChemin(Sommet source, Sommet cible, Map<Sommet, Sommet> predecesseurs) {
        List<Sommet> chemin = new ArrayList<>();
        Sommet sommetCourant = cible;

        while (sommetCourant != null) {
            chemin.add(sommetCourant);
            sommetCourant = predecesseurs.get(sommetCourant);
        }

        Collections.reverse(chemin);
        return chemin;
    }

    public Graph rendreGrapheConnexe() {
        Graph grapheConnexe = new Graph();

        // Copiez tous les sommets du graphe d'origine dans le graphe connexe
        for (Sommet sommet : sommets) {
            grapheConnexe.ajouterSommet(sommet);
        }

        // Utilisez une approche simple pour ajouter des arêtes manquantes
        // Vous pouvez personnaliser cette logique selon vos besoins
        for (int i = 0; i < sommets.size(); i++) {
            for (int j = i + 1; j < sommets.size(); j++) {
                Sommet sommet1 = sommets.get(i);
                Sommet sommet2 = sommets.get(j);
                boolean arreteExistante = false;

                for (Arete arete : aretes) {
                    if ((arete.sommet1.equals(sommet1) && arete.sommet2.equals(sommet2)) ||
                            (arete.sommet1.equals(sommet2) && arete.sommet2.equals(sommet1))) {
                        arreteExistante = true;
                        break;
                    }
                }

                if (!arreteExistante) {
                    // Si l'arête n'existe pas, ajoutez-la au graphe connexe
                    grapheConnexe.ajouterArete(new Arete(sommet1, sommet2, 1));
                    //grapheConnexe.ajouterArete(new Arete( sommet2,sommet1, 1));
                }
            }
        }

        return grapheConnexe;
    }


    public Graph ajouterAretesInverses() {
        List<Arete> arretesInverses = new ArrayList<>();

        // Itérer sur toutes les arêtes existantes
        for (Arete arete : aretes) {
            // Créer l'arête inverse en échangeant les sommets et en gardant le même poids (dans cet exemple, on suppose que le poids est un entier)
            Arete areteInverse = new Arete(arete.getSommet2(), arete.getSommet1(), arete.temps);

            // Ajouter l'arête inverse à la liste des arêtes inverses
            arretesInverses.add(areteInverse);
        }

        // Ajouter toutes les arêtes inverses au graphe
        aretes.addAll(arretesInverses);
        return this;
    }




}

