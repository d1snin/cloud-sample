package xyz.d1snin.server.commands;

import xyz.d1snin.server.api.events.channel.ChannelMessageReceivedEvent;
import xyz.d1snin.server.commands.entities.Argument;
import xyz.d1snin.server.commands.entities.Statement;
import xyz.d1snin.server.listeners.Listener;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public abstract class ServerCommand extends Listener<ChannelMessageReceivedEvent> {

  protected String usage = null;

  private Consumer<ChannelMessageReceivedEvent> defaultExecution = null;
  private final List<Statement> statements = new LinkedList<>();
  private Statement statement = null;

  @Override
  protected void execute(ChannelMessageReceivedEvent event) {
    if (usage == null) {
      event
          .getServer()
          .getLogger()
          .warn(String.format("Usage can not be null. (%s)", this.getClass().getSimpleName()));
      return;
    }

    ServerCommandExecutor executor =
        new ServerCommandExecutor(this, event, defaultExecution, statements);

    if (event.getContent().startsWith(usage)) {
      if (!executor.tryToExecute()) {
        executor.trigger();
      }
    }
  }

  protected void exec(Consumer<ChannelMessageReceivedEvent> consumer, Argument... arguments) {
    List<Argument> argumentList = Arrays.asList(arguments);

    if (!argumentList.isEmpty()) {
      Statement statement = new Statement(Arrays.asList(arguments), consumer);

      statements.add(statement);

      argumentList.forEach(
          it -> {
            if (it.isVariableLength()) {
              return;
            }

            if (it.isValueRequired() && it.getUsage() != null) {
              statement.setLength(statement.getLength() + 2);

            } else {
              statement.setLength(statement.getLength() + 1);
            }
          });
    } else {
      defaultExecution = consumer;
    }
  }

  protected void setStatement(Statement statement) {
    this.statement = statement;
  }

  protected String getArgVal(int index) {
    return statement.getArguments().get(index).getValue();
  }
}
