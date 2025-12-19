package com.example.notifier.app.service;

import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.*;

@Component
public class TelegramNotificationBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;
    
    private final UserServiceRestClient userServiceRestClient;

    @Autowired
    public TelegramNotificationBot(UserServiceRestClient userServiceRestClient) {
        System.out.println("[TelegramBot] Конструктор вызван - начинается создание бота");
        this.userServiceRestClient = userServiceRestClient;
    }

    @PostConstruct
    public void logBotConfig() {
        System.out.println("[TelegramBot] ===== PostConstruct: Бот инициализирован =====");
        System.out.println("[TelegramBot] Token: " + (botToken != null ? botToken.substring(0, 10) + "..." : "null"));
        System.out.println("[TelegramBot] Username: " + botUsername);
        System.out.println("[TelegramBot] =====================================");
        
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(this);
            System.out.println("[TelegramBot] ✓ БОТ УСПЕШНО ЗАРЕГИСТРИРОВАН В TELEGRAM API");
        } catch (Exception e) {
            System.out.println("[TelegramBot] ✗ ОШИБКА регистрации бота: " + e.getMessage());
            e.printStackTrace();
        }
    }



    @Override
    public String getBotUsername() {
        System.out.println("[TelegramBot] getBotUsername() called: " + botUsername);
        return botUsername;
    }

    @Override
    public String getBotToken() {
        System.out.println("[TelegramBot] getBotToken() called: " + (botToken != null ? botToken.substring(0, 10) + "..." : "null"));
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        System.out.println("[TelegramBot] ===== onUpdateReceived ВЫЗВАН! =====");
        System.out.println("[TelegramBot] Update object: " + update);
        if (update != null) {
            System.out.println("[TelegramBot] Has message: " + update.hasMessage());
            if (update.hasMessage()) {
                Long chatId = update.getMessage().getChatId();
                String username = update.getMessage().getFrom() != null ? update.getMessage().getFrom().getUserName() : null;
                String firstName = update.getMessage().getFrom() != null ? update.getMessage().getFrom().getFirstName() : null;
                String text = update.getMessage().hasText() ? update.getMessage().getText() : null;
                System.out.println("[TelegramBot] ===== СООБЩЕНИЕ ПОЛУЧЕНО =====");
                System.out.println("[TelegramBot] Chat ID: " + chatId);
                System.out.println("[TelegramBot] Username: " + username);
                System.out.println("[TelegramBot] First Name: " + firstName);
                System.out.println("[TelegramBot] Text: " + text);
                System.out.println("[TelegramBot] ================================");
                
                // Обработка команды !Подписаться
                if (text != null && text.equals("Подписаться")) {
                    handleSubscribe(chatId, username, firstName);
                } else if (text != null && text.equals("Отписаться")) {
                        handleUnsubscribe(chatId, username);
                    }
            } else {
                System.out.println("[TelegramBot] Update имеет ID: " + update.getUpdateId() + ", но нет сообщения");
            }
        }
    }

        private void handleUnsubscribe(Long chatId, String username) {
            try {
                System.out.println("[TelegramBot] Обработка отписки для chatId=" + chatId);
                //все пользователи из UserService
                List<Map<String, Object>> users = userServiceRestClient.getAllUsers();
                String foundUserId = null;
                for (Map<String, Object> user : users) {
                    Object socialsObj = user.get("socials");
                    if (socialsObj instanceof Map socials) {
                        Object telegramObj = socials.get("Telegram");
                        if (telegramObj instanceof Map telegram) {
                            String url = (String) telegram.get("url");
                            if (url != null && url.equalsIgnoreCase("@" + username)) {
                                foundUserId = (String) user.get("oid");
                                break;
                            }
                        }
                    }
                }
                if (foundUserId != null) {
                    userServiceRestClient.disableTelegramNotifications(foundUserId);
                    sendNotification(String.valueOf(chatId), "Вы успешно отписались от Telegram-уведомлений!");
                    System.out.println("[TelegramBot] ✓ Пользователь c id=" + foundUserId + " отписан от уведомлений Telegram");
                } else {
                    sendNotification(String.valueOf(chatId), "Не найден пользователь с таким Telegram в UserService");
                    System.out.println("[TelegramBot] Не найден пользователь с таким Telegram в UserService");
                }
            } catch (Exception e) {
                System.out.println("[TelegramBot] ✗ ОШИБКА при обработке отписки: " + e.getMessage());
                e.printStackTrace();
                try {
                    sendNotification(String.valueOf(chatId), "Ошибка при отписке. Попробуйте позже.");
                } catch (Exception ex) {
                    System.out.println("[TelegramBot] Не удалось отправить сообщение об ошибке");
                }
            }
        }
    
    private void handleSubscribe(Long chatId, String username, String firstName) {
        try {
            System.out.println("[TelegramBot] Обработка подписки для chatId=" + chatId);

            // Получаем всех пользователей из UserService
            List<Map<String, Object>> users = userServiceRestClient.getAllUsers();
            String foundUserId = null;
            for (Map<String, Object> user : users) {
                Object socialsObj = user.get("socials");
                if (socialsObj instanceof Map socials) {
                    Object telegramObj = socials.get("Telegram");
                    if (telegramObj instanceof Map telegram) {
                        String url = (String) telegram.get("url");
                        if (url != null && url.equalsIgnoreCase("@" + username)) {
                            foundUserId = (String) user.get("oid");
                            break;
                        }
                    }
                }
            }

            if (foundUserId != null) {
                userServiceRestClient.updateTelegramChatId(foundUserId, String.valueOf(chatId));
                sendNotification(String.valueOf(chatId), "✓ Вы успешно подписались на уведомления!");
                System.out.println("[TelegramBot] ✓ Пользователь c id=" + foundUserId + " подписан на уведомления Telegram");
            } else {
                sendNotification(String.valueOf(chatId), "✗ Не найден пользователь с таким Telegram в UserService");
                System.out.println("[TelegramBot] ✗ Не найден пользователь с таким Telegram в UserService");
            }
        } catch (Exception e) {
            System.out.println("[TelegramBot] ✗ ОШИБКА при обработке подписки: " + e.getMessage());
            e.printStackTrace();
            try {
                sendNotification(String.valueOf(chatId), "✗ Ошибка при подписке. Попробуйте позже.");
            } catch (Exception ex) {
                System.out.println("[TelegramBot] Не удалось отправить сообщение об ошибке");
            }
        }
    }

    public void sendNotification(String chatId, String message) {
        System.out.println("[TelegramBot] Отправка уведомления: chatId=" + chatId + ", message=" + message);
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(message);
        try {
            execute(sendMessage);
            System.out.println("[TelegramBot] ✓ Сообщение успешно отправлено в chat=" + chatId);
        } catch (TelegramApiException e) {
            System.out.println("[TelegramBot] ✗ ОШИБКА отправки в chat=" + chatId + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
}
