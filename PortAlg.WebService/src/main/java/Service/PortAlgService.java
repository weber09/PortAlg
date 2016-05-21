package Service;

import AST.SemanticError;
import Parser.PortAlgParser;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.io.ByteArrayInputStream;

/**
 * Created by Gabriel on 21/05/2016.
 */
@Path("/compiler")
public class PortAlgService {
    @GET
    @Produces("Aplication/Json")
    public Response getMsg(){
        return Response.ok("Hallo aus PortAlg, Gabriel!").header("Access-Control-Allow-Origin", "*").build();
    }

    @OPTIONS
    public Response optionsServer(@HeaderParam("Access-Control-Request-Headers") String requestH){
        return Response.ok("go ahead").header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, OPTIONS, PUT")
                .header("Access-Control-Allow-Headers", requestH)
                .build();
    }

    @PUT
    @Consumes("text/plain")
    @Produces("Aplication/Json")
    public Response compile(String code){
        try{
            PortAlgParser compiler = new PortAlgParser(new ByteArrayInputStream(code.getBytes()));
            compiler.compile(code.getBytes());
            String returnMessage = "Compilou com sucesso!";

            if(compiler.errorHasOccurred()) {
                StringBuilder sb = new StringBuilder();
                sb.append("Erros semânticos ocorreram:\n");
                for(SemanticError error : PortAlgParser.getSemanticErrors()){
                    sb.append(error.toString() + "\n");
                }
                returnMessage = sb.toString();
            }

            return Response.ok(returnMessage).header("Access-Control-Allow-Origin", "*").build();
        }
        catch(Exception ex){
            return Response.ok(ex.toString()).header("Access-Control-Allow-Origin", "*").build();
        }
        catch(Error er){
            return Response.ok(er.toString()).header("Access-Control-Allow-Origin", "*").build();
        }
    }
}
