public class Assignament {
    private String name;
    private String craft;

    public Assignament(String name, String craft) {
        this.name = name;
        this.craft = craft;
    }

    public String getCraft() {
        return craft;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Assignament{" +
                "name='" + name + '\'' +
                ", craft='" + craft + '\'' +
                '}';
    }
}
