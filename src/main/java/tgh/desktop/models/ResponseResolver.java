//package tgh.desktop.models;
//import javax.ws.rs.Produces;
//import javax.ws.rs.ext.Provider;
//import javax.xml.bind.JAXBContext;
//import javax.xml.bind.JAXBException;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.ext.ContextResolver;
//
//@Provider
//@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
//public class ResponseResolver implements ContextResolver<JAXBContext> {
//    private JAXBContext ctx;
//
//    public ResponseResolver() {
//        try {
//            this.ctx = JAXBContext.newInstance(
//
////            		lineInfo_Response.class, 
//                        lineInfo_Request.class
//
//                    );
//        } catch (JAXBException ex) {
//            throw new RuntimeException(ex);
//        }
//    }
//
//    public JAXBContext getContext(Class<?> type) {
//        return (type.equals(lineInfo_Response.class) ? ctx : null);
//    }
//}