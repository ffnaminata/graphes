import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Start {
    public static void main(String[] args) {

        Graph graph = new Graph("metroV.txt", "metroE.txt");

        graph.ajouteAreteT("metroE.txt");

        System.out.println(graph.estConnexe());
        graph.grapheOriente().bellmanFord(graph.rechercheSommet(53),graph.rechercheSommet(1));
        graph.grapheOriente().prim();

    }

}
