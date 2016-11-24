package thompson.soap.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import patron.wsdl.GetPatronCreateRequest;
import patron.wsdl.GetPatronCreateResponse;

public class PatronClient extends WebServiceGatewaySupport {
    private static final Logger log = LoggerFactory.getLogger(PatronClient.class);

    public GetPatronCreateResponse getPatronCreateResponse(String email){
        GetPatronCreateRequest request = new GetPatronCreateRequest();
        request.setFirstname("testfirst");
        request.setLastname("lastname");
        request.setCity("testcity");
        request.setState("AA");
        request.setZip(33333);
        request.setAddress("testaddress");
        request.setEmail(email);
        request.setPhone(2222222222L);
        request.setPassword("pword");
        GetPatronCreateResponse response = (
                GetPatronCreateResponse) getWebServiceTemplate().marshalSendAndReceive("http://localhost:8080/application/ws",
                request,
                new SoapActionCallback("http://localhost:8080/application/ws"));
        return response;
    }

    public void logResponse(GetPatronCreateResponse response){
        log.info("Response Code["+response.getCode() + "]: "+ response.getMessage());
    }
}
