package metier;

import java.util.*;

public class PyRat {
    HashSet<Point> hf = new HashSet<Point>();
    HashMap<Point, Set<Point>> hp = new HashMap<Point, Set<Point>>();
    /* Méthode appelée une seule fois permettant d'effectuer des traitements "lourds" afin d'augmenter la performace de la méthode turn. */
    public void preprocessing(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        // remplir la hashSet
        for (Point p : fromages)
        {
            hf.add(p);
        }
        // remplire la hashmap
        for (Point k : laby.keySet())
        {
            Set<Point> liste = new HashSet<Point>();
            for (Point p : laby.get(k))
            {
                liste.add(p);;
            };
            hp.put(k,liste);
        }
    }

    /* Méthode de test appelant les différentes fonctionnalités à développer.
        @param laby - Map<Point, List<Point>> contenant tout le labyrinthe, c'est-à-dire la liste des Points, et les Points en relation (passages existants)
        @param labyWidth, labyHeight - largeur et hauteur du labyrinthe
        @param position - Point contenant la position actuelle du joueur
        @param fromages - List<Point> contenant la liste de tous les Points contenant un fromage. */
    public void turn(Map<Point, List<Point>> laby, int labyWidth, int labyHeight, Point position, List<Point> fromages) {
        Point pt1 = new Point(2,1);
        Point pt2 = new Point(3,1);
        Point pt3 = new Point(4,1);
        System.out.println((fromageIci(pt1, fromages) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position, normal" + pt1);
        System.out.println((fromageIci(pt2, fromages) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position, normal " + pt2);
        System.out.println();
        System.out.println((fromageIci_EnOrdreConstant(pt1) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position, ordre constant " + pt1);
        System.out.println((fromageIci_EnOrdreConstant(pt2) ? "Il y a un" : "Il n'y a pas de") + " fromage ici, en position, ordre constant " + pt2);
        System.out.println();
        System.out.println((passagePossible(pt1, pt3, laby) ? "Il y a un" : "Il n'y a pas de") + " passage normal de " + pt1 + " vers " + pt3);
        System.out.println((passagePossible(pt1, pt2, laby) ? "Il y a un" : "Il n'y a pas de") + " passage normal de " + pt1 + " vers " + pt2);
        System.out.println();
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt3) ? "Il y a un" : "Il n'y a pas de") + " passage constant de " + pt1 + " vers " + pt3);
        System.out.println((passagePossible_EnOrdreConstant(pt1, pt2) ? "Il y a un" : "Il n'y a pas de") + " passage constant de " + pt1 + " vers " + pt2);
        System.out.println();
        System.out.println("Liste des points inatteignables depuis la position " + position + " : " + pointsInatteignables(position, laby));

        System.out.println();

        System.out.println();
        System.out.println();
        System.out.println(laby);
        System.out.println();
        System.out.println(fromages);
    }

    /* Regarde dans la liste des fromages s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci(Point pos, List<Point> laby) {
        return laby.contains(pos);
    }

    /* Regarde de manière performante (accès en ordre constant) s’il y a un fromage à la position pos.
        @return true s'il y a un fromage à la position pos, false sinon. */
    private boolean fromageIci_EnOrdreConstant(Point pos) {
        return hf.contains(pos);
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a ».
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible(Point de, Point a, Map<Point, List<Point>> laby) {

        for (Point k : laby.keySet())
        {
            if (k.equals(de)) {
                for (Point p : laby.get(k)) {
                    if (p.equals(a)){
                        return true;
                    }
                }
            }
        }

    return false;
    }

    /* Indique si le joueur peut passer de la position (du Point) « de » au point « a »,
        mais sans devoir parcourir la liste des Points se trouvant dans la Map !
        @return true s'il y a un passage depuis  « de » vers « a ». */
    private boolean passagePossible_EnOrdreConstant(Point de, Point a) {
        if (hp.get(de).contains(a)){return true;}
        else {
            return false;
        }
    }

    /* Retourne la liste des points qui ne peuvent pas être atteints depuis la position « pos ».
        @return la liste des points qui ne peuvent pas être atteints depuis la position « pos ». */
    private List<Point> pointsInatteignables(Point pos, Map<Point, List<Point>> laby) {
            System.out.print("Parcours depuis : " + pos + "\n");
            //int cpt = 0;
            ArrayList<Point> chemin = new ArrayList<>();
            parcoursRec(pos, chemin, laby);
            return chemin;
            }


    private void parcoursRec(Point pos, ArrayList<Point> chemin, Map<Point, List<Point>> laby){
        chemin.add(pos);

            for(Point voisin : laby.keySet()){
                if (!chemin.contains(voisin))
                    //cpt++;
                    parcoursRec(voisin, chemin, laby);
                    //cpt--;
                }
            chemin.remove(pos);
        }
}


