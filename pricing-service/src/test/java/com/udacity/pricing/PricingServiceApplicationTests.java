package com.udacity.pricing;

import com.udacity.pricing.domain.price.Price;
import com.udacity.pricing.domain.price.PriceRepository;
import com.udacity.pricing.service.PriceException;
import com.udacity.pricing.service.PricingService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class PricingServiceApplicationTests {
	@LocalServerPort
	private int port;

	@Autowired
	private PriceRepository priceRepository;

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void contextLoads() {
	}

	@Test
	public void getPrice() throws Exception {
		Optional<Price> optionalPrice = priceRepository.findById(Long.valueOf(1));
		Price storedPrice = null;
		try{
			storedPrice = optionalPrice.get();
		}catch (Exception e){
			e.printStackTrace();
		}

		ResponseEntity<Price> response =
				this.restTemplate.getForEntity("http://localhost:" + port + "/prices/1", Price.class);

		Price price = response.getBody();

		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
		assertThat(storedPrice.getPrice(), equalTo(price.getPrice()));
	}
	@Test
	public void getAllPrices() throws Exception{
		ResponseEntity<List> response =
				this.restTemplate.getForEntity("http://localhost:" + port + "/prices",  List.class);
		assertThat(response.getStatusCode(), equalTo(HttpStatus.OK));
	}

}
