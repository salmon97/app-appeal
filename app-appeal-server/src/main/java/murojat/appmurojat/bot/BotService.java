package murojat.appmurojat.bot;

import lombok.SneakyThrows;
import murojat.appmurojat.entity.FileMurojaat;
import murojat.appmurojat.entity.Murojaatlar;
import murojat.appmurojat.entity.enums.Status;
import murojat.appmurojat.payload.ResMurojaat;
import murojat.appmurojat.repository.FileMurojaatRepository;
import murojat.appmurojat.repository.MurojaatlarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class BotService {
    int num = 0;
    @Autowired
    MurojaatlarRepository murojaatlarRepository;

    @Autowired
    FileMurojaatRepository fileMurojaatRepository;

    @Autowired
    MurojaatBot murojaatBot;

    @Value("${upload.folder}")
    private String uploadFolder;

    public void mamnunVsMamnunmemas(String id,Status status){
        Murojaatlar murojaatlar = murojaatlarRepository.findById(UUID.fromString(id)).orElseThrow(() -> new ResourceNotFoundException("getMurojaat"));
        murojaatlar.setStatus(status);
        murojaatlarRepository.save(murojaatlar);
    }

    public SendMessage startMurojaat(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(Constant.START_MUROJAAT + " учун \" " + Constant.START_MUROJAAT + " \" тугмасини босинг")
                .setParseMode(ParseMode.MARKDOWN);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        List<InlineKeyboardButton> list = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(Constant.START_MUROJAAT);
        inlineKeyboardButton.setCallbackData(Constant.START_MUROJAAT);
        list.add(inlineKeyboardButton);
        lists.add(list);
        inlineKeyboardMarkup.setKeyboard(lists);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }


    public SendMessage checkCloseApplication(Long chatId,String id) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(chatId)
                .setText(Constant.CLOSE_APPLICATION)
                .setParseMode(ParseMode.MARKDOWN);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> lists = new ArrayList<>();
        List<InlineKeyboardButton> list = new ArrayList<>();
        InlineKeyboardButton inlineKeyboardButton = new InlineKeyboardButton(Constant.MAMNUNMAN);
        inlineKeyboardButton.setCallbackData(Constant.MAMNUNMAN+"/"+id);
        InlineKeyboardButton inlineKeyboardButton1 = new InlineKeyboardButton(Constant.MAMNUNEMASMAN);
        inlineKeyboardButton1.setCallbackData(Constant.MAMNUNEMASMAN+"/"+id);
        list.add(inlineKeyboardButton);
        list.add(inlineKeyboardButton1);
        lists.add(list);
        inlineKeyboardMarkup.setKeyboard(lists);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage shareContact(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
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

    public SendMessage saveContact(Update update, Murojaatlar murojaatlar) {
        SendMessage sendMessage = new SendMessage()
                .setText("контакт сакланди")
                .setParseMode(ParseMode.MARKDOWN)
                .setChatId(update.getMessage().getChatId());
        String phone;
        if (update.getMessage().hasContact()) {
            phone = update.getMessage().getContact().getPhoneNumber();
            murojaatlar.setPhoneNumber(phone);
        } else {
            phone = update.getMessage().getText();
        }
        String phoneNumber = phone.startsWith("+") ? phone : "+" + phone;
        murojaatlar.setPhoneNumber(phoneNumber);
        murojaatlar.setChatId(update.getMessage().getChatId());
        murojaatlar.setStatus(Status.NO_READY);
        murojaatlarRepository.save(murojaatlar);
        ReplyKeyboardRemove replyKeyboardRemove = new ReplyKeyboardRemove();
        sendMessage.setReplyMarkup(replyKeyboardRemove);
        return sendMessage;
    }

    public SendMessage districtsAndijon(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(Constant.CHOOSE_DISTRICT)
                .setParseMode(ParseMode.MARKDOWN);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> replyKeyboarbuttons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList3 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList4 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList5 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList6 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList7 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList8 = new ArrayList<>();
        InlineKeyboardButton andijonSh1 = new InlineKeyboardButton(Constant.ANDIJON_SH);
        andijonSh1.setCallbackData(Constant.ANDIJON_SH);
        InlineKeyboardButton xonobodSh2 = new InlineKeyboardButton(Constant.XONOBOD_SH);
        xonobodSh2.setCallbackData(Constant.XONOBOD_SH);
        InlineKeyboardButton andijonT3 = new InlineKeyboardButton(Constant.ANDIJON_T);
        andijonT3.setCallbackData(Constant.ANDIJON_T);
        InlineKeyboardButton asakaT4 = new InlineKeyboardButton(Constant.ASAKA_T);
        asakaT4.setCallbackData(Constant.ASAKA_T);
        InlineKeyboardButton baliqchiT5 = new InlineKeyboardButton(Constant.BALIQCHI_T);
        baliqchiT5.setCallbackData(Constant.BALIQCHI_T);
        InlineKeyboardButton bozT6 = new InlineKeyboardButton(Constant.BOZ_T);
        bozT6.setCallbackData(Constant.BOZ_T);
        InlineKeyboardButton buloqBoshiT7 = new InlineKeyboardButton(Constant.BULOQBOSHI_T);
        buloqBoshiT7.setCallbackData(Constant.BULOQBOSHI_T);
        InlineKeyboardButton jalaquduqT8 = new InlineKeyboardButton(Constant.JALAQUDUQ_T);
        jalaquduqT8.setCallbackData(Constant.JALAQUDUQ_T);
        InlineKeyboardButton izboskanT9 = new InlineKeyboardButton(Constant.IZBOSKAN_T);
        izboskanT9.setCallbackData(Constant.IZBOSKAN_T);
        InlineKeyboardButton qorgonTepaT10 = new InlineKeyboardButton(Constant.QORGONTEPA_T);
        qorgonTepaT10.setCallbackData(Constant.QORGONTEPA_T);
        InlineKeyboardButton marxamatT11 = new InlineKeyboardButton(Constant.MARHAMAT_T);
        marxamatT11.setCallbackData(Constant.MARHAMAT_T);
        InlineKeyboardButton oltinkolT12 = new InlineKeyboardButton(Constant.OLTINKOL_T);
        oltinkolT12.setCallbackData(Constant.OLTINKOL_T);
        InlineKeyboardButton paxtabodT13 = new InlineKeyboardButton(Constant.PAXTABOD_T);
        paxtabodT13.setCallbackData(Constant.PAXTABOD_T);
        InlineKeyboardButton ulugnorT14 = new InlineKeyboardButton(Constant.ULUGNOR_T);
        ulugnorT14.setCallbackData(Constant.ULUGNOR_T);
        InlineKeyboardButton xojabadT15 = new InlineKeyboardButton(Constant.XOJABOD_T);
        xojabadT15.setCallbackData(Constant.XOJABOD_T);
        InlineKeyboardButton shaxrixonT16 = new InlineKeyboardButton(Constant.SHAXRIXON_T);
        shaxrixonT16.setCallbackData(Constant.SHAXRIXON_T);
        inlineKeyboardButtonList1.add(andijonSh1);
        inlineKeyboardButtonList1.add(xonobodSh2);
        inlineKeyboardButtonList2.add(andijonT3);
        inlineKeyboardButtonList2.add(asakaT4);
        inlineKeyboardButtonList3.add(baliqchiT5);
        inlineKeyboardButtonList3.add(bozT6);
        inlineKeyboardButtonList4.add(buloqBoshiT7);
        inlineKeyboardButtonList4.add(jalaquduqT8);
        inlineKeyboardButtonList5.add(izboskanT9);
        inlineKeyboardButtonList5.add(qorgonTepaT10);
        inlineKeyboardButtonList6.add(marxamatT11);
        inlineKeyboardButtonList6.add(oltinkolT12);
        inlineKeyboardButtonList7.add(paxtabodT13);
        inlineKeyboardButtonList7.add(ulugnorT14);
        inlineKeyboardButtonList8.add(xojabadT15);
        inlineKeyboardButtonList8.add(shaxrixonT16);
        replyKeyboarbuttons.add(inlineKeyboardButtonList1);
        replyKeyboarbuttons.add(inlineKeyboardButtonList2);
        replyKeyboarbuttons.add(inlineKeyboardButtonList3);
        replyKeyboarbuttons.add(inlineKeyboardButtonList4);
        replyKeyboarbuttons.add(inlineKeyboardButtonList5);
        replyKeyboarbuttons.add(inlineKeyboardButtonList6);
        replyKeyboarbuttons.add(inlineKeyboardButtonList7);
        replyKeyboarbuttons.add(inlineKeyboardButtonList8);
        inlineKeyboardMarkup.setKeyboard(replyKeyboarbuttons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage categorys(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setText(Constant.CHOOSE_CATEGORY)
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

    public SendMessage murojaatType(Update update) {
        SendMessage sendMessage = new SendMessage()
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setText(Constant.CHOOSE_APPEAL_TYPE)
                .setParseMode(ParseMode.MARKDOWN);
        InlineKeyboardMarkup inlineKeyboardMarkup = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> replyKeyboarbuttons = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList1 = new ArrayList<>();
        List<InlineKeyboardButton> inlineKeyboardButtonList2 = new ArrayList<>();
        InlineKeyboardButton text = new InlineKeyboardButton(Constant.TEXT);
        text.setCallbackData(Constant.TEXT);
        InlineKeyboardButton photo = new InlineKeyboardButton(Constant.PHOTO);
        photo.setCallbackData(Constant.PHOTO);
        InlineKeyboardButton audio = new InlineKeyboardButton(Constant.AUDIO);
        audio.setCallbackData(Constant.AUDIO);
        InlineKeyboardButton video = new InlineKeyboardButton(Constant.VIDEO);
        video.setCallbackData(Constant.VIDEO);
        inlineKeyboardButtonList1.add(text);
        inlineKeyboardButtonList1.add(audio);
        inlineKeyboardButtonList2.add(photo);
        inlineKeyboardButtonList2.add(video);
        replyKeyboarbuttons.add(inlineKeyboardButtonList1);
        replyKeyboarbuttons.add(inlineKeyboardButtonList2);
        inlineKeyboardMarkup.setKeyboard(replyKeyboarbuttons);
        sendMessage.setReplyMarkup(inlineKeyboardMarkup);
        return sendMessage;
    }

    public SendMessage level5(Update update) {
        return new SendMessage()
                .setChatId(update.getCallbackQuery().getMessage().getChatId())
                .setText(Constant.SENDMUROJAAT)
                .setParseMode(ParseMode.MARKDOWN);
    }

    public SendMessage saveMurojaat(Update update) {
        return new SendMessage()
                .setChatId(update.getMessage().getChatId())
                .setText(Constant.FINISHMUROJAAT)
                .setParseMode(ParseMode.MARKDOWN);
    }


    public ResMurojaat resMurojaat(Update update, Murojaatlar murojaatlar) throws TelegramApiException {
        FileMurojaat fileMurojaat = new FileMurojaat();
        Message message = update.getMessage();
        GetFile getFile = new GetFile();
        if (message.hasText()) {
            murojaatlar.setMurojaatText(update.getMessage().getText());
        }
        if (message.hasPhoto()) {
            fileMurojaat.setMurojaatlar(murojaatlar);
            fileMurojaat.setFileId(message.getPhoto().get(0).getFileId());
            fileMurojaat.setFileType(Constant.PHOTO);
            getFile.setFileId(message.getPhoto().get(0).getFileId());
        }
        if (message.hasAudio()) {
            if (message.getVideo().getFileSize() > 20000089) {
                fileMurojaat.setMurojaatlar(murojaatlar);
                fileMurojaat.setFileId(message.getAudio().getFileId());
                fileMurojaat.setFileType(Constant.AUDIO);
                getFile.setFileId(message.getAudio().getFileId());
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Сиз юклаган файл хажми катта мурожаатни кайта жонатинг /start ");
                sendMessage.setChatId(message.getChatId());
                murojaatBot.execute(sendMessage);
                murojaatlarRepository.delete(murojaatlar);
                return null;
            }
        }
        if (message.hasVoice()) {
            fileMurojaat.setMurojaatlar(murojaatlar);
            fileMurojaat.setFileId(message.getVoice().getFileId());
            fileMurojaat.setFileType(Constant.VOICE);
            getFile.setFileId(message.getVoice().getFileId());
        }
        if (message.hasVideo()) {
            if (message.getVideo().getFileSize() < 20000089) {
                fileMurojaat.setMurojaatlar(murojaatlar);
                fileMurojaat.setFileId(message.getVideo().getFileId());
                fileMurojaat.setFileType(Constant.VIDEO);
                getFile.setFileId(message.getVideo().getFileId());
            } else {
                SendMessage sendMessage = new SendMessage();
                sendMessage.setText("Сиз юклаган файл хажми катта мурожаатни кайта жонатинг /start ");
                sendMessage.setChatId(message.getChatId());
                murojaatBot.execute(sendMessage);
                murojaatlarRepository.delete(murojaatlar);
                return null;
            }
        }
        if (message.hasDocument()) {
            fileMurojaat.setMurojaatlar(murojaatlar);
            fileMurojaat.setFileId(message.getDocument().getFileId());
            fileMurojaat.setFileType(Constant.DOCUMENT);
            getFile.setFileId(message.getDocument().getFileId());
        }
        if (message.hasVideoNote()) {
            fileMurojaat.setMurojaatlar(murojaatlar);
            fileMurojaat.setFileId(message.getVideoNote().getFileId());
            fileMurojaat.setFileType(Constant.VIDEONOTE);
            getFile.setFileId(message.getVideoNote().getFileId());
        }

        if (fileMurojaat.getFileId() != null) {
            org.telegram.telegrambots.meta.api.objects.File resFile = murojaatBot.execute(getFile);
            fileMurojaat.setExtension(getExt(resFile.getFilePath()));
            fileMurojaatRepository.save(fileMurojaat);
            downloadFile(fileMurojaat, resFile.getFilePath(), resFile.getFileSize());
        }
        murojaatlar.setStatus(Status.NO_RECEIVE);
        num++;
        DecimalFormat decimalFormat = new DecimalFormat("0000");
        String no = decimalFormat.format(num);
        murojaatlar.setCode("#" + no);
        Murojaatlar murojaatlar1 = murojaatlarRepository.save(murojaatlar);
        return new ResMurojaat(
                null,
                murojaatlar1.getCode(),
                murojaatlar.getId(),
                murojaatlar1.getCreatedAt().toString().substring(0, 10),
                murojaatlar1.getMurojaatText(),
                fileMurojaat.getFileId(),
                murojaatlar1.getPhoneNumber(),
                murojaatlar1.getCategory(),
                murojaatlar1.getDistrict(),
                fileMurojaat.getFileType()
        );
    }


    @SneakyThrows
    public void downloadFile(FileMurojaat fileMurojaat, String filePath, int fileSize) {
        String urlTemp = "https://api.telegram.org/file/bot1188427740:AAEuOa9BEeYfW0g7MCWXCSyo8ApYZry7d5A/" + filePath;
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
        URL url = new URL(urlTemp);
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

    private String getExt(String fileName) {
        String ext = null;
        if (fileName != null && !fileName.isEmpty()) {
            int dot = fileName.lastIndexOf(".");
            if (dot > 0 && dot <= fileName.length() - 2) {
                ext = fileName.substring(dot + 1);
            }
        }
        return ext;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void numchange() {
        num = 0;
    }

}