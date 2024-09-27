
public class Driver {
    public static void main(String [] args) {
        Polynomial p = new Polynomial();
        System.out.println(p.evaluate(3));
        double [] c1 = {6,5};
        int[] e1 = {0,3};
        Polynomial p1 = new Polynomial(c1, e1);
        double [] c2 = {-2,-9};
        int[] e2 = {1, 4};
        Polynomial p2 = new Polynomial(c2, e2);
        Polynomial s = p1.add(p2);
        System.out.println("s(0.1) = " + s.evaluate(0.1));
        if(s.hasRoot(1))
            System.out.println("1 is a root of s");
        else
            System.out.println("1 is not a root of s");
        
        //milen tests
        System.err.println("Milen's tests");
        double[] c3 = {3, 4, 1};
        int[] e3 = {0, 3, 1};
        double[] c4 = {2, 4, 5};
        int[] e4 = {2, 3, 0};

        Polynomial p3 = new Polynomial(c3, e3);
        Polynomial p4 = new Polynomial(c4, e4);
        Polynomial p5 = p3.multiply(p4);

        double[] c5 = {6, 5, 1};
        int[] e5 = {0, 3, 1};

        double[] c6 = {2, -5, 5};
        int[] e6 = {2, 3, 0};

        Polynomial p6 = new Polynomial(c5, e5);
        Polynomial p7 = new Polynomial(c6, e6);
        Polynomial p8 = p6.add(p7);
        
    }
}