package Exceptions;

public class WaitingFullException extends Exception {

    public String toString() {

        return "WaitingFullException : Tous les centres de tri ne peuvent plus accueillir de vaisseaux";

    }

}
