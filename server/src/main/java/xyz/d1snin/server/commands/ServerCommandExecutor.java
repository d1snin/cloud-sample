package xyz.d1snin.server.commands;

import xyz.d1snin.server.api.events.channel.ChannelMessageReceivedEvent;
import xyz.d1snin.server.commands.entities.Argument;
import xyz.d1snin.server.commands.entities.Statement;

import java.util.List;
import java.util.function.Consumer;

public class ServerCommandExecutor {

  private final ServerCommand cmd;
  private final ChannelMessageReceivedEvent event;
  private final Consumer<ChannelMessageReceivedEvent> defaultExecution;
  private final List<Statement> statements;
  private final List<String> args;

  public ServerCommandExecutor(
      ServerCommand cmd,
      ChannelMessageReceivedEvent event,
      Consumer<ChannelMessageReceivedEvent> defaultExecution,
      List<Statement> statements) {
    this.cmd = cmd;
    this.event = event;
    this.defaultExecution = defaultExecution;
    this.statements = statements;
    args = event.getContentArgs();
  }

  public boolean tryToExecute() {
    if (args.size() < 2) {
      if (defaultExecution != null) {
        defaultExecution.accept(event);
        return true;
      } else {
        return false;
      }

    } else {
      if (statements.isEmpty()) {
        return false;
      }

      outer:
      for (Statement s : statements) {
        if (s.getLength() != 0 && s.getLength() != args.size() - 1) {
          continue;
        }

        int argCount = 0;

        for (int i = 0; i < s.getArguments().size(); i++) {
          Argument arg = s.getArguments().get(argCount);

          if (arg.getUsage() != null) {
            if (!arg.getUsage().equals(args.get(i + 1))) {
              if (s.equals(statements.get(statements.size() - 1))) {
                return false;

              } else {
                continue outer;
              }
            }
            if (arg.isValueRequired() || arg.isVariableLength()) {
              if (arg.isVariableLength()) {
                arg.setValue(event.getContentByIndex(i + 2));
                break;
              }

              arg.setValue(args.get(i + 2));
              i++;
            }
          } else {
            if (arg.isVariableLength()) {
              arg.setValue(event.getContentByIndex(i + 1));

            } else {
              arg.setValue(args.get(i + 1));
            }

            argCount++;
          }
        }
        cmd.setStatement(s);
        s.getConsumer().accept(event);
        return true;
      }
    }
    return false;
  }

  public void trigger() {
    event.sendMessageSafe("Invalid command usage.");
  }

  public List<String> getArgs() {
    return args;
  }
}
