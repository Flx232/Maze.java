package edu.njit.cs114;
import java.util.*;
/**
 * Author: Frank Xu
 * Date created: 1/22/22
 */
public class ArrayPolynomial implements Polynomial{
    // coefficient array
    private double [] coefficients = new double[1];
    // Default constructor
    public ArrayPolynomial() {
    }
    /**
     * Create a single term polynomial
     * @param power
     * @param coefficient
     * @throws Exception when power is negative
     */
    public ArrayPolynomial(int power, double coefficient) throws Exception {
        addTerm(power, coefficient);
        /**
         * Complete code here for lab assignment
         */
    }
    /**
     * Create a new polynomial that is a copy of "another".
     * NOTE : you should use only the interface methods of Polynomial to get
     *         the coefficients of "another"
     *
     * @param another
     */
    public ArrayPolynomial(Polynomial another){
        int degree = another.degree();
        for(int power = degree; power >= 0; power--){
            try {
                addTerm(power, another.coefficient(power));
            }catch(Exception e){}
        }
        /**
         * Complete code here for lab assignment
         */
    }
    @Override
    public void addTerm(int power, double coefficient) throws Exception {
        if (power < 0){
            throw new Exception("Invalid Power");
        }
        if (power >= coefficients.length){
            coefficients = Arrays.copyOf(coefficients, power+1);
        }
        coefficients[power] += coefficient;
        /**
         * Complete code here for lab assignment
         * Make sure you check power for validity !!
         */
    }
    @Override
    public double removeTerm(int power) {
        if(power < 0 || power > degree()){
            return 0.0;
        }
        /**
         * Complete code here for homework
         * Make sure you check power for validity !!
         */
        double coeff = coefficients[power];
        coefficients[power] = 0.0;
        return coeff;
    }
    @Override
    public double coefficient(int power) {
        if (power < 0 || power > degree()){
            return 0;
        }
        /**
         * Complete code here for lab assignment (Modify return statement !!)
         * Make sure you check power for validity !!
         */
        return coefficients[power];
    }
    @Override
    public int degree() {
        /**
         * Complete code here for lab assignment
         */
        for(int power = coefficients.length-1; power >= 0; power--){
            if(coefficients[power] != 0){
                return power;
            }
        }
        return 0;
    }
    @Override
    public double evaluate(double point) {
        double value = 0.0;
        for(int power = 0; power < degree()+1; power++){
            value += Math.pow(point, power) * coefficient(power);
        }
        /**
         * Complete code here for homework
         */
        return value;
    }
    @Override
    public Polynomial add(Polynomial p) {
        Polynomial result = new ArrayPolynomial(p);
        int upperLimit = 0;
        if (p.degree() >= degree()){
            upperLimit = p.degree();
        }else{
            upperLimit = degree();
        }
        for(int power = 0; power < upperLimit+1; power++){
            try {
                result.addTerm(power, coefficient(power));

            }catch(Exception e){}
        }
        /**
         * Complete code here for homework
         */
        return result;
    }
    @Override
    public Polynomial subtract(Polynomial p) {
        Polynomial result = new ArrayPolynomial();
        int upperLimit = 0;
        if (p.degree() >= degree()){
            upperLimit = p.degree();
        }else{
            upperLimit = degree();
        }
        for(int power = 0; power < upperLimit+1; power++){
            try {
                double coeff = coefficient(power) - p.coefficient(power);
                result.addTerm(power, coeff);

            }catch(Exception e){}
        }
        /**
         * Complete code here for homework
         */
        return result;
    }
    @Override
    public Polynomial multiply(Polynomial p) {
        Polynomial result = new ArrayPolynomial();
        for (int power = 0; power < degree() + 1; power++) {
            for (int otherPower = 0; otherPower < p.degree() + 1; otherPower++){
                try {
                    double coeff = coefficient(power) * p.coefficient(otherPower);
                    int newPower = 0;
                    if (power == 0){
                        newPower = otherPower;
                    }else if (otherPower == 0){
                        newPower = power;
                    }else{
                        newPower = power + otherPower;
                    }
                    result.addTerm(newPower, coeff);

                } catch (Exception e) {}
            }
        }
        /**
         * Complete code here for homework
         */
        return result;
    }
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int degree = degree();
        if (degree == 0) {
            return "" + coefficients[0];
        }
        for (int i=degree; i >=0; i--) {
            if (coefficients[i] == 0) {
                continue;
            }
            if (i < degree) {
                builder.append(coefficients[i] > 0 ? " + " : " - ");
                builder.append(Math.abs(coefficients[i]));
            } else {
                builder.append(coefficients[i]);
            }
            if (i > 0) {
                builder.append("x");
                if (i > 1) {
                    builder.append("^" + i);
                }
            }
        }
        return builder.toString();
    }
    /**
     * Returns true if the object obj is a Polynomial and its coefficients are the
     same
     *   for all the terms in the polynomial
     * @param obj
     * @return true or false
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Polynomial)) {
            return false;
        }
        Polynomial other = (Polynomial) obj;
        if (degree() != other.degree()) {
            return false;
        }
        int degree = degree();
        for (int i=0; i <= degree; i++) {
            if (coefficients[i] != other.coefficient(i)) {
                return false;
            }
        }
        return true;
    }
    public static void main(String [] args) throws Exception {
        // Remove comments in code after you have implemented all the functions in
        // homework assignment
        Polynomial p1 = new ArrayPolynomial();
        System.out.println("p1(x) = " + p1);
        assert p1.degree() == 0;
        assert p1.coefficient(0) == 0;
        assert p1.coefficient(2) == 0;
        assert p1.equals(new ArrayPolynomial());
        Polynomial p2 = new ArrayPolynomial(4, 3.6);
        p2.addTerm(0,3.6);
        p2.addTerm(3,2.5);
        p2.addTerm(2,-2.5);
        System.out.println("p2(x) = " + p2);
        assert p2.degree() == 4;
        assert p2.coefficient(2) == -2.5;
        assert p2.toString().equals("3.6x^4 + 2.5x^3 - 2.5x^2 + 3.6");
        System.out.println("p2(1) = " + p2.evaluate(1));
        assert p2.evaluate(1) == 7.2;
        Polynomial p3 = new ArrayPolynomial(0, -4);
        p3.addTerm(5,3);
        p3.addTerm(5,-1);
        System.out.println("p3(x) = " + p3);
        assert p3.degree() == 5;
        assert p3.coefficient(5) == 2;
        assert p3.coefficient(0) == -4;
        System.out.println("p3(2) = " + p3.evaluate(2));
        assert p3.evaluate(2) == 60;
        Polynomial p21 = new ArrayPolynomial(p2);
        System.out.println("p21(x) = " + p21);
        assert p21.equals(p2);
        assert p21.removeTerm(4) == 3.6;
        assert p21.removeTerm(4) == 0;
        System.out.println("p21(x) = " + p21);
        assert !p21.equals(p2);
        assert p21.coefficient(4) == 0;
        assert p21.degree() == 3;
        try {
            Polynomial p5 = new ArrayPolynomial(-5, 4);
            assert false;
        } catch (Exception e) {
            Exception expected;
            assert true;
        }
        System.out.println("p2(x) + p3(x) = " + p2.add(p3));
        Polynomial result = p2.add(p3);
        assert result.degree() == 5;
        assert Math.abs(result.coefficient(5) - 2) <= 0.0001;;
        System.out.println("p2(x) - p3(x) = " +p2.subtract(p3));
        result = p2.subtract(p3);
        assert result.degree() == 5;
        assert Math.abs(result.coefficient(5) - -2) <= 0.0001;
        assert Math.abs(result.coefficient(0) - 7.6) <= 0.0001;
        System.out.println("p2(x) * p3(x) = " +p2.multiply(p3));
        result = p2.multiply(p3);
        assert result.degree() == 9;
        assert Math.abs(result.coefficient(9) - 7.2) <= 0.0001;
        assert Math.abs(result.coefficient(5) - 7.2) <= 0.0001;
        assert Math.abs(result.coefficient(0) - -14.4) <= 0.0001;
        assert Math.abs(p2.evaluate(1) * p3.evaluate(1) - result.evaluate(1)) <= 0.0001;
    }
}
