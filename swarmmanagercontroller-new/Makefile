.PHONY: build

all: clean build

clean:
	./gradlew clean

test:
	./gradlew clean check

format:
	./gradlew ktlintFormat

build:
	./gradlew clean ktlintFormat build

run:
	./gradlew run --continuous