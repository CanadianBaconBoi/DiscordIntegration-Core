package de.erdbeerbaerlp.dcintegration.common.discordCommands;

import de.erdbeerbaerlp.dcintegration.common.storage.configCmd.ConfigCommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.requests.restaction.interactions.ReplyCallbackAction;

import static de.erdbeerbaerlp.dcintegration.common.util.Variables.discord_instance;


public class CommandFromCFG extends DiscordCommand {
    private final String mcCmd;
    private final boolean admin;
    private final ConfigCommand.CommandArgument[] args;
    private final boolean hidden;

    public CommandFromCFG(String cmd, String description, String mcCommand, boolean adminOnly, ConfigCommand.CommandArgument[] args, boolean hidden) throws IllegalArgumentException {
        super(cmd, description);
        this.isConfigCmd = true;
        this.admin = adminOnly;
        this.mcCmd = mcCommand;
        this.hidden = hidden;
        if (args != null) {
            this.args = args;
            for (ConfigCommand.CommandArgument argument : args) {
                addOption(OptionType.STRING, argument.name, argument.description, !argument.optional);
            }
        } else {
            this.args = new ConfigCommand.CommandArgument[0];
        }
    }

    @Override
    public boolean adminOnly() {
        return admin;
    }

    /**
     * Sets the description for the help command
     */

    @Override
    public String getCommandUsage() {
        if (useArgs) return super.getCommandUsage() + " " + argText;
        else return super.getCommandUsage();
    }

    @Override
    public void execute(SlashCommandInteractionEvent ev, ReplyCallbackAction reply) {
        String cmd = mcCmd;
        for (ConfigCommand.CommandArgument arg : args) {
            final OptionMapping option = ev.getOption(arg.name);
            cmd = cmd.replace("%" + arg.name + "%", option == null ? "" : option.getAsString());

        }
        discord_instance.srv.runMcCommand(cmd, reply.setEphemeral(hidden).submit(), ev.getUser());
    }

}
