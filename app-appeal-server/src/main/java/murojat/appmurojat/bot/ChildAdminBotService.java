package murojat.appmurojat.bot;

import lombok.SneakyThrows;
import murojat.appmurojat.entity.ChildAdmin;
import murojat.appmurojat.entity.FileMurojaat;
import murojat.appmurojat.entity.Murojaatlar;
import murojat.appmurojat.entity.enums.Status;
import murojat.appmurojat.repository.ChildAdminRepository;
import murojat.appmurojat.repository.FileMurojaatRepository;
import murojat.appmurojat.repository.MurojaatlarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageReplyMarkup;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult;
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

@Service
public class ChildAdminBotService {


    @Value("${upload.folder}")
    private String uploadFolder;

    @Autowired
    ChildAdminRepository childAdminRepository;

    @Autowired
    BotService botService;

    @Autowired
    MurojaatBot murojaatBot;

    @Autowired
    MurojaatlarRepository murojaatlarRepository;

    int num = 0;

    @Autowired
    FileMurojaatRepository fileMurojaatRepository;

    public void queryRecive(Update update, String id) {
        Murojaatlar murojaatlar = murojaatlarRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("getMurojaat"));
        if (murojaatlar.getChatId() != update.getCallbackQuery().getFrom().getId().longValue()) {
            InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> replyKeyboarbuttons = new ArrayList<>();
            List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
            InlineKeyboardButton inlineButton = new InlineKeyboardButton("қабул килинди");
            inlineButton.setCallbackData("data0001");
            inlineKeyboardButtonList1.add(inlineButton);
            replyKeyboarbuttons.add(inlineKeyboardButtonList1);
            inlineKeyboardMarkup.setKeyboard(replyKeyboarbuttons);
            EditMessageReplyMarkup editMessageReplyMarkup = new EditMessageReplyMarkup();
//        editMessageReplyMarkup.setChatId(update.getCallbackQuery().getMessage().getChatId());
//        editMessageReplyMarkup.setMessageId(update.getCallbackQuery().getMessage().getMessageId());
            editMessageReplyMarkup.setInlineMessageId(update.getCallbackQuery().getInlineMessageId());
            editMessageReplyMarkup.setReplyMarkup(inlineKeyboardMarkup);
            murojaatlar.setStatus(Status.RECEIVE);
            murojaatlarRepository.save(murojaatlar);
            try {
                murojaatBot.execute(editMessageReplyMarkup);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public AnswerInlineQuery inlineQuery(Update update, String text) {
        InlineQueryResultArticle r2 = new InlineQueryResultArticle();
        r2.setThumbUrl("https://lh3.googleusercontent.com/proxy/JY51Lom4L6hmW7d0DzBfnxPtQaXmGmP-FWZbRw-c3RyOqbqIIGL10oVlEPZ5021etF7Tgajjp3PYjxffFJ7iGX_P");
        r2.setTitle("мурожаат йўллаш");
        r2.setId("1");
        r2.setDescription("мурожаат йўллаш учун устига босингг");
        r2.setInputMessageContent(new InputTextMessageContent().setMessageText(text));
        r2.setReplyMarkup(murojaatSave(text, update.getInlineQuery().getFrom().getId()));
        List<InlineQueryResult> inlineQueryResults = new ArrayList<>();
        inlineQueryResults.add(r2);
        AnswerInlineQuery answerInlineQuery = new AnswerInlineQuery();
        answerInlineQuery.setInlineQueryId(update.getInlineQuery().getId());
        answerInlineQuery.setResults(inlineQueryResults);
        return answerInlineQuery;
    }

    public InlineKeyboardMarkup murojaatSave(String text, Integer chatId) {
        Murojaatlar murojaatlar = new Murojaatlar();
        murojaatlar.setMurojaatText(text);
        murojaatlar.setStatus(Status.NO_RECEIVE);
        murojaatlar.setSource(Constant.PERSONAL);
        murojaatlar.setChatId(Long.valueOf(chatId));
        murojaatlar.setMurojaatText(murojaatlar.getMurojaatText());
        murojaatlarRepository.save(murojaatlar);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> replyKeyboarbuttons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
        InlineKeyboardButton b = new InlineKeyboardButton(Constant.RECEIVE);
        b.setCallbackData(Constant.RECEIVE_QUERY + "/" + murojaatlar.getId().toString());
        inlineKeyboardButtonList1.add(b);
        replyKeyboarbuttons.add(inlineKeyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(replyKeyboarbuttons);
        return inlineKeyboardMarkup;
    }


    public SendMessage shareContact(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setParseMode(ParseMode.MARKDOWN);
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();
        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow keyboardRow = new KeyboardRow();
        KeyboardButton keyboardButton = new KeyboardButton();
        sendMessage.setText(Constant.SHARE_CONTACT);
        keyboardButton.setText(Constant.SHARE_CONTACT_BUTTON);
        keyboardButton.setRequestContact(true);
        keyboardRow.add(keyboardButton);
        keyboardRows.add(keyboardRow);
        replyKeyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(replyKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage chooseSource(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setText(Constant.CHOOSE_SOURCE)
                .setParseMode(ParseMode.MARKDOWN);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> replyKeyboarbuttons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
        InlineKeyboardButton facebook = new InlineKeyboardButton(Constant.FACEBOOK);
        facebook.setCallbackData(Constant.FACEBOOK);
        InlineKeyboardButton instagram = new InlineKeyboardButton(Constant.INSTAGRAM);
        instagram.setCallbackData(Constant.INSTAGRAM);
        inlineKeyboardButtonList1.add(facebook);
        inlineKeyboardButtonList1.add(instagram);
        replyKeyboarbuttons.add(inlineKeyboardButtonList1);
        inlineKeyboardMarkup.setKeyboard(replyKeyboarbuttons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage registerName(Update update) {
        ChildAdmin childAdmin = new ChildAdmin();
        if (update.hasMessage()) {
            childAdmin.setChatId(update.getMessage().getChatId());
        }
        childAdminRepository.save(childAdmin);
        return new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Исмингизни киритинг")
                .setParseMode(ParseMode.MARKDOWN);
    }

    public void saveName(Update update) {
        ChildAdmin byChatId = childAdminRepository.getByChatId(update.getMessage().getChatId());
        byChatId.setName(update.getMessage().getText());
        childAdminRepository.save(byChatId);
    }

    public SendMessage saveContact(Update update) {
        ChildAdmin byChatId = childAdminRepository.getByChatId(update.getMessage().getChatId());
        SendMessage sendMessage = new SendMessage()
                .setText("сакланди")
                .setParseMode(ParseMode.MARKDOWN)
                .setChatId(update.getMessage().getChatId());
        String phone;
        if (update.getMessage().hasContact()) {
            phone = update.getMessage().getContact().getPhoneNumber();
            byChatId.setPhoneNumber(phone);
        } else {
            phone = update.getMessage().getText();
        }
        String phoneNumber = phone.startsWith("+") ? phone : "+" + phone;
        byChatId.setPhoneNumber(phoneNumber);
        byChatId.setChatId(update.getMessage().getChatId());
        childAdminRepository.save(byChatId);
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        return sendMessage;
    }


    public SendMessage startMurojaatAdmin(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(Constant.START_MUROJAATADMIN + " учун \" " + Constant.START_MUROJAATADMIN + " \" тугмасини босинг")
                .setParseMode(ParseMode.MARKDOWN);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        List<InlineKeyboardButton> list = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(Constant.START_MUROJAATADMIN);
        inlineKeyboardButton.setCallbackData(Constant.START_MUROJAATADMIN);
        list.add(inlineKeyboardButton);
        lists.add(list);
        inlineKeyboardMarkup.setKeyboard(lists);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    @SneakyThrows
    public void downloadFile(FileMurojaat fileMurojaat, String filePath, int fileSize) {
        Date data = new Date();
        File uploadFolders = new File(String.format("%s/upload_files/%d/%d/%d/", uploadFolder,
                1900 + data.getYear(), 1 + data.getMonth(), data.getDate()));
        if (!uploadFolders.exists() && uploadFolders.mkdirs()) {
            System.out.println("aytilgan papka ochildi");
        }

        fileMurojaat.setUploadPath(String.format(uploadFolder + "upload_files/%d/%d/%d/%s.%s", 1900 + data.getYear(), 1 + data.getMonth(), data.getDate(),
                fileMurojaat.getId(), fileMurojaat.getExtension()));

        uploadFolders = uploadFolders.getAbsoluteFile();
        File file = new File(uploadFolders, String.format("%s.%s", fileMurojaat.getId(), fileMurojaat.getExtension()));
        fileMurojaatRepository.save(fileMurojaat);
        URL url = new URL(filePath);
        URLConnection connection = url.openConnection();
        InputStream is = connection.getInputStream();
        FileOutputStream fos = new FileOutputStream(file);
        byte[] buffer = new byte[fileSize];
        int read = 0;
        while ((read = is.read(buffer, 0, buffer.length)) >= 0) {
            fos.write(buffer, 0, read);
        }
        fos.flush();
        fos.close();
        is.close();
    }

    public String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf(".");
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }
}
