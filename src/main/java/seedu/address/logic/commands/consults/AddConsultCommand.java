package seedu.address.logic.commands.consults;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_CONSULT_TIMING_CLASH;
import static seedu.address.logic.parser.CliSyntax.*;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.event.consult.Consult;

/**
 * Adds a consult into TAble.
 */
public class AddConsultCommand extends Command {

    public static final String COMMAND_WORD = "addConsult";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a consultation to TAble. "
            + "Parameters: "
            + PREFIX_CONSULT_BEGIN_DATE_TIME + "EVENT_BEGIN_DATE_TIME "
            + PREFIX_CONSULT_END_DATE_TIME + "EVENT_END_DATE_TIME "
            + PREFIX_PLACE + "PLACE "
            + PREFIX_STUDENT + "STUDENT "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_CONSULT_BEGIN_DATE_TIME + "2020-03-03 10:00 "
            + PREFIX_CONSULT_END_DATE_TIME + "2020-03-03 12:00 "
            + PREFIX_PLACE + "Outside SR1 "
            + PREFIX_STUDENT + "A0123456P";

    public static final String MESSAGE_SUCCESS = "New consultation added: %1$s";
    public static final String MESSAGE_DUPLICATE_CONSULT = "There is already a consultation at that timing.";

    private final Consult toAdd;

    /**
     * Creates an AddConsultCommand to add the specified {@code Consult}
     */
    public AddConsultCommand(Consult consult) {
        requireNonNull(consult);
        toAdd = consult;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.hasConsult(toAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_CONSULT);
        }

        if (model.hasSameTiming(toAdd)) {
            throw new CommandException(MESSAGE_CONSULT_TIMING_CLASH);
        }

        model.addConsult(toAdd);
        return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddConsultCommand // instanceof handles nulls
                && toAdd.equals(((AddConsultCommand) other).toAdd));
    }
}

