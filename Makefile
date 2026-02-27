SRC_DIR = src
BIN_DIR = bin

SOURCES = $(shell dir /s /b $(SRC_DIR)\*.java)

all: compile

compile:
	if not exist $(BIN_DIR) mkdir $(BIN_DIR)
	javac -d $(BIN_DIR) $(SOURCES)

run: compile
	java -cp $(BIN_DIR) Main

clean:
	if exist $(BIN_DIR) rmdir /s /q $(BIN_DIR)