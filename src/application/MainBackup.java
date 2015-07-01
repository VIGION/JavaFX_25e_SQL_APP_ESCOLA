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

/* SQL - Liga��o a Bases de Dados MySQL e SQLServer e comandos DML.
 * 		Neste esxerc�cio vamos manipular dados com recurso a bases
 * 		de dados. A partir de um menu acedemos a consultas de Alunos,
 * 		professores ou funcion�rios de uma escola. A partir de um de
 * 		3 bot�es, inserimos, alteramos ou eliminamos dados referentes
 * 		� entidade que estiver a ser exibida na altura.    

 *TODO: Copiar border layout ex. 17, manter layouts esq. e central
 * 		Regi�o Esq: Bot�es: inserir, alterar apagar
 * 		Regi�o TOP: Menu:
 * 		- File: Sair
 * 		- RH: Recursos humanos, carrega Alunos, Professores e Funcion�rios
 * 			RadioItemMenus para carregar a lista da regi�o central respetiva
 * 			Os bot�es DML, verificam o valor ativo no RadioItemMenu e atuam
 * 			em conformidade.
 * 			Qualquer das a��es DML abre uma modal para a� se atuar.
 * 
 * 		Regi�o Central, TableView com os dados da tabela repetiva.
 * ---------------------------------------------------------------------
 * Passo1 - Come�ar por criar o rootLayou do tipo borderPane e 
 * 			escrever o linhas de coment�rio para delimitar as regi�es.
 * Passo2 - Criar todo o Layout das 3 regi�es a usar: top, esq e centro.
 * Passo3 - Criar o menu na regi�o top
 * 			Criar os bot�es na regi�o esquerda.
 * 			Criar a��es de alert para testar funcionamento.
 * Passo4 - Criar as tableView 
 * 			Criar 3 tableViews para o layout da regi�o central: ex17
 * 			Criar 3 m�todos para carregar as tableVies (ex17)
 * 			Criar 3 classes: Aluno, Professor e Funcion�rio
 * 			Criar 3 Tabelas: Aluno, Professor e Funcion�rio
 */

public class MainBackup extends Application {
	
	static String btnClicado = "";					// Recebe o nome do bot�o clicado
	 
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			BorderPane layoutRoot = new BorderPane();			// Layout Principal: BorderPAne - 5 Regi�es
			
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
			MenuItem menuItemFileOpcao3 = new MenuItem("Op��o3");
			
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
			
			// Listeners para a��o do objeto
			menuItemEntidadeAlunos.setOnAction(e->{
				if(menuItemEntidadeAlunos.isSelected()){
					Utils.alertBox("Menu Entidades","TODO - TavleView Alunos");
					//TODO: CARREGA TABLEVIEW NA REGI�O CENTRAL COM DADOS DA TABELA ALUNOS
				}
			});
			
			menuItemEntidadeProfs.setOnAction(e->{
				if(menuItemEntidadeProfs.isSelected()){
					Utils.alertBox("Menu Entidades","TODO - TavleView Professores");
					//TODO: CARREGA TABLEVIEW NA REGI�O CENTRAL COM DADOS DA TABELA PROFS
				}
			});

			menuItemEntidadeFunc.setOnAction(e->{
				if(menuItemEntidadeFunc.isSelected()){
					Utils.alertBox("Menu Entidades","TODO - TavleView Funcion�rios");
					//TODO: CARREGA TABLEVIEW NA REGI�O CENTRAL COM DADOS DA TABELA FUNCS
				}
			});

			// Adicionar os MenuItens ao menuFile.
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
			 * 				selecionada no menu. 
			 * 				Se o bot�o for Inserir, carrega na Regi�o centro o layout do ex10, com 
			 * 				textfieds, para inserir dados, seguidos de 2 bot�es: OK e CANCELAR.
			 * 				Se o bot�o for Alterar ou Eliminar, executam 2 a��es:
			 * 				- Atualizam uma vari�vel onde registam o seu nome: Alterar ou Apagar
			 * 				- Carregam o mesmo Layout VBOX do ex17c na Regi�o centro com 2 objetos: 
			 * 				uma TableView, com os dados da tabela da entidade selecionada e 2 bot�es: 
			 * 				OK e CANCELAR. A a��o de executar o comando SQL DML pertence ao bot�o OK
			 * 				definido no texto da Regi�o Centro. Cancelar carrega a lista inicial.
			 *////////////////////////////////////////////////////////////////////////////////////////

