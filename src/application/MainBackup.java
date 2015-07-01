package application;
	
// JavaFX_25_SQL 

import java.sql.Connection;

import org.omg.CosNaming.NamingContextExtPackage.AddressHelper;

import application.Utils;
import application.UtilsSQLConn;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/* SQL - Ligação a Bases de Dados MySQL e SQLServer e comandos DML.
 * 		Neste esxercício vamos manipular dados com recurso a bases
 * 		de dados. A partir de um menu acedemos a consultas de Alunos,
 * 		professores ou funcionários de uma escola. A partir de um de
 * 		3 botões, inserimos, alteramos ou eliminamos dados referentes
 * 		à entidade que estiver a ser exibida na altura.    

 *TODO: Copiar border layout ex. 17, manter layouts esq. e central
 * 		Região Esq: Botões: inserir, alterar apagar
 * 		Região TOP: Menu:
 * 		- File: Sair
 * 		- RH: Recursos humanos, carrega Alunos, Professores e Funcionários
 * 			RadioItemMenus para carregar a lista da região central respetiva
 * 			Os botões DML, verificam o valor ativo no RadioItemMenu e atuam
 * 			em conformidade.
 * 			Qualquer das ações DML abre uma modal para aí se atuar.
 * 
 * 		Região Central, TableView com os dados da tabela repetiva.
 * ---------------------------------------------------------------------
 * Passo1 - Começar por criar o rootLayou do tipo borderPane e 
 * 			escrever o linhas de comentário para delimitar as regiões.
 * Passo2 - Criar todo o Layout das 3 regiões a usar: top, esq e centro.
 * Passo3 - Criar o menu na região top
 * 			Criar os botões na região esquerda.
 * 			Criar ações de alert para testar funcionamento.
 * Passo4 - Criar as tableView 
 * 			Criar 3 tableViews para o layout da região central: ex17
 * 			Criar 3 métodos para carregar as tableVies (ex17)
 * 			Criar 3 classes: Aluno, Professor e Funcionário
 * 			Criar 3 Tabelas: Aluno, Professor e Funcionário
 */

public class MainBackup extends Application {
	
	static String btnClicado = "";					// Recebe o nome do botão clicado
	 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane layoutRoot = new BorderPane();			// Layout Principal: BorderPAne - 5 Regiões
			
			/*//////////////////////////////////////////////////////////////////////////////////
			* Região TOP - 	MenuBAR com 2 Menus: Ficheiro e Entidade:
			* 				- 	Ficheiro tem menuItens simples para ações básicas como 
			* 					fechar a app, copy, past, save, etc.
			* 				- 	Entidade tem 3 RatioItensMenus para selecionar a Entidade
			* 					ativa. A ação de cada uma desta opções vai ter duas vertentes
			* 					1ª Executa uma TableView com os dados da respetiva Entidade.
			* 						Alunos,	Professores ou Funcionários.
			* 					2ª Condiciona a ação dos botões da região esquerda, que 
			* 						têm por função carregar layouts na zona central para 
			* 						Inserir, alterar ou Eliminar dados na Entidade selecionada. 
			* 						
			* 					
			*//////////////////////////////////////////////////////////////////////////////////
			
			// Criar o menuBar e associar os menus  
			MenuBar menuBar = new MenuBar();						// MenuBAR
			Menu menuFile = new Menu("_Ficheiros");					// Menu File.F é shortcut
			Menu menuEntidade = new Menu("_Entidades");				// Menu Entidades.E é shortcut 
			
			// Menu Ficheiro - Opções simples
			MenuItem menuItemFileOpcao1 = new MenuItem("Opção1");	// Opções do menu Ficheiro
			MenuItem menuItemFileOpcao2 = new MenuItem("Opção1");
			MenuItem menuItemFileOpcao3 = new MenuItem("Opção3");
			
			// Listeners
			menuItemFileOpcao1.setOnAction(e->Utils.alertBox("Menu File","op1 - a desenvolver"));
			menuItemFileOpcao2.setOnAction(e->Utils.alertBox("Menu File","op2 - a desenvolver"));
			menuItemFileOpcao3.setOnAction(e->Utils.alertBox("Menu File","op3 - a desenvolver"));

			//Separador de menu
			//SeparatorMenuItem menuItemSeparador = new SeparatorMenuItem(); //Separador de itens 

