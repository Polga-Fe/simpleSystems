
public class Menu {
    public static void main(String[] args) {
        Pet novoPet = new Pet(10);

        Cachorro cachorro1 = new Cachorro("Rex", "Labrador", "au au");
        Gato gato1 = new Gato("Mimi", "Branco", "miau");
        Passaros passaro1 = new Passaros("PiuPiu", "Canário", null);
        Peixes peixe1 = new Peixes("Nemo", "Água doce");
        Peixes peixe2 = new Peixes("Naruto", "Água salgada");


        novoPet.addAnimal(cachorro1);
        novoPet.addAnimal(gato1);
        novoPet.addAnimal(passaro1);
        novoPet.addAnimal(peixe1);
        novoPet.addAnimal(peixe2);

        System.out.println("Lista de Animais no PetShop:");
        novoPet.showAnimals();

        System.out.println("\nEmitindo sons dos animais:");
        novoPet.emitSom();
    }
}