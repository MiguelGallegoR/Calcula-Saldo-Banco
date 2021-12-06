import java.util.ArrayList;
import java.util.List;

public class Cuenta {

	private double saldo;
	private Usuario usuario;
	
	private List<Gasto> gastos;
	
	private List<Ingreso> ingresos;
	
	
	public Cuenta(Usuario usuario){
		
		this.usuario =usuario;
		this.saldo=0.0;
		
		this.ingresos = new ArrayList<>();
		this.gastos = new ArrayList<>();
		
	}
	
	public double getSaldo() {
		
		return this.saldo;
	}
	
	public void setSaldo(double saldo) {
		
		this.saldo=saldo;
		
	}
	
	public Usuario getUsuario(){
		
		return usuario;
	}
	
	
	void setUsuario(Usuario usuario) {
		
		this.usuario=usuario;
		
	}
	
	
	public double addIngresos( String description, double cantidad) {
		
		ingresos.add(new Ingreso(cantidad, description));
		
		this.saldo += cantidad;
		
		return this.getSaldo();
		
	}

 
	public double addGastos(String description, double cantidad) throws GastoException{
		
		boolean condicionGastoException = this.getSaldo()<cantidad;
		
		if(condicionGastoException) {
			throw new GastoException(); 
		}
		
		
		gastos.add(new Gasto(cantidad, description));
		
		this.saldo -=cantidad;
		
		return this.getSaldo();
	
	
	}



List <Ingreso> getIngresos(){
	
	return ingresos;
}

List <Gasto> getGastos(){
	
	return gastos;
}


public String toString(){
	
	return "Usuario: " + this.getUsuario() + ", saldo: " + this.getSaldo();
	
}



}
