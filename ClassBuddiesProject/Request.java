
import java.util.UUID;

public class Request {

  final private Student requester;
  final private Student requestedUser;
  final private UUID requestID;

  public Request(Student requester, Student requestedUser){
    this.requester = requester;
    this.requestedUser = requestedUser;
    this.requestID = UUID.randomUUID();
  }

  public void denyRequest(){

  }

  public void acceptRequest(){

  }


  public Student getRequester() {
    return requester;
  }

  public Student getRequestedUser() {
    return requestedUser;
  }

  public UUID getRequestID() {
    return requestID;
  }

}
