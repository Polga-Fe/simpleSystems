public class Passaros extends Animal{
    private String skin;
    private String som;

    public Passaros (String nome, String skin, String som){
        super(nome);
        this.skin = skin;
        this.som = som;
    }

    @Override
    public String toString(){
        return ("PASSARO: "+ getNome() + "\tPENUGEM: "+ skin);
    }

    @Override
    public void som(){
        System.out.println("pru pru");
    }
}
