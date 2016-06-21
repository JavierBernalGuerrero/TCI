/**
 * En primer lugar, el programa deberá generar la portadora, que será un fichero de texto creado 
 * con caracteres producidos de forma aleatoria. Se podrán especificar varios parámetros como el 
 * número de líneas o páginas que se quieren generar, si se incluyen letras únicamente o bien 
 * letras y números, frecuencia aproximada de espacios, saltos de línea y signos de puntuación, 
 * etc. Se obtienen portadoras de calidad - con más probabilidad de contener mensajes - cuando la 
 * proporción de las letras que contienen éstas coincide con las del idioma español.
 * 
 * Una vez creado el fichero de texto, el programa deberá ser capaz de analizarlo, al menos de una 
 * forma superficial, de modo que nos resulte fácil encontrar los posibles mensajes. Para realizar 
 * este análisis, el programa irá comparando una a una todas las palabras que aparecen en el texto 
 * con las que hay en el diccionario de español. En caso de coincidencia, la palabra o palabras 
 * encontradas deben aparecer resaltadas con un color diferente al resto del texto o bien en vídeo 
 * inverso. Opcionalmente se pueden mostrar estadísticas con el número total de palabras 
 * encontradas, distancia media entre esas palabras, palabras reales por cada mil palabras 
 * generadas, etc.
 *  
 * @author Javier Bernal Guerrero
 */

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Tci {
  public static void main(String[] args) {
    Scanner teclado = new Scanner(System.in);
    try {
	  if (args.length != 1) {
        System.out.println("El numero de parametros no es valido.");
        System.exit(-1);
      }
      
      String rutaPortadora = args[0];
      String rutaDiccionario = "resources/diccionario.txt";
      String rutaPortadoraModificada;
      int t = rutaPortadora.length();                       //Recoge el tamaño del argumento.
      String rutaPortadoraConExtension = rutaPortadora.substring(0, t - 4); //Recoge todo el nombre.
      int tamanoPalabra = 0;
      
      if (rutaPortadora.charAt(t - 4) == '.') {
        rutaPortadoraModificada = rutaPortadoraConExtension + ".html"; // Nombre SI tiene extension
        
      } else {
        rutaPortadoraModificada = rutaPortadora + ".html"; // Nombre si NO tiene extension
        
      }
      
      borraFichero(rutaPortadoraModificada);
      
      BufferedWriter bw = new BufferedWriter(new FileWriter(rutaPortadoraModificada, true));
      
      //BufferedReader brPortadora = new BufferedReader(new FileReader(rutaPortadora));
      BufferedReader brPortadora;
      
      
      int nParrafos = 0;
      String linea;
      System.out.println("--Generar el archivo de la señal portadora del mensaje--");
      while (nParrafos > 40 || nParrafos < 1) {

        System.out.print("Introduce el numero de parrafos que deseas (Minimo: 1 - Maximo: 40): ");
        nParrafos = teclado.nextInt();
        
        if (nParrafos > 40 || nParrafos < 1) {
          System.out.println("ERROR. El valor introducido no es valido, "
                                                    + "por favor vuelva a intentarlo.\n");
        }
      } // Fin de bucle while para recoger datos. (Numero de parrafos)
      
      while (tamanoPalabra < 3) {
		  
		System.out.print("Introduce el tamaño minimo para filtrar las palabras (tiene que ser mayor que 2): ");
        tamanoPalabra = teclado.nextInt();
		  
	    if (tamanoPalabra < 3) {
          System.out.println("ERROR. El valor introducido no es valido, "
                                                    + "por favor vuelva a intentarlo.\n");
        }
	  } // Fin de bucle while para recoger datos. (Tamaño minimo de letras)
      
      generaPortadora(rutaPortadora, nParrafos); // Genera el archivo portador con el mensaje
      
      generaCabecera(bw); // Genera la cabecera para procesar el archivo portador
      
      brPortadora = new BufferedReader(new FileReader(rutaPortadora));
      linea = brPortadora.readLine();
      
      System.out.println("--Procesar el archivo con la señal portadora--");
      System.out.println("Analizando portadora...");
      
      while (linea != null) {
        
        generaPoratadoraModificada (rutaDiccionario, bw, linea, tamanoPalabra); // Procesa todo el mensaje y 
                                                                 //resalta las palabras encontradas.
        
        linea = brPortadora.readLine();
      }
      
      generaPiePagina(bw); // Genera el pie para cerrar el archivo modificado.
      
      System.out.println("Fin del programa.");
      
      brPortadora.close();
      bw.close();
      
      Runtime.getRuntime().exec("firefox " + rutaPortadoraModificada); 
      
    } catch (FileNotFoundException e) { 
      System.out.println("No se encuentra el fichero.");
    
    } catch (IOException ioe) {
      System.out.println("No se ha podido escribir en el fichero.");
    
    }
  }
  
  private static void generaPortadora (String rutaEscritura, int tamano) throws IOException {
    
    BufferedWriter bw = new BufferedWriter(new FileWriter(rutaEscritura));
    
    int numeroAleatorio;
    int contadorLetras = 0;
    char letra = ' ';
    StringBuilder mensaje = new StringBuilder();    
    
    while (tamano != 0) {
        numeroAleatorio = (int)(Math.random() * 140); // CONTROLA LA PROPORCION, CUANTO MAYOR SEA DE 100 MAS SIMBOLOS GENERA.
        
        if (numeroAleatorio < 46) { // VOCALES
          if (numeroAleatorio < 14) {
            letra = 'e';
          } else {
            if (numeroAleatorio < 30) {
              letra = 'a';
            } else {
              if (numeroAleatorio < 38) {
                letra = 'o';
              } else {
                if (numeroAleatorio < 43) {
                  letra = 'i';
                } else {
                  letra = 'u';
                }
              }
            }
          }
          
        } else { // CONSONANTE
          if (numeroAleatorio < 109) {
            if (numeroAleatorio < 48) {
              letra = 'b';
            } else {
              if (numeroAleatorio < 53) {
                letra = 'c';
              } else {
                if (numeroAleatorio < 59) {
                  letra = 'd';
                } else {
                  if (numeroAleatorio < 60) {
                    letra = 'f';
                  } else {
                    if (numeroAleatorio < 61) {
                      letra = 'g';
                    } else {
                      if (numeroAleatorio < 62) {
                        letra = 'h';
                      } else {
                        if (numeroAleatorio < 63) {
                          letra = 'j';
                        } else {
                          if (numeroAleatorio < 64) {
                            letra = 'k';
                          } else {
                            if (numeroAleatorio < 69) {
                              letra = 'l';
                            } else {
                              if (numeroAleatorio < 72) {
                                letra = 'm';
                              } else {
                                if (numeroAleatorio < 79) {
                                  letra = 'n';
                                } else {
                                  if (numeroAleatorio < 80) {
                                    letra = 'ñ';
                                  } else {
                                    if (numeroAleatorio < 83) {
                                      letra = 'p';
                                    } else {
                                      if (numeroAleatorio < 84) {
                                        letra = 'q';
                                      } else {
                                        if (numeroAleatorio < 91) {
                                          letra = 'r';
                                        } else {
                                          if (numeroAleatorio < 99) {
                                            letra = 's';
                                          } else {
                                            if (numeroAleatorio < 104) {
                                              letra = 't';
                                            } else {
                                              if (numeroAleatorio < 105) {
                                                letra = 'v';
                                              } else {
                                                if (numeroAleatorio < 106) {
                                                  letra = 'w';
                                                } else {
                                                  if (numeroAleatorio < 107) {
                                                    letra = 'x';
                                                  } else {
                                                    if (numeroAleatorio < 108) {
                                                      letra = 'y';
                                                    } else {
                                                      letra = 'z';
                                                    }
                                                  }
                                                }
                                              }
                                            }
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
            
          } else { // SIMBOLOS
            numeroAleatorio = (int)((Math.random() * 17) + 32);
            
            if (numeroAleatorio == 48) { // Controla el salto de linea y almacena el fichero.
              numeroAleatorio= (int)(Math.random() * 99);
            
              if (numeroAleatorio % 11 == 0) {
                bw.append(mensaje + "</p>");
                bw.flush();
                
                mensaje.delete(0, mensaje.length());
                mensaje.append("<p>");
        
                tamano--; // Controla el numero de parrafos
              }
              
            } else {
              letra = (char)numeroAleatorio;
              
            }
          }
        }
        
        contadorLetras++;
        mensaje.append(letra);
      
      } // Fin de bucle while para generar el fichero.
      
      System.out.println("Cantidad de letras generadas: " + contadorLetras);
  }
  
  private static void generaPoratadoraModificada (String rutaLectura, BufferedWriter bw, String linea, int tamanoPalabra) throws FileNotFoundException, IOException {
    BufferedReader brDiccionario = new BufferedReader(new FileReader(rutaLectura));
    
    String palabraDiccionario;
    String palabraARemplazar;

    palabraDiccionario = brDiccionario.readLine();
    
    while (palabraDiccionario != null) {
      palabraARemplazar = "";
      
      if (palabraDiccionario.length() >= tamanoPalabra) {
//        System.out.println("Palabra del diccionario: " + palabraDiccionario);
        palabraARemplazar = palabraARemplazar.concat(" <i>").concat(palabraDiccionario).concat("</i> ");
        linea = linea.replace(palabraDiccionario, palabraARemplazar);
      }
      palabraDiccionario = brDiccionario.readLine();
      
    }
    
    bw.append(linea + "\n");
  }

  private static void generaCabecera (BufferedWriter bw) throws IOException {
    bw.append("<html>"
              + "<head>"
                + "<meta charset=\"UTF-8\"> "
                + "<style>"
                  + "html {background-color: black; color: white;}"
                  + "p {margin: 5em; text-align: justify;}"
                  + "i {background-color: #730000;}"
                + "</style>"
              + "</head>"
              + "<body>"
                + "<audio autoplay loop>"
                  + "<source src=\"resources/cancionFondo.mp3\" type=\"audio/ogg\">"
                + "</audio>"
                + "<p>");
    
    bw.flush();
  }
  
  private static void generaPiePagina (BufferedWriter bw) throws IOException {
    
      bw.append("</p></body></html>");
      
      bw.flush();
  }
  
  private static void borraFichero (String ruta) {
    
    File portadoraModificada = new File(ruta);
    if (portadoraModificada.exists()) {
      portadoraModificada.delete();

    }
  }
}
