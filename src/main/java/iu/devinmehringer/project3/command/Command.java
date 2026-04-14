package iu.devinmehringer.project3.command;

import iu.devinmehringer.project3.model.log.CommandType;

public interface Command {
    public void execute();
    public CommandType getCommandType();
    public Object getPayload();
}
