package entity;

import java.io.Serializable;
import java.util.ArrayList;

import enums.ActionType;

/**
 * This class include the replay from server to user.
 * @author nire
 *
 */
public class Replay implements Serializable {
	
/**
* variable serialVersionUID for serializable.
*/
private static final long serialVersionUID = 1L;

/**
 * Kind of action.
 */
private ActionType type;


/**
 * Kind of response.
 */
private boolean sucess;

/**
 * action to do when replay
 */
private int action;

/**
 * Show general messages
 */
private String GnrlMsg;

/**
 * Array list of elements to return to the Client.
 */
private ArrayList<String> elementsList;


/** Replay constructor that initialize the attributes.
 * @param type - Gets the type of action.
 * @param sucess - Gets the kind of response from SQL.
 */
public Replay(ActionType type, boolean sucess)
{
	setType(type);
	setSucess(sucess);
}

/** Replay constructor that initialize the attributes.
 * @param type - Gets the type of action.
 * @param sucess - Gets the kind of response from SQL.
 * @param action - Gets a sub-action to do.
 * @param elementsList - Gets the elements list that will be decrypt in the client.
 */
public Replay(ActionType type, boolean sucess,int action,ArrayList<String> elementsList)
{
	setType(type);
	setSucess(sucess);
	setAction(action);
	setElementsList(elementsList);
}

/** Replay constructor that initialize the attributes.
 * @param type - Gets the type of action.
 * @param sucess - Gets the kind of response from SQL.
 * @param msg - Gets a message.
 */
public Replay(ActionType type, boolean sucess,String msg)
{
	setType(type);
	setSucess(sucess);
	setGnrlMsg(msg);
}

/** Getter for action type.
 * @return - the type attribute.
 */
public ActionType getType() {
	return type;
}

/** Setter for action type.
 * @param type - Set the value into type.
 */
public void setType(ActionType type) {
	this.type = type;
}


/** Getter for sucess.
 * @return - the sucess attribute.
 */
public boolean getSucess() {
	return sucess;
}

/** Getter for action
 * @return - the action attribute.
 */
public int getAction() {
	return action;
}

/** Getter for GeneralMessages
 * @return - the GeneralMessages attribute.
 */
public String getGnrlMsg() {
	return GnrlMsg;
}

/** Getter for elements list.
 * @return - Gets the elements list.
 */
public ArrayList<String> getElementsList() {
	return elementsList;
}

/** Setter for sucess.
 * @param sucess - Set the value into sucess.
 */
public void setSucess(boolean sucess) {
	this.sucess = sucess;
}	

/** Setter for action
 * @param action - Set the value into action
 */
public void setAction(int action) {
	this.action = action;
}

/** Setter for GeneralMessages
 * @param msg
 */
public void setGnrlMsg(String msg) {
	this.GnrlMsg = msg;
}

/** Setter for elements list.
 * @param elementsList - Set the elementsList.
 */
public void setElementsList(ArrayList<String> elementsList) {
	this.elementsList = elementsList;
}
	
}
