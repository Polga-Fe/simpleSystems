import java.util.List;
import java.util.ArrayList;

public class Pet {
    private List<Animal> animals;
    private int maxAnimais;

    public Pet(int maxAnimais){
        this.maxAnimais = maxAnimais;
        this.animals = new ArrayList<>();
    }

    public void addAnimal(Animal animal){
        if (animals.size() < maxAnimais){
            animals.add(animal);
        } else {
            System.out.println("Pet is full !");
        }
    }
    public void showAnimals(){
        System.out.println("lotação atual: "+animals.size()+"/"+maxAnimais);
        for (Animal animal : animals){
            System.out.println(animal.toString());
        }
    }

    public void emitSom() {
        for (Animal animal : animals){
            animal.som();
        }
    }
}
