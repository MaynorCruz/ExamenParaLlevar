import java.util.Random;
import java.util.Scanner;

public class Juego {

	static Scanner sc;
	static Random random;
	
	//Tablero del juego
	static int[][] tablero;
	//Coordenadas de los barcos en tablero
	static int[][] barcos;
	static int[] coordenadaDisparo = new int[2];
	static int intentos = 0, maxIntentos = 0, cantidadBarcos = 0, barcosDestruidos = 0, escans = 0, maxEscans = 0;
	
	//Strings
	static String separador = "- - - - - - - - - - - - - - - - -";
	//Estado del juego
	static boolean jugando = false;

	public static void main(String[] args) {
		
		sc = new Scanner(System.in);
		random = new Random();
		
		menu();
	}
	
	static void menu()
	{
		int opcionMenu = 0;
		
		System.out.println(separador);
		System.out.println("- - BATTLE SHIP Version 1.0 - -");
		System.out.println(separador);
		System.out.println();
		System.out.println("Eliga una opcion del menu!");
		System.out.println("1) Jugar");
		System.out.println("2) Contactar al dev");
		System.out.println("3) Salir");
		
		opcionMenu = sc.nextInt();
		
		switch(opcionMenu)
		{
		case 1:
			jugar();
			break;
		case 2:
			System.out.println(separador);
			System.out.println("Juego creado por: Miguel Sabillon, Maynor y Jimmy");
			System.out.println(separador);
			System.out.println();
			menu();
			break;
		case 3:
			System.out.println(separador);
			System.out.println("Saliendo del juego!");
			System.out.println(separador);
			break;
		default:
			System.out.println(separador);
			System.out.println("Porfavor introdusca una opcion del menu!");
			System.out.println(separador);
			menu();
			break;
		}
		
	}
	
	static void jugar()
	{
		iniciarJuego();
		while(jugando)
		{
			System.out.println(separador);
			demostrarTablero(tablero);
			
			//System.out.println("-- Posicion de barcos --");
			//demostrarTablero(barcos);
		
			accionesJugador();
			statusJuego();
		}
	}
	
	//Resetea todas las variables del juego y crea el tablero con los barcos
	static void iniciarJuego()
	{
		intentos = 0;
		barcosDestruidos = 0;
		escans = 0;
		cantidadBarcos = 0;
		
		tablero = definirTablero(tablero);
		barcos = new int[tablero.length][tablero[1].length];
		
		iniciarTablero(tablero);
		iniciarTablero(barcos);
		
		colocarBarcosAleatoriamente(barcos);
		contarBarcosEnTablero();
		jugando = true;
		
		calcularChances();
	}
	
	//Calcula los intentos dependiendo de que tan grande es el tablero
	static void calcularChances()
	{
		double maxInt = 1.2*tablero.length;
		double maxScn = 0.9*tablero.length;
		
		maxIntentos = (int)maxInt;
		maxEscans = (int)maxScn;
	}
	
	//Chequeo si el jugador puede seguir jugando
	static void statusJugador()
	{
		System.out.println(separador);
		System.out.println("Intentos: "+intentos+"/"+maxIntentos);
		System.out.println("Barcos: "+barcosDestruidos+"/"+cantidadBarcos);
		System.out.println("Scaneos: "+escans+"/"+maxEscans);
		System.out.println(separador);
	}
	
	//Metodo que maneja si el jugador puede jugar/ganar o perder
	static void statusJuego()
	{
		if(intentos == maxIntentos)
		{
			jugando = false;
			System.out.println(separador);
			System.out.println("CAPITAN HEMOS PERDIDO LA GUERRA!!");
			System.out.println(separador);
			menu();
		}
		else if(barcosDestruidos == cantidadBarcos)
		{
			jugando = false;
			System.out.println(separador);
			System.out.println("CAPITAN HEMOS GANADO LA GUERRA!!");
			System.out.println(separador);
			menu();
		}
	}
	
	//Acciones que el jugador puede hacer
	static void accionesJugador()
	{
		statusJugador();
		
		System.out.println(separador);
		System.out.println("CAPITAN TENEMOS TODO LISTO, QUE HAREMOS?!");
		System.out.println(separador);
		System.out.println();
		System.out.println("1) Disparar/Atacar");
		System.out.println("2) Scannear columna");
		System.out.println("3) Rendirse!");
		
		int opcion;
		opcion = sc.nextInt();
		
		switch(opcion)
		{
		case 1:
			disparar();
			break;
		case 2:
			
			if(escans != maxEscans)
			{
				int numeroColumna;
				
				System.out.println(separador);
				System.out.println("CAPITAN CUAL COLUMNA SCANEAREMOS?!");
				System.out.println(separador);
				numeroColumna = sc.nextInt();
				System.out.println(separador);
				System.out.println("HAY "+escannearColumna(numeroColumna)+" BARCO(S) EN ESTA COLUMNA!!");
				System.out.println(separador);
				
				System.out.println(separador);
				demostrarTablero(tablero);
				
				accionesJugador();
			}
			else
			{
				System.out.println(separador);
				System.out.println("CAPITAN NO TENEMOS MAS ENEGIA PARA ESCANEOS!!");
				System.out.println(separador);
			}
			
			break;
		case 3:
			intentos = maxIntentos;
			break;
		default:
			System.out.println(separador);
			System.out.println("Debe elegir una opcion del menu de accion!");
			System.out.println(separador);
			accionesJugador();
			break;
		}
	}
	
