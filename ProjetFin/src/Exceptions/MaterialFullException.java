package Exceptions;

import java.io.Serializable;

public class MaterialFullException extends Exception implements Serializable {

    public String toString() {

        return "MaterialFullException : Tous les centres de tri ne peuvent plus accueillir un certain type de matière";

    }

}
