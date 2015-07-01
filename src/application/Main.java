package application;
	
// JavaFX_25e_SQL_APP_ESCOLA

import java.sql.Connection;

import application.Utils;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioMenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.converter.IntegerStringConverter;

/* SQL - Liga��o a Bases de Dados MySQL e SQLServer e comandos DML.
 * Neste esxerc�cio vamos usar um menu para selecionar uma de 3 entidades: 
 * Alunos, Professores ou Alunos e com recursos a 3 bot�es, executar 
 * comandos DML: query, insert, update e delete.    

 * Passo1 - Come�ar por criar o rootLayout do tipo borderPane e 
 * 			escrever o linhas de coment�rio para delimitar as regi�es.
 * Passo2 - Criar o menu na regi�o top
 * Passo3 - Criar os bot�es na regi�o esquerda.
 * 			Criar a��es de alert para testar funcionamento.
 * Passo4 - Criar as tableView em m�todos para atuar na regi�o centro.
 * 			Criar 3 tableViews para o layout da regi�o central: ex17
 * 			Criar 3 m�todos para carregar as tableViews (ex17)
 * 			Criar 3 classes: Aluno, Professor e Funcion�rio
 */ 

public class Main extends Application {
	
	// Atributos da classe: podem ser  acedidos de qualquer m�todo. S�o globais � classe 
	static String btnClicado = "";					// Recebe o nome do bot�o clicado (Inserir, Alterar, Eliminar)
	static Aluno alunoSelecionado = null;			// Recebe o aluno selecionado da lista, para altera��o/elimina��o
	BorderPane layoutRoot = new BorderPane();		// Layout Principal: BorderPAne - 5 Regi�es
	static Connection conn = null;					// Liga��o a base de dados
	static TableView<Aluno> tableViewAlunos = null;	// Cria��o de tabela de alunos
	
	static boolean msgON = false;					// Ativa Mensagens de controlo
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			/*//////////////////////////////////////////////////////////////////////////////////
			* Regi�o TOP - 	MenuBAR com 2 Menus: Ficheiro e Entidade:
			* 				- 	Ficheiro tem menuItens simples para a��es b�sicas como 
			* 					fechar a app, copy, past, save, etc.
			* 				- 	Entidade tem 3 RatioItensMenus para selecionar a Entidade
			* 					ativa. A a��o de cada uma desta op��es vai ter duas vertentes
			* 					1� Executa uma TableView com os dados da respetiva Entidade.
			* 						Alunos,	Professores ou Funcion�rios.
			* 					2� Condiciona a a��o dos bot�es da regi�o esquerda, que 
			* 						t�m por fun��o carregar layouts na zona central para 
			* 						Inserir, alterar ou Eliminar dados na Entidade selecionada. 
			* 						
			* 					
			*//////////////////////////////////////////////////////////////////////////////////
			
			// Criar o menuBar e associar os menus  
			MenuBar menuBar = new MenuBar();						// MenuBAR
			Menu menuFile = new Menu("_Ficheiros");					// Menu File.F � shortcut
			Menu menuEntidade = new Menu("_Entidades");				// Menu Entidades.E � shortcut 
			
			// Menu Ficheiro - Op��es simples
			MenuItem menuItemFileOpcao1 = new MenuItem("Op��o1");	// Op��es do menu Ficheiro
			MenuItem menuItemFileOpcao2 = new MenuItem("Op��o1");
			
			SeparatorMenuItem menuItemSeparador = new SeparatorMenuItem(); //Separador de menu
			
			
			RadioMenuItem menuItemFileMsgOn = new RadioMenuItem("Msg - ON");
			RadioMenuItem menuItemFileMsgOff = new RadioMenuItem("Msg - OFF");
			
			//Agrupar os RadioMenuItens da MSG num Toggle
			ToggleGroup toggleMenuFileMsg = new ToggleGroup();
			menuItemFileMsgOn.setToggleGroup(toggleMenuFileMsg);
			menuItemFileMsgOff.setToggleGroup(toggleMenuFileMsg);
			menuItemFileMsgOff.setSelected(true);					// op��o pr�definida (checked)
			
			
			// Listeners
			menuItemFileOpcao1.setOnAction(e->Utils.alertBox("Menu File","op1 - a desenvolver"));
			menuItemFileOpcao2.setOnAction(e->Utils.alertBox("Menu File","op2 - a desenvolver"));
			menuItemFileMsgOn.setOnAction(e-> {
				msgON = true; 							// Ativa mensagens para este projeto	
				UtilsSQLConn.msgON = true;				// Ativa mensagens na UtilsSQLconn
				//Utils.alertBox("Menu File","Mensagens de controlo Ativdas");
			});
			menuItemFileMsgOff.setOnAction(e->{
				msgON = true; 							// Ativa mensagens para este projeto	
				UtilsSQLConn.msgON = false;				// Ativa mensagens na UtilsSQLconn 
				//Utils.alertBox("Menu File","Mensagens de controlo Desativadas");
			});

