# Design Decisions

## Package layout deviation
The assignment brief lists a package named `interface`. This is a reserved Java
keyword and cannot legally be used as a package/identifier name, so it has been
renamed to `interfaces` (a very common Java convention). `Searchable.java` and
`Payable.java` live there unchanged in purpose.

## OOP & SOLID
- **Abstraction/Inheritance**: `Person` is an abstract base class; `Doctor` and
  `Patient` extend it and implement `getRole()`. This avoids duplicated fields
  and lets services and utilities work with a common `Person` type where useful.
- **Single Responsibility**: Entities hold data + basic invariants only. All
  business rules (validation, ID generation, clash detection) live in `service`
  and `util` classes.
- **Open/Closed**: `Searchable<T>` and `Payable` are interfaces the services
  implement, so new searchable/payable types can be added without modifying
  existing consumers.
- **Dependency direction**: `AppointmentService` depends on `DoctorService` and
  `PatientService` through constructor injection rather than instantiating them
  internally, making it easier to test/swap implementations.

## Generics & Collections
`DataStore<T>` is a generic, reusable in-memory store used by all three
services (`Doctor`, `Patient`, `Appointment`/`Bill`), keyed by an ID extracted
via a `Function<T, String>`. This avoids writing near-identical CRUD code three
times and demonstrates generics + functional interfaces + streams
(`filter`, `Collectors.toList()`).

## Exception Handling
Two custom **checked** exceptions are used deliberately (not unchecked):
- `InvalidDataException` — thrown by `Validator` and services when input fails
  business rules (empty name, bad age, malformed contact number, double-booking).
- `AppointmentNotFoundException` — thrown when an operation references an
  appointment ID that doesn't exist.

Checked exceptions were chosen because these are recoverable, expected failure
modes the caller (the CLI menu) should explicitly handle — not programming bugs.

## Immutability
`BillSummary` is a `final` class with `final` fields and no setters, used to
represent a read-only snapshot of billing info that can be safely shared/logged
without risk of accidental mutation.

## Concurrency (bonus)
`IdGenerator` uses `AtomicInteger` counters instead of plain `int` fields, so ID
generation would remain correct even if the system were extended to a
multi-threaded context (e.g. a future REST API layer).

## Persistence
`CSVUtil` provides read/write/append helpers using try-with-resources. `DataPersistence`
writes doctors, patients, and appointments on exit and restores them when `Main` is
started with `--loadData`. `SerializationUtil` demonstrates object serialization as
an additional persistence option.

## Patterns and concurrency
`BillFactory` centralizes bill creation, while `BillingStrategy` supports standard
tax and insurance billing. `AppointmentObserver` sends console notifications when
appointments are created. `AppConfig` is an eager Singleton, `ReportTemplate` is a
Template Method example, and `ReminderScheduler` uses a daemon `TimerTask`. The
ID generator uses `AtomicInteger` for thread-safe counters.

## Testing approach
Per the assignment, `TestRunner` uses a hand-rolled assertion helper rather than
JUnit, covering both the happy paths (add doctor/patient, book, cancel, bill)
and failure paths (invalid age, appointment not found) to demonstrate that
exceptions are actually thrown and caught correctly.
