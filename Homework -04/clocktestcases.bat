@echo off
@javac *.java
@java Clock                         >  testresults.txt
@java ClockSolver                   >> testresults.txt
@java ClockSolver  -1.0             >> testresults.txt
@java ClockSolver   0.0    -1.0     >> testresults.txt
@java ClockSolver  90.0     0.0     >> testresults.txt
@java ClockSolver 360.0    60.0     >> testresults.txt
@java ClockSolver  90.0  1801.0     >> testresults.txt
@java ClockSolver  abc              >> testresults.txt
@java ClockSolver  30.0    60.0  %1 >> testresults.txt
@java ClockSolver  30.0    10.0  %1 >> testresults.txt
@java ClockSolver  30.0     1.0  %1 >> testresults.txt
@java ClockSolver  75.23   60.0  %1 >> testresults.txt
@java ClockSolver  75.23    7.0  %1 >> testresults.txt
@java ClockSolver  75.23    1.3  %1 >> testresults.txt
@java ClockSolver  90.0     2.5  %1 >> testresults.txt
@java ClockSolver 180.0     1.8  %1 >> testresults.txt
@java ClockSolver  90.0  1800.0  %1 >> testresults.txt

