package iu.devinmehringer.project3.controller;

import iu.devinmehringer.project3.access.CommandLogAccess;
import iu.devinmehringer.project3.manager.ObservationManager;
import iu.devinmehringer.project3.manager.UserManager;
import iu.devinmehringer.project3.model.log.CommandLogEntry;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/command-log")
public class CommandLogController extends BaseController{

    private final CommandLogAccess commandLogAccess;
    private final ObservationManager observationManager;

    public CommandLogController(CommandLogAccess commandLogAccess, ObservationManager observationManager,
                                UserManager userManager) {
        super(userManager);
        this.commandLogAccess = commandLogAccess;
        this.observationManager = observationManager;
    }

    @GetMapping
    public ResponseEntity<List<CommandLogEntry>> getCommandLog() {
        return ResponseEntity.ok(commandLogAccess.getAll());
    }

    @PostMapping("/{id}/undo")
    public ResponseEntity<Void> undo(@PathVariable Long id, HttpServletRequest httpRequest) {
        observationManager.undo(id, resolveUser(httpRequest));
        return ResponseEntity.ok().build();
    }
}