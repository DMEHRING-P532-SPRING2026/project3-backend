package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.access.CommandLogAccess;
import iu.devinmehringer.project3.model.log.CommandLogEntry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/command-log")
public class CommandLogController {

    private final CommandLogAccess commandLogAccess;

    public CommandLogController(CommandLogAccess commandLogAccess) {
        this.commandLogAccess = commandLogAccess;
    }

    @GetMapping
    public ResponseEntity<List<CommandLogEntry>> getCommandLog() {
        return ResponseEntity.ok(commandLogAccess.getAll());
    }
}