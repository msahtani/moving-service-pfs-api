package ma.ensa.movingservice.exceptions;

public class ProviderNotAccepted extends Exception{

    public ProviderNotAccepted(){
        super("you're not accepted by the admin");
    }

}
