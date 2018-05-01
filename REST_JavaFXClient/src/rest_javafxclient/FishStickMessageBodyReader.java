package rest_javafxclient;

import entity.FishStick;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import javax.json.Json;
import javax.json.stream.JsonParser;
import static javax.json.stream.JsonParser.Event.END_OBJECT;
import static javax.json.stream.JsonParser.Event.KEY_NAME;
import static javax.json.stream.JsonParser.Event.START_OBJECT;
import javax.ws.rs.Consumes;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.Provider;

/**
 * FishStickMessageBodyReader.java
 * Created by: Jennifer Aube, Zachary Brule, Hiral Nilesh Bhatt, Evandro Ramos da Silva, John Smith
 * Date created: April 11, 2018
 * Purpose: Read FishStick values and put into FishSitck object and then into the FishStick list
 */

@Provider
@Consumes({"application/json"})
public class FishStickMessageBodyReader implements MessageBodyReader<List<FishStick>> {

    @Override
    public boolean isReadable(Class<?> type, Type type1, Annotation[] antns, MediaType mt) {
        return true;
    }

    @Override
    public List<FishStick> readFrom(Class<List<FishStick>> type, Type type1, Annotation[] antns, MediaType mt,
            MultivaluedMap<String, String> mm, InputStream in) throws IOException, WebApplicationException {
        if (mt.getType().equals("application") && mt.getSubtype().equals("json")) {
            FishStick stuff = new FishStick();
            List<FishStick> stuffs = new ArrayList();
            JsonParser parser = Json.createParser(in);
            while (parser.hasNext()) {
                JsonParser.Event event = parser.next();
                switch (event) {
                    case START_OBJECT:
                        stuff = new FishStick();
                        break;
                    case END_OBJECT:
                        stuffs.add(stuff);
                        break;
                    case KEY_NAME:
                        String key = parser.getString();
                        parser.next();
                        switch (key) {
                            case "id":
                                stuff.setId(parser.getLong());
                                break;
                            case "recordNum":
                                stuff.setRecordNum(parser.getInt());
                                break;
                            case "omega":
                                stuff.setOmega(parser.getString());
                                break;
                            case "lambda":
                                stuff.setLambda(parser.getString());
                                break;
                            case "uuid":
                                stuff.setUuid(parser.getString());
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
            return stuffs;
        }
        throw new UnsupportedOperationException("Not supported MediaType: " + mt);
    }
}
