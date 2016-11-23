package thompson.library.system.remote.soap;

import io.spring.guides.gs_producing_web_service.GetPatronCreateRequest;
import io.spring.guides.gs_producing_web_service.GetPatronCreateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import thompson.library.system.services.LibraryServices;
import thompson.library.system.utilities.EntryExistsException;

import java.util.Calendar;

@Endpoint
public class PatronEndpoint {
    private static final String NAMESPACE_URI = "http://spring.io/guides/gs-producing-web-service";

    @Autowired
    LibraryServices service;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getPatronCreateRequest")
    @ResponsePayload
    public GetPatronCreateResponse getCountry(@RequestPayload GetPatronCreateRequest request) {
        GetPatronCreateResponse response = new GetPatronCreateResponse();
        Calendar calendar = Calendar.getInstance();
        java.sql.Timestamp joinDate = new java.sql.Timestamp(calendar.getTime().getTime());
        try {
            service.createPatron(request.getFirstname(), request.getLastname(), request.getCity(), request.getState(),
                    request.getZip(), request.getAddress(), joinDate, request.getEmail(), request.getPhone(),
                    false, request.getPassword());
            response.setCode(1);
            response.setMessage("Patron successfully created!");
            return response;
        } catch (EntryExistsException e) {
            response.setCode(0);
            response.setMessage("ERROR: Patron already exists with email " + request.getEmail());
            return response;
        }
    }
}
