
package com.froad.client.dailyTask;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.froad.client.dailyTask package. 
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

    private final static QName _AddDailyTask_QNAME = new QName("http://service.CB.froad.com/", "addDailyTask");
    private final static QName _GetDailyTaskByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getDailyTaskByIdResponse");
    private final static QName _DeleteById_QNAME = new QName("http://service.CB.froad.com/", "deleteById");
    private final static QName _AddDailyTaskResponse_QNAME = new QName("http://service.CB.froad.com/", "addDailyTaskResponse");
    private final static QName _UpdateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateByIdResponse");
    private final static QName _UpdateById_QNAME = new QName("http://service.CB.froad.com/", "updateById");
    private final static QName _GetByUserId_QNAME = new QName("http://service.CB.froad.com/", "getByUserId");
    private final static QName _UpdateStateById_QNAME = new QName("http://service.CB.froad.com/", "updateStateById");
    private final static QName _UpdateStateByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "updateStateByIdResponse");
    private final static QName _DeleteByIdResponse_QNAME = new QName("http://service.CB.froad.com/", "deleteByIdResponse");
    private final static QName _GetByUserIdResponse_QNAME = new QName("http://service.CB.froad.com/", "getByUserIdResponse");
    private final static QName _GetDailyTaskById_QNAME = new QName("http://service.CB.froad.com/", "getDailyTaskById");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.froad.client.dailyTask
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link UpdateByIdResponse }
     * 
     */
    public UpdateByIdResponse createUpdateByIdResponse() {
        return new UpdateByIdResponse();
    }

    /**
     * Create an instance of {@link AddDailyTaskResponse }
     * 
     */
    public AddDailyTaskResponse createAddDailyTaskResponse() {
        return new AddDailyTaskResponse();
    }

    /**
     * Create an instance of {@link DeleteById }
     * 
     */
    public DeleteById createDeleteById() {
        return new DeleteById();
    }

    /**
     * Create an instance of {@link GetDailyTaskByIdResponse }
     * 
     */
    public GetDailyTaskByIdResponse createGetDailyTaskByIdResponse() {
        return new GetDailyTaskByIdResponse();
    }

    /**
     * Create an instance of {@link AddDailyTask }
     * 
     */
    public AddDailyTask createAddDailyTask() {
        return new AddDailyTask();
    }

    /**
     * Create an instance of {@link GetDailyTaskById }
     * 
     */
    public GetDailyTaskById createGetDailyTaskById() {
        return new GetDailyTaskById();
    }

    /**
     * Create an instance of {@link GetByUserIdResponse }
     * 
     */
    public GetByUserIdResponse createGetByUserIdResponse() {
        return new GetByUserIdResponse();
    }

    /**
     * Create an instance of {@link DeleteByIdResponse }
     * 
     */
    public DeleteByIdResponse createDeleteByIdResponse() {
        return new DeleteByIdResponse();
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
     * Create an instance of {@link GetByUserId }
     * 
     */
    public GetByUserId createGetByUserId() {
        return new GetByUserId();
    }

    /**
     * Create an instance of {@link UpdateById }
     * 
     */
    public UpdateById createUpdateById() {
        return new UpdateById();
    }

    /**
     * Create an instance of {@link DailyTask }
     * 
     */
    public DailyTask createDailyTask() {
        return new DailyTask();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDailyTask }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addDailyTask")
    public JAXBElement<AddDailyTask> createAddDailyTask(AddDailyTask value) {
        return new JAXBElement<AddDailyTask>(_AddDailyTask_QNAME, AddDailyTask.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDailyTaskByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getDailyTaskByIdResponse")
    public JAXBElement<GetDailyTaskByIdResponse> createGetDailyTaskByIdResponse(GetDailyTaskByIdResponse value) {
        return new JAXBElement<GetDailyTaskByIdResponse>(_GetDailyTaskByIdResponse_QNAME, GetDailyTaskByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteById")
    public JAXBElement<DeleteById> createDeleteById(DeleteById value) {
        return new JAXBElement<DeleteById>(_DeleteById_QNAME, DeleteById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddDailyTaskResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "addDailyTaskResponse")
    public JAXBElement<AddDailyTaskResponse> createAddDailyTaskResponse(AddDailyTaskResponse value) {
        return new JAXBElement<AddDailyTaskResponse>(_AddDailyTaskResponse_QNAME, AddDailyTaskResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateByIdResponse")
    public JAXBElement<UpdateByIdResponse> createUpdateByIdResponse(UpdateByIdResponse value) {
        return new JAXBElement<UpdateByIdResponse>(_UpdateByIdResponse_QNAME, UpdateByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "updateById")
    public JAXBElement<UpdateById> createUpdateById(UpdateById value) {
        return new JAXBElement<UpdateById>(_UpdateById_QNAME, UpdateById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByUserId }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByUserId")
    public JAXBElement<GetByUserId> createGetByUserId(GetByUserId value) {
        return new JAXBElement<GetByUserId>(_GetByUserId_QNAME, GetByUserId.class, null, value);
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
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "deleteByIdResponse")
    public JAXBElement<DeleteByIdResponse> createDeleteByIdResponse(DeleteByIdResponse value) {
        return new JAXBElement<DeleteByIdResponse>(_DeleteByIdResponse_QNAME, DeleteByIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetByUserIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getByUserIdResponse")
    public JAXBElement<GetByUserIdResponse> createGetByUserIdResponse(GetByUserIdResponse value) {
        return new JAXBElement<GetByUserIdResponse>(_GetByUserIdResponse_QNAME, GetByUserIdResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link GetDailyTaskById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://service.CB.froad.com/", name = "getDailyTaskById")
    public JAXBElement<GetDailyTaskById> createGetDailyTaskById(GetDailyTaskById value) {
        return new JAXBElement<GetDailyTaskById>(_GetDailyTaskById_QNAME, GetDailyTaskById.class, null, value);
    }

}
