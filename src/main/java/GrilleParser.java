/**
 * package-info.java: toutes les infos sur le package.
 */

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

import exception.ElementInterditException;
import exception.HorsBornesException;
import exception.ValeurImpossibleException;
import exception.ValeurInitialeModificationException;

/**
 * Méthodes utilitaire permettant de créer une Grille
 * à partir d'un fichier texte.
 * Il est attendu que la première ligne contiennet :
 * le symbole de case vide, suivi des symboles
 * possibles dans la grille (en UTF-8).
 * Les autres lignes contiennent le contenu de la grille.
 * <p>
 * Exemple:
 * -1234
 * 2---
 * 1--3
 * 3--4
 * -1-2
 *
 * @author Sébastien Choplin <sebastien.choplin@u-picardie.fr>
 */
public final class GrilleParser {

    /**
     * Constructeur privé pour empêcher l'instanciation de la classe.
     */
    private GrilleParser() {
    }

    /**
     * Fonction parse.
     *
     * @param in InputStream contenant les données de la grille
     * @return la grille créée à partir du fichier
     * @throws IOException
     * si une erreur de lecture se produit lors de la lecture du fichier
     * @throws ElementInterditException
     * si une valeur de la grille est interdite
     * @throws ValeurInitialeModificationException
     * si une valeur initiale de la grille est modifiée
     * @throws HorsBornesException
     * si les dimensions de la grille sont incorrectes
     * @throws ValeurImpossibleException
     * si la grille ne respecte pas les règles du Sudoku
     */
    public static Grille parse(final InputStream in)
            throws IOException, ElementInterditException,
            ValeurInitialeModificationException,
            HorsBornesException, ValeurImpossibleException {
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(in, StandardCharsets.UTF_8))) {

            String line = reader.readLine();
            if (line == null || line.length() == 0) {
                throw new IllegalArgumentException("pas de première ligne ?");
            }
            final int dimension = line.length() - 1;
            final char vide = line.charAt(0);
            Map<Character, ElementDeGrille> elementDeGrilleMap =
                    new HashMap<>();
            for (int i = 1; i < line.length(); i++) {
                char value = line.charAt(i);
                if (value == vide) {
                    continue;
                }
                if (elementDeGrilleMap.containsKey(value)) {
                    throw new IllegalArgumentException(
                            "valeur possible dupliquée : " + value
                    );
                }
                elementDeGrilleMap.put(
                        value, new ElementDeGrilleImplAsChar(value)
                );
            }

            if (elementDeGrilleMap.size() != dimension) {
                throw new IllegalArgumentException(
                        "pas le bon nombre de valeurs possibles"
                );
            }
            ElementDeGrille[] elementDeGrilles =
                    elementDeGrilleMap.values().toArray(
                            new ElementDeGrille[]{}
                    );
            ElementDeGrille[][] grilleTab =
                    new ElementDeGrille[dimension][dimension];

            for (int i = 0; i < dimension; i++) {
                line = reader.readLine();
                if (line == null || line.length() != dimension) {
                    throw new IOException(
                            "pas le bon nombre sur la ligne : " + line
                    );
                }
                for (int j = 0; j < dimension; j++) {
                    char c = line.charAt(j);
                    if (c != vide) {
                        ElementDeGrille elementDeGrille =
                                elementDeGrilleMap.get(c);
                        if (elementDeGrille == null) {
                            throw new ValeurImpossibleException(
                                    String.valueOf(c)
                            );
                        }
                        grilleTab[i][j] = elementDeGrille;
                    }
                }
            }

            return new GrilleImpl(elementDeGrilles, grilleTab);
        }
    }

}
