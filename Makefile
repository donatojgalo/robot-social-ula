CXX := gcc
CC := cc
FLX := flex
CXXFLAGS := -Wall -o
INC := -I.
LIB := -lfl -lm
SOURCES := testServer.c
EXECUTABLE := testServer

all: lex.yy.c analizador

testServer: testServer.c
	$(CXX) $^ $(CXXFLAGS) $@

lex.yy.c: lexico.l
	$(FLX) $^

analizador: lex.yy.c
	$(CC) $^ $(CXXFLAGS) $@ $(LIB)


clean:
	rm -fr *~ *.o $(EXECUTABLE)

