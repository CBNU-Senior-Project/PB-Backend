package com.sammaru.projectlinker;

import com.sammaru.projectlinker.domain.contest.service.ContestService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserserviceApplicationTests {

	private final ContestService contestService;

    UserserviceApplicationTests(@Autowired ContestService contestService) {
        this.contestService = contestService;
    }

    @Test
	void contextLoads() {
        contestService.createContest();
	}

}
