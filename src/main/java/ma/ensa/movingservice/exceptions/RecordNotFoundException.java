package ma.ensa.movingservice.exceptions;

public class RecordNotFoundException extends RuntimeException{

    public RecordNotFoundException(){
        super("NOT FOUND");
    }

    public RecordNotFoundException(String message){
        super(message);
    }

}
