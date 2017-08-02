package com.mycompany.discordzinho;

/**
 *
 * @author ketolow
 */
import sx.blah.discord.api.ClientBuilder;
import sx.blah.discord.api.IDiscordClient;
import sx.blah.discord.util.DiscordException;

public class Botzinho {

    static IDiscordClient maid;

    public static void main(String[] args) throws DiscordException {
        maid = new ClientBuilder().withToken("your token").login();
        maid.getDispatcher().registerListener(new Maid());
    }

    private static void iniciaBot() {
        if (maid.getGuilds().isEmpty()) {
            try {
                maid.login();
            } catch (DiscordException ex) {
            }
        }
    }
}
