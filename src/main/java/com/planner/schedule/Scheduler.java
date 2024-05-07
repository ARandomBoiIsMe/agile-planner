package com.planner.schedule;

import com.planner.manager.ScheduleManager;
import com.planner.schedule.day.Day;
import com.planner.models.Task;
import com.planner.models.UserConfig;
import com.planner.util.EventLog;

import java.util.List;
import java.util.PriorityQueue;

/**
 * The interface {@code Scheduler} is for all scheduling implementations to sort out tasks via the {@link ScheduleManager}
 *
 * @author Andrew Roe
 * @since 0.3.0
 */
public interface Scheduler {

    static Scheduler getInstance(UserConfig userConfig, EventLog eventLog, int idx) {
        if (idx == 0) return DynamicScheduler.getSingleton(userConfig, eventLog);
        return CompactScheduler.getSingleton(userConfig, eventLog);
    }

    /**
     * Assigns current {@link Day} a set of {@link Task.SubTask} via one of the specified scheduling algorithms
     *
     * @param day Day being processed
     * @param errorCount number of errors in current schedule
     * @param complete Tasks that are "finished scheduling" are added here
     * @param taskManager PriorityQueue of all Tasks in sorted order
     * @return number of errors in scheduling Day
     */
    int assignDay(Day day, int errorCount, PriorityQueue<Task> complete, PriorityQueue<Task> taskManager);

    /**
     * Corrects schedule by eliminating all overflows via adjusting number of hours per day
     * <p>
     * NOTE: This assumes that past days are stored in an archived list (thus, the first index
     * of schedule is for today)
     *
     * @param schedule list of days scheduled
     * @param errorCount number of errors currently in schedule
     * @return boolean value if successful (e.g. would be false if there are more than 24 hours assigned for a task due today)
     */
    boolean correctSchedule(List<Day> schedule, int errorCount);
}