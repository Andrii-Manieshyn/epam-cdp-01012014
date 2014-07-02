package com.cdp.epam.calendar;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by Andrii_Manieshyn on 07.02.14.
 */
public class CalendareTest {

    public CalendarAppointments firstAppointments;
    public CalendarAppointments secondAppointments;
    public CalendarAppointments thirdAppointments;

    public Calendar calendar;

    @Before
    public void initEvents(){
        calendar = new Calendar();

        firstAppointments = new CalendarAppointments();
        firstAppointments.setEventName("First event");
        firstAppointments.setTime(System.currentTimeMillis() + 100);

        secondAppointments = new CalendarAppointments();
        secondAppointments.setEventName("Second event");
        secondAppointments.setTime(System.currentTimeMillis() + 1000);


        thirdAppointments = new CalendarAppointments();
        thirdAppointments.setEventName("Third event");
        thirdAppointments.setTime(System.currentTimeMillis() + 10000);

    }

    @Test
    public void whenAddToCalendarNewAppointments_thanReturnAppointmentsInRightOrder(){
        calendar.addAppointments(secondAppointments);
        calendar.addAppointments(thirdAppointments);
        calendar.addAppointments(firstAppointments);

        Assert.assertEquals(firstAppointments, calendar.nextAppointments());
        Assert.assertEquals(secondAppointments, calendar.nextAppointments());
        Assert.assertEquals(thirdAppointments, calendar.nextAppointments());
    }
}
