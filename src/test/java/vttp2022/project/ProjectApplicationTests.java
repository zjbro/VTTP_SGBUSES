package vttp2022.project;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import vttp2022.project.model.ArrivalBus;
import vttp2022.project.services.BusService;

@SpringBootTest
class ProjectApplicationTests {

	@Autowired
	private BusService bService;

	@Test
	void busServicesShouldReturnTrue() {
		Optional<List<ArrivalBus>> a = bService.arrivingBus("44669");
		assertTrue(a.isPresent());

	}

}
