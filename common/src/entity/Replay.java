package entity;

import java.io.Serializable;

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


/** Replay constructor that initialize the attributes.
 * @param type - Gets the type of action.
 * @param sucess - Gets the kind of response from SQL.
 */
public Replay(ActionType type, boolean sucess)
{
	setType(type);
	setSucess(sucess);
}

public Replay(ActionType type, boolean sucess,int action)
{
	setType(type);
	setSucess(sucess);
	setAction(action);
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
	
}
