package seedu.address.model.event.consult;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Iterator;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.event.consult.exceptions.ConsultNotFoundException;
import seedu.address.model.event.consult.exceptions.DuplicateConsultException;



/**
 * A list of consults that enforces uniqueness between its elements and does not allow nulls.
 * A consult is considered unique by comparing using {@code Consult#equals(Consult)}. As such, adding, updating and
 * removal of consult uses Consult#equals(Consult) so as to ensure that the consult being added, updated or removed is
 * unique in terms of identity in the UniqueConsultList.
 *
 * Supports a minimal set of list operations.
 *
 * @see Consult#equals(Object)
 */
public class UniqueConsultList implements Iterable<Consult> {

    private final ObservableList<Consult> internalList = FXCollections.observableArrayList();
    private final ObservableList<Consult> internalUnmodifiableList =
            FXCollections.unmodifiableObservableList(internalList);

    /**
     * Returns true if the list contains an equivalent consult as the given argument.
     */
    public boolean contains(Consult toCheck) {
        requireNonNull(toCheck);
        return internalList.stream().anyMatch(toCheck::equals);
    }

    /**
     * Returns true if the list contains another consult which timing clashes with the argument.
     */
    public boolean hasSameDateTiming(Consult consult) {
        requireNonNull(consult);
        return internalList.stream().anyMatch(consult::timeClash);
    }

    /**
     * Adds a consult to the list.
     * The consult must not already exist in the list.
     */
    public void add(Consult toAdd) {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateConsultException();
        }
        internalList.add(toAdd);
    }

    /**
     * Replaces the consult {@code target} in the list with {@code editedConsult}.
     * {@code target} must exist in the list.
     * The person identity of {@code editedConsult} must not be the same as another existing consult in the list.
     */
    public void setConsult(Consult target, Consult editedConsult) {
        requireAllNonNull(target, editedConsult);

        int index = internalList.indexOf(target);
        if (index == -1) {
            throw new ConsultNotFoundException();
        }

        if (!target.equals(editedConsult) && contains(editedConsult)) {
            throw new DuplicateConsultException();
        }

        internalList.set(index, editedConsult);
    }

    /**
     * Removes the equivalent consult from the list.
     * The consult must exist in the list.
     */
    public void remove(Consult toRemove) {
        requireNonNull(toRemove);
        if (!internalList.remove(toRemove)) {
            throw new ConsultNotFoundException();
        }
    }

    public void setConsults(UniqueConsultList replacement) {
        requireNonNull(replacement);
        internalList.setAll(replacement.internalList);
    }

    /**
     * Replaces the contents of this list with {@code consults}.
     * {@code consults} must not contain duplicate consults.
     */
    public void setConsults(List<Consult> consults) {
        requireAllNonNull(consults);
        if (!consultsAreUnique(consults)) {
            throw new DuplicateConsultException();
        }

        internalList.setAll(consults);
    }

    /**
     * Returns the consult at the {@code index}.
     * @param index Index of the student.
     * @return Consult at the index.
     */
    public Consult getConsult(int index) {
        return internalList.get(index);
    }

    /**
     * Removes all {@code consults} from this {@code ConsultTAble}.
     */
    public void clearConsults() {
        internalList.remove(0, internalList.size());
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Consult> asUnmodifiableObservableList() {
        return internalUnmodifiableList;
    }

    @Override
    public Iterator<Consult> iterator() {
        return internalList.iterator();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UniqueConsultList // instanceof handles nulls
                && internalList.equals(((UniqueConsultList) other).internalList));
    }

    @Override
    public int hashCode() {
        return internalList.hashCode();
    }

    /**
     * Returns true if {@code consults} contains only unique consults.
     */
    private boolean consultsAreUnique(List<Consult> consults) {
        for (int i = 0; i < consults.size() - 1; i++) {
            for (int j = i + 1; j < consults.size(); j++) {
                if (consults.get(i).equals(consults.get(j))) {
                    return false;
                }
            }
        }
        return true;
    }

    public ObservableList<Consult> getAllConsults() {
        return this.internalList;
    }
}
