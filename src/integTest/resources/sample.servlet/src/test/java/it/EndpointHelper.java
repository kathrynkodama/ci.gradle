package it;

import static org.junit.Assert.assertTrue;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.Response;

public class EndpointHelper {

    public void testEndpoint(String endpoint, String expectedOutput) {
        String port = System.getProperty("liberty.test.port");
        String war = System.getProperty("war.name");
    	System.out.println("Port: " + port + "\nWar: " + war);
        String url = "http://localhost:" + port + "/" + war + endpoint;
        System.out.println("Testing " + url);
        Response response = sendRequest(url, "GET");
        int responseCode = response.getStatus();
        assertTrue("Incorrect response code: " + responseCode,
                   responseCode == 200);
        
        String responseString = response.readEntity(String.class);
        response.close();
        assertTrue("Incorrect response, response is " + responseString, responseString.contains(expectedOutput));
    }

    public Response sendRequest(String url, String requestType) {
        Client client = ClientBuilder.newClient();
        System.out.println("Testing " + url);
        WebTarget target = client.target(url);
        Invocation.Builder invoBuild = target.request();
        Response response = invoBuild.build(requestType).invoke();
        return response;
    }
}