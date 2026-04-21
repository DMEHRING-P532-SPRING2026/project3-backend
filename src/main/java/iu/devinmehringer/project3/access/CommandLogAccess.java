package iu.devinmehringer.project3.access;

import iu.devinmehringer.project3.access.repository.CommandLogEntryRepository;
import iu.devinmehringer.project3.model.log.CommandLogEntry;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommandLogAccess {
    private final CommandLogEntryRepository commandLogEntryRepository;

    public CommandLogAccess(CommandLogEntryRepository commandLogEntryRepository) {
        this.commandLogEntryRepository = commandLogEntryRepository;
    }

    public CommandLogEntry save(CommandLogEntry commandLogEntry) {
        return this.commandLogEntryRepository.save(commandLogEntry);
    }

    public List<CommandLogEntry> getAll() {
        return commandLogEntryRepository.findAll();
    }

    public Optional<CommandLogEntry> findById(Long id) {
        return commandLogEntryRepository.findById(id);
    }
}
