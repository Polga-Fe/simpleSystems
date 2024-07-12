public class Cachorro extends Animal {
    private String raça;
    private String som;

    public Cachorro(String nome, String raça, String som){
        super(nome);
        this.raça = raça;
        this.som = som;
    }

    @Override
    public String toString(){
        return "CACHORRO: "+ getNome() + "\tRAÇA: " + raça;
    }

    @Override
    public void som(){
        System.out.println("au au");
    }


}
