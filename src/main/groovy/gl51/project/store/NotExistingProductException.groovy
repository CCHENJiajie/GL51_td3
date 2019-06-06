package gl51.project.store

public class NotExistingProductException extends Exception{
    NotExistingProductException(){
        super()
    }

    public NotExistingProductException(String errorMessage) {
        super(errorMessage);
    }
}
