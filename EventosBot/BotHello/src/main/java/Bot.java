import java.util.List;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.BaseResponse;
import com.pengrad.telegrambot.response.GetUpdatesResponse;
import com.pengrad.telegrambot.response.SendResponse;

public class Bot {

	public static void main(String[] args) throws InterruptedException {

		// Criacao do objeto bot com as informacoes de acesso
		TelegramBot bot = TelegramBotAdapter.build("495154617:AAFWWIdCuAGSDu6cu80WCtF_KrXIJSemC5k");

		// objeto responsavel por receber as mensagens
		GetUpdatesResponse updatesResponse;
		// objeto responsavel por gerenciar o envio de respostas
		SendResponse sendResponse;
		// objeto responsavel por gerenciar o envio de aï¿½ï¿½es do chat
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

			// anï¿½lise de cada acao da mensagem
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
						//		"Numero enviado com sucesso\n"));

						// Criação de Keyboard
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Número enviado com sucesso\n").replyMarkup(new ReplyKeyboardMarkup(
											new String[] { "Menu" })));

					} else if (update.message().location() != null) {
						//sendResponse = bot.execute(new SendMessage(update.message().chat().id(),
						//		"localização enviada com sucesso\n"));

							
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

							// Pedir contato
						} else if (update.message().text().equals("Enviar contato")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat().id(), "Pedindo contato")
									.replyMarkup(new ReplyKeyboardMarkup(new KeyboardButton[] {
											new KeyboardButton("Enviar contato").requestContact(true) })));


							// Pedir localização 
						} else if (update.message().text().equals("Enviar localização para entrega")) {
							sendResponse = bot
									.execute(new SendMessage(update.message().chat().id(), "Pedindo localização")
											.replyMarkup(new ReplyKeyboardMarkup(
													new KeyboardButton[] { new KeyboardButton("Enviar localização")
															.requestLocation(true) })));
						
							
							
							
						//Proximo	
						} else if (update.message().text().equals("Próximo")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "No EventosBot você poderá realizar a sua festa dos sonhos, para vários tipos de festas e gostos, e vários pacotes de serviços que cabem no seu bolso").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Menu"})));	
							
							
							
							
							//Menu de Festas
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
									.id(), "Escolha seu pacote de serviço :\n\nPacote 1: DJ + Estrutura de Iluminação + Iluminação.\nR$: 1.400,00\n\nPacote 2: Buffet completo(comida, bebida, etc).\nR$:4.000,00\n\nPacote 3: Festa completa(Pacote 1 + Pacote 2).\nR$:5.200,00 ").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Pacote 1"},
											new String[] {"Pacote 2"},
											new String[] {"Pacote 3"})));
							
							
						//Formatura
						} else if (update.message().text().equals("Formatura")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Escolha seu pacote de serviço :\n\nPacote 1: DJ + Estrutura de Iluminação + Iluminação.\nR$: 1.400,00\n\nPacote 2: Buffet completo(comida, bebida, etc).\nR$:4.000,00\n\nPacote 3: Festa completa(Pacote 1 + Pacote 2).\nR$:5.200,00 ").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Pacote 1"},
											new String[] {"Pacote 2"},
											new String[] {"Pacote 3"})));

							
							
						//Casamento 
						} else if (update.message().text().equals("Casamento")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Escolha seu pacote de serviço :\\n\\nPacote 1: DJ + Estrutura de Iluminação + Iluminação.\nR$: 1.400,00\n\nPacote 2: Buffet completo(comida, bebida, etc).\nR$:4.000,00\n\nPacote 3: Festa completa(Pacote 1 + Pacote 2).\nR$:5.200,00 ").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Pacote 1"},
											new String[] {"Pacote 2"},
											new String[] {"Pacote 3"})));
							
							
							
							
						//Aniversário	
						} else if (update.message().text().equals("Aniversário")) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Escolha seu pacote de serviço :\n\nPacote 1: DJ + Estrutura de Iluminação + Iluminação.\nR$: 1.400,00\n\nPacote 2: Buffet completo(comida, bebida, etc).\nR$:4.000,00\n\nPacote 3: Festa completa(Pacote 1 + Pacote 2).\nR$:5.200,00 ").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Pacote 1"},
											new String[] {"Pacote 2"},
											new String[] {"Pacote 3"})));
							
							
							
						
							
							
							
						//Pacotes	
						} else if (update.message().text().equals("Pacote 1") ||(update.message().text().equals("Pacote 2")) || (update.message().text().equals("Pacote 3")) || (update.message().text().equals("Pacote 4"))) {
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Pacote selecionado com sucesso").replyMarkup(new ReplyKeyboardMarkup(
											new String[] {"Menu"})));
							
							
							
							// keyboard inline - callBackData
						} else if (update.message().text().equals("Confirmar Pedido")) {
							sendResponse = bot
									.execute(new SendMessage(update.message().chat().id(), "Deseja confirmar seu pedido?\nCaso deseje confirmar o pedido clique abaixo em Confirmar pedido")
											.replyMarkup(new InlineKeyboardMarkup(new InlineKeyboardButton[] {
													new InlineKeyboardButton("Confirmar pedido").callbackData("Pedido confirmado com sucesso").callbackData("Entraremos em contato para mais informações\nObrigado por utilizar o EventosBot para realizar sua festa!!!!")})));
											sendResponse = bot.execute(new SendMessage(update.message().chat()
														.id(), "Caso contrario clique em Menu para voltar:").replyMarkup(new ReplyKeyboardMarkup(
																new String[] {"Menu"})));
							

							
						

						} else {
							//sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Bem Vindo ao Delivery de Ã¡gua\n\n"));

							//sendResponse = bot.execute(new SendMessage(update.message().chat().id(),"Informe seu contato e sua localizaÃ§Ã£o"));

							// CriaÃ§Ã£o de Keyboard
							sendResponse = bot.execute(new SendMessage(update.message().chat()
									.id(), "Bem Vindo ao EventosBot\n\n").replyMarkup(new ReplyKeyboardMarkup(
											new String[] { "Próximo" })));

						}


						

					}

				}

			}

		}

	}

}