	//Cuenta los barcos en el tablero
	static void contarBarcosEnTablero()
	{
		for(int columna = 0; columna < barcos.length; columna++)
		{
			for(int fila = 0; fila < barcos[columna].length; fila++)
			{
				if(barcos[columna][fila] == 2)
				{
					cantidadBarcos = cantidadBarcos + 1;
				}
			}
		}
	}
	
	//escanea columna especificada por el usuario
	static int escannearColumna(int numeroColumna)
	{
		int barcosEnColumna = 0;
		
		for(int fila = 0; fila < barcos[numeroColumna-1].length; fila++)
		{
			if(barcos[numeroColumna-1][fila] == 2)
			{
				barcosEnColumna = barcosEnColumna + 1;
			}
		}
		
		escans++;
		return barcosEnColumna;
	}
	
	//Definir tamanio del tablero
	static int[][] definirTablero(int[][] tablero)
	{
		int tamanio;
		System.out.println(separador);
		System.out.println("Introdusca tamanio de tablero (Ejemplo: 10x10): ");
		tamanio = sc.nextInt();
		
		tablero = new int[tamanio][tamanio];
		System.out.println("Tamanio definido como: "+tamanio+"x"+tamanio);
		System.out.println(separador);
		return tablero;
	}
	
	static void iniciarTablero(int[][] tablero)
	{
		for(int columna = 0; columna < tablero.length; columna++)
		{
			for(int fila = 0; fila < tablero[columna].length; fila++)
			{
				tablero[columna][fila] = -1;
			}
		}
	}

	//Mostrar tablero con signos visuales
	static void demostrarTablero(int[][] tablero)
	{
		
		//Imprimir numero de columnas
		for(int columnas = 0; columnas < tablero.length; columnas++)
		{
			if(columnas == 0)
			{
				System.out.print("\t");
			}
			System.out.print((columnas+1)+"\t");
		}
		
		System.out.println();
		
		//Imprimir tablero con signos ~, *, X
		for (int columna = 0; columna < tablero.length; columna++) {
			System.out.print((columna + 1) + "");
			for (int fila = 0; fila < tablero[columna].length; fila++) {
				if (tablero[columna][fila] == -1)
				{
					System.out.print("\t" + "~");
				} 
				else if (tablero[columna][fila] == 0)
				{
					System.out.print("\t" + "*");
				}
				else if(tablero[columna][fila] == 1)
				{
					System.out.print("\t" + "X");
				}
				else
				{
					System.out.print("\t" + "b");
				}
			}
			System.out.println();
		}
	}
	
	static void colocarBarcosAleatoriamente(int[][] barcos)
	{
		for(int columna = 0; columna < barcos.length; columna++)
		{
			barcos[columna][random.nextInt(barcos.length)] = 2;
		}
	}
	
	//Accion disparo
	static void disparar()
	{
		coordenadaDisparo();
		dispararCoordenada();
	}
	
	//Colocar coordenada de disparlo del jugador
	static void coordenadaDisparo()
	{
		int coordenada1;
		int coordenada2;
		
		System.out.println(separador);
		System.out.println("Eliga la primera coordenada Capitan!: ");
		System.out.println(separador);
		coordenada1 = sc.nextInt();
		coordenada1--;
		
		System.out.println(separador);
		System.out.println("Eliga la segunda coordenada Capitan!: ");
		System.out.println(separador);
		coordenada2 = sc.nextInt();
		coordenada2--;
		
		//Chequear si ya ha disparado en esa coordenada
		if(tablero[coordenada1][coordenada2] == 0 || tablero[coordenada1][coordenada2] == 1)
		{
			System.out.println(separador);
			System.out.println("CAPITAN YA HEMOS DISPARADO EN ESA COORDENADA, ELIGA OTRA!");
			coordenadaDisparo();
		}
		else
		{
			coordenadaDisparo[0] = coordenada1;
			coordenadaDisparo[1] = coordenada2;
		}
	}
	
	//Dispara a la coordenada especificada por el usuario
	static void dispararCoordenada()
	{
		System.out.println(separador);
		System.out.println("DISPAROOO!");
		if(chequearSiGolpeo())
		{
			System.out.println("GOLPE CONFIRMADO CAPITAN!!");
			System.out.println(separador);
			tablero[coordenadaDisparo[0]][coordenadaDisparo[1]] = 1;
			barcosDestruidos++;
		}
		else
		{
			System.out.println("HEMOS FALLADO EL GOLPE CAPITAN!!");
			System.out.println(separador);
			tablero[coordenadaDisparo[0]][coordenadaDisparo[1]] = 0;
			intentos++;
		}
	}
	
	//Chequea si golpeo un barco
	static boolean chequearSiGolpeo()
	{
		if(barcos[coordenadaDisparo[0]][coordenadaDisparo[1]] == 2)
		{
			return true;
		}
		return false;
	}
	
}
