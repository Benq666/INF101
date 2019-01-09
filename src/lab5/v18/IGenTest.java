package lab5.v18;

import lab5.v18.datastructures.IGrid;
import lab5.v18.util.IGenerator;
import lab5.v18.util.generators.IntGenerator;
import lab5.v18.util.generators.MyGridGenerator;
import lab5.v18.util.generators.StringGenerator;

import java.util.ArrayList;
import java.util.List;

public class IGenTest {
    public static void main(String[] args) {
        int min = 10;
        int max = 20;
        int n = 10;
        IGenerator<String> strGener = new StringGenerator(min, max);

        System.out.printf("%d random strings (%d - %d chars):%n", n, min, max);
        for (int i = 0; i < n; i++) {
            System.out.printf("%d.\t%s%n", i + 1, strGener.generate());
        }

        n = 5;
        List<String> fiveEquals = new ArrayList<>(strGener.generateEquals(n));
        System.out.printf("%nA list of %d equal strings:%n", n);
        for (int i = 0; i < n; i++) {
            System.out.printf("%d. %s%n", i + 1, fiveEquals.get(i));
        }

        min = 0;
        max = 1000;
        IGenerator<Integer> intGener = new IntGenerator(min, max);
        long summ = 0;
        for (int i = 0; i < 1000000; i++) {
            summ += intGener.generate();
        }
        System.out.printf("%nAverage of a sum of 1.000.000 ints from %d to %d:%n%d%n",
                min, max, summ / 1000000);

        // grid with random string elements
        min = 1;
        max = 8;
        intGener = new IntGenerator(min, max);
        IGenerator<IGrid<String>> gridGen =
                new MyGridGenerator<>(new StringGenerator(min, max), intGener, intGener);
        IGrid<String> test = gridGen.generate();
        intGener = new IntGenerator(test.getWidth());
        int randWidth = intGener.generate();
        intGener = new IntGenerator(test.getHeight());
        int randHeight = intGener.generate();

        System.out.printf("%nElement at %d, %d from a %dx%d grid:%n%s%n",
                randWidth + 1, randHeight + 1, test.getWidth(), test.getHeight(),
                test.get(randWidth, randHeight));

        System.out.println("\nGrid:");
        for (int i = 0; i < test.getWidth(); i++) {
            for (int j = 0; j < test.getHeight(); j++) {
                System.out.printf("|%7s", test.get(i, j));
            }
            System.out.printf("|%n");
        }
    }
}