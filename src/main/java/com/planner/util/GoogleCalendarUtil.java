package com.planner.util;

import com.planner.models.CheckList;
import com.planner.models.Task;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;
import com.planner.schedule.day.Day;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Handler of all core linker and utility functions for GoogleIO
 *
 * @author Andrew Roe
 */
public class GoogleCalendarUtil {

    public static Event formatTaskToEvent(Task task, Day.TimeStamp timeStamp, int dayIdx) {
        Event event = new Event().setSummary(task.getName()); //todo need to display labels with given Task
        StringBuilder sb = new StringBuilder("Due: ");
        sb.append(task.getDueDate().get(Calendar.YEAR))
                .append("-")
                .append(task.getDueDate().get(Calendar.MONTH) + 1)
                .append("-")
                .append(task.getDueDate().get(Calendar.DAY_OF_MONTH))
                .append("\n\n");
        CheckList cl = task.getCheckList();
        if(cl != null) {
            sb.append(cl.getName()).append(":<ul>");
            for(CheckList.Item i1 : cl.getItems()) {
                sb.append("<li>")
                        .append(i1.getDescription())
                        .append("</li>");
            }
            sb.append("</ul>\n");
        }

        sb.append("Agile Planner\n\neb007aba6df2559a02ceb17ddba47c85b3e2b930");
        event.setDescription(sb.toString());

        Calendar startCalendar = Time.getFormattedCalendarInstance(dayIdx);
        startCalendar.set(Calendar.HOUR_OF_DAY, timeStamp.getStartHour());
        startCalendar.set(Calendar.MINUTE, timeStamp.getStartMin());
        DateTime startDateTime = new DateTime(startCalendar.getTime());
        EventDateTime start = new EventDateTime().setDateTime(startDateTime);
        event.setStart(start);

        Calendar endCalendar = Time.getFormattedCalendarInstance(dayIdx);
        endCalendar.set(Calendar.HOUR_OF_DAY, timeStamp.getEndHour());
        endCalendar.set(Calendar.MINUTE, timeStamp.getEndMin());

        // Check if end time is after midnight, adjust the day accordingly
        if (timeStamp.getEndHour() < timeStamp.getStartHour() ||
                (timeStamp.getEndHour() == 0 && timeStamp.getEndMin() < timeStamp.getStartMin())) {
            endCalendar.add(Calendar.DAY_OF_MONTH, 1);
        }

        DateTime endDateTime = new DateTime(endCalendar.getTime());
        EventDateTime end = new EventDateTime().setDateTime(endDateTime);
        event.setEnd(end);

        // will use a switch case here with regards to setting the color ids
        if (task.getColor() != null) {
            switch (task.getColor()) {
                case RED:
                    event.setColorId("4");
                    break;
                case ORANGE:
                    event.setColorId("6");
                    break;
                case YELLOW:
                    event.setColorId("5");
                    break;
                case GREEN:
                    event.setColorId("2");
                    break;
                case LIGHT_BLUE:
                    event.setColorId("7");
                    break;
                case BLUE:
                    event.setColorId("1");
                    break;
                case INDIGO:
                    event.setColorId("9");
                    break;
                case VIOLET:
                    event.setColorId("3");
                    break;
                case BLACK:
                    event.setColorId("8");
                    break;
            }
        }
//        if (!task.getLabel().isEmpty()) {
//            event.setColorId("" + task.getLabel().get(0).getColor());
//        } else event.setColorId("7");

        return event;
    }

    public static List<String> formatEventsToTasks(List<Event> items) throws IOException {
        List<String> tasks = new ArrayList<>();
        for(Event i1 : items) {
            if(i1.getDescription() != null && i1.getDescription().contains("eb007aba6df2559a02ceb17ddba47c85b3e2b930")) {
                String title = i1.getSummary();
                String start = i1.getStart().toPrettyString();
                String end = i1.getEnd().toPrettyString();
                tasks.add("title=" + title + ", start=" + start + ", end=" + end);
            }
        }
        return tasks;
    }

}