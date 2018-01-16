package io.pivotal.pal.tracker;

import java.util.List;

public interface TimeEntryRepository {
    TimeEntry create(TimeEntry timeEntry) throws Exception;
    TimeEntry find(long id) throws Exception;
    List<TimeEntry> list() throws Exception;
    TimeEntry update(long id, TimeEntry timeEntry) throws Exception;
    void delete(long id) throws Exception;
}
