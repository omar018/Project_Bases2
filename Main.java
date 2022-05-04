import multichain.command.*;
import multichain.object.*;
import java.util.List;

/**
 *
 * @author Juan Felipe Peña
 */
public class Main {
    private String [] candidatos = {"juan","jose","correa"};

    public String [] getCandidatos(){
        return candidatos;
    }
    public static void main(String[] args) {
        new GUI();
        CommandManager commandManager = new CommandManager(
                "localhost",
                "8381",
                "multichainrpc",
                "5uHUWEMHdWsWXSpXDMug78oBtFdk8mMbBX8AUMQzX6sE"
        );
        
        
        
        List<StreamKeyItem> items;
        // Este programa imprime por consola la información de los datos almacenados en un stream
        try {
        commandManager.invoke(CommandElt.SUBSCRIBE, "Votos");
         items = (List<StreamKeyItem>) commandManager.invoke(CommandElt.LISTSTREAMITEMS, 
                  "Votos");
            
          for (StreamKeyItem item : items) {
              System.out.println(item);
          }
         } catch (MultichainException e) {
            e.printStackTrace();
        }
    }
}
