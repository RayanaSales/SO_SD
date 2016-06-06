import java.rmi.*;
import java.util.Scanner;

public class VetClient
{
	public static void main(String[] arg)
	{
		VetInterface v;
		Scanner pegar = new Scanner(System.in);

		try
		{
			v = (VetInterface) Naming.lookup("rmi://192.168.98.66:1099/Vet10");
			int minhaPosicao = 1;

			while(true)
			{
				boolean temAlguemUsando = v.getFlag(); //EXCLUSAO MUTUA

				System.out.println("tem alguem usando a rede: " + temAlguemUsando);
				if (temAlguemUsando == false) //EXCLUSAO MUTUA
				{
					v.setFlag(true);//EXCLUSAO MUTUA

					System.out.println("Quero ser produtor ou consumidor ? p/c/sair");
					String quero = pegar.nextLine();

					if (quero.equals("p"))
					{ // sou produtor - push
						boolean cheio = false;

						if ((v.getContador() < v.getTamanhoBuffer()) && (v.getEstado(minhaPosicao) == true)) //se meu estado for true, to acordado - SINCRONIZACAO CONDICIONAL (SLEPP WAKE UP)
						{
							System.out.println("Digite o valor que deseja inserir: ");
							cheio = v.push(pegar.nextInt());
							v.wakeup('c'); //SINCRONIZACAO CONDICIONAL (SLEPP WAKE UP)
							System.out.println("Acordei os consumidores.");
						}
						else if (v.getContador() == v.getTamanhoBuffer()) //SINCRONIZACAO CONDICIONAL (SLEPP WAKE UP)
						{
							v.setFlag(false); //EXCLUSAO MUTUA
							v.sleep(minhaPosicao);//SINCRONIZACAO CONDICIONAL (SLEPP WAKE UP)
							v.wakeup('c');//SINCRONIZACAO CONDICIONAL (SLEPP WAKE UP)
							System.out.println("Acordei os consumidores.");
							System.out.println("Nao da para inserir nada, array cheio. Fui dormir.");
						}
						else if (v.getEstado(minhaPosicao) == false)
						{	 System.out.println("To dormindo.");}
					}
					else if (quero.equals("c"))
					{// sou consumidor - pop
						if ((v.getContador() > 0) && (v.getEstado(minhaPosicao) == true))//SINCRONIZACAO CONDICIONAL (SLEPP WAKE UP)
						{
							System.out.println("Tirei o valor: " + v.pop());
							v.wakeup('p');//SINCRONIZACAO CONDICIONAL (SLEPP WAKE UP)
							System.out.println("Acordei os produtores.");
						}
						else if (v.getContador() == 0)
						{
							v.setFlag(false);//EXCLUSAO MUTUA
							v.sleep(minhaPosicao);//SINCRONIZACAO CONDICIONAL (SLEPP WAKE UP)
							v.wakeup('p');//SINCRONIZACAO CONDICIONAL (SLEPP WAKE UP)
							System.out.println("Acordei os produtores.");
							System.out.println("Array vazio, nao posso consumir. Fui dormir");
						}
						else if (v.getEstado(minhaPosicao) == false)
						{	 System.out.println("To dormindo.");}
					}
					else if(quero.equals("sair"))
						System.exit(0);

					for (int i = 0; i < 10; i++)
						System.out.println("Posicao " + i + " " + " Valor = " + v.getInt(i));

					v.setFlag(false);//EXCLUSAO MUTUA
				}

			}

		}
		catch (Exception e)
		{
			System.out.println("Deu merda");
		}
	}
}
