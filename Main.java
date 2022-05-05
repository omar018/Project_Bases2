import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import com.sun.source.tree.BindingPatternTree;

import multichain.object.*;
import multichain.command.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

/**
 *
 * @author Juan Felipe Peña
 */
public class Main {
    // private String [] candidatos = {"juan","jose","correa"};

    // public String [] getCandidatos(){
    //     return candidatos;
    // } 

    public static CommandManager commandManager = new CommandManager( //nos conectamos a la blockchain
    "localhost",
    "5750",
    "multichainrpc",
    "Gi5CvG4rHt4FrbfQwywoTXNJnXTRHDLpYJF8qEtUSZ1W"
);

     
        //hashmap con la llave del candidato y su nombre y partido
    public HashMap<String,String[]> cuentaCandidatos = new HashMap<String,String[]>();
        //hashmap con el nombre y el partido del candidato y su cantidad de votos
    public static HashMap<String[],Integer> candidatosVotos = new HashMap<String[],Integer>();

    


    private void llenarCandidatos(List<StreamKeyItem> candidatos){ //Método para llenar el hash de candidatos
        String llave = "";
        JsonParser parser = new JsonParser();
        for (StreamKeyItem candidato : candidatos) {
            //System.out.println(candidato.getKeys().get(0).getClass()); //Número del candidato
            String[] np = {"",""};
            llave = (String) candidato.getKeys().get(0);
            String gson = new Gson().toJson(candidato.getData());
            JsonObject json = parser.parse(gson).getAsJsonObject();
            np[0] = json.getAsJsonObject("json").get("nombre").getAsString();
            np[1] = json.getAsJsonObject("json").get("partido").getAsString();
            //np es el nombre y el partido
            //System.out.println(np[0] + " " + np[1] + " " + llave);
            cuentaCandidatos.put(llave, np);
            candidatosVotos.put(np,0);
    }
    }

    private void llenarVotos (List<StreamKeyItem> votos){
        JsonParser parser = new JsonParser();
        if (votos.size()>0){
        for (StreamKeyItem voto : votos){
            String gson = new Gson().toJson(voto.getData());
            JsonObject json = parser.parse(gson).getAsJsonObject();
            String llaveCandidato = json.getAsJsonObject("json").get("voto").getAsString();
            //tomamos el nombre y el partido asociados a la llave del candidato
            String [] nompar = cuentaCandidatos.get(llaveCandidato);
            //tomamos la cantidad de votos del candidato
            Integer voteCount = candidatosVotos.get(nompar);
            if (nompar[0] !=null){
                System.out.println("llave que me trae la lista: "+llaveCandidato+ " string[] " + nompar[0] + " votos " + voteCount);
            }
            //System.out.println(Arrays.toString(nompar) +  " " + voteCount);
            //le sumamos un voto
            //candidatosVotos.put(nompar,voteCount+1);
        }
        }
    }
    public void subirVotoCadena(String voto, String votante){
        try{
        String s = "{\"json\":{\"voto\":" + "\"" + voto  + "\""+ "}}";
        Gson gson = new Gson();
        String jsonString = gson.toJson(s);
        JsonParser parser = new JsonParser();
        JsonObject j = parser.parse(s).getAsJsonObject();
        System.out.println(j);
        commandManager.invoke(CommandElt.SUBSCRIBE, "Votos");
        commandManager.invoke(CommandElt.PUBLISH, "Votos", voto, j);
        } catch (MultichainException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {

        Main m = new Main();
        

        // sacamos los valores de los streams de la blockchain
        List<StreamKeyItem> items;
        List<StreamKeyItem> candidatos;
        List<StreamKeyItem> votos;
        try {
            commandManager.invoke(CommandElt.SUBSCRIBE, "Candidatos");
            candidatos = (List<StreamKeyItem>) commandManager.invoke(CommandElt.LISTSTREAMITEMS,//Obteniendo los candidatos del Stream
                    "Candidatos");
            m.llenarCandidatos(candidatos); 
            commandManager.invoke(CommandElt.SUBSCRIBE, "Votos");
            votos = (List<StreamKeyItem>) commandManager.invoke(CommandElt.LISTSTREAMITEMS, "Votos");
            m.llenarVotos(votos);
        } catch (MultichainException e) {
            e.printStackTrace();

        }
        // String[] key = {"",""}, key1={"",""},key2 = {"",""};
        // key[0] = "juan";
        // key[1] = "elp";
        // candidatosVotos.put(key, 1);
        // //System.out.println(key[0] + " "  + " " + key[1]+ " " +candidatosVotos.entrySet());
        // key1[0] = "jose";
        // key1[1] = "ppp";
        // candidatosVotos.put(key1,3);
        // //System.out.println(key[0] + " "  + " " + key[1]+ " " +candidatosVotos.entrySet());
        // key2[0] = "mor";
        // key2[1] = "ttt";
        // candidatosVotos.put(key2,5);
        // //System.out.println(key[0] + " "  + " " + key[1]+ " " + candidatosVotos.entrySet());
        // // for(Entry<String[],Integer> entry : candidatosVotos.entrySet()){
        // //     String[] keys = entry.getKey();
        // //     Integer value = entry.getValue();
        // //     System.out.println(Arrays.toString(keys) + " " + value);
        // // }
        // new GUI(); // lanzamos la interfaz 
    }
}
