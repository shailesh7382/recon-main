# recon-main

Given:

JDK 1.8 compilable
Compiler compliance level 1.8
No external jars except JUNIT
SingleThreaded(No Threadpools and worker thread) but lot of boilerplate code is written and can be easily wired in with any executorService.
Generics
90%+ code converage by tests using eclemma
JDK 1.8 features.(JodaTime copy)
Assumptions:

Recon bundle will contain only duplex comparison(comparison between two input sources only). Extension points exists for further implementation.
System will also identify Error and Warning level rows and will not die out on erroneous rows.
Problem statement and example does a row vs row comparison. It seems like row n in source 1 is compared to row n in source 2. But, it is assumed that if row match does not happen, line in source 1 should be compared to other lines in source 2 and should stop at first match.
Eg: a.txt b.txt a1; 11; 01-Jun-2016; 100.00 b1; 11; 01-Jun-2016; 101.00 a2; 12; 01-Jun-2016; 100.00 b2; 12; 01-Jun-2016; 102.00 a3; 11; 01-Jun-2016; 100.00 b3; 13; 01-Jun-2016; 102.00 a4; 11; 01-Jun-2016; 100.00 b4; 12; 02-Jun-2016; 100.00 a5; 11; 01-Jun-2016; 100.00 b5; 11; 01-Jun-2016; 100.00

Above should have exact match for a1b5 and weak match for a2b4 and rest of lines should be breaks.

-Amount column successfully processes "101.0", "101","0101" and identifies them as Weak matches -Amount column successfully processes "101.333" but identifies it as error row.Error is different from breaks as it denotes that system had an issue with reading it and that row does not participate in reconciliation.

Invalid dates like 30 Feb are taken care off by setting Strict Resolver
System does space trimming.
How to run:

User should have all the below four folders as source folders. src/main/java src/test/java src/main/resources src/test/resources -- contains test txt files and is needed in build path

Launcher.java defines the symbolic run for five different flavours of input files. Report is currently dumped on the console.
