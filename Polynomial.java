import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Polynomial {
    
    //fields
    double[] coefficients;
    int[] exponents;

    //methods

    //constructors
    public Polynomial() {
        coefficients = new double[0];
        exponents = new int[0];
    }

    public Polynomial(double[] coefficients, int[] exponents) {
        this.coefficients = coefficients;
        this.exponents = exponents;
    }

    // Constructor that reads from a file
    public Polynomial(File file) {
        //read from file
        try {
            // read from file
            Scanner input = new Scanner(file);
            String polynomial = input.nextLine();
            // close the file
            input.close();

            // parse the string
            // ?= makes it so that it keeps the + or - sign along with term intead of just using ["+-"]
            String regex = "(?=[+-])";
            String[] terms = polynomial.split(regex);
            double[] actual_coefficients = new double[terms.length];
            int[] actual_exponents = new int[terms.length];

            for (int i = 0; i < terms.length; i++) {
                // split the terms into coefficient and exponent
                if (terms[i].contains("x")) {
                    String[] parts = terms[i].split("x");

                    // Parse the coefficient

                    // If the coefficient is empty, it is 1
                    if (parts[0].equals("")) {
                        actual_coefficients[i] = 1;
                    // If the coefficent is "-", it is -1
                    } else if (parts[0].equals("-")) {
                        actual_coefficients[i] = -1;
                    // Otherwise, parse the coefficient
                    } else {
                        actual_coefficients[i] = Double.parseDouble(parts[0]);
                    }

                    //Parse the exponent

                    // If the exponent is empty, it is 1
                    if (parts.length == 1) {
                        actual_exponents[i] = 1;
                    // Otherwise, parse the exponent
                    } else {
                        actual_exponents[i] = Integer.parseInt(parts[1]);
                    }
                
                // Case where there is no x and only a coefficient
                } else {
                    // If the term is just a number, then the coefficient is that number and the exponent is 0, constant term
                    actual_coefficients[i] = Double.parseDouble(terms[i]);
                    actual_exponents[i] = 0;
                }
            }

            coefficients = actual_coefficients;
            exponents = actual_exponents;
        }
        catch (FileNotFoundException e) {
            System.out.println("File not found");
        }
    }

    //helped method for add to add coefficents 
    public void addHelper(double[] resultCoefficients, int[] resultExponents, int index, double coefficient, int exponent) {
        for (int k = 0; k < index; k++) {
            if (resultExponents[k] == exponent) {
                //Case where exponent is already in the result, then we just add coefficent
                resultCoefficients[k] += coefficient;
                return;
            }
        }

        //Case where exponent is new
        resultCoefficients[index] = coefficient;
        resultExponents[index] = exponent;
    }

    //helper method for add to trim empty elements
    public double[] trim(double[] arr) {
        int empty = 0;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == 0.0) {
                empty++;
            }
        }

        double[] trimmed = new double[arr.length - empty];

        System.arraycopy(arr, 0, trimmed, 0, trimmed.length);

        return trimmed;
    }

    public int[] trim(int[] arr, int coefficentLength) {
        
        int[] trimmed = new int[coefficentLength];
        System.arraycopy(arr, 0, trimmed, 0, coefficentLength);

        return trimmed;
    }

    //add method
    public Polynomial add(Polynomial func) {
        
        // get max length of coefficients and exponents if they're unique
        int maxLength = this.coefficients.length + func.coefficients.length;
        double[] resultCoefficients = new double[maxLength];
        int[] resultExponents = new int[maxLength];

        int i = 0;
        int index = 0;

        while (i < Math.min(this.coefficients.length, func.coefficients.length)) {

            addHelper(resultCoefficients, resultExponents, index, this.coefficients[i], this.exponents[i]);
            index++;
            addHelper(resultCoefficients, resultExponents, index, func.coefficients[i], func.exponents[i]);
            index++;
            i++;

        }

        // Now we have to add remaining elements of the polynomial who's longer
        while (i < this.coefficients.length) {
            addHelper(resultCoefficients, resultExponents, i, this.coefficients[i], this.exponents[i]);
            i++;
        }

        while (i < func.coefficients.length) {
            addHelper(resultCoefficients, resultExponents, i, func.coefficients[i], func.exponents[i]);
            i++;
        }

        double[] zerosAfterCoefficient = new double[resultCoefficients.length];
        int[] zerosAfterExponent = new int[resultExponents.length];

        int index2 = 0;

        for (int j = 0; j < resultCoefficients.length; j++) {
            if (resultCoefficients[j] != 0.0) {
                zerosAfterCoefficient[index2] = resultCoefficients[j];
                zerosAfterExponent[index2] = resultExponents[j];
                index2++;
            }
        }
        
        //trim the empty elements
        double[] trimmedCoefficients = trim(zerosAfterCoefficient);
        int[] trimmedExponents = trim(zerosAfterExponent, trimmedCoefficients.length);

        Polynomial result = new Polynomial(trimmedCoefficients, trimmedExponents);

        return result;
    }

    //evaluate method
    public double evaluate(double x) {
        
        //initialize result
        double result = 0;

        //get length of coefficients
        int length = this.coefficients.length;

        for (int i = 0; i < length; i++) {

            //add the coefficients times x to the power of i to the result
            result += this.coefficients[i] * Math.pow(x, this.exponents[i]);
        }

        return result;
    }

    //hasRoot method
    public boolean hasRoot(double x) {

        //evaluate the polynomial at x
        if (evaluate(x) == 0) {
            return true;
        }
        return false;

    }

    //multiply method 
    public Polynomial multiply(Polynomial func) {
        
        int maxLength = this.coefficients.length * func.coefficients.length;
        int[] resultExponents = new int[maxLength];
        double[] resultCoefficients = new double[maxLength];
        int index = 0;

        if (this.coefficients.length <= func.coefficients.length) {
           for (int i = 0; i < this.coefficients.length; i++) {
               for (int j = 0; j < func.coefficients.length; j++) {
                   int exponent = this.exponents[i] + func.exponents[j];
                   double coefficient = this.coefficients[i] * func.coefficients[j];
                   addHelper(resultCoefficients, resultExponents, index, coefficient, exponent);
                   index++;
               }
           }
        } else {
            for (int i = 0; i < func.coefficients.length; i++) {
                for (int j = 0; j < this.coefficients.length; j++) {
                    int exponent = this.exponents[j] + func.exponents[i];
                    double coefficient = this.coefficients[j] * func.coefficients[i];
                    addHelper(resultCoefficients, resultExponents, index, coefficient, exponent);
                    index++;
                }
            }
        }


        double zerosAfterCoefficient[] = new double[resultCoefficients.length];
        int zerosAfterExponent[] = new int[resultExponents.length];

        int index2 = 0;
        for (int i = 0; i < resultCoefficients.length; i++) {
            if (resultCoefficients[i] != 0.0) {
                zerosAfterCoefficient[index2] = resultCoefficients[i];
                zerosAfterExponent[index2] = resultExponents[i];
                index2++;
            }
        }

        //trim the empty elements
        double[] trimmedCoefficients = trim(zerosAfterCoefficient);

        int[] trimmedExponents = trim(zerosAfterExponent, zerosAfterCoefficient.length);

        Polynomial result = new Polynomial(trimmedCoefficients, trimmedExponents);
        
        return result;

    }

    // method to save to file
    public void saveToFile(String filename) {
        try {
            FileWriter output = new FileWriter(filename, true);


            for (int i = 0; i < this.coefficients.length; i++) {
                // Case where the exponent is 0 so we just append the coefficient
                if (this.exponents[i] == 0) {
                    // Case when coefficent is greater than 0 so we add a "+" sign
                    if (i > 0 && this.coefficients[i] > 0){
                        output.write("+" + Double.toString(this.coefficients[i]));
                    // All other cases, we just append the coefficient bc the negative sign is already there and if the first 
                    // term is positive, we don't need to add a "+" sign
                    }else {
                        output.write(Double.toString(this.coefficients[i]));
                    }
                
                // Case where the exponent is > 0 so we have to append the coefficent and exponent
                } else {
                    // Case when coefficent is greater than 0 so we add a "+" sign
                    if (i > 0 && this.coefficients[i] > 0){
                        output.write("+" + Double.toString(this.coefficients[i]));
                    // All other cases, we just append the coefficient bc the negative sign is already there and if the first 
                    // term is positive, we don't need to add a "+" sign
                    } else {
                        output.write(Double.toString(this.coefficients[i]));
                    }

                    // Append the exponent as in this if statement, the exponent is greater than 0
                    output.write("x" + Integer.toString(this.exponents[i]));
                }
            }

            output.close();
        }
        catch (IOException e) {
            System.out.println("Error writing to file");
        }
    }
}