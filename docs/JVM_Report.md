# JVM Report

## Environment used for development/testing
```
openjdk version "21.0.11" 2026-04-21
OpenJDK Runtime Environment (build 21.0.11+10-1-24.04.2-Ubuntu)
OpenJDK 64-Bit Server VM (build 21.0.11+10-1-24.04.2-Ubuntu, mixed mode, sharing)
```
(Any JDK 17+ will work; the code uses no version-specific APIs beyond `java.time` and streams, both available since Java 8.)

## Memory Model Notes
- All entities (`Person`, `Doctor`, `Patient`, `Appointment`, `Bill`) implement
  `Serializable`, so instances can be written to disk or across a stream if the
  project is later extended with file-based persistence beyond CSV.
- `DataStore<T>` holds objects in a `LinkedHashMap` on the heap for the
  lifetime of the JVM process; nothing is persisted between runs unless
  `CSVUtil` is wired in.

## Garbage Collection Considerations
This is a small, short-lived console application — object churn is minimal
(a handful of `Doctor`/`Patient`/`Appointment`/`Bill` objects per run), so no
GC tuning is necessary. Default JVM GC settings (e.g. G1GC on modern JDKs)
are more than sufficient.

## Class Loading
Standard classloading applies — all classes live under the
`com.airtribe.meditrack` package hierarchy and are loaded by the application
classloader when `Main` (or `TestRunner`) starts.

## Runtime Data Areas
Objects such as doctors, patients, appointments, and bills are allocated on the
heap. Each running method has a stack frame containing parameters, local
variables, and the operand stack. Class metadata and static fields are managed
in the JVM's method area (implemented through Metaspace in modern HotSpot JVMs).
The program counter register tracks the current instruction for each thread.

## Execution Engine and JIT
The JVM execution engine initially interprets bytecode. HotSpot monitors frequently
executed code and the JIT compiler compiles hot paths to native machine code, which
reduces repeated interpretation overhead. This is why the same compiled bytecode
can run on different operating systems with a compatible JVM: Java follows the
"Write Once, Run Anywhere" model.

## Concurrency
`IdGenerator` uses `java.util.concurrent.atomic.AtomicInteger` for its ID
counters. This isn't strictly required for the current single-threaded CLI,
but ensures correctness if the system is later adapted to a multi-threaded
context (e.g., handling concurrent booking requests in a server).

## Performance
No performance bottlenecks are expected at this scale — all lookups go
through a `HashMap`-backed `DataStore`, giving O(1) average-case get/add/remove,
and `filter`/`search` operations are simple O(n) linear scans over in-memory
collections, appropriate for a small clinic's dataset.