			// Adicionar os MenuItens ao menuFile.
			menuFile.getItems().addAll(
					menuItemFileOpcao1,
					menuItemFileOpcao2,
					menuItemSeparador,
					menuItemFileMsgOn,
					menuItemFileMsgOff);

			
			// Menu Entidade - RadioMenuItens, com listeners, para chamar as a��es
			RadioMenuItem menuItemEntidadeAlunos = new RadioMenuItem("Alunos");
			RadioMenuItem menuItemEntidadeProfs = new RadioMenuItem("Professores");
			RadioMenuItem menuItemEntidadeFunc = new RadioMenuItem("Funcion�rios");
			
			//Agrupar os RadioMenuItens num Toggle
			ToggleGroup toggleMenuEntidades = new ToggleGroup();
			menuItemEntidadeAlunos.setToggleGroup(toggleMenuEntidades);
			menuItemEntidadeProfs.setToggleGroup(toggleMenuEntidades);
			menuItemEntidadeFunc.setToggleGroup(toggleMenuEntidades);
			
			menuItemEntidadeAlunos.setSelected(true);		// op��o pr�definida (checked)
			
			// Listeners para a��o dos RadioMenuItens: Carregam as respetivas listas
			menuItemEntidadeAlunos.setOnAction(e->{
				if(menuItemEntidadeAlunos.isSelected()){
					// Carrega lista de alunos na regi�o central
					layoutRoot.setCenter(   layoutAlunoLista()  );
				}
			});
			
			menuItemEntidadeProfs.setOnAction(e->{
				if(menuItemEntidadeProfs.isSelected()){
					if(msgON){
						Utils.alertBox("Menu Entidades","TODO - TavleView Professores");
					}
					
					//TODO: CARREGA TABLEVIEW NA REGI�O CENTRAL COM DADOS DA TABELA PROFS
					//layoutRoot.setCenter(   layoutProfLista()  );
				}
			});

			menuItemEntidadeFunc.setOnAction(e->{
				if(menuItemEntidadeFunc.isSelected()){
					if(msgON){
						Utils.alertBox("Menu Entidades","TODO - TavleView Funcion�rios");
					}
					//TODO: CARREGA TABLEVIEW NA REGI�O CENTRAL COM DADOS DA TABELA FUNCS
					//layoutRoot.setCenter(   layoutFuncLista()  );
				}
			});

			// Adicionar os MenuItens ao menuEntidades.
			menuEntidade.getItems().addAll(
					menuItemEntidadeAlunos,
					menuItemEntidadeProfs,
					menuItemEntidadeFunc);
			
			
			
			// Aassociar os menus ao MenuBar
			menuBar.getMenus().addAll(menuFile, menuEntidade);

			// associar o menu � regi�o TOP do rootLayout
			layoutRoot.setTop(menuBar);
			
			
			
			//////////////////////////////////////////////////////////////////////////////////////////
			/* Regi�o Esq - 3 bot�es: Inserir Alterar e Apagar: Atuam na regi�o centro, carregando uma
			 * 				lista com os registos da tabela de base de dados, referente � Entidade 
			 * 				selecionada no menu. ex17b.
			 * 				Qualquer dos bot�es executa 2 a��es:
			 * 				- regista num atributo String "btnClicado", o seu nome.
			 * 				- carrega na Regi�o centro o layout GridPane (ex10), com textfieds para
			 * 				  tratar os dados do registo, seguidos de 2 bot�es: OK e CANCELAR.
			 * 				Atrav�s da String btnClicado podemos manipular o aspeto do Layout:
			 * 				- Inserir: ter� os TextFields vazios, prontos a receber dados para Insert;
			 * 				- Alterar: ter� os dados do registo selecionado, para serem alterados;
			 * 				- Eliminar: ter� os TextFields disabled para serem apenas observados.
			 * 				Bot�es OK e CANCELAR s�o discutidos no texto da regi�o central
			 *///////////////////////////////////////////////////////////////////////////////////////

