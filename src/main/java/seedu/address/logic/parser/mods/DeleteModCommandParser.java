package seedu.address.logic.parser.mods;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_REPEATED_PREFIXES;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULE_CODE;

import java.util.stream.Stream;

import seedu.address.logic.commands.mods.DeleteModCommand;
import seedu.address.logic.parser.ArgumentMultimap;
import seedu.address.logic.parser.ArgumentTokenizer;
import seedu.address.logic.parser.Parser;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.logic.parser.Prefix;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.mod.ModCode;

/**
 * Parses input arguments and creates a new DeleteModCommand object
 */
public class DeleteModCommandParser implements Parser<DeleteModCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteModCommand
     * and returns a DeleteModCommand object for execution.
     * @throws ParseException if the user input does not conform to the expected format
     */
    public DeleteModCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_MODULE_CODE);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULE_CODE)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteModCommand.MESSAGE_USAGE));
        }

        if (!arePrefixesUnique(argMultimap, PREFIX_MODULE_CODE)) {
            throw new ParseException(String.format(MESSAGE_REPEATED_PREFIXES,
                    DeleteModCommand.MESSAGE_USAGE));
        }

        ModCode modCode = ParserUtil.parseModCode(argMultimap.getValue(PREFIX_MODULE_CODE).get());
        return new DeleteModCommand(modCode);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if at least one of the prefixes is repeated in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesUnique(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).filter(argumentMultimap::isRepeated).count() == 0;
    }
}
