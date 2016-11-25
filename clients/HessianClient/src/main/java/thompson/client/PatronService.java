package thompson.client;

public interface PatronService {
    String createPatron(String firstname, String lastname, String city, String state,
                                        int zip, String address, String email, long phone, String password);
}
