package murojat.appmurojat.bot;

import murojat.appmurojat.entity.Murojaatlar;
import murojat.appmurojat.entity.Staff;
import murojat.appmurojat.entity.enums.Status;
import murojat.appmurojat.payload.ResMurojaat;
import murojat.appmurojat.repository.ChildAdminRepository;
import murojat.appmurojat.repository.MurojaatlarRepository;
import murojat.appmurojat.repository.StaffRepository;
import murojat.appmurojat.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.*;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class MurojaatBot extends TelegramLongPollingBot {

    @Autowired
    BotService botService;

    @Autowired
    StafftBotService applicantService;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    MurojaatlarRepository murojaatlarRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChildAdminBotService childAdminBotService;

    @Autowired
    ChildAdminRepository childAdminRepository;

    int level = 0;

    Murojaatlar murojaatlar = null;

    String resMurojaatId = "";

    String text = "";

    @Override
    public void onUpdateReceived(Update update) {
        SendMessage sendMessage = new SendMessage();
        if (update.hasInlineQuery()) {
            level = 0;
            if (update.getInlineQuery().hasQuery()) {
                text = update.getInlineQuery().getQuery();
                if (text.endsWith("#")) {
                    text = text.substring(0, text.lastIndexOf("#"));
                    try {
                        execute(childAdminBotService.inlineQuery(update, text));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        if (update.hasMessage()) {
            Message message = update.getMessage();
            if (message.hasText()) {
                if (userRepository.existsByRegisterCodeEqualsIgnoreCase(message.getText())) {

                    level = 7;
                }
                if (userRepository.existsByRegisterCodeAdminEqualsIgnoreCase(message.getText())) {
                    level = 14;
                }
//                if () {
                if (message.getText().equals("/start")) {
                    try {
                        execute(botService.startMurojaat(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
//                    else {
//                        sendMessage.setText("/start <= босинг");
//                        sendMessage.setChatId(update.getMessage().getChatId());
//                        try {
//                            execute(sendMessage);
//                        } catch (TelegramApiException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }
                if (message.getText().equals("/stop")) {
                    level = 0;
                    if (murojaatlar != null) {
                        murojaatlarRepository.delete(murojaatlar);
                        murojaatlar = null;
                    }

                }
            }
        } else if (update.hasCallbackQuery()) {
            String data = update.getCallbackQuery().getData();
            if (data.equals(Constant.START_MUROJAAT)) level = 1;
            if (level == 2 && Constant.ANDIJON_SH.equals(data)) level = 3;
            if (level == 2 && Constant.XONOBOD_SH.equals(data)) level = 3;
            if (level == 2 && Constant.ANDIJON_T.equals(data)) level = 3;
            if (level == 2 && Constant.ASAKA_T.equals(data)) level = 3;
            if (level == 2 && Constant.BALIQCHI_T.equals(data)) level = 3;
            if (level == 2 && Constant.BOZ_T.equals(data)) level = 3;
            if (level == 2 && Constant.BULOQBOSHI_T.equals(data)) level = 3;
            if (level == 2 && Constant.JALAQUDUQ_T.equals(data)) level = 3;
            if (level == 2 && Constant.IZBOSKAN_T.equals(data)) level = 3;
            if (level == 2 && Constant.MARHAMAT_T.equals(data)) level = 3;
            if (level == 2 && Constant.OLTINKOL_T.equals(data)) level = 3;
            if (level == 2 && Constant.PAXTABOD_T.equals(data)) level = 3;
            if (level == 2 && Constant.ULUGNOR_T.equals(data)) level = 3;
            if (level == 2 && Constant.XOJABOD_T.equals(data)) level = 3;
            if (level == 2 && Constant.SHAXRIXON_T.equals(data)) level = 3;
            if (level == 2 && Constant.QORGONTEPA_T.equals(data)) level = 3;
            if (level == 3 && Constant.GAZ.equals(data)) level = 4;
            if (level == 3 && Constant.SUV.equals(data)) level = 4;
            if (level == 3 && Constant.UY_JOY.equals(data)) level = 4;
            if (level == 4 && Constant.TEXT.equals(data)) level = 5;
            if (level == 4 && Constant.PHOTO.equals(data)) level = 5;
            if (level == 4 && Constant.AUDIO.equals(data)) level = 5;
            if (level == 4 && Constant.VIDEO.equals(data)) level = 5;
            if (level == 8 && Constant.GAZ.equals(data)) level = 9;
            if (level == 8 && Constant.SUV.equals(data)) level = 9;
            if (level == 8 && Constant.UY_JOY.equals(data)) level = 9;
            if (data.equals(Constant.START_MUROJAATADMIN)) level = 18;
            if (level == 18 && Constant.FACEBOOK.equals(data)) level = 19;
            if (level == 18 && Constant.INSTAGRAM.equals(data)) level = 19;
            if (level == 19 && Constant.UY_JOY.equals(data)) level = 20;
            if (level == 19 && Constant.SUV.equals(data)) level = 20;
            if (level == 19 && Constant.GAZ.equals(data)) level = 20;
            if (Constant.RECEIVE.equals(data)) {
                level = 13;
            }
            try {
                UUID uuid = UUID.fromString(data);
                if (murojaatlarRepository.existsById(uuid)) level = 10;
            } catch (IllegalArgumentException exception) {
                //handle the case where string is not valid UUID
            }
            String[] split = data.split("/");
            if (split[0].equals(Constant.RECEIVE_QUERY) && murojaatlarRepository.existsById(UUID.fromString(split[1]))) {
                childAdminBotService.queryRecive(update, split[1]);
            }
            if (Constant.MAMNUNMAN.equals(split[0])) botService.mamnunVsMamnunmemas(split[1], Status.CHECKED);
            if (Constant.MAMNUNEMASMAN.equals(split[0])) botService.mamnunVsMamnunmemas(split[1], Status.NO_CHECKED);
        }
        switch (level) {
            case 14:
                if (childAdminRepository.existsByChatId(update.getMessage().getChatId())) {
                    sendMessage.setChatId(update.getMessage().getChatId());
                    sendMessage.setText("сиз алла қачон ройҳатдан ўткансиз");
                    try {
                        execute(sendMessage);
                        execute(childAdminBotService.startMurojaatAdmin(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    level = 0;
                } else {
                    try {
                        execute(childAdminBotService.registerName(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    level = 15;
                }
                break;
            case 15:
                childAdminBotService.saveName(update);
                try {
                    execute(childAdminBotService.shareContact(update));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                level = 16;
                break;
            case 16:
                try {
                    execute(childAdminBotService.saveContact(update));
                    execute(childAdminBotService.startMurojaatAdmin(update));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                break;
            case 18:
                if (update.hasCallbackQuery()) {
                    try {
                        execute(childAdminBotService.chooseSource(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 19:
                if (update.hasCallbackQuery()) {
                    Murojaatlar murojaatlar = new Murojaatlar();
                    murojaatlar.setChatId(update.getCallbackQuery().getMessage().getChatId());
                    murojaatlar.setSource(update.getCallbackQuery().getData());
                    murojaatlarRepository.save(murojaatlar);
                    try {
                        execute(botService.categorys(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 20:
                if (update.hasCallbackQuery()) {
                    Murojaatlar byChatId = murojaatlarRepository.getByChatId(update.getCallbackQuery().getMessage().getChatId());
                    byChatId.setCategory(update.getCallbackQuery().getData());
                    murojaatlarRepository.save(byChatId);
                    sendMessage.setChatId(update.getCallbackQuery().getMessage().getChatId());
                    sendMessage.setText(Constant.SENDMUROJAAT);
                    try {
                        execute(sendMessage);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    level = 21;
                }
                break;
            case 21:
                if (update.hasMessage()) {
                    Murojaatlar byChatId = murojaatlarRepository.getByChatId(update.getMessage().getChatId());
                    try {
                        sendMurojaat(update, byChatId);
                        execute(childAdminBotService.startMurojaatAdmin(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                level = 0;
                break;
            case 1:
                if (update.hasCallbackQuery()) {
                    try {
                        execute(botService.shareContact(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                    level = 2;
                }
                break;
            case 2:
                String phoneNum = update.getMessage().getText();
                if (update.getMessage().hasContact() || phoneNum.startsWith("+998") || phoneNum.startsWith("9") || phoneNum.startsWith("7")) {
                    murojaatlar = new Murojaatlar();
                    try {
                        execute(botService.saveContact(update, murojaatlar));
                        execute(botService.districtsAndijon(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                if (update.hasCallbackQuery()) {
                    murojaatlar.setDistrict(update.getCallbackQuery().getData());
                    murojaatlarRepository.save(murojaatlar);
                    try {
                        execute(botService.categorys(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 4:
                if (update.hasCallbackQuery()) {
                    murojaatlar.setCategory(update.getCallbackQuery().getData());
                    murojaatlar.setSource(Constant.TELEGRAM_BOT);
                    murojaatlarRepository.save(murojaatlar);
                    try {
                        execute(botService.murojaatType(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 5:
                try {
                    if (update.hasMessage()) {
                        if (sendMurojaat(update, murojaatlar)) {
                            execute(botService.saveMurojaat(update));
                            sendMessage.setChatId(update.getMessage().getChatId());
                            sendMessage.setText("мурожаатни кайта йўллаш учун /start боcинг");
                            sendMessage.setParseMode(ParseMode.MARKDOWN);
                            execute(sendMessage);
                            level = 6;
                        }
                    } else {
                        execute(botService.level5(update));
                    }
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            case 6:
                murojaatlar = null;
                if (update.hasMessage()) {
                    Message message = update.getMessage();
                    if (message.hasText()) {
                        if (message.getText().equals("/start")) {
                            level = 1;
                        } else {
                            sendMessage.setChatId(update.getMessage().getChatId());
                            sendMessage.setText("мурожаатни йўллаш учун /start <= боcинг");
                            sendMessage.setParseMode(ParseMode.MARKDOWN);
                            try {
                                execute(sendMessage);
                            } catch (TelegramApiException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                break;
            case 7:
                if (update.hasMessage()) {
                    if (staffRepository.existsByChatId(update.getMessage().getChatId())) {
                        sendMessage.setChatId(update.getMessage().getChatId());
                        sendMessage.setText("сиз алла қачон ройҳатдан ўткансиз");
                        level = 0;
                        try {
                            execute(sendMessage);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Staff staff = new Staff();
                        staff.setChatId(update.getMessage().getChatId());
                        staffRepository.save(staff);
                        try {
                            execute(applicantService.name(update));
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                        level = 8;
                    }
                }
                break;
            case 8:
                if (update.hasMessage()) {
                    Staff byChatId = staffRepository.getByChatId(update.getMessage().getChatId());
                    byChatId.setName(update.getMessage().getText());
                    staffRepository.save(byChatId);
                    try {
                        execute(applicantService.registerStaff(update));
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 9:
                Staff byChatId = staffRepository.getByChatId(update.getCallbackQuery().getMessage().getChatId());
                byChatId.setCategory(update.getCallbackQuery().getData());
                staffRepository.save(byChatId);
                try {
                    execute(botService.shareContact(update));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                level = 12;
                break;
            case 10:
                Murojaatlar murojaatlar = murojaatlarRepository.findById(UUID.fromString(update.getCallbackQuery().getData())).orElseThrow(() -> new ResourceNotFoundException("getMurojaat"));
                try {
                    execute(botService.checkCloseApplication(murojaatlar.getChatId(), murojaatlar.getId().toString()));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                level = 0;
                break;
            case 11:
                getThreeDays();
                level = 0;
                break;
            case 12:
                Staff staff1 = staffRepository.getByChatId(update.getMessage().getChatId());
                try {
                    execute(applicantService.saveContact(update, staff1));
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                level = 0;
                break;
            case 13:
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> replyKeyboarbuttons = new ArrayList<>();
                List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
                InlineKeyboardButton inlineButton = new InlineKeyboardButton(Constant.CHECKED);
                inlineButton.setCallbackData(resMurojaatId);
                inlineKeyboardButtonList1.add(inlineButton);
                replyKeyboarbuttons.add(inlineKeyboardButtonList1);
                inlineKeyboardMarkup.setKeyboard(replyKeyboarbuttons);

                EditMessageText editMessageText = new EditMessageText()
                        .setChatId(update.getCallbackQuery().getMessage().getChatId())
                        .setMessageId(update.getCallbackQuery().getMessage().getMessageId())
                        .setText("мурожаатни ёпиш тугамасини босишни унутманг");
                EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
                editMessageReplyMarkup.setChatId(update.getCallbackQuery().getMessage().getChatId());
                editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
                editMessageReplyMarkup.setInlineMessageId(update.getCallbackQuery().getInlineMessageId());
                editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
                Murojaatlar murojaatlar1 = murojaatlarRepository.findById(UUID.fromString(resMurojaatId)).orElseThrow(() -> new ResourceNotFoundException("getMurojaat"));
                murojaatlar1.setStatus(Status.RECEIVE);
                murojaatlarRepository.save(murojaatlar1);
                try {
                    execute(editMessageText);
                    execute(editMessageReplyMarkup);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                break;
            default:
                level = 0;
                break;
        }
    }


    @Scheduled(cron = "0 0 8 * * *")
    public void getThreeDays() {
        level = 11;
        List<Murojaatlar> byOut = murojaatlarRepository.getByOut();
        if (byOut.size() != 0) {
            for (Murojaatlar murojaatlar1 : byOut) {
                murojaatlar1.setStatus(Status.NO_CHECKED);
                murojaatlarRepository.save(murojaatlar1);
            }
        }
        List<Murojaatlar> byStatusAndDay3 = murojaatlarRepository.getByStatusAndDay3();
        if (byStatusAndDay3.size() != 0) {
            List<Staff> all = staffRepository.findAll();
            for (Murojaatlar murojaatlar1 : byStatusAndDay3) {
                ResMurojaat resMurojaat = applicantService.resMurojaat(murojaatlar1, "йопилмаганлар ройхатига тушиш учун 3 кун колди");
                tempSendMurojaat(all, resMurojaat);
            }
        }
        List<Murojaatlar> byStatusAndDay1 = murojaatlarRepository.getByStatusAndDay1();
        if (byStatusAndDay1.size() != 0) {
            List<Staff> all = staffRepository.findAll();
            for (Murojaatlar murojaatlar1 : byStatusAndDay1) {
                ResMurojaat resMurojaat = applicantService.resMurojaat(murojaatlar1, "йопилмаганлар ройхатига тушиш учун 1 кун колди");
                tempSendMurojaat(all, resMurojaat);
            }
        }

        List<Murojaatlar> byNoReady = murojaatlarRepository.getByNoReady();
        if (byNoReady.size() != 0) {
            for (Murojaatlar murojaatlar1 : byNoReady) {
                murojaatlarRepository.delete(murojaatlar1);
            }
        }
    }

    public void tempSendMurojaat(List<Staff> all, ResMurojaat resMurojaat) {
        SendMessage sendMessage = new SendMessage()
                .setParseMode(ParseMode.MARKDOWN);
        for (Staff staff : all) {
            if (staff.getCategory().equals(resMurojaat.getCategory())) {
                sendMessage.setChatId(staff.getChatId());
                String sms = "№ " + resMurojaat.getCode() + "\n" +
                        "Cанаси: " + resMurojaat.getCreated_at() + " \n" +
                        "Tел номери: " + resMurojaat.getPhoneNumber() + " \n" +
                        "Йўналиши: " + resMurojaat.getCategory() + " \n" +
                        "Xудуди: " + resMurojaat.getDistrict() + "\n" +
                        "Mурожаати: ⬇ ⬇ ⬇ ⬇ ⬇ \n";
                if (resMurojaat.getText() != null) {
                    sms = sms + resMurojaat.getText();
                }
                if (resMurojaat.getDefinition() != null) {
                    sms = resMurojaat.getDefinition() + "\n" + sms;
                }
                sendMessage.setText(sms);
                resMurojaatId = resMurojaat.getId().toString();
                try {
                    execute(sendMessage);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }

                if (resMurojaat.getMainMurojaat() != null) {
                    if (resMurojaat.getFileType().equals(Constant.AUDIO)) {
                        SendPhoto send = new SendPhoto();
                        send.setChatId(staff.getChatId());
                        send.setPhoto(resMurojaat.getMainMurojaat());
                        try {
                            execute(send);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    if (resMurojaat.getFileType().equals(Constant.VOICE)) {
                        SendVoice send = new SendVoice();
                        send.setChatId(staff.getChatId());
                        send.setVoice(resMurojaat.getMainMurojaat());

                        try {
                            execute(send);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    if (resMurojaat.getFileType().equals(Constant.VIDEO)) {
                        SendVideo send = new SendVideo();
                        send.setChatId(staff.getChatId());
                        send.setVideo(resMurojaat.getMainMurojaat());
                        try {
                            execute(send);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    if (resMurojaat.getFileType().equals(Constant.VIDEONOTE)) {
                        SendVideoNote send = new SendVideoNote();
                        send.setChatId(staff.getChatId());
                        send.setVideoNote(resMurojaat.getMainMurojaat());
                        try {
                            execute(send);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    if (resMurojaat.getFileType().equals(Constant.DOCUMENT)) {
                        SendDocument send = new SendDocument();
                        send.setChatId(staff.getChatId());
                        send.setDocument(resMurojaat.getMainMurojaat());
                        try {
                            execute(send);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                    if (resMurojaat.getFileType().equals(Constant.PHOTO)) {
                        SendPhoto send = new SendPhoto();
                        send.setChatId(staff.getChatId());
                        send.setPhoto(resMurojaat.getMainMurojaat());
                        try {
                            execute(send);
                        } catch (TelegramApiException e) {
                            e.printStackTrace();
                        }
                    }
                }
                SendMessage sendMessage1 = new SendMessage()
                        .setParseMode(ParseMode.MARKDOWN);
                sendMessage1.setText("қабул қилдм тугмасини босинг");
                sendMessage1.setChatId(staff.getChatId());
                InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
                List<List<InlineKeyboardButton>> replyKeyboarbuttons = new ArrayList<>();
                List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
                InlineKeyboardButton inlineButton = new InlineKeyboardButton();
                inlineButton.setText(Constant.RECEIVE);
                inlineButton.setCallbackData(Constant.RECEIVE);
                inlineKeyboardButtonList1.add(inlineButton);
                replyKeyboarbuttons.add(inlineKeyboardButtonList1);
                inlineKeyboardMarkup.setKeyboard(replyKeyboarbuttons);
                sendMessage1.setReplyMarkup(inlineKeyboardMarkup);
                try {
                    execute(sendMessage1);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
                //todo
            }
        }
    }


    public boolean sendMurojaat(Update update, Murojaatlar murojaatlar) throws TelegramApiException {
        ResMurojaat resMurojaat = botService.resMurojaat(update, murojaatlar);
        if (resMurojaat != null) {
            List<Staff> all = staffRepository.findAll();
            tempSendMurojaat(all, resMurojaat);
            return true;
        } else {
            level = 0;
            return false;
        }
    }

    @Override
    public String getBotUsername() {
        return "mening_murojaatim_bot";
    }

    @Override
    public String getBotToken() {
        return "1188427740:AAEuOa9BEeYfW0g7MCWXCSyo8ApYZry7d5A";
    }

}