			// Bot�es - Executam os comandos DML, conforme a op��o ativada nomenu Entidades
			Button btnLeftLayoutInserir = new Button("Inserir");			
			Button btnLeftLayoutAlterar = new Button("Alterar");
			Button btnLeftLayoutApagar = new Button("Eliminar");
			
			//Tamanhos normalizados. adquirem o m�ximo da VBox que os recebe
			btnLeftLayoutInserir.setMaxWidth(Double.MAX_VALUE);
			btnLeftLayoutAlterar.setMaxWidth(Double.MAX_VALUE);
			btnLeftLayoutApagar.setMaxWidth(Double.MAX_VALUE);
			
			// Listeners
			/*TODO: verifica a Entidade ativa no menu Entidade e ativa o layout de inserir dados
			 * no Layout central. Ali haver� TextFields e 2 bot�es, Cancelar e Inserir que far�
			 * a respetiva valida��o dos dados introduzicos e a inser��o na BD
			 */
			btnLeftLayoutInserir.setOnAction(e->{
				
				btnClicado = "Inserir";			// Ativa btnClicado para Inserir
				
				// Carrega layout de detalhes da Entidade ativa
				if(menuItemEntidadeAlunos.isSelected()){layoutRoot.setCenter(layoutAlunoDetalhes());}
				
				/*TODO: Desenvolver os m�todos de detalhes para Professor e Funcionario
				if(menuItemEntidadeProfs.isSelected()){layoutRoot.setCenter(layoutProfDetalhes());}
				if(menuItemEntidadeFunc.isSelected()){layoutRoot.setCenter(layoutFuncDetalhes());}
				*/
			});

			btnLeftLayoutAlterar.setOnAction(e->{
				
				btnClicado = "Alterar";							// Ativa btnClicado para Alterar

				// Extrai e testa se h� registo clicado da lista de selecionados
				// Como s� pode haver 1, estar� na posi��o 0.
				ObservableList<Aluno> itemSelecionado = tableViewAlunos.getSelectionModel().getSelectedItems();
				if(msgON){
					Utils.alertBox("INFO", "N�o selecionou qualquer registo");
				}
				else {
					/* 1 - O Item selecionado vai ser guardado no atributo alunoSelecionado
					 * 		Vai ser dada ordem para mostrar o layout de detalhes do aluno
					 * 		Ao inspecionar o atributo btnClicado, encontra-o com a String "Alterar"
					 * 		Isto define que os detalhes ter�o que exibir os dados do item selecionado
					 * 		guardados em alunoSelecionado. 
					 * 2 - Carrega Layout com detalhes para Alterar. Com os TextFields Enabled e com
					 * 		os dados do itemSelecionado, para o utilizador alterar.
					 */

					alunoSelecionado = itemSelecionado.get(0);
					layoutRoot.setCenter( layoutAlunoDetalhes() );
				}
			});

			
			btnLeftLayoutApagar.setOnAction(e->{
				
				btnClicado = "Eliminar";			// Ativa btnClicado para Eliminar

				// Extrai e testa se h� registo clicado da lista de selecionados
				// Como s� pode haver 1, estar� na posi��o 0.
				
				ObservableList<Aluno> itemSelecionado = tableViewAlunos.getSelectionModel().getSelectedItems();
				if(msgON){
					Utils.alertBox("INFO", "N�o selecionou qualquer registo");
				}
				else {
					/* 1 - O Item selecionado vai ser guardado no atributo alunoSelecionado
					 * 		Vai ser dada ordem para mostrar o layout de detalhes do aluno
					 * 		Ao inspecionar o atributo btnClicado, encontra-o com a String "Alterar"
					 * 		Isto define que os detalhes ter�o que exibir os dados do item selecionado
					 * 		guardados em alunoSelecionado. 
					 * 2 - Carrega Layout com detalhes para Eliminar. Com os TextFields Disabled, s�
					 * 		para o utilizador ver os dados antes de eliminar.
					 */

					alunoSelecionado = itemSelecionado.get(0);
					layoutRoot.setCenter( layoutAlunoDetalhes() );
				}
			});
			
			
			
			
			// VBox UP - Recebe os bot�es DML da Base de Dados e ajusta-os em cima.
			VBox layoutLeftUpper = new VBox(10);						// VBox bot�es DML
			layoutLeftUpper.setAlignment(Pos.TOP_CENTER);
			layoutLeftUpper.getChildren().addAll(
					btnLeftLayoutInserir,
					btnLeftLayoutAlterar,
					btnLeftLayoutApagar);

			
			
			
			// VBox Down - Recebe os bot�es de testes � Base de Dados
			// Teste � SGBD MySQL
			Button btnMySQLconnection = new Button("MySQL");
			btnMySQLconnection.setMaxWidth(Double.MAX_VALUE);
			btnMySQLconnection.setOnAction(e->{
				
				UtilsSQLConn.mySqlTeste();	// m�todo para testar liga��o � BD
			});
			
