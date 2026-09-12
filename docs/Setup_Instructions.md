# Setup Instructions

## Prerequisites
- JDK 17 or later installed (`java -version` / `javac -version` to check)
- Git (for cloning/pushing to GitHub)
- A terminal (or VS Code with the Java Extension Pack)

## 1. Clone the repository
```bash
git clone https://github.com/<your-username>/MediTrack.git
cd MediTrack
```

## 2. Compile the project
From the project root:
```bash
javac -d out $(find src -name "*.java")
```
This compiles every `.java` file under `src/` and places the class files in `out/`.

> On Windows PowerShell, use:
> ```powershell
> Get-ChildItem -Recurse -Filter *.java src | ForEach-Object { $_.FullName } > sources.txt
> javac -d out "@sources.txt"
> ```

## 3. Run the application
```bash
java -cp out com.airtribe.meditrack.Main
```
Follow the on-screen menu to add doctors/patients, book appointments, and generate bills.

Data is saved to `data/doctors.csv`, `data/patients.csv`, and
`data/appointments.csv` when the application exits. Start with `--loadData` to
restore those files:
```bash
java -cp out com.airtribe.meditrack.Main --loadData
```

## 4. Run the manual test suite
```bash
java -cp out com.airtribe.meditrack.test.TestRunner
```
Expect all tests to print `[PASS]` and a final summary line.
The current suite contains 14 manual checks covering core CRUD, cloning, enums,
tax strategies, streams, Singleton configuration, and serialization.

## 5. (Optional) Generate JavaDoc
```bash
javadoc -d docs/javadoc -sourcepath src/main/java -subpackages com.airtribe.meditrack
```
Open `docs/javadoc/index.html` in a browser.

## Common Issues
| Problem | Fix |
|---|---|
| `javac: command not found` | Install a JDK and ensure it's on your PATH |
| `package com.airtribe.meditrack.X does not exist` | Make sure you compiled **all** files together, not one at a time |
| Date parsing error | Use the exact format `yyyy-MM-dd HH:mm`, e.g. `2026-09-01 10:00` |
