package io.pivotal.pal.tracker;

import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository  {

    private List<TimeEntry> storeTimeEntries = new ArrayList<>();

    @Override
    public TimeEntry create(TimeEntry timeEntry) {
        timeEntry.setId((long) (storeTimeEntries.size() + 1));
        storeTimeEntries.add(timeEntry);
        return timeEntry;
    }
    @Override
    public TimeEntry find(long id) {
        return storeTimeEntries
                .stream()
                .filter(storedTimeEntry -> storedTimeEntry.getId() == id)
                .findFirst()
                .orElse(null);
    }
    @Override
    public List<TimeEntry> list() {
        return storeTimeEntries;
    }
    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {
        TimeEntry existingTimeEntry = find(id);
        existingTimeEntry.setUserId(timeEntry.getUserId());
        existingTimeEntry.setProjectId(timeEntry.getProjectId());
        existingTimeEntry.setHours(timeEntry.getHours());
        existingTimeEntry.setDate(timeEntry.getDate());
        return existingTimeEntry;
    }
    @Override
    public void delete(long id) {
        storeTimeEntries.remove(find(id));
    }
}
