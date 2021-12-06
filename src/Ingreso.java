
public class Ingreso extends Dinero{

	
	public Ingreso(double ingreso, String description){
		 
		 this.setDinero(ingreso);
		 
		 super.setDescription(description);
		 
	 }

	public String toString() {
		
		
		return "Ingreso: "+ super.getDescription() +  ", cantidad:" + this.getDinero() +"€" ;
	}


	
	
	
	
	
}
