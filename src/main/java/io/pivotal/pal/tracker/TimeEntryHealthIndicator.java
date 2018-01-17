package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class TimeEntryHealthIndicator implements HealthIndicator {

    private final TimeEntryRepository timeEntryRepository;
    private final int Int_max = 5;

    public TimeEntryHealthIndicator(TimeEntryRepository timeEntryRepository){
        this.timeEntryRepository = timeEntryRepository;
    }

    @Override
    public Health health() {
        Health.Builder builder = new Health.Builder();
        try {
            if(Int_max <= timeEntryRepository.list().size()){
                builder.down();
            }
            else{
                builder.up();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return builder.build();
    }
}
