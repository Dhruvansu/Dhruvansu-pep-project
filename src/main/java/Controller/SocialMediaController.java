package Controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Model.*;
import Service.*;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.get("example-endpoint", this::exampleHandler);
        app.post("/register", this::postAccountHandler);
        app.post("/login", this::postUserLoginHandler);
        app.post("/messages", this::postMessagesHandler);
        app.get("/messages", this::getAllMessagesHandler);
        app.get("/messages/{message_id}", this::getMessageUsingMessageIdHandler);
        app.delete("/messages/{message_id}", this::deleteMessageUsingMessageIdHandler);
        app.patch("/messages/{message_id}", this::messageUpdateHandler);
        app.get("/accounts/{account_id}/messages", this::getMessageUsingAccountIdHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void exampleHandler(Context context) {
        context.json("sample text");
    }

    /**
     * Method handler for account creation
     * @param context The Javalin Context object which manages information about the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void postAccountHandler(Context context) throws JsonProcessingException{
        ObjectMapper obj = new ObjectMapper();
        Account acc = obj.readValue(context.body(), Account.class);
        Account addedAcc = accountService.addAccount(acc);
        if (addedAcc != null){
            context.status(200);
            context.json(obj.writeValueAsString(addedAcc));
        } else{
            context.status(400);
        }
    }

    /**
     * Method handler for User logins
     * @param context The Javalin Context object which manages information about the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void postUserLoginHandler(Context context) throws JsonProcessingException{
        ObjectMapper obj = new ObjectMapper();
        Account acc = obj.readValue(context.body(), Account.class);
        Account validAccount = accountService.checkIfAccountCredsAreValid(acc);
        if(validAccount != null){
            context.json(obj.writeValueAsString(validAccount));
            context.status(200);
        } else{
            context.status(401);
        }
    }

    /**
     * Method handler for posting a message
     * @param context The Javalin Context object which manages information about the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void postMessagesHandler(Context context) throws JsonProcessingException{
        ObjectMapper obj = new ObjectMapper();
        Message msg = obj.readValue(context.body(), Message.class);
        Message addedMsg = messageService.addMessage(msg);
        if(addedMsg != null){
            context.json(obj.writeValueAsString(addedMsg));
            context.status(200);
        } else{
            context.status(400);
        }
    }

    /**
     * Method handler for reteriving all the messages
     * @param context The Javalin Context object which manages information about the HTTP request and response.
     */
    private void getAllMessagesHandler(Context context){
        context.json(messageService.getAllMessages());
        context.status(200);
    }

    /**
     * Method handler for reteriving a message using message_id
     * @param context The Javalin Context object which manages information about the HTTP request and response.
     */
    private void getMessageUsingMessageIdHandler(Context context){
        int msg_id = Integer.parseInt(context.pathParam("message_id"));
        Message msg = messageService.getMessageByMessageId(msg_id);
        if(msg != null){
            context.json(msg);
            context.status(200);
        }
        context.result();
        context.status(200);
    }

    /**
     * Method handler for posting a message
     * @param context The Javalin Context object which manages information about the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void deleteMessageUsingMessageIdHandler(Context context) throws JsonProcessingException{
        ObjectMapper obj = new ObjectMapper();
        Message msg = obj.readValue(context.body(), Message.class);
        int msg_id = Integer.parseInt(context.pathParam("message_id"));
        Message deletedMsg = messageService.deleteMessage(msg_id, msg);
        if(deletedMsg != null) {
            context.json(obj.writeValueAsString(deletedMsg));
            context.status(200);
        } else{
            context.json("");
            context.status(200);
        }
    }

    /**
     * Method handler for updating an exiting message's message body
     * @param context The Javalin Context object which manages information about the HTTP request and response.
     * @throws JsonProcessingException will be thrown if there is an issue converting JSON into an object
     */
    private void messageUpdateHandler(Context context) throws JsonProcessingException{
        ObjectMapper obj = new ObjectMapper();
        int msg_id = Integer.parseInt(context.pathParam("message_id"));
        String bodyUpdate = obj.readValue(context.body(), String.class);
        Message updatedMsg = messageService.messageBodyUpdate(msg_id, bodyUpdate);
        if(updatedMsg != null){
            context.json(updatedMsg);
            context.status(200);
        } else{
            context.status(400);
        }
    }

    /**
     * Method handler for getting messages from a specific user
     * @param context The Javalin Context object which manages information about the HTTP request and response.
    */
    private void getMessageUsingAccountIdHandler(Context context){
        int acc_id = Integer.parseInt(context.pathParam("account_id"));
        context.json(messageService.getMessageByAccountId(acc_id));
        context.status(200);
    }
}