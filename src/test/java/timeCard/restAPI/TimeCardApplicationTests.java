package timeCard.restAPI;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.random.RandomGenerator;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;


@SpringBootTest
class TimeCardApplicationTests {

	private static final String baseUrl = "http://localhost:8080/";
	@Test
	void testClockIn_whenEmpty_then200() {
		// given
		int id = RandomGenerator.getDefault().nextInt(1000);

	}

	@Test
	void testClockIn_whenAfterClockIn_then500() {
	}

	@Test
	void testClockIn_afterClockOut_then200() {
	}

	@Test
	void testClockOut_whenEmpty_then500() {
	}
	@Test
	void testClockOut_whenAfterClockOut_then500() {
	}

	@Test
	void testClockOut_whenAfterClockIn_then200() {
	}
	@Test
	void testGetInfoForId() {
	}

	@Test
	void testGetInfoForAllIds() {
	}

}
