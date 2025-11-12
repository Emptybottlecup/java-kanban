package timetable.components;

public class TrainerAndHisTraineeAtWeek implements Comparable<TrainerAndHisTraineeAtWeek> {
    private final String fullName;
    private final int traineeAtWeek;

    public TrainerAndHisTraineeAtWeek(String fullName, int traineeAtWeek) {
        this.fullName = fullName;
        this.traineeAtWeek = traineeAtWeek;
    }

    @Override
    public int compareTo(TrainerAndHisTraineeAtWeek o) {
        return o.traineeAtWeek - this.traineeAtWeek;
    }

    public String getFullName() {
        return fullName;
    }

    public int getTraineeAtWeek() {
        return traineeAtWeek;
    }

    @Override
    public String toString() {
        return "TrainerAndHisTraineeAtWeek{" +
                "fullName='" + fullName + '\'' +
                ", traineeAtWeek=" + traineeAtWeek +
                '}';
    }
}
