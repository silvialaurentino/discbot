package com.mycompany.discordzinho;

import java.awt.Event;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.text.SimpleDateFormat;
import java.util.Date;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.MessageDeleteEvent;
import sx.blah.discord.handle.impl.events.MessageReceivedEvent;
import sx.blah.discord.handle.impl.events.MessageUpdateEvent;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.impl.events.UserJoinEvent;
import sx.blah.discord.handle.obj.IMessage.Attachment;
import sx.blah.discord.util.DiscordException;
import sx.blah.discord.util.MessageBuilder;
import sx.blah.discord.util.MissingPermissionsException;
import sx.blah.discord.util.RateLimitException;

/**
 *
 * @author ketolow
 */
public class Maid {

    long ultima_msg_que_a_bot_enviou = 0;
    long intervalo_entre_mensagens = 300000;

    @EventSubscriber
    public void delete(MessageDeleteEvent event)
            throws RateLimitException, DiscordException, MissingPermissionsException, IOException {
        String repost = "";
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
        String resposta = "\r\n[" + sdf.format(dt) + "]" + " MENSAGEM DELETADA: " + event.getMessage() + "\r\nPELO MELIANTE: " + event.getMessage().getAuthor().getName()+ "\r\n";
        try {
            Files.write(Paths.get("log.txt"), (resposta + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
        }
        for (Attachment anexo : event.getMessage().getAttachments()) {
            repost += anexo.getUrl() + " ";
        }
        new MessageBuilder(Botzinho.maid).withChannel("channel id")
                .withContent("```MENSAGEM DELETADA: " + event.getMessage() + " " + repost
                        + "\nPELO MELIANTE: " + event.getMessage().getAuthor().getName() + "```").build();
    }
    
    @EventSubscriber
    public void onEdit(MessageUpdateEvent event) throws
            RateLimitException, DiscordException, MissingPermissionsException {
        if (event.getNewMessage().getContent() != null) {
            new MessageBuilder(Botzinho.maid).withChannel("channel id")
                    .withContent("```A MENSAGEM: " + event.getOldMessage().getContent() + "\nFOI EDITADO PARA: "
                            + event.getNewMessage().getContent() + "\nPELO MELIANTE: "
                            + event.getNewMessage().getAuthor().getName() + "```").build();
        } else {
            new MessageBuilder(Botzinho.maid).withChannel("channel id")
                    .withContent("```A MENSAGEM: " + event.getOldMessage().getContent() + "\nFOI EDITADO PARA: "
                            + event.getNewMessage().getContent() + "\nPELO MELIANTE: "
                            + event.getNewMessage().getAuthor().getName() + "```").build();
        }
    }

    @EventSubscriber
    public void bomDia(MessageReceivedEvent event) throws RateLimitException,
            DiscordException, MissingPermissionsException {
        if (event.getMessage().getContent().toLowerCase().contains("bom dia")) {
            if (System.currentTimeMillis() > ultima_msg_que_a_bot_enviou + intervalo_entre_mensagens) {
                ultima_msg_que_a_bot_enviou = System.currentTimeMillis();
                new MessageBuilder(Botzinho.maid).withChannel(event.getMessage().getChannel())
                        .withContent("Não.").build();
            }
        }
    }

    @EventSubscriber
    public void boaNoite(MessageReceivedEvent event) throws RateLimitException,
            DiscordException, MissingPermissionsException {
        if (event.getMessage().getContent().toLowerCase().contains("boa noite")) {
            if (System.currentTimeMillis() > ultima_msg_que_a_bot_enviou + intervalo_entre_mensagens) {
                ultima_msg_que_a_bot_enviou = System.currentTimeMillis();
                new MessageBuilder(Botzinho.maid).withChannel(event.getMessage().getChannel())
                        .withContent("Isso são horas?!").build();
            }
        }
    }

    @EventSubscriber
    public void voltar(MessageReceivedEvent event) throws RateLimitException,
            DiscordException, MissingPermissionsException {
        if (event.getMessage().getContent().toLowerCase().equals("voltei")) {
            if (System.currentTimeMillis() > ultima_msg_que_a_bot_enviou + intervalo_entre_mensagens) {
                ultima_msg_que_a_bot_enviou = System.currentTimeMillis();
                new MessageBuilder(Botzinho.maid).withChannel(event.getMessage().getChannel())
                        .withContent("Tinhas ido? Nem notei.").build();
            }
        }
    }

    @EventSubscriber
    public void maid(MessageReceivedEvent event) throws RateLimitException,
            DiscordException, MissingPermissionsException {
        if (event.getMessage().getMentions().contains(Botzinho.maid.getOurUser())) {
            if (System.currentTimeMillis() > ultima_msg_que_a_bot_enviou + intervalo_entre_mensagens) {
                ultima_msg_que_a_bot_enviou = System.currentTimeMillis();
                new MessageBuilder(Botzinho.maid).withChannel(event.getMessage().getChannel())
                        .withContent("Não estou interessada.").build();
            }
        }
    }

    @EventSubscriber
    public void cry(MessageReceivedEvent event) throws RateLimitException,
            DiscordException, MissingPermissionsException {

        if (event.getMessage().getContent().toLowerCase().contains("cry")) {
            new MessageBuilder(Botzinho.maid).withChannel(event.getMessage().getChannel())
                    .withContent("cry cry").build();
        }
    }
    
    @EventSubscriber
    public void log(MessageReceivedEvent event){
    Date dt = new Date();
    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss.SSS");
    String resposta = "\r\n[" + sdf.format(dt) + "]" + "[#" + event.getMessage().getAuthor().getName() + "]" + ": " + event.getMessage();
    try {
            Files.write(Paths.get("logz.txt"), (resposta + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
	
}