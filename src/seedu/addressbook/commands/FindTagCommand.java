package seedu.addressbook.commands;

import seedu.addressbook.data.person.ReadOnlyPerson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Finds and lists all persons in address book whose tags match any of the argument keywords.
 * Keyword matching is case sensitive.
 */
public class FindTagCommand extends Command {

    public static final String COMMAND_WORD = "findtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all persons whose tags match any of "
            + "the specified keywords (case-sensitive) and displays them as a list with index numbers. \n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " criminal friend";

    private final Set<String> keywords;

    public FindTagCommand(Set<String> keywords) { this.keywords = keywords; }

    /**
     * Returns a copy of keywords in this command.
     */
    public Set<String> getKeywords() { return new HashSet<>(keywords); }

    @Override
    public CommandResult execute() {
        final List<ReadOnlyPerson> personsFound = getPersonsWithTagContainingAnyKeyword(keywords);
        return new CommandResult(getMessageForPersonListShownSummary(personsFound), personsFound);
    }

    /**
     * Retrieves all persons in the address book whose tags match some of the specified keywords.
     * @param keywords for searching
     * @return list of persons found
     */
    private List<ReadOnlyPerson> getPersonsWithTagContainingAnyKeyword(Set<String> keywords) {
        final List<ReadOnlyPerson> matchedPersons = new ArrayList<>();
        for (ReadOnlyPerson person : addressBook.getAllPersons()) {
            final Set<String> wordsInTag = person.getTags().stream().map(x -> x.getTrimmedName())
                    .collect(Collectors.toSet());
            if (!Collections.disjoint(wordsInTag, keywords)) {
                matchedPersons.add(person);
            }
        }
        return matchedPersons;
    }
}