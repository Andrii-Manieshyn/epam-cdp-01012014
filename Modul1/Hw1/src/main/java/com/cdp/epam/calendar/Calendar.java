package com.cdp.epam.calendar;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by Andrii_Manieshyn on 07.02.14.
 */
public class Calendar {
    private Queue<CalendarAppointments> calendarAppointments;

    public Calendar(){
        calendarAppointments = new PriorityQueue<CalendarAppointments>(17 , new CalendareAppointmentsComparator());
    }

    public boolean addAppointments(CalendarAppointments event){
        return calendarAppointments.add(event);
    }

    public CalendarAppointments nextAppointments(){
        return calendarAppointments.poll();
    }

    class CalendareAppointmentsComparator implements Comparator<CalendarAppointments>{

        @Override
        public int compare(CalendarAppointments o1, CalendarAppointments o2) {
            Long o1Time = o1.getTime();
            Long o2Time = o2.getTime();
            return o1Time.compareTo(o2Time);
        }
    }
}
