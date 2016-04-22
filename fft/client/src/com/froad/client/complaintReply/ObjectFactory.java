
package com.froad.client.complaintReply;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.complaintReply package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _UpdateStateById_QNAME = new QName("http://service.CB.froad.com/", "updateStateById");
    private final static QName _UpdateStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateStateByIdResponse");
    private final static QName _AddComplaintReply_QNAME = new QName("http://service.CB.froad.com/", "addComplaintReply");
    private final static QName _AddComplaintReplyResponse_QNAME = new QName("http://service.CB.froad.com/", "addComplaintReplyResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.complaintReply
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link AddComplaintReplyResponse }
     * 
     */
    public AddComplaintReplyResponse createAddComplaintReplyResponse() {
        return new AddComplaintReplyResponse();
    }

    /**
     * Create an instance of {@link AddComplaintReply }
     * 
     */
    public AddComplaintReply createAddComplaintReply() {
        return new AddComplaintReply();
    }

    /**
     * Create an instance of {@link UpdateStateByIdResponse }
     * 
     */
    public UpdateStateByIdResponse createUpdateStateByIdResponse() {
        return new UpdateStateByIdResponse();
    }

    /**
     * Create an instance of {@link UpdateStateById }
     * 
     */
    public UpdateStateById createUpdateStateById() {
        return new UpdateStateById();
    }

    /**
     * Create an instance of {@link ComplaintReply }
     * 
     */
    public ComplaintReply createComplaintReply() {
        return new ComplaintReply();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateStateById")
    public JAXBElement<UpdateStateById> createUpdateStateById(UpdateStateById value) {
        return new JAXBElement<UpdateStateById>(_UpdateStateById_QNAME, UpdateStateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateStateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateStateByIdResponse")
    public JAXBElement<UpdateStateByIdResponse> createUpdateStateByIdResponse(UpdateStateByIdResponse value) {
        return new JAXBElement<UpdateStateByIdResponse>(_UpdateStateByIdResponse_QNAME, UpdateStateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddComplaintReply }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addComplaintReply")
    public JAXBElement<AddComplaintReply> createAddComplaintReply(AddComplaintReply value) {
        return new JAXBElement<AddComplaintReply>(_AddComplaintReply_QNAME, AddComplaintReply.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddComplaintReplyResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addComplaintReplyResponse")
    public JAXBElement<AddComplaintReplyResponse> createAddComplaintReplyResponse(AddComplaintReplyResponse value) {
        return new JAXBElement<AddComplaintReplyResponse>(_AddComplaintReplyResponse_QNAME, AddComplaintReplyResponse.class, null, value);
    }

}
