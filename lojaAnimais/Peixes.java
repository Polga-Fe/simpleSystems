public class Peixes extends Animal{
    private String water;

    public Peixes(String nome, String water){
        super(nome);
        this.water = water;
    }

    @Override
    public String toString(){
        return ("PEIXE: "+ getNome()+"\tTIPO D'AGUA: "+ water);
    }

    @Override
    public void som(){
    }
}
