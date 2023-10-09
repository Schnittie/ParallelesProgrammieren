public class VL1 {
    public static void main(String[] args) {
        System.out.println(pot(4, 3));
    }

    static private int add(int a, int b) {
        if (b == 0) return a;
        return add(a, b - 1) + 1;
    }
    //g: Π1,1
    //h: s( Π3,3(x1, x2, x3) )

    static private int mul(int a, int b) {
        return (mulHelp(a,b,0));
    }
    static private int mulHelp(int a, int b, int z){
        if (b == 0 || a == 0) return z;
        return mulHelp(a,b-1,add(a,z));
    }
    //g: Π1,1 if 0 undefined sonst
    //h: (x1,x2,x3) return add(x1,x3)

    static private int pot(int a, int b) {
        return (potHelp(a,b,1));
    }
    static private int potHelp(int a, int b, int z){
        if (b == 0 || a == 0) return z;
        return potHelp(a,b-1,mul(a,z));
    }
    //g: Π1,1 if 0 undefined sonst
    //h: (x1,x2,x3) return mul(x1,x3)


}
