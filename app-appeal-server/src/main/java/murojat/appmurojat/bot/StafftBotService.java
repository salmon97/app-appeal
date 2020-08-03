package murojat.appmurojat.bot;

import murojat.appmurojat.entity.FileMurojaat;
import murojat.appmurojat.entity.Murojaatlar;
import murojat.appmurojat.entity.Staff;
import murojat.appmurojat.entity.enums.Status;
import murojat.appmurojat.payload.ResMurojaat;
import murojat.appmurojat.repository.FileMurojaatRepository;
import murojat.appmurojat.repository.MurojaatlarRepository;
import murojat.appmurojat.repository.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@Service
public class StafftBotService {

    @Autowired
    FileMurojaatRepository fileMurojaatRepository;

    @Autowired
    StaffRepository staffRepository;

    @Autowired
    MurojaatlarRepository murojaatlarRepository;

    @Autowired
    BotService botService;

    public SendMessage registerStaff(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Мурожаат Ларни категория Бойича Кабул Килиш Учун Буттон Лардан Бирини Танланг")
                .setParseMode(ParseMode.MARKDOWN);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> replyKeyboarbuttons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList<>();
        InlineKeyboardButton gaz = new InlineKeyboardButton(Constant.GAZ);
        gaz.setCallbackData(Constant.GAZ);
        InlineKeyboardButton suv = new InlineKeyboardButton(Constant.SUV);
        suv.setCallbackData(Constant.SUV);
        InlineKeyboardButton uyJoy = new InlineKeyboardButton(Constant.UY_JOY);
        uyJoy.setCallbackData(Constant.UY_JOY);
        inlineKeyboardButtonList1.add(gaz);
        inlineKeyboardButtonList1.add(suv);
        inlineKeyboardButtonList2.add(uyJoy);
        replyKeyboarbuttons.add(inlineKeyboardButtonList1);
        replyKeyboarbuttons.add(inlineKeyboardButtonList2);
        inlineKeyboardMarkup.setKeyboard(replyKeyboarbuttons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage name(Update update) {
        return new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText("Исмингизни киритинг")
                .setParseMode(ParseMode.MARKDOWN);
    }

    public SendMessage saveContact(Update update, Staff staff) {
        SendMessage sendMessage = new SendMessage()
                .setText("сакланди")
                .setParseMode(ParseMode.MARKDOWN)
                .setChatId(update.getMessage().getChatId());
        String phone;
        if (update.getMessage().hasContact()) {
            phone = update.getMessage().getContact().getPhoneNumber();
            staff.setPhoneNumber(phone);
        } else {
            phone = update.getMessage().getText();
        }
        String phoneNumber = phone.startsWith("+") ? phone : "+" + phone;
        staff.setPhoneNumber(phoneNumber);
        staff.setChatId(update.getMessage().getChatId());
        staffRepository.save(staff);
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        return sendMessage;
    }

    public ResMurojaat resMurojaat(Murojaatlar murojaatlar,String definition) {
        FileMurojaat fileMurojaat = fileMurojaatRepository.findByMurojaatlar_Id(murojaatlar.getId());
        if (fileMurojaat != null) {
            return new ResMurojaat(
                    definition,
                    murojaatlar.getCode(),
                    murojaatlar.getId(),
                    murojaatlar.getCreatedAt().toString().substring(0, 10),
                    murojaatlar.getMurojaatText(),
                    fileMurojaat.getFileId(),
                    murojaatlar.getPhoneNumber(),
                    murojaatlar.getCategory(),
                    murojaatlar.getDistrict(),
                    fileMurojaat.getFileType()
            );
        } else {
            return new ResMurojaat(
                    definition,
                    murojaatlar.getCode(),
                    murojaatlar.getId(),
                    murojaatlar.getCreatedAt().toString().substring(0, 10),
                    murojaatlar.getMurojaatText(),
                    null,
                    murojaatlar.getPhoneNumber(),
                    murojaatlar.getCategory(),
                    murojaatlar.getDistrict(),
                    null
            );
        }

    }
}
