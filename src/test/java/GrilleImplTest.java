import exception.ElementInterditException;
import exception.HorsBornesException;
import exception.ValeurImpossibleException;
import exception.ValeurInitialeModificationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

public class GrilleImplTest {
    private GrilleImpl grille;

    private final char UN = '1';
    private final char DEUX = '2';
    private final char TROIS = '3';

    private ElementDeGrille[] elements;

    //Mise en place avant les tests
    @BeforeEach
    public void setup(){
        // Initialiser la grille avant chaque test
         this.elements = new ElementDeGrille[]{
                 new ElementDeGrilleImplAsChar(UN),
                 new ElementDeGrilleImplAsChar(DEUX),
                 new ElementDeGrilleImplAsChar(TROIS)
         };
        ElementDeGrille[][] grilleTab = {
                {null, null, null},
                {null, null, null},
                {null, null, elements[0]}
        };
        grille = new GrilleImpl(elements, grilleTab);
    }

    //Mise en place des tests sur le constructeur
    @Test
    public void testConstructorElementsSetCorrectly() {
        // Arrange
        Set<ElementDeGrille> expectedElements = new HashSet<>();
        expectedElements.add(elements[0]);
        expectedElements.add(elements[1]);
        expectedElements.add(elements[2]);

        // Act
        Set<ElementDeGrille> actualElements = grille.getElements();

        // Assert
        Assertions.assertEquals(expectedElements, actualElements);
    }

    @Test
    public void testConstructorDimensionSetCorrectly() {
        // Arrange
        int expectedDimension = 3;

        // Act
        int actualDimension = grille.getDimension();

        // Assert
        Assertions.assertEquals(expectedDimension, actualDimension);
    }

    //Série de tests sur getElements
    @Test
    public void testGetElementsReturnCorrectSet() {
        // Arrange
        Set<ElementDeGrille> expectedElements = new HashSet<>();
        expectedElements.add(elements[0]);
        expectedElements.add(elements[1]);
        expectedElements.add(elements[2]);

        // Act
        Set<ElementDeGrille> actualElements = grille.getElements();

        // Assert
        Assertions.assertEquals(expectedElements, actualElements);
    }

    //Série de tests sur getDimension
    @Test
    public void testGetDimensionReturnCorrectValue() {
        // Arrange
        int expectedDimension = 3;

        // Act
        int actualDimension = grille.getDimension();

        // Assert
        Assertions.assertEquals(expectedDimension, actualDimension);
    }

    //Série de tests sur SetValue
    @Test
    public void testSetValueValidValueSuccess() throws HorsBornesException, ValeurImpossibleException,
            ElementInterditException, ValeurInitialeModificationException {
        // Arrange
        int x = 0;
        int y = 0;
        ElementDeGrille value = elements[0];

        // Act
        grille.setValue(x, y, value);

        // Assert
        ElementDeGrille result = grille.getValue(x, y);
        Assertions.assertEquals(value, result);
    }

    @Test
    public void testSetValueInvalidPositionThrowHorsBornesException() {
        // Arrange
        int x = 4;
        int y = 2;
        ElementDeGrille value = elements[1];

        // Act and Assert
        Assertions.assertThrows(HorsBornesException.class, () -> grille.setValue(x, y, value));
    }

    @Test
    public void testSetValueInitialValueThrowValeurInitialeModificationException() {
        // Arrange
        int x = 2;
        int y = 2;
        ElementDeGrille value = elements[2];

        // Act and Assert
        Assertions.assertThrows(ValeurInitialeModificationException.class, () -> grille.setValue(x, y, value));
    }

    @Test
    public void testSetValueInvalidValueThrowElementInterditException() {
        // Arrange
        int x = 1;
        int y = 2;
        ElementDeGrille value = new ElementDeGrilleImplAsChar('4');

        // Act and Assert
        Assertions.assertThrows(ElementInterditException.class, () -> grille.setValue(x, y, value));
    }

    @Test
    public void testIsValeurInitialeWithValueReturnsTrue() throws HorsBornesException {
        // Arrange
        int x = 2;
        int y = 2;

        // Act
        boolean result = grille.isValeurInitiale(x, y);

        // Assert
        Assertions.assertTrue(result);
    }

    @Test
    public void testIsValeurInitialeWithoutValueReturnsFalse() throws HorsBornesException {
        // Arrange
        int x = 1;
        int y = 0;

        // Act
        boolean result = grille.isValeurInitiale(x, y);

        // Assert
        Assertions.assertFalse(result);
    }

    @Test
    public void testIsValeurInitialeWithInvalidPositionThrowsHorsBornesException() {
        // Arrange
        int x = 5;
        int y = 0;

        // Act and Assert
        Assertions.assertThrows(HorsBornesException.class, () -> grille.isValeurInitiale(x, y));
    }
}
