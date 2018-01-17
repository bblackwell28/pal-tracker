package io.pivotal.pal.tracker;

import org.springframework.boot.actuate.metrics.CounterService;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {

    private final TimeEntryRepository timeEntryRepository;
    private final CounterService counter;
    private final GaugeService gauge;

    public TimeEntryController(TimeEntryRepository timeEntryRepository, CounterService counter, GaugeService gauge) {
        this.timeEntryRepository = timeEntryRepository;
        this.counter = counter;
        this.gauge = gauge;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) throws Exception {

        TimeEntry createdTimeEntry = timeEntryRepository.create(timeEntry);
        counter.increment("TimeEntry.created");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());

        return new ResponseEntity<>(createdTimeEntry, HttpStatus.CREATED);
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(value="id") long id) throws Exception {
        TimeEntry foundTimeEntry = timeEntryRepository.find(id);
        if(foundTimeEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            counter.increment("TimeEntry.read");
            return new ResponseEntity<>(
                    foundTimeEntry,
                    HttpStatus.OK
            );
        }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() throws Exception {
        counter.increment("TimeEntry.listed");
        return new ResponseEntity<>(
                timeEntryRepository.list(),
                HttpStatus.OK
        );
    }

    @PutMapping("/time-entries/{id}")
    public ResponseEntity update(@PathVariable(value="id") long id, @RequestBody TimeEntry timeEntry) throws Exception {
        TimeEntry updatedTimeEntry = timeEntryRepository.update(id, timeEntry);
        if(updatedTimeEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            counter.increment("TimeEntry.updated");
            return new ResponseEntity<>(
                    updatedTimeEntry,
                    HttpStatus.OK
            );
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable(value="id") long id) throws Exception {
        timeEntryRepository.delete(id);
        counter.increment("TimeEntry.deleted");
        gauge.submit("timeEntries.count", timeEntryRepository.list().size());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
