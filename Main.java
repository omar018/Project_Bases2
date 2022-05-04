import com.sun.source.tree.BindingPatternTree;
import multichain.command.*;
import multichain.object.*;
import java.util.List;

/**
 *
 * @author Juan Felipe Peña
 */
public class Main {

    public static void main(String[] args) {
        CommandManager commandManager = new CommandManager(
                "localhost",
                "8381",
                "multichainrpc",
                "5uHUWEMHdWsWXSpXDMug78oBtFdk8mMbBX8AUMQzX6sE"
        );
        List<StreamKeyItem> candidatos;
        List<StreamKeyItem> items;
        try {
            commandManager.invoke(CommandElt.SUBSCRIBE, "Candidatos");
            candidatos = (List<StreamKeyItem>) commandManager.invoke(CommandElt.LISTSTREAMITEMS,//Obteniendo los candidatos del Stream
                    "Candidatos");
        } catch (MultichainException e) {
            e.printStackTrace();
        }

        void voto(List<StreamKeyItem>){ //Método para imprimir lo que existe en el Stream de candidatos
            for (StreamKeyItem candidato : candidatos) {
                System.out.println(candidato.getKey()); //Número del candidato
                System.out.println(candidato.getData()); //Aquí se obtienen los datos del candidato (supongo)
        }
        }




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
