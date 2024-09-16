public class Polynomial {
    
    //fields
    double[] coefficients;

    //methods

    //constructors
    public Polynomial() {
        coefficients = new double[0];
    }

    public Polynomial(double[] coefficients) {
        this.coefficients = coefficients;
    }

    //add method
    public Polynomial add(Polynomial func) {
        
        //get length of how many coefficients there are at minimum
        int length1 = Math.min(this.coefficients.length, func.coefficients.length);

        //get length of how many coefficients there are at maximum
        int length2 = Math.max(this.coefficients.length, func.coefficients.length);

        //set up coeffecients to 0 for new poly func
        double[] new_coefficients =  new double[length2];

        //make new polynomial func
        Polynomial result =  new Polynomial(new_coefficients);

        int j;
        for (j = 0; j < length1; j++) {
            //add the coefficients together into the new polynomial func
            result.coefficients[j] = this.coefficients[j] + func.coefficients[j];
        }

        //add leftover coefficnents if our func and given func are different lengths
        for (int i = j; i < length2; i++){
            if (this.coefficients.length > func.coefficients.length) {
                result.coefficients[i] = this.coefficients[i];
            } else {
                result.coefficients[i] = func.coefficients[i];
            }
        }

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
            result += this.coefficients[i] * Math.pow(x, i);
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
}