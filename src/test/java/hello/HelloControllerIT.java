package hello;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.*;

import java.net.URL;

import audit.SepaClassicAuditor;
import audit.SepaInstantAuditor;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.annotation.Conditional;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
        "sepa.instant.mode=true",
})
public class HelloControllerIT {

    @LocalServerPort
    private int port;

    private URL base;

    @Autowired
    private TestRestTemplate template;

    @Autowired(required = false)
    private SepaInstantAuditor sepaInstantAuditor;

    @Autowired(required = false)
    private SepaClassicAuditor sepaClassicAuditor;

    @Value( "${sepa.instant.mode}" )
    private String sepaMode;

    @Before
    public void setUp() throws Exception {
        this.base = new URL("http://localhost:" + port + "/");
    }

    @Test
    public void sepaIndicatorSetTest() throws Exception {
        assertNotNull(sepaMode);
        assertTrue(sepaMode.length() > 0);
        assertTrue(Boolean.valueOf(sepaMode));
        assertNotNull(sepaInstantAuditor);
    }

    @Test
    public void getHello() throws Exception {
        ResponseEntity<String> response = template.getForEntity(base.toString(),
                String.class);
        assertThat(response.getBody(), equalTo("Greetings from Spring Boot!"));
    }
}