			// Adicionar os MenuItens ao menuFile.
			menuFile.getItems().addAll(
					menuItemFileOpcao1,
					menuItemFileOpcao2,
					//menuItemSeparador,
					menuItemFileOpcao3);

			
			// Menu Entidade - RadioMenuItens, com listeners, para chamar as ações
			RadioMenuItem menuItemEntidadeAlunos = new RadioMenuItem("Alunos");
			RadioMenuItem menuItemEntidadeProfs = new RadioMenuItem("Professores");
			RadioMenuItem menuItemEntidadeFunc = new RadioMenuItem("Funcionários");
			
			//Agrupar os RadioMenuItens num Toggle
			ToggleGroup toggleMenuEntidades = new ToggleGroup();
			menuItemEntidadeAlunos.setToggleGroup(toggleMenuEntidades);
			menuItemEntidadeProfs.setToggleGroup(toggleMenuEntidades);
			menuItemEntidadeFunc.setToggleGroup(toggleMenuEntidades);
			
			menuItemEntidadeAlunos.setSelected(true);		// opção prédefinida (checked)
			
			// Listeners para ação do objeto
			menuItemEntidadeAlunos.setOnAction(e->{
				if(menuItemEntidadeAlunos.isSelected()){
					Utils.alertBox("Menu Entidades","TODO - TavleView Alunos");
					//TODO: CARREGA TABLEVIEW NA REGIÃO CENTRAL COM DADOS DA TABELA ALUNOS
				}
			});
			
			menuItemEntidadeProfs.setOnAction(e->{
				if(menuItemEntidadeProfs.isSelected()){
					Utils.alertBox("Menu Entidades","TODO - TavleView Professores");
					//TODO: CARREGA TABLEVIEW NA REGIÃO CENTRAL COM DADOS DA TABELA PROFS
				}
			});

			menuItemEntidadeFunc.setOnAction(e->{
				if(menuItemEntidadeFunc.isSelected()){
					Utils.alertBox("Menu Entidades","TODO - TavleView Funcionários");
					//TODO: CARREGA TABLEVIEW NA REGIÃO CENTRAL COM DADOS DA TABELA FUNCS
				}
			});

			// Adicionar os MenuItens ao menuFile.
			menuEntidade.getItems().addAll(
					menuItemEntidadeAlunos,
					menuItemEntidadeProfs,
					menuItemEntidadeFunc);
			
			// Aassociar os menus ao MenuBar
			menuBar.getMenus().addAll(menuFile, menuEntidade);

			// associar o menu à região TOP do rootLayout
			layoutRoot.setTop(menuBar);
			
			
			
			//////////////////////////////////////////////////////////////////////////////////////////
			/* Região Esq - 3 botões: Inserir Alterar e Apagar: Atuam na região centro, carregando uma
			 * 				lista com os registos da tabela de base de dados, referente à Entidade 
			 * 				selecionada no menu. 
			 * 				Se o botão for Inserir, carrega na Região centro o layout do ex10, com 
			 * 				textfieds, para inserir dados, seguidos de 2 botões: OK e CANCELAR.
			 * 				Se o botão for Alterar ou Eliminar, executam 2 ações:
			 * 				- Atualizam uma variável onde registam o seu nome: Alterar ou Apagar
			 * 				- Carregam o mesmo Layout VBOX do ex17c na Região centro com 2 objetos: 
			 * 				uma TableView, com os dados da tabela da entidade selecionada e 2 botões: 
			 * 				OK e CANCELAR. A ação de executar o comando SQL DML pertence ao botão OK
			 * 				definido no texto da Região Centro. Cancelar carrega a lista inicial.
			 *////////////////////////////////////////////////////////////////////////////////////////

			VBox layoutLeft = new VBox(20);									// Preparação da VBox para a região esq do BorderPane
			layoutLeft.setBackground(null);
			layoutLeft.setPadding(new Insets(15, 12, 15, 12));				// Espaços para os limites
			layoutLeft.setSpacing(10);										// espaços entre as células
			layoutLeft.setStyle("-fx-background-color: #336699;");			// Cor de fundo

			// Botões - Executam os comandos DML, conforme a opção ativada nomenu Entidades
			Button btnLeftLayoutInserir = new Button("Inserir");			
			Button btnLeftLayoutAlterar = new Button("Alterar");
			Button btnLeftLayoutApagar = new Button("Eliminar");
			
			//Tamanhos normalizados ?????????????????????????       PESQUISAR ???
			btnLeftLayoutInserir.prefWidth(175);
			btnLeftLayoutAlterar.minWidth(95);
			btnLeftLayoutApagar.maxWidth(105);
			
