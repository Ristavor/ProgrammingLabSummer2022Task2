package du;

public class Outer {
    private boolean isConsole = false;
    public StringBuilder str = new StringBuilder();

    public Outer() {
    }

    public Outer(boolean isConsole) {
        this.isConsole = isConsole;
    }

    public void out(String s) {
        if (isConsole) System.out.print(s);
        else str.append(s);
    }

    public String get(){
        String s = str.toString();
        str.setLength(0);
        return s;
    }
}
