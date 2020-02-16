import java.util.*;
public class Main
{
    
    public static Polynomial polynom;

    public static void main() {
        Polynomial poly = new Polynomial();
        poly.addTerm(1,2);
        polynom = poly;
        Design.main(null);
    }
    
}
