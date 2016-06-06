import java.rmi.*;
public class VetClient
{ public static void main(String[] arg)
  {    
    VetInterface v;
    try
    {
      	v = (VetInterface)Naming.lookup("rmi://192.168.98.66:1099/Vet10");

	boolean temAlguemUsando = v.getFlag();
	int vez = v.getVez();

	System.out.println("É a vez do número: " + vez + "\ntem alguém usando: " + temAlguemUsando);

	if (temAlguemUsando == false && vez == 0) //se ngm tiver usando, e for a minha vez
	{
		v.setFlag(true);

      		v.setInt(0,3);
      		for(int i=0 ; i< 10 ; i++)
      			System.out.println("Posição "+ i + " " + " Valor = " + v.getInt(i));

		v.setFlag(false); //passa a vez
      		v.setVez(++vez);//passa a vez

		System.out.println("É a vez do número: " + vez + "\ntem alguém usando: " + v.getFlag());
    	}
	else System.out.println("Tem alguém usando.");
     }
    	catch(Exception e) {System.out.println("Deu merda");}

  }
}
