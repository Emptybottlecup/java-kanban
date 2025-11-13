package timetable;

import timetable.components.TimeOfDay;
import timetable.components.TrainingSession;
import timetable.enums.DayOfWeek;
import timetable.components.TrainerAndHisTraineeAtWeek;

import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeMap;
import java.util.Collections;

public class Timetable {

    private final HashMap<DayOfWeek, TreeMap<TimeOfDay, ArrayList<TrainingSession>>> timetable;

    private Map<String, Integer> coachesAndTraineeCount;

    public Timetable() {
        timetable = new HashMap<>();
        coachesAndTraineeCount = new HashMap<>();
    }

    public void addNewTrainingSession(TrainingSession trainingSession) {

        String fullNameOfCoach = trainingSession.getCoach().getSurname() + ' ' + trainingSession.getCoach().getName() + ' ' + trainingSession.getCoach().getMiddleName();

        coachesAndTraineeCount.put(fullNameOfCoach, coachesAndTraineeCount.getOrDefault(fullNameOfCoach, 0) + 1);

        TreeMap<TimeOfDay, ArrayList<TrainingSession>> dayOfNewTrainingSession = timetable.get(trainingSession.getDayOfWeek());

        if (dayOfNewTrainingSession == null) {
            dayOfNewTrainingSession = new TreeMap<>();
            timetable.put(trainingSession.getDayOfWeek(), dayOfNewTrainingSession);
        }

        ArrayList<TrainingSession> trainings = dayOfNewTrainingSession.get(trainingSession.getTimeOfDay());

        if (trainings == null) {
            trainings = new ArrayList<>();
            dayOfNewTrainingSession.put(trainingSession.getTimeOfDay(), trainings);
        }

        trainings.add(trainingSession);
    }

    public TreeMap<TimeOfDay, ArrayList<TrainingSession>> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.get(dayOfWeek);
    }

    public ArrayList<TrainingSession> getTrainingSessionsForDayAndTime(DayOfWeek dayOfWeek, TimeOfDay timeOfDay) {
        return timetable.get(dayOfWeek).get(timeOfDay);
    }

    public ArrayList<TrainerAndHisTraineeAtWeek> getCountByCoaches() {
        ArrayList<TrainerAndHisTraineeAtWeek> trainersAndTheirsTraineesAtWeek = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : coachesAndTraineeCount.entrySet()) {
            trainersAndTheirsTraineesAtWeek.add(new TrainerAndHisTraineeAtWeek(entry.getKey(), entry.getValue()));
        }

        Collections.sort(trainersAndTheirsTraineesAtWeek);

        return trainersAndTheirsTraineesAtWeek;
    }

}