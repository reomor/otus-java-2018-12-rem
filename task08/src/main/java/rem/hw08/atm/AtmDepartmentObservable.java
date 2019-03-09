package rem.hw08.atm;

public interface AtmDepartmentObservable extends AtmDepartment {
    void registerObserver(AtmObserver o);
    void removeObserver(AtmObserver o);
}
