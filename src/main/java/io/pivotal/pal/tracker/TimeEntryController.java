package io.pivotal.pal.tracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TimeEntryController {
    private final TimeEntryRepository timeEntryRepository;

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @PostMapping("/time-entries")
    public ResponseEntity<TimeEntry> create(@RequestBody TimeEntry timeEntry) throws Exception {
        return new ResponseEntity<>(
                timeEntryRepository.create(timeEntry),
                HttpStatus.CREATED
        );
    }

    @GetMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> read(@PathVariable(value="id") long id) throws Exception {
        TimeEntry foundTimeEntry = timeEntryRepository.find(id);
        if(foundTimeEntry == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(
                    foundTimeEntry,
                    HttpStatus.OK
            );
        }
    }

    @GetMapping("/time-entries")
    public ResponseEntity<List<TimeEntry>> list() throws Exception {
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
            return new ResponseEntity<>(
                    updatedTimeEntry,
                    HttpStatus.OK
            );
        }
    }

    @DeleteMapping("/time-entries/{id}")
    public ResponseEntity<TimeEntry> delete(@PathVariable(value="id") long id) throws Exception {
        timeEntryRepository.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
