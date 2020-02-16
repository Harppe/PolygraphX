import java.util.*;
/**
 * Manages a polynomial using LinkedList and iterators
 * 
 * can add, multiply, sort, addterms, compareTo
 * 
 */
public class Polynomial implements Comparable
{
    private LinkedList<Term> polynomial; // linked list of all the terms
    public final int VALS_IN_TERM = 2; // coeff and power term vals
    public final double ERROR_BOUND = 0.000001;
    /**
     * constructor method
     */
    public Polynomial() {
        polynomial = new LinkedList(); // initiate linkedlist
    }

    /**
     * returns the polynomial linkedlist
     */
    public LinkedList getPoly() {
        return polynomial;
    }

    public double solve(double val) {
        double total = 0;
        for (Term term : polynomial) {
            total+=term.solve(val);
        }
        return total;
    }

    public double derive(double val) {
        return ((solve(val+ERROR_BOUND)-solve(val))/ERROR_BOUND);
    }

    public double integral(double lowerBound, double upperBound) {
        double total = 0;
        for (double currentVal = lowerBound; currentVal<upperBound;currentVal+=ERROR_BOUND) {
            total+=solve(currentVal)*ERROR_BOUND;
        }
        return total;
    }

    /**
     * gets an iterator for the polynomial linkedlist
     */
    public ListIterator getPolyIter() {
        return polynomial.listIterator();   
    }

    /**
     * add a term to the given polynomial (end of it) with input of coeff and power
     */
    public void addTerm(double coefficient, double power)
    {
        ListIterator lI = polynomial.listIterator();
        while (lI.hasNext()) {
            lI.next();   
        }
        lI.add(new Term(coefficient, power));
    }

    /**
     * adds a term object to a polynomial (end of it)
     */
    public void addTerm(Term t) {
        ListIterator lI = polynomial.listIterator();
        while (lI.hasNext()) {
            lI.next();
        }
        lI.add(t);
    }

    /**
     * add the given polynomial and returns the sum
     */
    public Polynomial add(Polynomial p)
    {
        Polynomial tempPoly = new Polynomial();
        appendPolynomial(tempPoly, p);
        appendPolynomial(tempPoly, this);
        return tempPoly;
    }

    /**
     * adds the terms of an apendee polynomial to the end of a target polynomial
     * changes target, but not apendee
     */
    public static void appendPolynomial(Polynomial target, Polynomial apendee) {
        ListIterator apendIter = apendee.getPolyIter();
        while (apendIter.hasNext()) {
            target.addTerm((Term)apendIter.next());   
        }
    }

    /**
     * multiply the given polynomial and return the product
     */
    public Polynomial multiply(Polynomial p)
    {
        ListIterator lI = polynomial.listIterator();
        ListIterator lI2 = p.polynomial.listIterator();
        Polynomial polyTemp = new Polynomial();
        if (!(lI.hasNext() && lI2.hasNext())) { // if one of the two has no values, returns null
            return null;
        }
        while (lI.hasNext()) {
            Term temp1 = (Term)lI.next();
            lI2 = p.polynomial.listIterator();

            while (lI2.hasNext()) {
                Term temp2 = (Term)lI2.next();

                polyTemp.addTerm(temp1.multiply(temp2));
            }
        }
        return polyTemp;
    }

    /**
     * removes all numbers from polynomial with a coefficient of zero
     */
    public void removeZeros() {
        ListIterator lI = polynomial.listIterator();
        while (lI.hasNext()) {
            Term temp = (Term)lI.next();
            if (temp.getCoefficient()==0) {
                lI.remove();
            }
        }
    }

    /**
     * Collect like terms (and removes coefficients of zero)
     */
    public void simplify()
    {
        removeZeros();
        sort();
        ListIterator lI = polynomial.listIterator();
        while (lI.hasNext()) {
            Term temp1 = (Term)lI.next();
            if (lI.hasNext()) {
                Term temp2 = (Term)lI.next();
                if (temp1.getPower() == temp2.getPower()) { // like terms
                    lI.remove();
                    lI.previous(); // goes back to temp1
                    lI.set(temp1.add(temp2)); // adds terms together
                    if (lI.hasPrevious()) // resets it so can compare if more than two like terms in a row
                        lI.previous();
                    else
                        lI = polynomial.listIterator();
                }
                else {
                    lI.previous(); //resets to one previous to compare next pair in series instead of every other term and its pair 
                }
            }
        }
        removeZeros(); // further simplification
        sort();
    }

    /**
     * sorts the terms by decreasing exponent
     */
    public void sort()
    {
        Collections.sort(polynomial);
    }

    /**
     * returns a string of the polynomial
     */
    public String toString() 
    {
        ListIterator lI = polynomial.listIterator();
        String str = "";
        String addSign = " + ";
        while (lI.hasNext()) {
            str+= ((Term)lI.next()) + addSign;
        }
        if (str.length()>3) {
            str = str.substring(0,str.length()-addSign.length()); // remove plus sign after last term
        }
        return str;
    }

    /**
     * duplicates this object and returns the duplicated object
     */
    public Polynomial duplicate() {
        Polynomial dupe = new Polynomial();
        appendPolynomial(dupe, this);
        return dupe;
    }

    /**
     * gets the size of the polynomial linked list object (can be used in program without getting list)
     */
    public int size() {
        ListIterator lI = polynomial.listIterator();
        int size = 0;
        while (lI.hasNext()) {
            lI.next();
            size++;
        }
        return size;
    }

    /**
     * equals method, sees if two polynomials are equal returns true/false
     */
    public boolean equals(Polynomial other)
    {
        return (compareTo(other)==0);
    }

    /**
     * gets the listIterator for the polynomial linkedlist of a duplicated, simplified, linkedlist
     */
    public ListIterator getDupeIter() {
        Polynomial dupe = duplicate();
        dupe.simplify();
        return dupe.getPolyIter();
    }

    /**
     * 1 if other poly larger
     * 0 if same
     * -1 if other smaller or not a polynomial
     * 
     * largeness determined by which poly has a bigger term than all the others
     * of the other
     * if both polys are the same, with one having an extra term, extra-termed one is larger
     */
    public int compareTo(Object other) {
        if (other instanceof Polynomial) {
            ListIterator thisIt = getDupeIter(); // iterator of a duplicated list
            ListIterator otherIt = ((Polynomial)other).getDupeIter();
            while (thisIt.hasNext()) {
                Term temp1 = (Term)thisIt.next();
                if (!otherIt.hasNext()) // this object has one more object with previous terms equal
                    return -1;
                Term temp2 = (Term)otherIt.next(); // same index in linkedlist as other term
                if (temp1.compareTo(temp2) !=0) // sees which has the larger term at the same index
                    return temp1.compareTo(temp2); // same comp for terms, 1 if other larger (parameter), 0 same, -1 other smaller
            }
            if (otherIt.hasNext()) { //  other object has one more object with previous terms equal
                return 1;   
            }
        }
        else // not poly obj
            return -1;
        return 0; // if whole of both polys equal, will get to this point
    }
}
