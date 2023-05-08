package timeCard.restAPI;

import java.net.URI;

import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

	/*=============================================================
	 The tests are run in a specific order to verify
	 the required behavior in each state of the timesheet records.
	 ==============================================================*/
@SpringBootTest(webEnvironment=WebEnvironment.DEFINED_PORT)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TimeCardApplicationTests {
	@Autowired
	private TestRestTemplate restTemplate;

	@LocalServerPort
	int serverPort = 8080;

	@Test
	@Order(1)
	public void testClockOut_whenEmpty_then500() throws Exception {
		final String testUrl = "http://localhost:" + serverPort + "/exit?id=10";
		URI uri = new URI(testUrl);
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

		//Verify request fails
		Assertions.assertEquals(500, response.getStatusCode().value());
	}
	@Test
	@Order(2)
	public void testClockIn_whenEmpty_then200() throws Exception {
		final String testUrl = "http://localhost:" + serverPort + "/enter?id=11";
		URI uri = new URI(testUrl);
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

		//Verify request succeeds
		Assertions.assertEquals(200, response.getStatusCode().value());
	}

	@Test
	@Order(3)
	public void testClockIn_whenAfterClockIn_then500 () throws Exception {
		final String testUrl = "http://localhost:" + serverPort + "/enter?id=11";
		URI uri = new URI(testUrl);
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

		//Verify request fails
		Assertions.assertEquals(500, response.getStatusCode().value());
	}

	@Test
	@Order(4)
	public void testClockOut_whenAfterClockIn_then200 () throws Exception {
		final String testUrl = "http://localhost:" + serverPort + "/exit?id=11";
		URI uri = new URI(testUrl);
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

		//Verify request succeeds
		Assertions.assertEquals(200, response.getStatusCode().value());
	}
	@Test
	@Order(5)
	public void testClockOut_whenAfterClockOut_then500 () throws Exception {
		final String testUrl = "http://localhost:" + serverPort + "/exit?id=11";
		URI uri = new URI(testUrl);
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

		//Verify request fails
		Assertions.assertEquals(500, response.getStatusCode().value());
	}
	@Test
	@Order(6)
	public void testClockIn_afterClockOut_then200 () throws Exception {
		final String testUrl = "http://localhost:" + serverPort + "/enter?id=11";
		URI uri = new URI(testUrl);
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

		//Verify request succeeds
		Assertions.assertEquals(200, response.getStatusCode().value());
	}

	@Test
	@Order(7)
	public void testGetInfoForId () throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/info?id=11";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> request = new HttpEntity<>("", headers);
		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

		//Verify request succeed
		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertTrue(response.getBody().contains("\"employee_id\":11"));
	}

	@Test
	@Order(8)
	public void testClockIn_newEmployee_then200 () throws Exception {
		final String testUrl = "http://localhost:" + serverPort + "/enter?id=12";
		URI uri = new URI(testUrl);
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.postForEntity(uri, request, String.class);

		//Verify request succeeds
		Assertions.assertEquals(200, response.getStatusCode().value());
	}
	@Test
	@Order(9)
	public void testGetInfoForAllIds () throws Exception {
		final String baseUrl = "http://localhost:" + serverPort + "/info";
		URI uri = new URI(baseUrl);
		HttpHeaders headers = new HttpHeaders();

		HttpEntity<String> request = new HttpEntity<>("", headers);

		ResponseEntity<String> response = restTemplate.getForEntity(uri, String.class);

		//Verify request succeed
		Assertions.assertEquals(200, response.getStatusCode().value());
		Assertions.assertTrue(response.getBody().contains("\"employee_id\":11"));
		Assertions.assertTrue(response.getBody().contains("\"employee_id\":12"));
	}
}