import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Driver {
    public static void main(String[] args) {
        Polynomial p0 = new Polynomial();
        System.out.println("p0(2) = " + p0.evaluate(2));  

        double[] c1 = {1.0, 2.0};
        double[] v1 = {1.0, 0.0};
        Polynomial p1 = new Polynomial(c1, v1);
        System.out.println("p1(2) = " + p1.evaluate(2));  
        double[] c2 = {3.0};
        double[] v2 = {2.0};
        Polynomial p2 = new Polynomial(c2, v2);
        System.out.println("p2(2) = " + p2.evaluate(2));  

        Polynomial sum = p1.add(p2);
        System.out.println("sum coefficients and exponents:");
        for (int i = 0; i < sum.coefficients.length; i++) {
            System.out.println(
                sum.coefficients[i] + " x^" + (int) sum.variables[i]
            );
        }

        Polynomial prod = p1.multiply(p2);
        System.out.println("prod coefficients and exponents:");
        for (int i = 0; i < prod.coefficients.length; i++) {
            System.out.println(
                prod.coefficients[i] + " x^" + (int) prod.variables[i]
            );
        }
        try {
            File f = new File("test.txt");
            Polynomial fromFile = new Polynomial(f);
            System.out.println("fromFile coefficients and exponents:");
            for (int i = 0; i < fromFile.coefficients.length; i++) {
                System.out.println(
                    fromFile.coefficients[i] + " x^" + (int) fromFile.variables[i]
                );
            }
        } catch (FileNotFoundException e) {
            System.out.println("Could not find test.txt");
        }

        try {
            p2.saveToFile("out.txt");
            System.out.println("Saved p2 to out.txt");
        } catch (IOException e) {
            System.out.println("Failed to write out.txt");
        }
    }
}