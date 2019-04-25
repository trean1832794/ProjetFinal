package Exceptions;

public class MaterialFullException extends Exception {

    public String toString() {

        return "MaterialFullException : Tous les centres de tri ne peuvent plus accueillir un certain type de matière";

    }

}