			// Listeners
			/*TODO: verifica a Entidade ativa no menu Entidade e ativa o layout de inserir dados
			 * no Layout central. Ali haverá TextFields e 2 botões, Cancelar e Inserir que fará
			 * a respetiva validação dos dados introduzicos e a inserção na BD
			 */
			btnLeftLayoutInserir.setOnAction(e->{
				
				//TODO: efetua a validação dos textfields e executa a DML
				if(menuItemEntidadeAlunos.isSelected()){
					Utils.alertBox("Botão Inserir","op Alunos Está ativa");
				}
				if(menuItemEntidadeProfs.isSelected()){
					Utils.alertBox("Botão Inserir","op professores está ativa");
				}
				if(menuItemEntidadeFunc.isSelected()){
					Utils.alertBox("Botão Inserir","op Funcionários está ativa");
				}
				
			});
			btnLeftLayoutAlterar.setOnAction(e->{
				//TODO: efetua a validação dos textfields e executa a DML
				if(menuItemEntidadeAlunos.isSelected()){
					Utils.alertBox("Botão Alterar","op Alunos Está ativa");
				}
				if(menuItemEntidadeProfs.isSelected()){
					Utils.alertBox("Botão Alterar","op professores está ativa");
				}
				if(menuItemEntidadeFunc.isSelected()){
					Utils.alertBox("Botão Alterar","op Funcionários está ativa");
				}
			});
			btnLeftLayoutApagar.setOnAction(e->{
				//TODO: efetua a validação dos textfields e executa a DML
				if(menuItemEntidadeAlunos.isSelected()){
					Utils.alertBox("Botão Apagar","op Alunos Está ativa");
				}
				if(menuItemEntidadeProfs.isSelected()){
					Utils.alertBox("Botão Apagar","op professores está ativa");
				}
				if(menuItemEntidadeFunc.isSelected()){
					Utils.alertBox("Botão Apagar","op Funcionários está ativa");
				}
			});

			// Adicionar os Botões ao Layout da região esquerda.
			layoutLeft.getChildren().addAll(
					btnLeftLayoutInserir,
					btnLeftLayoutAlterar,
					btnLeftLayoutApagar);
			
			// associar o menu à região TOP do rootLayout
			layoutRoot.setLeft(layoutLeft);
			
			
			/*//////////////////////////////////////////////////////////////////////////////////////////
			* Região Centro - 	Por defeito carrega o layout do ex17a com a lista simples (TableView) 
			* 					da Entidades que estiver selecionada no menu.
			* 					Mas por ação de um dos botões da Região esquerda, Inserir, alterar 
			* 					ou eliminar, carrega um de dois layouts: 
			* 					- Inserir, verifica qual a entidade selecionada no menu e carrega uma 
			* 						GridPane (ex10 alterada) com os Textfields correspondentes, para 
			* 						receber os dados e inserir na respetiva tabela. Terá de haver 3 
			* 						destes Layouts, um para cada Entidades, porque terão forçosamente 
			* 						atributos diferentes. 
			* 						No final destes Layouts estarão 2 botões: OK e CANCELAR.
			* 					- Alterar e Apagar verificam também qual a entidade selecionada no menu
			* 						mas carregam ambas 1 de 3 Layouts com VBox, com base no ex17c, 
			* 						devidamente adaptados às respetivas Entidades e onde para além da 
			* 						TableView, existem 2 botões: OK e CANCELAR. 
			* 					Qualquer destes 3 botões: Inserir, Alterar ou Apagar tem que também fazer
			* 					uma terceira ação: ativam uma variável String "btnClicado" que guarde até 
			* 					ao fim do processo, o nome do botão clicado.  Isto é necessário porque
			* 					vamos usar os mesmos Layouts para situações diferentes:  
			* 
			* 					INSERIR	-> LAYOUT GridPane com textFields e botões (OK e CANCELAR) 
			* 							-> Carimba a variável "btnClicado" com "Inserir"
			* 							OK -> SE btnClicado == "Inserir" 
			* 									- executa o insert na BD
			* 									- btnClicado == "" -> limpa a variável "btnClicado"
			* 							CANCELAR -> carrega a lista simples da entidade selecionada
			* 										limpa a variável "btnClicado"
			* 					ALTERAR -> LAYOUT VBOX com lista da entidade selecionada + botões. 
			* 							-> Carimba a variável "btnClicado" com "Alterar"
			* 							OK -> Verifica se há algum item selecionado: 
			* 									Se Não -> AlertBox(bláblá)
			* 									Se Sim -> LAYOUT dos textFields referente à entidade
			* 											> OK -> SE btnClicado == "Alterar" 
			* 												- executa o update na BD
			* 												- btnClicado == "" -> limpa "btnClicado"
			* 											> Cancelar - carrega a lista anterior
			* 							CANCELAR -> carrega a lista simples da entidade selecionada
			* 					APAGAR -> Igual a Alterar, mas a ação do 2º botão OK executa diretamente
			* 								o delete da tabela da entidade selecionada.
			* 
			* 					Notar ainda que as ações dos botões Alterar e Apagar, obrigam a que 
			* 					exista uma pré-seleção de um dos itens da TableView, caso contrário, 
			* 					como saber qual o item a alterar ou eliminar?
			* 
			* 					Notar também que, quer a Inserção de dados, quer a alteração têm que
			* 					executar a validação de dados, com base no ex10, caso contrário 
			* 					incorre-se em exceções.
			* 
			* 					CANCELAR - Carrega simplesmente o layout da lista simples (sem botões)
			*///////////////////////////////////////////////////////////////////////////////////////////
		 
