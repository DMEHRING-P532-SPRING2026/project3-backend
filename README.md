[![CI/CD](https://github.com/DMEHRING-P532-SPRING2026/project3-backend/actions/workflows/ci.yml/badge.svg)](https://github.com/DMEHRING-P532-SPRING2026/project3-backend/actions/workflows/ci.yml)

## Running

```bash
docker build -t tracker:latest .
docker run -p 8080:8080 -v ${PWD}/data:/app/data tracker:latest
```

**Live:** https://project3-backend-latest.onrender.com/

---

## Design Patterns

### Strategy
**Files:** `DiagnosisEngine`, `DiagnosisStrategy`, `SimpleConjunctiveStrategy`  
**Location:** `engine/`  
This design allows the rule evaluation algorithm to be swapped at runtime without hardcoding a specific strategy. `DiagnosisEngine` accepts any `DiagnosisStrategy` implementation, with `SimpleConjunctiveStrategy` as the Week 1 concrete strategy.

### Observer
**Files:** `AuditLogListener`, `ObservationEvent`, `RuleEvaluationListener`, `ObservationManager`  
**Location:** `log/`  
Whenever an observation is created or rejected, `ObservationManager` publishes an `ObservationEvent` via Spring's `ApplicationEventPublisher`. Registered listeners react independently — `AuditLogListener` appends to the audit log and `RuleEvaluationListener` re-evaluates diagnostic rules. New listeners can be added in Week 2 with zero changes to existing code.

### Factory
**Files:** `ObservationFactory`  
**Location:** `manager/`  
All validation and subtype discrimination for observations is centralised in the factory. Commands resolve database entities and pass them in — the factory validates compatibility (correct kind, allowed units, qualitative phenomenon type) and constructs the correct subtype. This keeps commands and managers free of observation-specific validation logic.

### Command
**Files:** `Command`, `CreateObservationCommand`, `CreatePatientCommand`, `CreatePhenomenonTypeCommand`, `CreateProtocolCommand`, `DeletePhenomenonTypeCommand`, `DeleteProtocolCommand`, `RejectObservationCommand`, `UpdatePhenomenonTypeCommand`, `UpdateProtocolCommand`  
**Location:** `command/`  
Every state-changing user action is wrapped in a command object. `CommandRunner` executes each command and writes a `CommandLogEntry` with the payload serialised as JSON — capturing enough information to support undo in Week 2 without any schema changes.
