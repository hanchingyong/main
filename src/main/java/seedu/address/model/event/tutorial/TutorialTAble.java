package seedu.address.model.event.tutorial;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javafx.collections.ObservableList;

/**
 * Wraps all tutorial data at the Tutorial TAble level
 * Duplicates are not allowed
 */

public class TutorialTAble implements ReadOnlyTutorial {

    private final UniqueTutorialList tutorials;

    /*
     * The 'unusual' code block below is a non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        tutorials = new UniqueTutorialList();
    }

    public TutorialTAble() {}

    /**
     * Creates a TutorialTAble using the tutorials in the {@code toBeCopied}
     */
    public TutorialTAble(ReadOnlyTutorial toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    /**
     * Creates a TutorialTAble using the tutorials in the {@code consultList}
     */
    public TutorialTAble(List<Tutorial> tutorialList) {
        this.tutorials.setTutorials(tutorialList);
    }

    //// list overwrite operations

    /**
     * Replaces the contents of the tutorial list with {@code tutorials}.
     * {@code tutorials} must not contain duplicate tutorials.
     */
    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials.setTutorials(tutorials);
    }

    /**
     * Resets the existing data of this {@code TutorialTAble} with {@code newData}.
     */
    public void resetData(ReadOnlyTutorial newData) {
        requireNonNull(newData);

        setTutorials(newData.getAllTutorials());
    }

    //// Tutorial-level operations

    /**
     * Returns true if a tutorial with the same identity as {@code tutorial} exists in TAble.
     */
    public boolean hasTutorial(Tutorial tutorial) {
        requireNonNull(tutorial);
        return tutorials.contains(tutorial);
    }

    /**
     * Adds a tutorial to TAble.
     * The tutorial must not already exist in TAble.
     */
    public void addTutorial(Tutorial tutorial) {
        tutorials.add(tutorial);
    }


    /**
     * Removes {@code key} from this {@code TutorialTAble}.
     * {@code key} must exist in the Tutorial TAble.
     */
    public void removeTutorial(Tutorial key) {
        tutorials.remove(key);
    }


    /**
     * Returns {@code tutorial} from {@code TAble}.
     * {@code tutorial} must exist in the address book.
     */
    public Tutorial getTutorial(int index) {
        return tutorials.getTutorial(index);
    }

    //// util methods

    @Override
    public String toString() {
        return tutorials.asUnmodifiableObservableList().size() + " consults";
        // TODO: refine later
    }

    @Override
    public ObservableList<Tutorial> getAllTutorials() {
        return tutorials.asUnmodifiableObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TutorialTAble // instanceof handles nulls
                && tutorials.equals(((TutorialTAble) other).tutorials));
    }

    @Override
    public int hashCode() {
        return tutorials.hashCode();
    }
}