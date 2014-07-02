package com.cdp.epam.calendar;

/**
 * Created by Andrii_Manieshyn on 07.02.14.
 */
public class CalendarAppointments {
    private Long time;
    private String eventName;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CalendarAppointments that = (CalendarAppointments) o;

        if (!eventName.equals(that.eventName)) return false;
        if (!time.equals(that.time)) return false;

        return true;
    }

}
