import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args) {

        //Graph graph = new Graph("metroV.txt", "metroE.txt");

        /*
        //graph.afficherAretes(graph.lireAretesDepuisFichier("metroV.txt","metroE.txt"));
        Sommet sommet1 = new Sommet(1, "S1", "ligne", false, 0);
        graph.ajouterSommet(sommet1);

        Sommet sommet2 = new Sommet(2, "S2", "ligne", false, 0);
        graph.ajouterSommet(sommet2);

        Sommet sommet3 = new Sommet(3, "S3", "ligne", false, 0);
        graph.ajouterSommet(sommet3);

        Sommet sommet4 = new Sommet(4, "S4", "ligne", false, 0);
        graph.ajouterSommet(sommet4);

        Sommet sommet5 = new Sommet(5, "S5", "ligne", false, 0);
        graph.ajouterSommet(sommet5);

        Sommet sommet6 = new Sommet(6, "S6", "ligne", false, 0);
        graph.ajouterSommet(sommet6);


        Arete arete = new Arete(sommet1,sommet2,4);
        Arete arete1 = new Arete(sommet1,sommet5,7);
        Arete arete3 = new Arete(sommet5,sommet6,3);
        Arete arete4 = new Arete(sommet2,sommet3,3);
        Arete arete5 = new Arete(sommet5,sommet2,-4);
        //Arete arete6 = new Arete(sommet6, sommet5, 0);
        Arete arete7 = new Arete(sommet2,sommet6,5);
        Arete arete8 = new Arete(sommet5, sommet3, -1);
        Arete arete9 = new Arete(sommet3,sommet5,2);


        Arete arete11 = new Arete(sommet3,sommet6,6);
        Arete arete12 = new Arete(sommet6, sommet3, -2);
        Arete arete13 = new Arete(sommet3,sommet4,3);
        Arete arete14 = new Arete(sommet6, sommet4, 2);



        graph.ajouterArete(arete);
        graph.ajouterArete(arete1);
        graph.ajouterArete(arete3);
        graph.ajouterArete(arete4);
        graph.ajouterArete(arete5);
        graph.ajouterArete(arete7);

        graph.ajouterArete(arete7);
        graph.ajouterArete(arete8);
        graph.ajouterArete(arete9);
        graph.ajouterArete(arete11);
        graph.ajouterArete(arete12);
        graph.ajouterArete(arete13);
        graph.ajouterArete(arete14);
        System.out.println(graph.estConnexe());
        graph.trouverPlusCourtsChemins(sommet1,sommet6);
        graph.bellmanFord(sommet1,sommet6);


        System.out.println(graph.rendreGrapheConnexe().estConnexe());
        */





        Graph graph = new Graph("metroV.txt", "metroE.txt"); // Assurez-vous d'avoir les fichiers sommets.txt et aretes.txt



        //Sélectionnez un sommet source et un sommet cible
            /*
            graph.rendreGrapheConnexe();
            Sommet source = graph.getSommets().get(1);
            Sommet cible = graph.getSommets().get(20);
            System.out.println();
            System.out.println(graph.rendreGrapheConnexe().estConnexe());

            //graph.trouverPlusCourtsChemins(source,cible);
            //Arete ar = new Arete(graph.getSommets().get(0),graph.getSommets().get(1),5);

            System.out.println(graph.getAretes().toString());
            //graph.ajouterArete(ar);




            graph.rendreGrapheConnexe().trouverPlusCourtsChemins(source, cible );
            */
        //System.out.println(graph.getAretes().toString());
        //System.out.println(graph.rendreGrapheConnexe().getAretes().size());

        //graph.bellmanFord(graph.recherche(7),graph.recherche(8) );


        // Obtenez la liste des arêtes du graphe
        List<Arete> aretes = graph.getAretes();

// Obtenez les deux sommets dont vous souhaitez vérifier la connexion
        //Sommet sommet1 = graph.recherche(13)/* Obtenez le premier sommet */;
        //Sommet sommet2 = graph.recherche(14);/* Obtenez le deuxième sommet */;

// Vérifiez s'il existe une arête reliant les deux sommets
        try (BufferedReader br = new BufferedReader(new FileReader("metroE.txt"))) {
            String ligne;
            while ((ligne = br.readLine()) != null) {
                String[] tokens = ligne.split(" ");

                if (tokens.length >= 3) {
                    int numsommet1 = Integer.parseInt(tokens[0]);
                    int numsommet2 = Integer.parseInt(tokens[1]);
                    int temps = Integer.parseInt(tokens[2]);

                    Sommet sommet11 = null;
                    Sommet sommet22 = null;

                    for (Sommet sommet : graph.getSommets()) {
                        if (sommet.numero == numsommet1) {
                            sommet11 = sommet;
                        } else if (sommet.numero == numsommet2) {
                            sommet22 = sommet;
                        }
                    }

                    if (sommet11 != null && sommet22 != null) {
                        graph.ajouterArete(new Arete(sommet11, sommet22, temps));
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*
        List <Arete> aretesAlle = graph.getAretes();
        List <Arete> all = null;
        for (Arete arete : aretesAlle) {
            Arete retour = new Arete(arete.getSommet2(), arete.getSommet1(), 1);

            all.add(retour);


        }graph.getAretes().addAll(all);
        */


        // Vous pouvez maintenant effectuer des opérations sur votre graphe
        // ...

        // Par exemple, trouver le plus court chemin entre deux sommets
        Sommet source = graph.recherche(113);/* Votre sommet source */;
        Sommet cible =graph.recherche(360); /* Votre sommet cible */;
        System.out.println(graph.getAretes().size());
        graph.ajouterAretesInverses();
        System.out.println(graph.ajouterAretesInverses().getAretes().size());
        graph.trouverPlusCourtsChemins(source, cible);

        graph.bellmanFord(source, cible);


    }

}
