public class Gasto extends Dinero {

public Gasto(double gasto, String description){
	 
	 this.setDinero(gasto);
	 
	 this.setDescription(description);
	 
	 
 }

public String toString() {
	
	
	return  "Gasto: "+super.getDescription()+ ", cantidad: "+ this.getDinero()+ "€";
}



}