			// Teste � SGBD MySQL e executa uma query
			Button btnMySQLquery = new Button("MySQL Query");
			btnMySQLquery.setMaxWidth(Double.MAX_VALUE);
			btnMySQLquery.setOnAction(e->{
				UtilsSQLConn.mySqlQwery("Select nProc, NAluno, Nome from aluno");
				
			});
			
			// Teste � SGBD SQLSERVER
			Button btnSQLserverconnection = new Button("SQLserver");
			btnSQLserverconnection.setMaxWidth(Double.MAX_VALUE);
			btnSQLserverconnection.setOnAction(e->{
				UtilsSQLConn.connectToSQLSerrver();
			});
			
			// VBox Down - Adiciona os bot�es
			VBox layoutLeftDown = new VBox(10);	
			layoutLeftDown.getChildren().addAll(
					btnMySQLconnection,
					btnMySQLquery,
					btnSQLserverconnection);
			layoutLeftDown.setAlignment(Pos.BOTTOM_CENTER);
			
			
			
			
			
			// VBOX LEFT - recebe os dois VBox com os respetivos bot�es
			VBox layoutLeft = new VBox(40);								// VBox regi�o esq do BorderPane
			//layoutLeft.setBackground(null);
			layoutLeft.setPadding(new Insets(15, 12, 15, 12));			// Espa�os para os limites
			layoutLeft.setStyle("-fx-background-color: #336699;");		// Cor de fundo
			layoutLeft.getChildren().addAll(layoutLeftUpper, layoutLeftDown);
			
			// associar a VBox � regi�o LEFT do rootLayout
			layoutRoot.setLeft(layoutLeft);
			
			
			
			
			
			/*//////////////////////////////////////////////////////////////////////////////////////////
			* Regi�o Centro - 	Por defeito carrega um de 3 m�todos com os layouts (identicos ao ex17b) 
			* 					com listas simples (TableView), da Entidade que estiver selecionada no
			* 					menu. Mas por a��o de um dos bot�es da Regi�o esquerda, Inserir, Alterar 
			* 					ou Eliminar, carrega um de 3 outros layouts (ex10) com os TextFields 
			* 					da Entidade selecionada no menu. Deste modo usa o mesmo Layout antes de
			* 					Inserir, Alterar ou Eliminar um registo na respetiva tabela de BD, com
			* 					uma diferen�a:
			* 					- Se Inserir: os TextFieds estar�o enabled e vazios.
			* 					- Se Alterar: os TextFieds estar�o enabled e preenchidos.
			* 					- Se Eliminar: estar�o disabled e preenchidos.
			* 					Estes 3 layouts ser�o do tipo VBox com 2 partes: 
			* 					- parte superior recebe o layout Gridpane com os TextFilds (ex10)
			* 					- parte de baixo recebe o layout HBox com 2 bot�es: OK e CANCELAR.
			* 					Os bot�es executam o seguinte:
			* 					- Cancelar carrega simplesmente o layout com a lista por defeito, da
			* 					  Entidade que estiver selecionada no menu.
			* 					- OK faz mais a��es, dependendo do que estiver no atributo btnClicado: 
			* 						- Se Inserir, executa a valida��o do ex10 e faz o SQL Insert
			* 						- Se Alterar, executa a valida��o do ex10 e faz o SQL update
			* 						- Se Eliminar,  executa o SQL delete:
			* 					- Ap�s o sucesso da a��o anterior, recarrega a lista na regi�o esquerda.
			* 
			* 					Esquema resumido:
			* 					INSERIR	-> LAYOUT VBox com GridPane de textFields e HBox(OK e CANCELAR) 
			* 							-> Carimba a vari�vel "btnClicado" com "Inserir"
			* 							OK  - Valida dados nas TextFields
			* 								- executa o insert na BD
			* 								- limpa a vari�vel "btnClicado"
			* 							CANCELAR -> carrega a lista simples da entidade selecionada
			* 										limpa a vari�vel "btnClicado"
			* 					ALTERAR -> LAYOUT VBox com GridPane de textFields e HBox(OK e CANCELAR) 
			* 							-> Carimba a vari�vel "btnClicado" com "Alterar"
			* 							OK -> Verifica se h� algum item selecionado: 
			* 									Se N�o -> AlertBox(bl�bl�)
			* 									Se Sim -> LAYOUT dos textFields referente � entidade
			* 											> OK -> SE btnClicado == "Alterar" 
			* 												- executa o update na BD
			* 												- btnClicado == "" -> limpa "btnClicado"
			* 											> Cancelar - carrega a lista anterior
			* 							CANCELAR -> carrega a lista simples da entidade selecionada
			* 					APAGAR -> Igual a Alterar, mas a a��o do OK executa diretamente o 
			* 							delete da tabela da entidade selecionada.
			*///////////////////////////////////////////////////////////////////////////////////////////
		 
