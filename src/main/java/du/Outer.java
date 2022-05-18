package du;

public class Outer {
    boolean isConsole = false;
    public String str = "";

    public Outer() {
    }

    public Outer(boolean isConsole) {
        this.isConsole = isConsole;
    }

    public void out(String s) {
        if (isConsole) System.out.print(s);
        else this.str += s;
    }

    public String get(){
        String s = this.str;
        this.str = "";
        return s;
    }
}
