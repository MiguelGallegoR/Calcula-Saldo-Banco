
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

public class Main {

	
	private static final String MSG_DESPEDIDA = "Fin del programa.\nGracias por utilizar la aplicación.";
	
	//Mensajes de errores
	private static final String MSG_ACCION_NO_VALIDA = "Accion no valida, elija una nueva acción";
	private static final String MSG_DNI_NO_VALIDO = "DNI introducido incorrecto";
	
	

	//mensajes para el menú 
	private static final String MSG_MENU = 
			"Realiza una nueva acción\n" +
			"1 Introduce un nuevo gasto\n" +
			"2 Introduce un nuevo ingreso\n" +
			"3 Mostrar gastos\n" +
			"4 Mostrar ingresos\n" +
			"5 Mostrar saldo\n" +
			"0 Salir\n" ;
		
	//mensajes para solicitar atributos clase Usuario
	private static final String MSG_SOLICITAR_NOMBRE = "Introduce el nombre de usuario: ";
	private static final String MSG_SOLICITAR_EDAD = "Introduce la edad del usuario: ";
	private static final String MSG_SOLICITAR_DNI = "Introduce el DNI del usuario: ";
	private static final String MSG_USUARIO_CREADO = "Usuario creado correctamente ";

	//mensajes para gasto
	private static final String MSG_SOLICITAR_DESCRIPCION_GASTO = "Descripcion del gasto: ";
	private static final String MSG_SOLICITAR_NUEVO_GASTO = "Introduzca cantidad a gastar: ";
	
	//mensajes para ingreso
	private static final String MSG_SOLICITAR_DESCRIPCION_INGRESO = "Introduce descripción: ";
	private static final String MSG_SOLICITAR_NUEVO_INGRESO = "Introduce la cantidad: ";
	
	//Opciones numéricas para las distintas acciones del programa
	private static final int NUEVO_GASTO = 1; 
	private static final int NUEVO_INGRESO = 2; 
	private static final int MOSTRAR_GASTOS = 3; 
	private static final int MOSTRAR_INGRESOS = 4; 
	private static final int MOSTRAR_SALDO = 5; 
	private static final int SALIR = 0;
	
	//mensaje saldo
	private static final String MSG_SALDO_RESTANTE = "Saldo restante: ";
	private static final String MSG_SALDO_ACTUAL = "El saldo actual de la cuenta es: ";
	
	//Variables de Objeto
	private static Cuenta miCuenta;
	
	
public static void main(String[] args) {
		
		solicitarDatos(); 
	
		boolean condicionSalir = true; 
		int opcion; 
	
		
		
		//utilizo un bloque do...while() porque como mínimo se debe de ejecutar una vez
		do {
			mostraMenu();
			opcion = elegirOpcion();
			condicionSalir = ejecutarOpcion(opcion); 
		} while (condicionSalir);
	
	
}

private static void solicitarDatos() {
	
	//creo un usuario con new 
	Usuario miUsuario = new Usuario(); 

	boolean DNICorrecto = false;
	
	String nombre = solicitarString(MSG_SOLICITAR_NOMBRE);
	
	while(nombre.isEmpty()) {
		nombre = solicitarString(MSG_SOLICITAR_NOMBRE); 
	}
	
	int edad = solicitarEntero(MSG_SOLICITAR_EDAD);
	miUsuario.setNombre(nombre);
	miUsuario.setEdad(edad);
	
	//bucle para comprobar que se cumpla las condiciones de un DNI valido segun enunciado de PAC
	do {
		String DNI = solicitarString(MSG_SOLICITAR_DNI); 
		miUsuario.setDNI(DNI);
		
		if(miUsuario.setDNI(DNI)) {
			DNICorrecto = true; 
		}else {
			System.out.println(MSG_DNI_NO_VALIDO);
		}
		
	} while (!DNICorrecto );
	
	System.out.println(MSG_USUARIO_CREADO);
	
	//una vez creado el usuario, creo la cuenta y le paso el usuario valido
	miCuenta = new Cuenta(miUsuario);
	
}
	
private static void mostraMenu() {
	System.out.print(MSG_MENU);
}

private static int elegirOpcion() {
	return leerEntero(); 
}

private static boolean ejecutarOpcion(int opcion) {
	
	switch (opcion) {
	case NUEVO_GASTO:
		nuevoGasto(); 
		break;
	case NUEVO_INGRESO:
		nuevoIngreso();
		break;
	case MOSTRAR_GASTOS:
		mostrarGastos();
		break;
	case MOSTRAR_INGRESOS:
		mostrarIngresos();
		break;
	case MOSTRAR_SALDO:
		mostrarSaldo();
		break;
	case SALIR:
		System.out.println(MSG_DESPEDIDA);
		return false; 
		
	default:
		System.out.println(MSG_ACCION_NO_VALIDA);
		break;
	}
	return true; 
}
	
private static void nuevoGasto() {
	
	String description = solicitarString(MSG_SOLICITAR_DESCRIPCION_GASTO); 
	double cantidad = solicitarDouble(MSG_SOLICITAR_NUEVO_GASTO);
	
	//sino existe saldo suficiente puede lanzar un error
	
	try {
		double saldo = miCuenta.addGastos(description,cantidad);
		System.out.println(MSG_SALDO_RESTANTE + saldo);
	} catch (GastoException e) {
		System.out.println(e.getMessage());
	}
	
}

private static void nuevoIngreso() {
	
	
	String descripcion = solicitarString(MSG_SOLICITAR_DESCRIPCION_INGRESO); 
	double cantidad = solicitarDouble(MSG_SOLICITAR_NUEVO_INGRESO);
	double saldo = miCuenta.addIngresos(descripcion,cantidad);
	
	System.out.println(MSG_SALDO_RESTANTE + saldo);
}

private static void mostrarIngresos() {
	
	
	List<Ingreso> listaIngresos = miCuenta.getIngresos(); 
	
	Stream<Ingreso> str = listaIngresos.stream(); 
	str.forEach(System.out::println);
}

private static void mostrarGastos() {
	
	List<Gasto> listaGastos = miCuenta.getGastos(); 
	
	//con un for accedo a List<Gasto>
	for (int i = 0; i < listaGastos.size(); i++) {
		// tengo que acceder a cada casilla de List<Gasto>, mediante el método get() de getGastos() 
		// y mostrarla por pantalla
		System.out.println(listaGastos.get(i));
	}
}

private static void mostrarSaldo() {
	System.out.println(MSG_SALDO_ACTUAL + miCuenta.getSaldo()+"€");
}


//metodos para solicitar datos por consola
private static String solicitarString(String mensaje) {
	System.out.println(mensaje);
	return leerString();
}

private static int solicitarEntero(String mensaje) {
	System.out.println(mensaje);
	return leerEntero();
}

private static double solicitarDouble(String mensaje) {
	System.out.println(mensaje);
	return leerDouble(); 
}
//métodos para leer por consola
private static String leerString() {
	return new Scanner(System.in).nextLine(); 
}

private static int leerEntero() {
	
	try {
		return new Scanner(System.in).nextInt();
	} catch (InputMismatchException e) {
		System.out.println("Introduzca un número entero: ");
		
		return leerEntero(); 
	}
}

private static double leerDouble() {
	
	return new Scanner(System.in).nextDouble(); 
}


}

