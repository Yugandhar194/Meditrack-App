# MediTrack — Clinic & Appointment Management System

A console-based, modular Clinic & Appointment Management System built in Core Java,
demonstrating OOP design, SOLID principles, generics, collections, exceptions, and I/O.

## Features
- Manage doctors and patients (add, list, search)
- Full doctor and patient CRUD through the console menu
- Book, cancel, and track appointments (clash detection included)
- Generate and track bills per appointment
- Input validation with custom checked exceptions
- Manual test suite (no external frameworks)
- Deep cloning, enums, equality/hashCode, comparators, iterators, and Java serialization
- Tax-aware billing with Factory and Strategy patterns
- CSV persistence, `--loadData`, Singleton configuration, Observer notifications, and reminders
- Stream analytics for average consultation fees and appointments per doctor

## Project Structure
```
src/main/java/com/airtribe/meditrack/
├── Main.java                     # Entry point, menu-driven CLI
├── constants/Constants.java
├── entity/
│   ├── Person.java                # abstract base class
│   ├── Doctor.java
│   ├── Patient.java
│   ├── Appointment.java
│   ├── Bill.java
│   └── BillSummary.java           # immutable
├── service/
│   ├── DoctorService.java
│   ├── PatientService.java
│   └── AppointmentService.java
├── util/
│   ├── Validator.java
│   ├── DateUtil.java
│   ├── CSVUtil.java
│   ├── IdGenerator.java
│   ├── DataStore.java              # generic DataStore<T>
│   └── AIHelper.java                # optional
├── exception/
│   ├── AppointmentNotFoundException.java
│   └── InvalidDataException.java
├── interfaces/                     # NOTE: renamed from "interface" — reserved keyword
│   ├── Searchable.java
│   └── Payable.java
└── test/
    └── TestRunner.java             # manual tests (no JUnit)
docs/
├── JVM_Report.md
├── Setup_Instructions.md
└── Design_Decisions.md
```

## Build & Run
See `docs/Setup_Instructions.md` for full details. Quick version:
```bash
javac -d out $(find src -name "*.java")
java -cp out com.airtribe.meditrack.Main
```

To restore CSV data from the previous run:
```bash
java -cp out com.airtribe.meditrack.Main --loadData
```

## Run Tests
```bash
java -cp out com.airtribe.meditrack.test.TestRunner
```

## Sample Run Output
```
===== MediTrack Menu =====
1. Add Doctor
2. Add Patient
3. Book Appointment
4. Cancel Appointment
5. Generate Bill
6. List Doctors
7. List Patients
8. List Appointments
9. Search Doctors
10. Search Patients
11. Update Doctor
12. Delete Doctor
13. Update Patient
14. Delete Patient
0. Exit
Enter choice: 1
Name: Dr. Sample
Age: 35
Gender: Male
Contact (10 digits): 9876543210
Specialization: General Physician
Doctor added: [DOC001] Dr. Sample | Age: 35 | Gender: Male | Contact: 9876543210 | Specialization: General Physician | Slots: []
```

Test run:
```
[PASS] Add valid doctor
[PASS] Add valid patient
[PASS] Book valid appointment
[PASS] Reject invalid age
[PASS] Cancel appointment
[PASS] Generate bill
[PASS] Reject cancel of missing appointment

=== Test Summary ===
Passed: 14 | Failed: 0
```
