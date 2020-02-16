
/**
 * A term in a Polynomial
 * TODO:  Make it implement the Comparable doubleerface
 */
public class Term implements Comparable
{
    private double coefficient;
    private double power;

    /**
     * Constructor for objects of class Term
     */
    public Term(double c,double p)
    {
        coefficient = c;
        power = p;
    }
    
    public double solve(double val) {
     double d;
     d = Math.pow(val,power);
     d*=coefficient;
     return d;
    }

    public double getCoefficient(){
        return coefficient;   
    }

    public void setCoefficient(double newC){
        coefficient = newC;   
    }

    public double getPower(){
        return power;   
    }

    public void setPower(double newP){
        power = newP;   
    }

    public String toString(){
        String str = "";
        if (power==0) {
            str+=coefficient;   
        }
        else if (coefficient == 1) {
            if (power == 1)
                str+="x";
            else
                str+="x^"+power;
        }
        else if (power == 1) {
            str+=coefficient + "x";   
        }
        else {
            str+=coefficient+"x^"+power;
        }
        return str;   
    }

    public boolean equals(Term other) {
        if (coefficient == other.getCoefficient() && power == other.getPower()) {
            return true;   
        }
        return false;   
    }

    /**
     * multiplies two terms (this object and another) and returns the result
     */
    public Term multiply(Term other) {
        return new Term(coefficient * other.coefficient, power + other.power);
    }

    /**
     * Adds two like-terms (this object and another) and returns the result
     * if not like-terms, returns null
     */
    public Term add(Term other) {
        if (other.power != power)
            return null;
        else {
            return new Term(other.coefficient + coefficient, power);
        }
    }

    /**
     * 1 if other term larger
     * 0 if same
     * -1 if other is smaller or not a term
     */
    public int compareTo(Object o) {
        Term temp = ((Term)o);
        double tempPower = temp.power;
        double tempCoeff = temp.coefficient;
        if (tempPower > power) {
            return 1;
        }
        else if (tempPower == power) {
            if (tempCoeff > coefficient) {
                return 1;   
            }
            else if (tempCoeff == coefficient) {
                return 0;
            }
            else {//tempCoeff < coefficient
                return -1;
            }
        }
        else { //tempPower < power
            return -1;
        }
    }
}
