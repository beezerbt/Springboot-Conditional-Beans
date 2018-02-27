package hello;

import audit.SepaClassicAuditor;
import audit.SepaInstantAuditor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SepaLoggerConfig {

    @Bean
    @ConditionalOnProperty(name="sepa.instant.mode", havingValue="true")
    public SepaInstantAuditor sepaInstantAuditor(){
        return new SepaInstantAuditor();
    }

    @Bean
    @ConditionalOnProperty(name="sepa.instant.mode", havingValue="false")
    public SepaClassicAuditor sepaClassicAuditor(){
        return new SepaClassicAuditor();
    }
}


