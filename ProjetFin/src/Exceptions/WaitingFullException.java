package Exceptions;

import java.io.Serializable;

public class WaitingFullException extends Exception implements Serializable {

    public String toString() {

        return "WaitingFullException : Tous les centres de tri ne peuvent plus accueillir de vaisseaux";

    }

}
