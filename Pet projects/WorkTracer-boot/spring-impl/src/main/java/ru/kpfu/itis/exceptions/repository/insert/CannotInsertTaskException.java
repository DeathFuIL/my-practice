package ru.kpfu.itis.exceptions.repository.insert;

public class CannotInsertTaskException extends CannotInsertRepositoryException {

    public CannotInsertTaskException() {
        super("Failed to create task, try again");
    }

}
