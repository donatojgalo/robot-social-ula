/*****************
  Definiciones. Se colocan las cabeceras, variables y expresiones regulares
********************/

%{
  #include <stdio.h>
  #include <stdlib.h>
  #include "sintactico.tab.h"
  int linea=0;
%}
/* Creamos todas las expresiones regulares */

SALUDO	("hola"|"ey"|"estas"|"como"|"epale"|"andas"|"que paso"|"que hubo"|"hablame"|"ejele"|"estás"|"cómo"|"qué pasó"|"qué hubo")+

CONOCIDO ("donato"|"javier"|"fabio"|"jesus"|"marco"|"eladio"|"rafael"|"nadia"|"dulce")

GROSERIA ("coño"|"joda"|"mierda"|"marico")

DESPEDIDA ("chao"|"nos vemos"|"me despido"|"me voy"|"hasta luego"|"quedamos asi"|"bueno pues"|"bye")+

RISA ("ja"|"je"|"ji"|"jo"|"ju")+

QUEJA ("no puede ser"|"vertale"|"conchale"|"chintrolas"|"ñelda")+

ESPACIO (" ")

SALUDANDO ({SALUDO}{ESPACIO}{SALUDO}{ESPACIO}{0,1})+

DESPIDIENDO ({DESPEDIDA}{ESPACIO}{DESPEDIDA}{ESPACIO}{0,1})+

INAPROPIADO ({GROSERIA}{ESPACIO}{GROSERIA}{ESPACIO}{0,1})+

QUEJANDO ({QUEJA}{ESPACIO}{QUEJA}{ESPACIO})+

%%
{SALUDANDO}	{printf("saludando\n");}

{CONOCIDO}	{printf("conocido\n");}

{DESPIDIENDO}	{printf("despidiendo\n");}

{INAPROPIADO}	{printf("inapropiado\n");}

{QUEJANDO}	{printf("quejando\n");}

{RISA}		{printf("riendo\n");}

[\t\r\f] 	{}

.		/*{printf("Error lexico en linea %d\n",linea);}*/
%%
