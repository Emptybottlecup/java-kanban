import timetable.enums.*;
import timetable.components.*;
import timetable.*;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.TreeMap;

public class TimetableTest {
    @Test
    public void  testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник вернулось одно занятие
        Assert.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());
        //Проверить, что за вторник не вернулось занятий
        Assert.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    public void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        // Проверить, что за понедельник вернулось одно занятие
        Assert.assertEquals(1, timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY).size());

        // Проверить, что за четверг вернулось два занятия в правильном порядке: сначала в 13:00, потом в 20:00
        TreeMap<TimeOfDay, ArrayList<TrainingSession>> oneDayTimeTable = timetable.getTrainingSessionsForDay(DayOfWeek.
                THURSDAY);

        Assert.assertEquals(2, oneDayTimeTable.size());
        int i = 0;

        for (Map.Entry<TimeOfDay, ArrayList<TrainingSession>> entry : oneDayTimeTable.entrySet()) {
            if (i == 0) {
                Assert.assertTrue(entry.getKey().getHours() == 13 && entry.getKey().getMinutes() == 0);
                ++i;
            } else {
                Assert.assertTrue(entry.getKey().getHours() == 20 && entry.getKey().getMinutes() == 0);
            }
        }

        // Проверить, что за вторник не вернулось занятий
        Assert.assertNull(timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY));
    }

    @Test
    public void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        //Проверить, что за понедельник в 13:00 вернулось одно занятие
        Assert.assertEquals(1, timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(13,0)).size());

        //Проверить, что за понедельник в 14:00 не вернулось занятий
        Assert.assertNull(timetable.getTrainingSessionsForDayAndTime(DayOfWeek.MONDAY,new TimeOfDay(14,0)));
    }

    @Test
    public void testTimetableRememberNewTrainer() {
        Timetable timetable = new Timetable();
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        Coach coach1 = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession thursdayAdultTrainingSession1 = new TrainingSession(groupAdult, coach1,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession1);

        Coach coach2 = new Coach("Антонов", "Антон", "Сергеевич");
        TrainingSession thursdayAdultTrainingSession2 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        timetable.addNewTrainingSession(thursdayAdultTrainingSession2);

        Assert.assertEquals(2, timetable.getCountByCoaches().size());
    }

    @Test
    public void testTimetableRememberTrainerCountOfTrainee() {
        Timetable timetable = new Timetable();
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        TrainingSession mondayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);
        timetable.addNewTrainingSession(mondayAdultTrainingSession);

        Assert.assertEquals(2, timetable.getCountByCoaches().getFirst().getTraineeAtWeek());
    }

    @Test
    public void testTimetableShowCorrectNamesAndCountOfTrainee() {
        Timetable timetable = new Timetable();
        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession thursdayAdultTrainingSession1 = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession mondayAdultTrainingSession1 = new TrainingSession(groupAdult, coach,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));
        TrainingSession saturdayAdultTrainingSession1 = new TrainingSession(groupAdult, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(20, 0));

        Coach coach2 = new Coach("Антов", "Антон", "Сергеевич");
        TrainingSession thursdayAdultTrainingSession2 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));
        TrainingSession mondayAdultTrainingSession2 = new TrainingSession(groupAdult, coach2,
                DayOfWeek.MONDAY, new TimeOfDay(20, 0));

        Coach coach3 = new Coach("Андреев", "Антон", "Сергеевич");
        TrainingSession thursdayAdultTrainingSession3 = new TrainingSession(groupAdult, coach3,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));


        timetable.addNewTrainingSession(thursdayAdultTrainingSession1);
        timetable.addNewTrainingSession(mondayAdultTrainingSession1);
        timetable.addNewTrainingSession(saturdayAdultTrainingSession1);

        timetable.addNewTrainingSession(thursdayAdultTrainingSession2);
        timetable.addNewTrainingSession(mondayAdultTrainingSession2);

        timetable.addNewTrainingSession(thursdayAdultTrainingSession3);


        String fullNameOfFirst = "Васильев Николай Сергеевич";
        String fullNameOfSecond = "Антов Антон Сергеевич";
        String fullNameOfThird = "Андреев Антон Сергеевич";


        Assert.assertTrue((3 == timetable.getCountByCoaches().get(0).getTraineeAtWeek()) && (fullNameOfFirst.equals(timetable.getCountByCoaches().get(0).getFullName())));
        Assert.assertTrue((2 == timetable.getCountByCoaches().get(1).getTraineeAtWeek()) && (fullNameOfSecond.equals(timetable.getCountByCoaches().get(1).getFullName())));
        Assert.assertTrue((1 == timetable.getCountByCoaches().get(2).getTraineeAtWeek()) && (fullNameOfThird.equals(timetable.getCountByCoaches().get(2).getFullName())));
    }

}
