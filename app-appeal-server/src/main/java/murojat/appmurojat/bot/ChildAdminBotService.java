package murojat.appmurojat.bot;

import murojat.appmurojat.entity.ChildAdmin;
import murojat.appmurojat.entity.Staff;
import murojat.appmurojat.repository.ChildAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class ChildAdminBotService {

    @Autowired
    ChildAdminRepository childAdminRepository;

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

    public SendMessage registerName(Update update){
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

    public void saveName(Update update){
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
}