			VBox layoutLeft = new VBox(20);									// Prepara��o da VBox para a regi�o esq do BorderPane
			layoutLeft.setBackground(null);
			layoutLeft.setPadding(new Insets(15, 12, 15, 12));				// Espa�os para os limites
			layoutLeft.setSpacing(10);										// espa�os entre as c�lulas
			layoutLeft.setStyle("-fx-background-color: #336699;");			// Cor de fundo

			// Bot�es - Executam os comandos DML, conforme a op��o ativada nomenu Entidades
			Button btnLeftLayoutInserir = new Button("Inserir");			
			Button btnLeftLayoutAlterar = new Button("Alterar");
			Button btnLeftLayoutApagar = new Button("Eliminar");
			
			//Tamanhos normalizados ?????????????????????????       PESQUISAR ???
			btnLeftLayoutInserir.prefWidth(175);
			btnLeftLayoutAlterar.minWidth(95);
			btnLeftLayoutApagar.maxWidth(105);
			
			// Listeners
			/*TODO: verifica a Entidade ativa no menu Entidade e ativa o layout de inserir dados
			 * no Layout central. Ali haver� TextFields e 2 bot�es, Cancelar e Inserir que far�
			 * a respetiva valida��o dos dados introduzicos e a inser��o na BD
			 */
			btnLeftLayoutInserir.setOnAction(e->{
				
				//TODO: efetua a valida��o dos textfields e executa a DML
				if(menuItemEntidadeAlunos.isSelected()){
					Utils.alertBox("Bot�o Inserir","op Alunos Est� ativa");
				}
				if(menuItemEntidadeProfs.isSelected()){
					Utils.alertBox("Bot�o Inserir","op professores est� ativa");
				}
				if(menuItemEntidadeFunc.isSelected()){
					Utils.alertBox("Bot�o Inserir","op Funcion�rios est� ativa");
				}
				
			});
			btnLeftLayoutAlterar.setOnAction(e->{
				//TODO: efetua a valida��o dos textfields e executa a DML
				if(menuItemEntidadeAlunos.isSelected()){
					Utils.alertBox("Bot�o Alterar","op Alunos Est� ativa");
				}
				if(menuItemEntidadeProfs.isSelected()){
					Utils.alertBox("Bot�o Alterar","op professores est� ativa");
				}
				if(menuItemEntidadeFunc.isSelected()){
					Utils.alertBox("Bot�o Alterar","op Funcion�rios est� ativa");
				}
			});
			btnLeftLayoutApagar.setOnAction(e->{
				//TODO: efetua a valida��o dos textfields e executa a DML
				if(menuItemEntidadeAlunos.isSelected()){
					Utils.alertBox("Bot�o Apagar","op Alunos Est� ativa");
				}
				if(menuItemEntidadeProfs.isSelected()){
					Utils.alertBox("Bot�o Apagar","op professores est� ativa");
				}
				if(menuItemEntidadeFunc.isSelected()){
					Utils.alertBox("Bot�o Apagar","op Funcion�rios est� ativa");
				}
			});

			// Adicionar os Bot�es ao Layout da regi�o esquerda.
			layoutLeft.getChildren().addAll(
					btnLeftLayoutInserir,
					btnLeftLayoutAlterar,
					btnLeftLayoutApagar);
			
