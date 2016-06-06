import java.util.*;
import java.io.File;
public class ListaArquivoDiretorio
{   public static void main (String args [])
 {      Scanner sc = new Scanner (System.in);
        String dir = sc.next();
        File diretorio = new File(dir);
        File fList[] = diretorio.listFiles();
        System.out.println("Numero de arquivos no diretorio : " + fList.length );
        for ( int i = 0; i < fList.length; i++ )
        { System.out.println(fList[i].getName() +" " + new Date(fList[i].lastModified())); }
 }
}
