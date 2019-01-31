
/*
    This class was added super late as a way to utilize the assignment requirements.
    Sorry for the simplicity behind it. I mask a lot of the tile management in the GameLogic class...
    It just felt right to do so.
 */

public class Animal{
    public String name;
    public int strength;

    public Animal(String species,int str){
        name = species;
        strength = str;
    }

    public void setStrength(int str){
        strength = str;
    }
}
