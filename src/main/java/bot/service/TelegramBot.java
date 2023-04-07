package bot.service;

import bot.config.BotConfig;
import bot.config.Mathematica;
import com.google.zxing.WriterException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.methods.send.SendVideoNote;
import org.telegram.telegrambots.meta.api.methods.send.SendVoice;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {
    String str = "";
    final BotConfig config;

    public TelegramBot(BotConfig config) throws FileNotFoundException {
        this.config = config;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()){
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String name = update.getMessage().getChat().getFirstName();
            str +=" "+ messageText;

            if(messageText.equals("/start")){
                SendMessage sendMessage =new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("привет я крутой математик!\nВведи /math чтобы я показал свои умения!");
                execute(sendMessage);
                str = "";
            }else
            if(messageText.equals("/math")){
                SendMessage sendMessage =new SendMessage();
                sendMessage.setChatId(chatId);
                ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
                List<KeyboardRow> keyboard = new ArrayList<>();
                KeyboardRow row1 = new KeyboardRow();
                row1.add(new KeyboardButton("v1"));
                KeyboardRow row2 = new KeyboardRow();
                row2.add(new KeyboardButton("v2"));
                KeyboardRow row3 = new KeyboardRow();
                row3.add(new KeyboardButton("v3"));
                KeyboardRow row4 = new KeyboardRow();
                row4.add(new KeyboardButton("v4"));
                KeyboardRow row5 = new KeyboardRow();
                row5.add(new KeyboardButton("v5"));
                KeyboardRow row6 = new KeyboardRow();
                row6.add(new KeyboardButton("v6"));
                KeyboardRow row7 = new KeyboardRow();
                row7.add(new KeyboardButton("v7"));
                keyboard.add(row1);
                keyboard.add(row2);
                keyboard.add(row3);
                keyboard.add(row4);
                keyboard.add(row5);
                keyboard.add(row6);
                keyboard.add(row7);
                keyboardMarkup.setKeyboard(keyboard);
                keyboardMarkup.setOneTimeKeyboard(true);
                sendMessage.setReplyMarkup(keyboardMarkup);
                sendMessage.setText("Привет, ты открыл раздел по математике\n" +
                        "Сейчас на твой выбор предложутся различные варианты, решение которых тебе потребуется" +
                        "\nТак же прошу ввести аргументы для решения данного варианта!");
                SendPhoto sendPhoto = new SendPhoto();
                sendPhoto.setChatId(chatId);
                InputFile inputFile = new InputFile(new File("/Users/daniilmarukha/Desktop/TelegramBot/src/main/java/bot/files/img.png"));
                sendPhoto.setPhoto(inputFile);
                execute(sendMessage);
                execute(sendPhoto);
            }else
            if(str.length() >5 && str.length() < 10 && str.contains("/math v")) {



                String variant = str.substring(7);
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Вы выбрали варинт : "+variant);
                execute(sendMessage);
                switch (variant){
                    case("v1") -> sendMessage.setText("Для 1 варианта надо 5 аргументов!");
                    case("v2") -> sendMessage.setText("Для 2 варианта надо 5 аргументов!");
                    case("v3") -> sendMessage.setText("Для 3 варианта надо 4 аргумента!");
                    case("v4") -> sendMessage.setText("Для 4 варианта надо 2 аргумента!");
                    case("v5") -> sendMessage.setText("Для 5 варианта надо 6 аргументов!");
                    case("v6") -> sendMessage.setText("Для 6 варианта надо 2 аргумента!");
                    case("v7") -> sendMessage.setText("Для 7 варианта надо 1 аргумент!");
                }
                execute(sendMessage);
            }else if(str.length() > 10 && str.contains("/math v")){
                str = str.substring(7);
                String [] arr= str.split(" ");
                classWork(arr,chatId);

                str = "";
                SendMessage sendMessage = new SendMessage();
                sendMessage.setChatId(chatId);
                sendMessage.setText("Используйте комманду /math чтобы начать заново");
                execute(sendMessage);
            }else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Я вас не понял, сейчас начинайте все сначала! /math");
                sendMessage.setChatId(chatId);
                execute(sendMessage);
                str = "";
            }

            System.out.println(str);
        }
    }

    private void classWork(String [] args, long chatId) throws TelegramApiException {
        try{
            Mathematica mathematica = new Mathematica();
            double asnwer = mathematica.doWork(args);
            SendMessage message = new SendMessage();
            message.setText(String.valueOf(asnwer));
            message.setChatId(chatId);
            execute(message);
        }catch (IndexOutOfBoundsException e){
            SendMessage sendMessage =new SendMessage();
            sendMessage.setText("Index out of bound!\nЭто значит что вы ввели неправильное количетсво переменных! ");
            sendMessage.setChatId(chatId);
            execute(sendMessage);
        }
    }
}