			// associar a lista ativa � regi�o CENTER do rootLayout
			if(menuItemEntidadeAlunos.isSelected()){layoutRoot.setCenter(   layoutAlunoLista()  );}
			if(menuItemEntidadeProfs.isSelected()){Utils.alertBox("setCenter", "Profs por Por fazer")  ;}
			if(menuItemEntidadeFunc.isSelected()){Utils.alertBox("setCenter", "Funcs Por fazer")  ;}


			
								
						
			/*//////////////////////////////////////////////////////////////////////////
			* Prepara��o do layout principal e da janela 	
			*///////////////////////////////////////////////////////////////////////////
			
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
	 * M�todos para carregamento das Listas de alimenta��o das TableViews. 
	 * S�o executados pelo bot�o EDITAR, eliminar, alterar ou eliminar de cada entidade
	 * Popular uma ObservableList com os dados da BD e desvolvemr � TableView
	 *************************************************************************************************/
    private ObservableList<Aluno> carregaListaAlunos(){
    	
    	/*TODO: Executa uma query � tabela Aluno
    	 * para cada registo, extrai os 3 atributos: nProc, NAluno e nome
    	 *  Executa um m�tofo nas UtilsSQLconn que 
    	 *  executa a query, 
    	 *  preenche uma lista destas
    	 *  e devolve-a preenchida para quem a chamar. 
    	 *  	- TableView, sempre que quer construir a lista
    	 *  	- 
    	 */
    	
    	
    	ObservableList<Aluno> listaAlunos = FXCollections.observableArrayList();
    	
        listaAlunos.add(new Aluno(125, 0, "A.Ferraz"));
        listaAlunos.add(new Aluno(126, 1, "Bruno Coimbra"));
        listaAlunos.add(new Aluno(127, 2, "Catalin Criste"));
        listaAlunos.add(new Aluno(128, 3, "David Sousa"));
        listaAlunos.add(new Aluno(129, 6, "Jo�o Ventura"));
        listaAlunos.add(new Aluno(130, 7, "Jorge Silva"));
        listaAlunos.add(new Aluno(131, 8, "Marcelo Rachado"));
        listaAlunos.add(new Aluno(132, 9, "Mariana Costa"));
        listaAlunos.add(new Aluno(133, 10,"Patr�cia Carmo"));
        listaAlunos.add(new Aluno(134, 11,"Ricardo Gomes"));
        listaAlunos.add(new Aluno(135, 12,"R�ben Severino"));
        listaAlunos.add(new Aluno(136, 14,"Rui Ricardo"));
        listaAlunos.add(new Aluno(137, 15,"Samuel Santos"));
        
        return listaAlunos;
    }
    
    
    /*************************************************************************************************
	 * layoutAlunoLista() 	- Constroi e devolve um layout StackPane com uma tableView de alunos 
	 *************************************************************************************************/
    private StackPane layoutAlunoLista(){
    	
		// TableView Alunos
		//TableView<Aluno> tableViewAlunos = new TableView<>();	// Cria��o de tabela 
    	tableViewAlunos = new TableView<>();	// Cria��o de tabela 
		
		//Coluna Numero de processo do aluno
		TableColumn<Aluno, String> colunaNProc = new TableColumn<>("Processo");
		/* Cada coluna �  definida com um PAR de dados:
		 * 	- 1� � o tipo de dados base a usar na lista: Aluno
		 * 	- 2� � o tipo de dados a usar na coluna: String para o nome 
		 * O construtor da classe TableColumn recebe o nome da coluna
		*/
		colunaNProc.setMinWidth(20);
		colunaNProc.setCellValueFactory(new PropertyValueFactory<>("nProc"));
		
		//Coluna Numero de aluno na turma
		TableColumn<Aluno, String> colunaNumeroTurma = new TableColumn<>("Numero");
		colunaNumeroTurma.setMinWidth(20);
		colunaNumeroTurma.setCellValueFactory(new PropertyValueFactory<>("nTurma"));
		
		// coluna do nome de Aluno
		TableColumn<Aluno, String> colunaNome = new TableColumn<>("Nome");
		colunaNome.setMinWidth(200);	// largura em pixeis da coluna
		colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
		
		// Associar as colunas � tabela
		tableViewAlunos.getColumns().addAll(colunaNProc, colunaNumeroTurma, colunaNome);
		
		// Carregar a TableView de Alunos a partir da BD. M�todo de UtilsSQLconn
		tableViewAlunos.setItems( UtilsSQLConn.carregaListaAlunos() );
		
		// Inserir TableView numa StackPane para controlar posi��o
		StackPane layoutCentralTableView = new StackPane();	
		layoutCentralTableView.getChildren().add(tableViewAlunos);
		layoutCentralTableView.alignmentProperty().set(Pos.CENTER);
		
		return layoutCentralTableView;			// Devolve o Layout com a lista de alunos pronta.
    }
    