			// TableViews com Lista simples de Alunos, Profs e Func., sem botões
			// layout
			
			TableView<Aluno> tableViewAlunos = new TableView<>();	// Criação de tabela 
			
			//Coluna Numero de processo do aluno
			TableColumn<Aluno, String> colunaNProc = new TableColumn<>("Processo");
			colunaNProc.setMinWidth(20);
			colunaNProc.setCellValueFactory(new PropertyValueFactory<>("nProc"));
			
			//Coluna Numero de aluno na turma
			TableColumn<Aluno, String> colunaNumeroTurma = new TableColumn<>("Numero");
			colunaNumeroTurma.setMinWidth(20);
			colunaNumeroTurma.setCellValueFactory(new PropertyValueFactory<>("nTurma"));
			
			// coluna do nome de Aluno
			TableColumn<Aluno, String> colunaNome = new TableColumn<>("Nome");
			/* Cada coluna é  definida com um PAR de dados:
			 * 	- 1º é o tipo de dados a usar na lista: Aluno
			 * 	- 2º é o tipo de dados a usar na coluna: String para o nome 
			 * O construtor da classe TableColumn recebe o nome da coluna
			*/
			colunaNome.setMinWidth(200);	// largura em pixeis da coluna
			colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			/*Nome do atributo, na Lista, onde vai ler os dados */
			
			// Associar as colunas à tabela
			tableViewAlunos.getColumns().addAll(colunaNProc, colunaNumeroTurma, colunaNome);
			
			// Carregar a lista com dados
			tableViewAlunos.setItems( carregaListaAlunos() );		// Carrega Alunos a partir do método
			
			//TODO: falta construir Classes Professor e Funcionario
			//TODO: falta construir método de carregamento da tableView de Professores e Funcionários
			
			
			
			
			// layout para conter a tableView selecionada no menu
			StackPane layoutCentralTableView = new StackPane();		
			if(menuItemEntidadeAlunos.isSelected()){layoutCentralTableView.getChildren().add(tableViewAlunos);};
			//if(menuItemEntidadeProfs.isSelected()){	layoutCentralTableView.getChildren().add(tableViewProfs);};
			//if(menuItemEntidadeFunc.isSelected()){	layoutCentralTableView.getChildren().add(tableViewFuncs);};
			//TODO: ativar apenas depois das Classes, TableViews e métodos de carregamento estarem construidas.

			/* Botões OK e CANCELAR
			 *  Só são Mostrados se a string btnClicado não for "vazia", caso contrário:
			 *  Se for vazia não executa esta 2ª parte, carrega apenas a lista simples, sem botões
			 *  Se "Alterar" ou Anular:
			 *  - btnCancelar limpa a string btnClicado e executa este método para a lista simples
			 *  - btnOk -> verifica se há algum item selecionado na lista
			 *  		-> Se não, alert()
			 *  		-> Se Sim, Testa outra vez a string btnClicado
			 *  			-> se "Alterar" -> Verifica qual a Entidade Selecionada no menu
			 *  							-> Obtem os dados da respetiva Tabela
			 *  							-> Carrega o Layout com os TextFields desta Entidade
			 *  							-> Preenche os TextFields com os dados recolhidos da tabela
			 *  							-> O utilizador poderá alterar aí os dados e OK ou CANCELAR
			 *  							-> CANCELAR -> carrega o layout anterior: lista com botões
			 *  							-> OK 	-> executa um update na tabela selecionada
			 *  									-> limpa a variável btnClicado 
			 *  									-> carrega o layout com a lista simples da entidade.
			 *				-> Se "Anular"	-> CANCELAR -> carrega o layout anterior: lista com botões
			 *  							-> OK 	-> executa um delete na tabela selecionada
			 *  									-> limpa a variável btnClicado 
			 *  									-> carrega o layout com a lista simples da entidade.   
			 */
			
			
			
