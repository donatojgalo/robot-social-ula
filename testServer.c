#define __GPROLOG_FOREIGN_STRICT__
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <arpa/inet.h>
#include <stdio.h>
#include <stdlib.h>
#include <unistd.h>
#include <errno.h>
#include <string.h>
#include <signal.h>
#include <time.h>
#include "gprolog.h"

#define PORT 5000
#define STR_SIZE 1024

#define NORMAL "nor:"
#define ENTHUSIASM "ent:"
#define JOY "joy:"
#define SURPRISE "sur:"
#define SADNESS "sad:"
#define ANGER "and:"
#define FEAR "fea:"

int ctl;
int sock, connected;
char send_data[STR_SIZE];

void signal_handler(int);
void close_connection();
void separate_words();

int main(int argc, char *argv[]) {
        
    int ale;
	char *resp;
	int adivina = 0;
	int func;
	char str[100];
    char adivinando[100];
    char respose[STR_SIZE];
	char *sol[100];
	int i, nb_sol = 0;
	PlBool res;
    PlTerm arg[10];
	int bytes_recieved, true = 1;
	char recv_data[STR_SIZE];
	struct sockaddr_in server_addr, client_addr;
	int sin_size;    
    time_t tiempo = time(0);
    char output[128];
    struct tm *tlocal;
    int f = 0;
    
    FILE *pgr, *pgr2;
    char rec[STR_SIZE];
    int ic;

    Pl_Start_Prolog(argc, argv); 		
	signal(SIGINT, signal_handler);

	if ((sock = socket(AF_INET, SOCK_STREAM, 0)) == -1) {
		perror("ERROR: Impossible to create the socket.\n");
		exit(EXIT_FAILURE);
	}

	if (setsockopt(sock, SOL_SOCKET, SO_REUSEADDR, &true, sizeof(int)) == -1) {
		perror("ERROR: Can not allocate socket options.\n");
		exit(EXIT_FAILURE);
	}

	server_addr.sin_family = AF_INET;
	server_addr.sin_port = htons(PORT);
	server_addr.sin_addr.s_addr = INADDR_ANY;
	bzero(&(server_addr.sin_zero), 8);

	if (bind(sock, (struct sockaddr *)&server_addr, sizeof(struct sockaddr)) == -1) {
		perror("ERROR: Can not bind the socket.\n");
		exit(EXIT_FAILURE);
	}

	if (listen(sock, 5) == -1) {
		perror("ERROR: Not setting the socket listen connections.\n");
		exit(EXIT_FAILURE);
	}

	sin_size = sizeof(struct sockaddr_in);

	ctl = 1;
	while (ctl) {

		printf("\nTCP Server Waiting for client on port %d", PORT);
		fflush(stdout);

		connected = accept(sock, (struct sockaddr *)&client_addr, &sin_size);

		printf("\n I got a connection from (%s, %d)",
				inet_ntoa(client_addr.sin_addr), ntohs(client_addr.sin_port));

		while (ctl) {
			printf("\nWaiting for data...");
			fflush(stdout);

			bytes_recieved = recv(connected, recv_data, STR_SIZE, 0);

			recv_data[bytes_recieved - 1] = '\0';
            if (recv_data[0]=='H'){
                recv_data[0]='h';
            }
			printf("\nData recieved: -%s-", recv_data);
			fflush(stdout);
            
            razonamiento:
            Pl_Query_Begin(PL_TRUE);
     
            //Reconocedor
/*	        pgr = fopen("cons", "w");

	        fprintf(pgr, recv_data);
	        fclose(pgr);

	        system("./analizador < cons > resultado.txt");

	        pgr2 = fopen("resultado.txt", "r");
	        fscanf(pgr2,"%s",rec);
	        printf("SE LEYÓ ESTO: %s\n",rec);

	        if(strcmp(rec,"saludando")||strcmp(rec,"conocido")||strcmp(rec,"despidiendo")||strcmp(rec,"inapropiado")||strcmp(rec,"quejando")||strcmp(rec,"riendo")) 
		        strcpy(recv_data,rec);

	        fclose(pgr2);
	system("rm cons");
*/            //Fin reconocedor
            //printf("Data: -%s-",recv_data);
            //fflush(stdout);
    
			if (strcmp(recv_data, "salir") == 0) {//Cerrar conexion
				close_connection();
				break;
			} else if (adivina == 2){//Comienza juego de adivinanza
				func = Pl_Find_Atom("enunciado");
                arg[0] = Pl_Mk_Variable();
	      		arg[1] = Pl_Mk_String(adivinando);
	      		res = Pl_Query_Call(func, 2, arg);
                adivina = 4;
            }else if (adivina == 4){//Jugando la adivinanaza
				resp = NULL;			
				resp = strstr(recv_data,adivinando);
                if (resp != NULL){
					strcpy(respose, "joy:Muy bien, ya la adivinaste");
                    adivina = 3;
            	}else{		
                    func = Pl_Find_Atom("incorrecto");
					arg[0] = Pl_Mk_Variable();
	      			res = Pl_Query_Call(func, 1, arg);
			    }	
            }else{ 
				
 				resp = NULL;			
				resp = strstr(recv_data,"chiste");                
				if (resp != NULL){
					func = Pl_Find_Atom("chiste");
					arg[0] = Pl_Mk_Variable();
	      			res = Pl_Query_Call(func, 1, arg);
            	}else{		
					resp = strstr(recv_data,"adivinanza");
	                if(resp != NULL){
						func = Pl_Find_Atom("adivinanza");
						arg[0] = Pl_Mk_Variable();
		      			res = Pl_Query_Call(func, 1, arg);		
						adivina = 1;				
					}else{
                        resp = strstr(recv_data,"hora");
                        if(resp != NULL){
                            tlocal = localtime(&tiempo);
                            strftime(output,128,"nor:son %H horas y %M minutos",tlocal);
                            printf("%s\n",output);
                            f = 1;
                            goto enviar;
						}else{
                            resp = strstr(recv_data,"fecha");
                            if(resp != NULL){
                              tlocal = localtime(&tiempo);
                              strftime(output,128,"nor:hoy es %d del mes %m",tlocal);
                              printf("%s\n",output);
                              f = 1;
                              goto enviar;
                            }else{
                                resp = strstr(recv_data,"año");
                                if(resp != NULL){
                                    tlocal = localtime(&tiempo);
                                    strftime(output,128,"nor:estamos en el año 20%y",tlocal);
                                    printf("%s\n",output);
                                    f = 1;
                                   goto enviar;
                                }else{
 		    						func = Pl_Find_Atom("respuesta");
 		                         	resp = strrchr(recv_data,' ');
								    if(resp != NULL){                    
										strcpy(recv_data, resp+1);
 			                   	    }  
								    arg[0] = Pl_Mk_Variable();
			      				    arg[1] = Pl_Mk_String(recv_data);
			      				    res = Pl_Query_Call(func, 2, arg);
 		                       }
                            }
                        }
					}
            	}
            }

            	nb_sol = 0;        
      			while (res) {
	  				sol[nb_sol++] = Pl_Rd_String(arg[0]);
	  				res = Pl_Query_Next_Solution();
				}
                
				if (nb_sol==0){
					func = Pl_Find_Atom("error");
					arg[0] = Pl_Mk_Variable();
	      			res = Pl_Query_Call(func, 1, arg);
					while (res) {
		  				sol[nb_sol++] = Pl_Rd_String(arg[0]);
		  				res = Pl_Query_Next_Solution();
					}
				}
      		
				Pl_Query_End(PL_RECOVER);

   				for (i = 0; i < nb_sol; i++){
					printf("\n solucion %d: %s",i, sol[i]);
       				fflush(stdout);
				}

				if (nb_sol>1){
					srand(time(NULL));
            		ale = rand()%nb_sol;
            	   	printf("\nAleatorio: %d", ale);
	       			fflush(stdout);
            	}else{
					ale=0;
				}

				if (adivina == 1){ //Adivinanza seleccionada
                  strcpy(adivinando, sol[ale]);
                  adivina=2;                  
                  goto razonamiento;                  
                } else if (adivina == 3){ //Adivinanza encontrada 
                	strcpy(send_data, respose);
	                separate_words();
	                send(connected, send_data,strlen(send_data), 0);	
                    adivina = 0;
                }else{
					strcpy(send_data, sol[ale]);
	            	separate_words();
	            	send(connected, send_data,strlen(send_data), 0);
				}
                  
                enviar:
                    if (f == 1){
                    strcpy(send_data, output);
                    send(connected, send_data,strlen(send_data), 0);
                    f = 0;
                	}	
	        
/*			for(ic=0; ic<STR_SIZE; ic++)
			{
				rec[ic]='\0';
				recv_data[ic]='\0';
			}
			
			system("rm cons");
			system("rm resultado.txt");
*/
		}


	}	

	close(sock);

	printf("\nServer closed\n");
	fflush(stdout);

	return 0;
}

void signal_handler(int sig_num) {
	ctl = 0;
	close_connection();
	close(sock);
}

void close_connection() {
	close(connected);
	printf("\nComplete connection...");
	fflush(stdout);
}

void separate_words() {
    int j;
    for (j=0;j<strlen(send_data);j++){
		if (send_data[j] == '0'){        
			send_data[j] = ':';
		}else if (send_data[j] == '_') {
			send_data[j] = ' ';
        }else if (send_data[j] == '1'){
			send_data[j] = ',';
		}else if (send_data[j]=='2'){
			send_data[j] = '?';
		}
	}
}

