Librerias requeridas:

Jlayer
https://www.dropbox.com/s/jz9qspzbzlfksos/jlayer1.0.1.zip
FlacEncoder
https://www.dropbox.com/s/h0wcjvu1cjamdyk/javaFlacEncoder-0.3.1-all.tar.gz
Gprolog

Referencias:

http://www.vogella.com/articles/EclipseGit/article.html


-------------------------------- IMPORTANTE TENER EL EJECUTABLE "analizador"----------------
Instalar flex:	sudo apt-get install flex 

crear el analizador lexico con flex:	flex lexico.l
cc lex.yy.c -o analizador -lfl -lm