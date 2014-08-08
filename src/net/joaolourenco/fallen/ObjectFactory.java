
package net.joaolourenco.fallen;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the net.joaolourenco.fallen package. 
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

    private final static QName _Login_QNAME = new QName("http://fallen.joaolourenco.net/", "Login");
    private final static QName _Update_QNAME = new QName("http://fallen.joaolourenco.net/", "Update");
    private final static QName _ChangeUserAccount_QNAME = new QName("http://fallen.joaolourenco.net/", "ChangeUserAccount");
    private final static QName _AddUser_QNAME = new QName("http://fallen.joaolourenco.net/", "AddUser");
    private final static QName _LogoutResponse_QNAME = new QName("http://fallen.joaolourenco.net/", "LogoutResponse");
    private final static QName _ManageBan_QNAME = new QName("http://fallen.joaolourenco.net/", "ManageBan");
    private final static QName _AddUserResponse_QNAME = new QName("http://fallen.joaolourenco.net/", "AddUserResponse");
    private final static QName _CreateUserResponse_QNAME = new QName("http://fallen.joaolourenco.net/", "CreateUserResponse");
    private final static QName _Logout_QNAME = new QName("http://fallen.joaolourenco.net/", "Logout");
    private final static QName _ChangeUserAccountResponse_QNAME = new QName("http://fallen.joaolourenco.net/", "ChangeUserAccountResponse");
    private final static QName _CreateUser_QNAME = new QName("http://fallen.joaolourenco.net/", "CreateUser");
    private final static QName _ManageUser_QNAME = new QName("http://fallen.joaolourenco.net/", "ManageUser");
    private final static QName _ManageUserResponse_QNAME = new QName("http://fallen.joaolourenco.net/", "ManageUserResponse");
    private final static QName _DeleteUserResponse_QNAME = new QName("http://fallen.joaolourenco.net/", "DeleteUserResponse");
    private final static QName _DeleteUser_QNAME = new QName("http://fallen.joaolourenco.net/", "DeleteUser");
    private final static QName _UpdateResponse_QNAME = new QName("http://fallen.joaolourenco.net/", "UpdateResponse");
    private final static QName _ManageBanResponse_QNAME = new QName("http://fallen.joaolourenco.net/", "ManageBanResponse");
    private final static QName _LoginResponse_QNAME = new QName("http://fallen.joaolourenco.net/", "LoginResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: net.joaolourenco.fallen
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Login }
     * 
     */
    public Login createLogin() {
        return new Login();
    }

    /**
     * Create an instance of {@link AddUser }
     * 
     */
    public AddUser createAddUser() {
        return new AddUser();
    }

    /**
     * Create an instance of {@link LogoutResponse }
     * 
     */
    public LogoutResponse createLogoutResponse() {
        return new LogoutResponse();
    }

    /**
     * Create an instance of {@link Update }
     * 
     */
    public Update createUpdate() {
        return new Update();
    }

    /**
     * Create an instance of {@link ChangeUserAccount }
     * 
     */
    public ChangeUserAccount createChangeUserAccount() {
        return new ChangeUserAccount();
    }

    /**
     * Create an instance of {@link AddUserResponse }
     * 
     */
    public AddUserResponse createAddUserResponse() {
        return new AddUserResponse();
    }

    /**
     * Create an instance of {@link CreateUserResponse }
     * 
     */
    public CreateUserResponse createCreateUserResponse() {
        return new CreateUserResponse();
    }

    /**
     * Create an instance of {@link Logout }
     * 
     */
    public Logout createLogout() {
        return new Logout();
    }

    /**
     * Create an instance of {@link ManageBan }
     * 
     */
    public ManageBan createManageBan() {
        return new ManageBan();
    }

    /**
     * Create an instance of {@link ChangeUserAccountResponse }
     * 
     */
    public ChangeUserAccountResponse createChangeUserAccountResponse() {
        return new ChangeUserAccountResponse();
    }

    /**
     * Create an instance of {@link CreateUser }
     * 
     */
    public CreateUser createCreateUser() {
        return new CreateUser();
    }

    /**
     * Create an instance of {@link DeleteUser }
     * 
     */
    public DeleteUser createDeleteUser() {
        return new DeleteUser();
    }

    /**
     * Create an instance of {@link DeleteUserResponse }
     * 
     */
    public DeleteUserResponse createDeleteUserResponse() {
        return new DeleteUserResponse();
    }

    /**
     * Create an instance of {@link LoginResponse }
     * 
     */
    public LoginResponse createLoginResponse() {
        return new LoginResponse();
    }

    /**
     * Create an instance of {@link UpdateResponse }
     * 
     */
    public UpdateResponse createUpdateResponse() {
        return new UpdateResponse();
    }

    /**
     * Create an instance of {@link ManageBanResponse }
     * 
     */
    public ManageBanResponse createManageBanResponse() {
        return new ManageBanResponse();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Login }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "Login")
    public JAXBElement<Login> createLogin(Login value) {
        return new JAXBElement<Login>(_Login_QNAME, Login.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Update }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "Update")
    public JAXBElement<Update> createUpdate(Update value) {
        return new JAXBElement<Update>(_Update_QNAME, Update.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUserAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "ChangeUserAccount")
    public JAXBElement<ChangeUserAccount> createChangeUserAccount(ChangeUserAccount value) {
        return new JAXBElement<ChangeUserAccount>(_ChangeUserAccount_QNAME, ChangeUserAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "AddUser")
    public JAXBElement<AddUser> createAddUser(AddUser value) {
        return new JAXBElement<AddUser>(_AddUser_QNAME, AddUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LogoutResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "LogoutResponse")
    public JAXBElement<LogoutResponse> createLogoutResponse(LogoutResponse value) {
        return new JAXBElement<LogoutResponse>(_LogoutResponse_QNAME, LogoutResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ManageBan }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "ManageBan")
    public JAXBElement<ManageBan> createManageBan(ManageBan value) {
        return new JAXBElement<ManageBan>(_ManageBan_QNAME, ManageBan.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AddUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "AddUserResponse")
    public JAXBElement<AddUserResponse> createAddUserResponse(AddUserResponse value) {
        return new JAXBElement<AddUserResponse>(_AddUserResponse_QNAME, AddUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "CreateUserResponse")
    public JAXBElement<CreateUserResponse> createCreateUserResponse(CreateUserResponse value) {
        return new JAXBElement<CreateUserResponse>(_CreateUserResponse_QNAME, CreateUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Logout }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "Logout")
    public JAXBElement<Logout> createLogout(Logout value) {
        return new JAXBElement<Logout>(_Logout_QNAME, Logout.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUserAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "ChangeUserAccountResponse")
    public JAXBElement<ChangeUserAccountResponse> createChangeUserAccountResponse(ChangeUserAccountResponse value) {
        return new JAXBElement<ChangeUserAccountResponse>(_ChangeUserAccountResponse_QNAME, ChangeUserAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link CreateUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "CreateUser")
    public JAXBElement<CreateUser> createCreateUser(CreateUser value) {
        return new JAXBElement<CreateUser>(_CreateUser_QNAME, CreateUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUserAccount }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "ManageUser")
    public JAXBElement<ChangeUserAccount> createManageUser(ChangeUserAccount value) {
        return new JAXBElement<ChangeUserAccount>(_ManageUser_QNAME, ChangeUserAccount.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ChangeUserAccountResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "ManageUserResponse")
    public JAXBElement<ChangeUserAccountResponse> createManageUserResponse(ChangeUserAccountResponse value) {
        return new JAXBElement<ChangeUserAccountResponse>(_ManageUserResponse_QNAME, ChangeUserAccountResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUserResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "DeleteUserResponse")
    public JAXBElement<DeleteUserResponse> createDeleteUserResponse(DeleteUserResponse value) {
        return new JAXBElement<DeleteUserResponse>(_DeleteUserResponse_QNAME, DeleteUserResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DeleteUser }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "DeleteUser")
    public JAXBElement<DeleteUser> createDeleteUser(DeleteUser value) {
        return new JAXBElement<DeleteUser>(_DeleteUser_QNAME, DeleteUser.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link UpdateResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "UpdateResponse")
    public JAXBElement<UpdateResponse> createUpdateResponse(UpdateResponse value) {
        return new JAXBElement<UpdateResponse>(_UpdateResponse_QNAME, UpdateResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ManageBanResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "ManageBanResponse")
    public JAXBElement<ManageBanResponse> createManageBanResponse(ManageBanResponse value) {
        return new JAXBElement<ManageBanResponse>(_ManageBanResponse_QNAME, ManageBanResponse.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link LoginResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://fallen.joaolourenco.net/", name = "LoginResponse")
    public JAXBElement<LoginResponse> createLoginResponse(LoginResponse value) {
        return new JAXBElement<LoginResponse>(_LoginResponse_QNAME, LoginResponse.class, null, value);
    }

}
