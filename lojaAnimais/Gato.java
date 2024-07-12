public class Gato extends Animal {
    private String skin;
    private String som;

    public Gato (String nome, String skin, String som){
        super(nome);
        this.skin = skin;
        this.som = som;
    }

    @Override
    public void som(){
        System.out.println("miau miau");
    }

    @Override
    public String toString(){
        return "GATO: " + getNome() + "\tPELAGEM: " + skin;
    }
}
