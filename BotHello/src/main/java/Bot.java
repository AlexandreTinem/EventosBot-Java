import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.File;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.request.SendPhoto;
import com.pengrad.telegrambot.request.SetWebhook;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Properties;


public class Bot {
	public static void main(String[] args) throws InterruptedException {		
	int valorPacote =0, valorTema =0, valorTotal =0;
	String p = null, pt=null;
		// Criacao do objeto bot com as informacoes de acesso
		TelegramBot bot = TelegramBotAdapter.build("495154617:AAFWWIdCuAGSDu6cu80WCtF_KrXIJSemC5k");

		// objeto responsavel por receber as mensagens
		GetUpdatesResponse updatesResponse;
		// objeto responsavel por gerenciar o envio de respostas
		SendResponse sendResponse;
		// objeto responsavel por gerenciar o envio de a��es do chat
		BaseResponse baseResponse;

		// controle de off-set. A partir deste ID serao lidas as mensagens
		// pendentes na fila
		int m = 0;

		// loop infinito pode ser alterado por algum timer de intervalo curto
		while (true) {

			// executa comando no Telegram para obter as mensagens pendentes a partir de um
			// off-set (limite inicial)
			updatesResponse = bot.execute(new GetUpdates().limit(100).offset(m));

			// lista de mensagens
			List<Update> updates = updatesResponse.updates();

			// an�lise de cada acao da mensagem
			for (Update update : updates) {

				// atualizacao do off-set
				m = update.updateId() + 1;

				if (update.callbackQuery() != null) {

					sendResponse = bot.execute(new SendMessage(update.callbackQuery().message().chat().id(),
							update.callbackQuery().data()));
					
				} else {
					System.out.println("Recebendo mensagem:" + update.message().text());

					// envio de "Escrevendo" antes de enviar a resposta
					baseResponse = bot
							.execute(new SendChatAction(update.message().chat().id(), ChatAction.typing.name()));
					// verificacao de acao de chat foi enviada com sucesso
					System.out.println("Resposta de Chat Action Enviada?" + baseResponse.isOk());

					// enviando numero de contato recebido
					if (update.message().contact() != null) {
						//sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
						//		"Número enviado com sucesso\n"));

						// Criação de Keyboard
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Número enviado com sucesso\n").replyMarkup(new ReplyKeyboardMarkup(
											new String[] { "Menu" })));

					} else if (update.message().location() != null) {
						//sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
						//		"Localização enviada com sucesso\n"));

							
						// Criação de Keyboard
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Sua localização foi enviada com sucesso\n").replyMarkup(new ReplyKeyboardMarkup(
											new String[] { "Menu" })));
					}

					if (update.message().text() != null) {


						// Criação de Keyboard
						if (update.message().text().equals("Menu")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Menu").replyMarkup(new ReplyKeyboardMarkup(
											new String[] { "Tipo de Festa" },
											new String[] { "Tema da festa" },
											new String[] { "Enviar contato" },
											new String[] { "Enviar localização para entrega" },
											new String[] {"Confirmar Pedido"})));


						} else if (update.message().text().equals("Envia")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Pedindo contato")
									.replyMarkup(new ReplyKeyboardMarkup(new KeyboardButton[] {
											new KeyboardButton("Enviar contato").requestContact(true) })));
							
							
							
							
							
							// Pedir contato
						} else if (update.message().text().equals("Enviar contato")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Pedindo contato")
									.replyMarkup(new ReplyKeyboardMarkup(new KeyboardButton[] {
											new KeyboardButton("Enviar contato").requestContact(true) })));


							// Pedir localização
						} else if (update.message().text().equals("Enviar localização para entrega")) {
							sendResponse = bot
									.execute(new SendMessage(update.message().chat().id(), "Pedindo Localização")
											.replyMarkup(new ReplyKeyboardMarkup(
													new KeyboardButton[] { new KeyboardButton("Enviar localização")
															.requestLocation(true) })));

							//Proximo
						} else if (update.message().text().equals("Próximo")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "No EventosBot você poderá realizar a sua festa dos sonhos, para vários tipos de festas e gostos, e vários pacotes de serviços que cabem no seu bolso").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Menu"})));	
							
							
							
							
						} else if (update.message().text().equals("Tipo de Festa")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Escolha o tipo de festa a ser realizada:").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Festa de 15 anos"},
											new String[] {"Formatura"},
											new String[] {"Casamento"},
											new String[] {"Aniversário"})));

						
						
						//Festa de 15 anos
						} else if (update.message().text().equals("Festa de 15 anos")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Escolha seu pacote de serviço :\n\nPacote 1: DJ + Estrutura de Iluminação + Iluminaçãoo.\nR$: 1.400,00\n\nPacote 2: Buffet completo(comida, bebida, etc).\nR$:4.000,00\n\nPacote 3: Festa completa(Pacote 1 + Pacote 2).\nR$:5.200,00 ").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Pacote 1"},
											new String[] {"Pacote 2"},
											new String[] {"Pacote 3"})));
							
							
						//Formatura
						} else if (update.message().text().equals("Formatura")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Escolha seu pacote de serviço :\n\nPacote 1: DJ + Estrutura de Iluminação + Iluminaçãoo.\nR$: 1.400,00\n\nPacote 2: Buffet completo(comida, bebida, etc).\nR$:4.000,00\n\nPacote 3: Festa completa(Pacote 1 + Pacote 2).\nR$:5.200,00 ").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Pacote 1"},
											new String[] {"Pacote 2"},
											new String[] {"Pacote 3"})));

							
							
						//Casamento 
						} else if (update.message().text().equals("Casamento")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Escolha seu pacote de serviço :\n\nPacote 1: DJ + Estrutura de Iluminação + Iluminaçãoo.\nR$: 1.400,00\n\nPacote 2: Buffet completo(comida, bebida, etc).\nR$:4.000,00\n\nPacote 3: Festa completa(Pacote 1 + Pacote 2).\nR$:5.200,00 ").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Pacote 1"},
											new String[] {"Pacote 2"},
											new String[] {"Pacote 3"})));
							
							
							
							
						//AniversÃ¡rio	
						} else if (update.message().text().equals("Aniversário")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Escolha seu pacote de serviço :\n\nPacote 1: DJ + Estrutura de Iluminação + Iluminaçãoo.\nR$: 1.400,00\n\nPacote 2: Buffet completo(comida, bebida, etc).\nR$:4.000,00\n\nPacote 3: Festa completa(Pacote 1 + Pacote 2).\nR$:5.200,00 ").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Pacote 1"},
											new String[] {"Pacote 2"},
											new String[] {"Pacote 3"})));
							
							
						//Tema de Festa	
						} else if (update.message().text().equals("Tema da festa")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Pacote selecionado com sucesso:\n\n Tema Havaiano(decoração + entreterimento) = R$ 1.500,00 \n\n Tema Disney(decoração + entreterimento) = R$ 1.800,00 \n\n Tema Futebol(decoração + entreterimento) = R$ 1.350,00").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Tema Havaiano"},
											new String[] {"Tema Disney"},
											new String[] {"Tema Futebol"})));							
							
						sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Foto do tema das festas: \n")
													.replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[] {
															new InlineKeyboardButton("Tema Havaiano").callbackData("https://i.imgur.com/z5nAKRf.jpg"),
															new InlineKeyboardButton("Tema Disney").callbackData("https://i.imgur.com/Z7bLduI.jpg"),
															new InlineKeyboardButton("Tema Futebol").callbackData("https://i.imgur.com/9TEbR36.jpg"),})));
							
							
						
						//Tema selecionado
						} else if (update.message().text().equals("Tema Havaiano")) {
							valorTema = 1500;
							pt = "Tema Havaiano";
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Pacote selecionado com sucesso").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Menu"})));
							
						} else if (update.message().text().equals("Tema Disney")) {
							valorTema = 1800;
							pt = "Tema Disney";
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Pacote selecionado com sucesso").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Menu"})));
							
						} else if (update.message().text().equals("Tema Futebol")) {
							valorTema = 1350;
							pt ="Tema Futebol";
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Pacote selecionado com sucesso").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Menu"})));
						
						
						
						//Pacotes	
						} else if (update.message().text().equals("Pacote 1")) {
							valorPacote = 1400;
							p = "Pacote 1";
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Pacote selecionado com sucesso").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Menu"})));
							
						} else if (update.message().text().equals("Pacote 2")) {
							valorPacote = 4000;
							p = "Pacote 2";
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Pacote selecionado com sucesso").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Menu"})));
							
						} else if (update.message().text().equals("Pacote 3")) {
							valorPacote = 5200;
							p = "Pacote 3";
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Pacote selecionado com sucesso").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Menu"})));
							
						
						
							
							// keyboard inline - callBackData
						} else if (update.message().text().equals("Confirmar Pedido")) {
							valorTotal = valorPacote + valorTema;
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Valor total da festa : R$ "+(valorPacote + valorTema)));
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Deseja confirmar seu pedido?\nCaso deseje confirmar o pedido clique abaixo em Confirmar pedido\n\nCaso contrario clique em Menu para voltar: \n").replyMarkup(new ReplyKeyboardMarkup(
									new String[] {"Confirmar pedido"},
									new String[] {"Menu"})));

						
							
							
						} else if (update.message().text().equals("Confirmar pedido")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Pedido Confirmado.\n\nObrigado por escolher o EventosBot para realizar a sua festa dos senhos.!!\n\nEntraremos em contato para mais informações."));

							

						} else {
							

							// Criação de Keyboard
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Seja bem vindo ao EventosBot\n\n").replyMarkup(new ReplyKeyboardMarkup(
											new String[] { "Próximo" })));

						}


						

					}

				}

			}

		}

	}

}