			// associar o menu � regi�o TOP do rootLayout
			layoutRoot.setLeft(layoutLeft);
			
			
			/*//////////////////////////////////////////////////////////////////////////////////////////
			* Regi�o Centro - 	Por defeito carrega o layout do ex17a com a lista simples (TableView) 
			* 					da Entidades que estiver selecionada no menu.
			* 					Mas por a��o de um dos bot�es da Regi�o esquerda, Inserir, alterar 
			* 					ou eliminar, carrega um de dois layouts: 
			* 					- Inserir, verifica qual a entidade selecionada no menu e carrega uma 
			* 						GridPane (ex10 alterada) com os Textfields correspondentes, para 
			* 						receber os dados e inserir na respetiva tabela. Ter� de haver 3 
			* 						destes Layouts, um para cada Entidades, porque ter�o for�osamente 
			* 						atributos diferentes. 
			* 						No final destes Layouts estar�o 2 bot�es: OK e CANCELAR.
			* 					- Alterar e Apagar verificam tamb�m qual a entidade selecionada no menu
			* 						mas carregam ambas 1 de 3 Layouts com VBox, com base no ex17c, 
			* 						devidamente adaptados �s respetivas Entidades e onde para al�m da 
			* 						TableView, existem 2 bot�es: OK e CANCELAR. 
			* 					Qualquer destes 3 bot�es: Inserir, Alterar ou Apagar tem que tamb�m fazer
			* 					uma terceira a��o: ativam uma vari�vel String "btnClicado" que guarde at� 
			* 					ao fim do processo, o nome do bot�o clicado.  Isto � necess�rio porque
			* 					vamos usar os mesmos Layouts para situa��es diferentes:  
			* 
			* 					INSERIR	-> LAYOUT GridPane com textFields e bot�es (OK e CANCELAR) 
			* 							-> Carimba a vari�vel "btnClicado" com "Inserir"
			* 							OK -> SE btnClicado == "Inserir" 
			* 									- executa o insert na BD
			* 									- btnClicado == "" -> limpa a vari�vel "btnClicado"
			* 							CANCELAR -> carrega a lista simples da entidade selecionada
			* 										limpa a vari�vel "btnClicado"
			* 					ALTERAR -> LAYOUT VBOX com lista da entidade selecionada + bot�es. 
			* 							-> Carimba a vari�vel "btnClicado" com "Alterar"
			* 							OK -> Verifica se h� algum item selecionado: 
			* 									Se N�o -> AlertBox(bl�bl�)
			* 									Se Sim -> LAYOUT dos textFields referente � entidade
			* 											> OK -> SE btnClicado == "Alterar" 
			* 												- executa o update na BD
			* 												- btnClicado == "" -> limpa "btnClicado"
			* 											> Cancelar - carrega a lista anterior
			* 							CANCELAR -> carrega a lista simples da entidade selecionada
			* 					APAGAR -> Igual a Alterar, mas a a��o do 2� bot�o OK executa diretamente
			* 								o delete da tabela da entidade selecionada.
			* 
			* 					Notar ainda que as a��es dos bot�es Alterar e Apagar, obrigam a que 
			* 					exista uma pr�-sele��o de um dos itens da TableView, caso contr�rio, 
			* 					como saber qual o item a alterar ou eliminar?
			* 
			* 					Notar tamb�m que, quer a Inser��o de dados, quer a altera��o t�m que
			* 					executar a valida��o de dados, com base no ex10, caso contr�rio 
			* 					incorre-se em exce��es.
			* 
			* 					CANCELAR - Carrega simplesmente o layout da lista simples (sem bot�es)
			*///////////////////////////////////////////////////////////////////////////////////////////
		 
			// TableViews com Lista simples de Alunos, Profs e Func., sem bot�es
			// layout
			
			TableView<Aluno> tableViewAlunos = new TableView<>();	// Cria��o de tabela 
			
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
			/* Cada coluna �  definida com um PAR de dados:
			 * 	- 1� � o tipo de dados a usar na lista: Aluno
			 * 	- 2� � o tipo de dados a usar na coluna: String para o nome 
			 * O construtor da classe TableColumn recebe o nome da coluna
			*/
			colunaNome.setMinWidth(200);	// largura em pixeis da coluna
			colunaNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
			/*Nome do atributo, na Lista, onde vai ler os dados */
			
			// Associar as colunas � tabela
			tableViewAlunos.getColumns().addAll(colunaNProc, colunaNumeroTurma, colunaNome);
			
			// Carregar a lista com dados
			tableViewAlunos.setItems( carregaListaAlunos() );		// Carrega Alunos a partir do m�todo
			
			//TODO: falta construir Classes Professor e Funcionario
			//TODO: falta construir m�todo de carregamento da tableView de Professores e Funcion�rios
			
			
			
			
			// layout para conter a tableView selecionada no menu
			StackPane layoutCentralTableView = new StackPane();		
			if(menuItemEntidadeAlunos.isSelected()){layoutCentralTableView.getChildren().add(tableViewAlunos);};
			//if(menuItemEntidadeProfs.isSelected()){	layoutCentralTableView.getChildren().add(tableViewProfs);};
			//if(menuItemEntidadeFunc.isSelected()){	layoutCentralTableView.getChildren().add(tableViewFuncs);};
			//TODO: ativar apenas depois das Classes, TableViews e m�todos de carregamento estarem construidas.

