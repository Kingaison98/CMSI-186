
@echo "cleaning up for build....."
@del /Q *.class
@javac  *.java
@del testresults.txt

@echo "creating javadocs....."
@rmdir /Q /S docs
@mkdir docs
@copy BrobInt.java docs\.
@copy BrobIntTester.java docs\.
@copy Doofus.java docs\.
@copy Fibonacci.java docs\.
@copy Halver.java docs\.
@pushd docs
@javadoc -html4 *.java
@popd
@start "C:\Program Files (x86)\Mozilla Firefox\firefox.exe" .\docs\index.html

@echo "running test harnesses for evaluation....."
@java BrobIntTester >> testresults.txt
@java Fibonacci    23 >> testresults.txt
@java Fibonacci   100 >> testresults.txt
@java Fibonacci   250 >> testresults.txt
@java Fibonacci  1000 >> testresults.txt
@java Fibonacci 10000 >> testresults.txt

@echo
@echo ...........done..............
@echo