    /*************************************************************************************************
	 * layoutAlunoDetalhes() - Constroi e devolve um layout VBOX com 2 layouts:
	 * - VBox 1� parte - GridPane com TextFields para tratamento dos dados de um Aluno.
	 * - VBox 2� parte - HBox(Bot�es OK e CANCELAR) OK - executa o insert, update ou delete.
	 * 
	 * � chamado a partir dos bot�es da regi�o esquerda, Inserir, Alterar e Eliminar:
	 * - Se Inserir, os TextFields est�o abertos e os dados ser�o validados antes de inseridos na tabela
	 * - Se Alterar, os TextFields s�o preenchidos para poderem ser alterados 
	 * 	 e os dados ser�o validados antes de inseridos na tabela.
	 * - Se Eliminar, os TextFields est�o disabled e o registo ser� eliminado.
	 * 
	 * Para as op��es alterar e eliminar, � necess�rio ter o registo clicado numa var tempor�ria, para 
	 * poder comparar os dados antes e depois, para altera��o e o nProc em ELIMINAR 
	 * 
	 * No final das opera��es � necess�rio correr outra vez a lista na regi�o central, com os dados
	 * da Tabela: layoutRoot.setCenter(layoutAlunoLista()).
	 *************************************************************************************************/
    private VBox layoutAlunoDetalhes(){
		
    	// Layout VBox ALunos
    	VBox layoutAlunoDados = new VBox(20);			// VBox - Recebe 2 Layouts: GridPane e HBox
		
    	
    	
		//GridPane (TextFields)
		GridPane layoutAlunoTextFilds = new GridPane();	// GridPane (TextFields)
		layoutAlunoTextFilds.setPadding(new Insets(10, 10, 10, 10));// espa�os para a janela
		layoutAlunoTextFilds.setVgap(8);							// espa�o entre colunas (pixeis)
		layoutAlunoTextFilds.setHgap(10);							// espa�o entre linhas	
		
		
		//TODO: Se alterar ou eliminar, recolhe dados da tableView
    	/*Tem que receber aqui um identiificador do elemento selecionado*/
    	
		

		
		
		// N�mero de processo
		Label labelAlunoProcesso = new Label("Processo:");			// Nova Label
		layoutAlunoTextFilds.add(labelAlunoProcesso, 0, 0);			// c�lula col 0,linha 0
		
		TextField txtAlunoProcesso = new TextField();				// Campo de texto vazio
		if(btnClicado == "Inserir"){txtAlunoProcesso.setPromptText("N�Processo");}
		
		// Se em altera��o ou para eliminar, o campo � preenchido com os dados do item selecionado
		if(btnClicado == "Alterar" || btnClicado == "Eliminar"){
			
			txtAlunoProcesso.setText(String.valueOf(alunoSelecionado.getNProc()));
			
			// Se em Elimina��o, o campo � inacess�vel
			if(btnClicado == "Eliminar"){txtAlunoProcesso.setDisable(true);}
		}
		layoutAlunoTextFilds.add(txtAlunoProcesso, 1, 0);			// c�lula: col 1, linha 0
		

		
		
		// N�mero de Aluno na Turma
		Label labelAlunoNTurma = new Label("N�Aluno:");				// Nova Label
		layoutAlunoTextFilds.add(labelAlunoNTurma, 0, 1);			// c�lula col 0,linha 0
		
		TextField txtAlunoNTurma = new TextField();					// Campo de texto vazio
		if(btnClicado == "Inserir"){txtAlunoNTurma.setPromptText("N�Aluno");}
		
		// Se em altera��o ou para eliminar, o campo � preenchido com os dados do item selecionado
		if(btnClicado == "Alterar" || btnClicado == "Eliminar"){
			
			txtAlunoNTurma.setText(String.valueOf(alunoSelecionado.getNTurma()));
			
			// Se em Elimina��o, o campo � inacess�vel
			if(btnClicado == "Eliminar"){txtAlunoNTurma.setDisable(true);}
		}
		layoutAlunoTextFilds.add(txtAlunoNTurma, 1, 1);				// c�lula: col 1, linha 0
		
		
		
		
		// Nome de Aluno
		Label labelAlunoNome = new Label("Nome:");					// Nova Label
		layoutAlunoTextFilds.add(labelAlunoNome, 0, 2);				// c�lula col 0,linha 0
		TextField txtAlunoNome = new TextField();					// Campo de texto vazio
		if(btnClicado == "Inserir"){txtAlunoNome.setPromptText("Nome");}
		
		// Se em altera��o ou para eliminar, o campo � preenchido com os dados do item selecionado
		if(btnClicado == "Alterar" || btnClicado == "Eliminar"){
			
			txtAlunoNome.setText(String.valueOf(alunoSelecionado.getNome()));
			
			// Se em Elimina��o, o campo � inacess�vel
			if(btnClicado == "Eliminar"){txtAlunoNome.setDisable(true);}
		}
		layoutAlunoTextFilds.add(txtAlunoNome, 1, 2);				// c�lula: col 1, linha 0


		
		
		// VBox 2� parte - Constroi numa HBox os 2 bot�es OK e CANCELAR
		HBox layoutAlunoBtn = new HBox(20);				// HBox (OK e CANCELAR)

		Button btnCancelar = new Button("Cancelar");	// Bot�o Cancelar
		btnCancelar.setOnAction(e->{
			btnClicado = "";							// btnClicado passa a vazio
			layoutRoot.setCenter(layoutAlunoLista());	// Carrega na regi�o central, o Layout da lista de Alunos
		});			
		
		Button btnOk = new Button("ok");				// Bot�o OK
		btnOk.setOnAction(e->{
			
			/* 1� 	Valida os dados
			 * 2� 	Verifica qual a Entidade selecionada no menu e qual o bot�o
			 * 		selecionado, para decidir o comando SQL a executar: 
			 * 			Se bot�o Inserir, SQL INSERT
			 * 			Se bot�o Alterar, SQL UPDATE
			 * 			Se bot�o Apagar, SQL DELETE
			 * 		Desativa btnClicado
			 * 		Carrega a lista da entidade Selecionada no menu
			 */
			
			if(txtAlunoProcesso.getText().isEmpty() || txtAlunoNTurma.getText().isEmpty() || txtAlunoNome.getText().isEmpty()){
				Utils.alertBox("ERRO", "Preencha os campos");
			}
			else {
				int nProc;				// Recebem e preparam os dados dos TextFields
				int nAluno;
				String nome = "";
				
				
				/* // Fazer a valida��o dos dados aqui, com os m�todos do ex10
				if(txtAlunoProcesso.getText()) || txtAlunoNTurma.getText(){
					//Valia��es com os m�todos do ex10, em Utils
				}
				else{
					// encaixar aqui o c�digo seguinte
				}
				*/
				
				// TODO: A encaixar no else, do bloco de cima. 
				
				// Se n�o foi selecionado qq bot�o, emite aviso.
				if (btnClicado.isEmpty()){
					Utils.alertBox("INFO", "N�o foi clicado nenhum bot�o para DML");
				}
				
				
				if(btnClicado == "Inserir"){			// SE Bot�o regi�o esq clicado foi INSERIR
					
					///1� Se o bot�o clicado no layoutLeft foi INSERIR,
					//2� btnOK executa a DML insert no m�todo UtilsSQLConn.mySqlDml(dml)

					nProc   = Integer.parseInt(txtAlunoProcesso.getText());
					nAluno  = Integer.parseInt(txtAlunoNTurma.getText());
					nome 	= txtAlunoNome.getText();

					/* TODO: Antes de executar o Insert, temos que verificar se o dado 
					 * referente � coluna PK (nProc), j� existe. Isso faz-se com 
					 * um Query � coluna da tabela
					*/
					
					if(UtilsSQLConn.mySqlQweryCheckPK("Select nProc from Aluno WHERE nProc = "+nProc+"")){
						Utils.alertBox("BD", "PK nProc j� existe");
					}else {
						// Executar o DML Insert, com os dados no formato correto
						// Lembrar que o m�todo Liga ao SGBD e abre a BD Escola
						// S� � preciso passar o comando DML (insert, update, delete)
						UtilsSQLConn.mySqlDml("Insert into Aluno"				// Tabela Aluno
								+" (nProc, NAluno, Nome)"						// Nomes Colunas
								+" Values("+nProc+", "+nAluno+", '"+nome+"')");	// Dados
						/*NOTAS:
						 * 1 - os campos alfab�ticos t�m que ser envolvidos em plicas
						 * 2 - os campos num�ricos n�o t�m.
						 * */
						
						// Procedimentos finais
						btnClicado = "";					// Desativar bot�o clicado
						if(msgON){
							Utils.alertBox("BD", " Alunos - Insert OK");
						}
					}
				}
				
				
				
				if(btnClicado == "Alterar"){			//SQL UPDATE
					//1� Se o bot�o clicado no layoutLeft foi ALTERAR,
					//2� btnOK executa a DML update no m�todo UtilsSQLConn.mySqlDml(dml)
					
					// Os tipos de dados t�m que coincidir com os tabela
					nProc   = Integer.parseInt(txtAlunoProcesso.getText());
					nAluno  = Integer.parseInt(txtAlunoNTurma.getText());
					nome 	= txtAlunoNome.getText();

					// altera os dados para o nProc do item selecionado
					UtilsSQLConn.mySqlDml("Update Aluno set"
							+ " nProc 	= "+nProc+", "			// aspas em num�ricos
							+ " NAluno 	= "+nAluno+", "			// aspas em num�ricos
							+ " Nome 	= '"+nome+"' "		// aspas e plicas em strings
							+ " where nProc = "+alunoSelecionado.getNProc()+" ");			
					/*NOTAS:
					 * 1 - os campos alfab�ticos t�m que ser envolvidos em plicas
					 * 2 - os campos num�ricos n�o t�m.
					 * */
					
					// Procedimentos finais
					btnClicado = "";					// Desativar bot�o clicado
					alunoSelecionado = null;			// Desativar aluno selecionado
					if(msgON){
						Utils.alertBox("BD", " Alunos - Update OK");
					}
				}
				
				
				
				
				if(btnClicado == "Eliminar"){			//SQL DELETE
					//1� Se o bot�o clicado no layoutLeft foi ELIMINAR,
					//2� btnOK executa a DML delete no m�todo UtilsSQLConn.mySqlDml(dml)
					
					// Recolhe o nProc da TextField
					nProc   = Integer.parseInt(txtAlunoProcesso.getText());
					UtilsSQLConn.mySqlDml("Delete from Aluno where nProc = "+nProc+" ");				
						
					// Procedimentos finais
					btnClicado = "";					// Desativar bot�o clicado
					alunoSelecionado = null;			// Desativar aluno selecionado
					if(msgON){
						Utils.alertBox("BD", " Alunos - Delete OK");
					}
				}
				
				layoutRoot.setCenter( layoutAlunoLista() );	// Carrega regi�o central com lista de Alunos
			}
		});
		layoutAlunoBtn.getChildren().addAll(btnOk, btnCancelar);
		layoutAlunoBtn.setAlignment(Pos.BASELINE_LEFT);

		// Carregar o layout VBox com os layouts Gridpane e HBOX 
		layoutAlunoDados.getChildren().addAll(layoutAlunoTextFilds, layoutAlunoBtn);
		
		return layoutAlunoDados;
    }
    
    
    
    public static void main(String[] args) {
		launch(args);
	}
}