			/* Bot�es OK e CANCELAR
			 *  S� s�o Mostrados se a string btnClicado n�o for "vazia", caso contr�rio:
			 *  Se for vazia n�o executa esta 2� parte, carrega apenas a lista simples, sem bot�es
			 *  Se "Alterar" ou Anular:
			 *  - btnCancelar limpa a string btnClicado e executa este m�todo para a lista simples
			 *  - btnOk -> verifica se h� algum item selecionado na lista
			 *  		-> Se n�o, alert()
			 *  		-> Se Sim, Testa outra vez a string btnClicado
			 *  			-> se "Alterar" -> Verifica qual a Entidade Selecionada no menu
			 *  							-> Obtem os dados da respetiva Tabela
			 *  							-> Carrega o Layout com os TextFields desta Entidade
			 *  							-> Preenche os TextFields com os dados recolhidos da tabela
			 *  							-> O utilizador poder� alterar a� os dados e OK ou CANCELAR
			 *  							-> CANCELAR -> carrega o layout anterior: lista com bot�es
			 *  							-> OK 	-> executa um update na tabela selecionada
			 *  									-> limpa a vari�vel btnClicado 
			 *  									-> carrega o layout com a lista simples da entidade.
			 *				-> Se "Anular"	-> CANCELAR -> carrega o layout anterior: lista com bot�es
			 *  							-> OK 	-> executa um delete na tabela selecionada
			 *  									-> limpa a vari�vel btnClicado 
			 *  									-> carrega o layout com a lista simples da entidade.   
			 */
			
			
			
			// Decis�o se carrega no layout a lista simpes ou com bot�es
			
			if(btnClicado.isEmpty()){	// carrega na zona central apenas a TableView, para a lista simples
				layoutRoot.setCenter(layoutCentralTableView);
			}
			else {						// carrega na zona central a TableView com bot�es
				
			
				HBox layoutHBtns = new HBox(40);		// Cria��o do layout para encaixar bot�es
				VBox layoutVBtns = new VBox(20);		// Cria��o do layout para encaixar lista e bot�es
			
				
				// Bot�o CANCELAR carrega lista simples
				Button btnCancelar = new Button("Cancelar");
				btnCancelar.setOnAction(e->{
					btnClicado = "";								// btnClicado passa a vazio
					
					// Carrega no Layout, a tableView simples da entidade selecionada no menu
					if(menuItemEntidadeAlunos.isSelected()){layoutCentralTableView.getChildren().add(tableViewAlunos);};
					//if(menuItemEntidadeProfs.isSelected()){	layoutCentralTableView.getChildren().add(tableViewProfs);};
					//if(menuItemEntidadeFunc.isSelected()){	layoutCentralTableView.getChildren().add(tableViewFuncs);};
					//TODO: ativar apenas depois das Classes, TableViews e m�todos de carregamento estarem construidas.

					// Carrega na regi�o central, o Layout anterior
					layoutRoot.setCenter(layoutCentralTableView);
				});
				
				
				
				// Bot�o OK - Verifica se btnClicado � Inserir, Alterar ou Apagar.
				
				/*isto n�o est� bem.
				 * Btn Inserir: Carrega layout com TextFields: ok -> insere
				 * Btn Alterar: Carrega layout com lista + bot�es: ok-> carrega layout com TextFields - update
				 * Btn Anular: Carrega layout com lista + bot�es: ok-> delete o item selecionado
				 * 
				 * Tirar bot�es do layout esquerdo
				 * A lista tem sempre bot�es
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
	 * M�todos para carregamento das Listas de alimenta��o das TableViews. 
	 * S�o executados pelo bot�o EDITAR, eliminar, alterar ou eliminar de cada entidade
	 * Popular uma ObservableList com os dados da BD e desvolvemr � TableView
	 *************************************************************************************************/
    private ObservableList<Aluno> carregaListaAlunos(){
    	
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
}