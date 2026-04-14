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
**Reasoning:** This Design allows for us to switch out the strategy we use when executing rules on observations at runtime without having to hardcode out strategy. This lives in the engine folder.

### Observer
**Files:** `AuditLogListener`, `ObservationEvent`, `RuleEvaluationListener`, `ObservationManager`  
**Reasoning:** This allowed us to add listeners whenever a observation is added to a patient to log and rerun our rules, and in the future we can easily add more listeners. This lives in the log folder.

### Factory
**Files:** `ObservationFactory`  
**Reasoning:** Instead of having to do all of the validation and figuring out what type of observation it was in our manager/commands we delegate this to the factory which will do this all for us such that we may reuse it. This lives in the manager folder for now.

### Command
**Files:** `Command`, `CreateObservationCommand`, `CreatePatientCommand`, `CreatePhenomenonTypeCommand`, `CreateProtocolCommand`, `DeletePhenomenonTypeCommand`, `DeleteProtocolCommand`, `RejectObservationCommand`, `UpdatePhenomenonTypeCommand`, `UpdateProtocolCommand`  
**Reasoning:** By putting these all in commands it makes it easy to log and then eventually add more features since all actions are recorded in command objects. This lives in the command folder.
