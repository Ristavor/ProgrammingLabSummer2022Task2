package du;

public class Outer {
    private boolean isConsole = false; //хранит в себе указание, идёт ли вывод в консоль
    private final StringBuilder str = new StringBuilder(); //требуется в случаях вывода в String

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

    public void clear(){
        str.setLength(0);
    }
}
