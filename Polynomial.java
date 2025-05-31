
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {

    double[] coefficients;
    double[] variables;

    public Polynomial() {
        this.coefficients = new double[]{0};
        this.variables = new double[]{0};
    }

    public Polynomial(double[] coefficients, double[] variables) {
        this.coefficients = coefficients;
        this.variables = variables;
    }

    public Polynomial(File file) throws FileNotFoundException {
        String l = "";
        try (Scanner in = new Scanner(file)) {
            if (in.hasNextLine()) {
                l = in.nextLine();
            }
        }

        // splitting! 
        String[] terms = l.split("(?=[+-])");
        int n = terms.length;
        coefficients = new double[n];
        variables = new double[n];

        for (int i = 0; i < n; i++) {
            String str_temp = terms[i];
            if (str_temp.isEmpty()) {
                continue;
            }

            String[] temp_split = str_temp.split("(?=[x])");
            String coeffStr; //1
            String varStr;  //2

            if (temp_split.length > 1) {
                coeffStr = temp_split[0];
                String withoutx = temp_split[1].substring(1);

                varStr = withoutx.isEmpty() ? "1" : withoutx;
            } else {
                coeffStr = temp_split[0];
                varStr = "0";
            }

            // normalize coeffs
            if (coeffStr.equals("") || coeffStr.equals("+")) {
                coeffStr = "1";
            } else if (coeffStr.equals("-")) {
                coeffStr = "-1";
            }

            // parse
            coefficients[i] = Double.parseDouble(coeffStr);
            variables[i] = Double.parseDouble(varStr);
        }
    }

    // Add Function
    public Polynomial add(Polynomial other) {
        int len1 = this.variables.length;
        int len2 = other.variables.length;

        double[] resultVariables = new double[len1 + len2];
        double[] resultCoefficients = new double[len1 + len2];
        int resultSize = 0;

        for (int i = 0; i < len1; i++) {
            boolean check = false;
            for (int j = 0; j < len2; j++) {
                if (this.variables[i] == other.variables[j]) {
                    resultVariables[resultSize] = this.variables[i];
                    resultCoefficients[resultSize] = this.coefficients[i] + other.coefficients[j];
                    check = true;
                    break;
                }
            }
            if (!check) {
                resultVariables[resultSize] = this.variables[i];
                resultCoefficients[resultSize] = this.coefficients[i];
            }
            resultSize++;
        }
        for (int i = 0; i < len2; i++) {
            boolean check = false;
            for (int j = 0; j < len1; j++) {
                if (other.variables[i] == this.variables[j]) {
                    check = true;
                    break;
                }
            }
            if (!check) {
                resultVariables[resultSize] = other.variables[i];
                resultCoefficients[resultSize] = other.coefficients[i];
                resultSize++;
            }
        }
        // Resize them
        double[] finalCoeffs = new double[resultSize];
        double[] finalVars = new double[resultSize];
        for (int i = 0; i < resultSize; i++) {
            finalCoeffs[i] = resultCoefficients[i];
            finalVars[i] = resultVariables[i];
        }
        return new Polynomial(finalCoeffs, finalVars);

    }

    // Evaluate Function
    public double evaluate(double x) {
        double[] array = new double[this.coefficients.length];
        double sum = 0.0f;
        for (int i = 0; i < this.coefficients.length; i++) {
            array[i] = this.coefficients[i];
            sum += (Math.pow(x, this.variables[i]) * array[i]);
        }

        return sum;

    }

    // Has root Function
    public boolean hasRoot(double x) {
        return evaluate(x) == 0;
    }

    // Multiply Function
    public Polynomial multiply(Polynomial other) {
        int len1 = this.variables.length;
        int len2 = other.variables.length;
        double[] resultVars = new double[len1 * len2];
        double[] resultCoefs = new double[len1 * len2];
        int resultlen = 0;
        for (int i = 0; i < len1; i++) {
            for (int j = 0; j < len2; j++) {
                double exp = this.variables[i] + other.variables[j];
                double coef = this.coefficients[i] * other.coefficients[j];
                boolean check = false;
                for (int k = 0; k < resultlen; k++) {
                    if (resultVars[k] == exp) {
                        resultCoefs[k] += coef;
                        check = true;
                        break;
                    }
                }

                if (!check) {
                    resultVars[resultlen] = exp;
                    resultCoefs[resultlen] = coef;
                    resultlen++;
                }
            }
        }
        return new Polynomial(resultVars, resultCoefs);
    }

    public void saveToFile(String fileName) throws IOException {
        String output = "";
        boolean first = true;

        for (int i = 0; i < coefficients.length; i++) {
            double coef = coefficients[i];
            int exp = (int) variables[i];

            // just incase of zero coefficient
            if (coef == 0) {
                continue;
            }

            // sign
            if (coef < 0) {
                output += "-";
            } else if (!first) {
                output += "+";
            }

            double absCoef = Math.abs(coef);

            // constant term
            if (exp == 0) {
                output += absCoef;
            } else {
                // variable term
                if (absCoef != 1.0) {
                    output += absCoef;
                }
                output += "x";
                if (exp != 1) {
                    output += exp;
                }
            }

            first = false;
        }

        if (first) {
            output = "0";
        }

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write(output);
        }
    }

}