			// Decisão se carrega no layout a lista simpes ou com botões
			
			if(btnClicado.isEmpty()){	// carrega na zona central apenas a TableView, para a lista simples
				layoutRoot.setCenter(layoutCentralTableView);
			}
			else {						// carrega na zona central a TableView com botões
				
			
				HBox layoutHBtns = new HBox(40);		// Criação do layout para encaixar botões
				VBox layoutVBtns = new VBox(20);		// Criação do layout para encaixar lista e botões
			
				
				// Botão CANCELAR carrega lista simples
				Button btnCancelar = new Button("Cancelar");
				btnCancelar.setOnAction(e->{
					btnClicado = "";								// btnClicado passa a vazio
					
					// Carrega no Layout, a tableView simples da entidade selecionada no menu
					if(menuItemEntidadeAlunos.isSelected()){layoutCentralTableView.getChildren().add(tableViewAlunos);};
					//if(menuItemEntidadeProfs.isSelected()){	layoutCentralTableView.getChildren().add(tableViewProfs);};
					//if(menuItemEntidadeFunc.isSelected()){	layoutCentralTableView.getChildren().add(tableViewFuncs);};
					//TODO: ativar apenas depois das Classes, TableViews e métodos de carregamento estarem construidas.

					// Carrega na região central, o Layout anterior
					layoutRoot.setCenter(layoutCentralTableView);
				});
				
				
				
				// Botão OK - Verifica se btnClicado é Inserir, Alterar ou Apagar.
				
				/*isto não está bem.
				 * Btn Inserir: Carrega layout com TextFields: ok -> insere
				 * Btn Alterar: Carrega layout com lista + botões: ok-> carrega layout com TextFields - update
				 * Btn Anular: Carrega layout com lista + botões: ok-> delete o item selecionado
				 * 
				 * Tirar botões do layout esquerdo
				 * A lista tem sempre botões
				 * INSERIR - Carrega layout com TextFields: ok -> insere, cancela - recarrega lista 
				 * ALTERER - Carrega layout com TextFields: ok -> update, cancela - recarrega lista
				 * APAGAR  - Delete o registo selected 
				 * 
				 * */
			
			}
			Scene scene = new Scene(layoutRoot,600,400);		// Trataamento da janela
			primaryStage.setScene(scene);
			primaryStage.setTitle("SQL DML");
			primaryStage.show();		
		} 
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	/*************************************************************************************************
	 * Métodos para carregamento das Listas de alimentação das TableViews. 
	 * São executados pelo botão EDITAR, eliminar, alterar ou eliminar de cada entidade
	 * Popular uma ObservableList com os dados da BD e desvolvemr à TableView
	 *************************************************************************************************/
    private ObservableList<Aluno> carregaListaAlunos(){
    	
    	ObservableList<Aluno> listaAlunos = FXCollections.observableArrayList();
    	
        listaAlunos.add(new Aluno(125, 0, "A.Ferraz"));
        listaAlunos.add(new Aluno(126, 1, "Bruno Coimbra"));
        listaAlunos.add(new Aluno(127, 2, "Catalin Criste"));
        listaAlunos.add(new Aluno(128, 3, "David Sousa"));
        listaAlunos.add(new Aluno(129, 6, "João Ventura"));
        listaAlunos.add(new Aluno(130, 7, "Jorge Silva"));
        listaAlunos.add(new Aluno(131, 8, "Marcelo Rachado"));
        listaAlunos.add(new Aluno(132, 9, "Mariana Costa"));
        listaAlunos.add(new Aluno(133, 10,"Patrícia Carmo"));
        listaAlunos.add(new Aluno(134, 11,"Ricardo Gomes"));
        listaAlunos.add(new Aluno(135, 12,"Rúben Severino"));
        listaAlunos.add(new Aluno(136, 14,"Rui Ricardo"));
        listaAlunos.add(new Aluno(137, 15,"Samuel Santos"));
        
        return listaAlunos;
    }
}