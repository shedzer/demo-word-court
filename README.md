# demo-word-count
This is a demo project to show the following logic:
1. Read from a text file.
2. Calculate the number of occurrence of a word in the file.
3. Print the results in word:number of occurrence sorted by highest number of word occurrence.

## Usage
You will require JDK 21, Maven and Docker Desktop installed.

## How to Run

Navigate your terminal to the main folder of the project.

To test, run the below
```bash
mvn clean test
```

To compile, run the below
```bash
mvn clean package
```

To run, navigate to the target folder, run the below
```bash
java -jar demo-word-count-1.0.jar {YOUR_FILE_PATH}
```
You may use the sample file in the test resources or any valid text files as {YOUR_FILE_PATH}.

### Docker
To create OCI and push to local Docker, run the below command
```bash
mvn compile jib:dockerBuild 
```
To run the container, use the below command replacing the variable
```bash
docker run -v {HOST_PATH}:app/file demo-word-count "/app/file/{FILE}"
```
Where:
<br> {HOST_PATH} = path in your OS where the input file exists
<br> {FILE} = the file name

Sample command:
```bash
docker run -v /Users/danny:/app/file demo-word-count "app/file/input.txt"
```
